package com.cqecom.cms.workflow;

import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentFilter;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.JavaProcessExt;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;

/**
 * Workflow process step which activates a page via specified agent.
 * Name of the agent should be set process arguments.
 */
@Component
@Service
public class RSActivatePageProcess implements JavaProcessExt {

    @Reference
    private SlingRepository repository;

    @Reference
    private Replicator replicator;

    private final Logger log = LoggerFactory.getLogger(RSActivatePageProcess.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, String[] strings)
            throws Exception {
        log.info("executing workflow");

        WorkflowData workflowData = workItem.getWorkflowData();
        String payload = workflowData.getPayload().toString();
        log.info("payload " + payload);

        //find agent id
        if (strings.length == 0) {
            throw new Exception("Agent name is not set in arguments for workflow process step");
        }

        final String agentId = StringUtils.trim(strings[0]);
        log.info("agent id " + agentId);

        //filter agent
        ReplicationOptions opts = new ReplicationOptions();
        opts.setFilter(new AgentFilter() {
            public boolean isIncluded(final Agent agent) {
                return agentId.equals(agent.getId());
            }
        });

        //activate page
        Session session = null;
        try {
            session = repository.loginAdministrative(null);
            replicator.replicate(session, ReplicationActionType.ACTIVATE, payload, opts);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession) throws Exception {
        execute(workItem, workflowSession, new String[]{});
    }
}
