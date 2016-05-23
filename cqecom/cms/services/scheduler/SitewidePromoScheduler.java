package com.cqecom.cms.services.scheduler;

import java.io.Serializable;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.services.pageSearch.PageSearchService;
import com.cqecom.cms.services.pageSearch.settings.SiteWidePromoSettingsException;
import com.cqecom.cms.services.pageSearch.settings.SitewidePromoSettings;
import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentFilter;
import com.day.cq.replication.AgentNotFoundException;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;

@Component(immediate = true, label = "Sitewide promo scheduler", description = "Sitewide promo action scheduler", metatype = true)
@Service(SitewidePromoScheduler.class)
@Properties({ @Property(name = "service.description", value = "RS SitewidePromo Scheduler"),
        @Property(name = "service.vendor", value = "CqEcom") })
public class SitewidePromoScheduler implements Runnable {

    public static final String SITEWIDEPROMO_ID = "cq5.services.scheduler.sitewidepromo";

    @Property(value = SitewidePromoScheduler.DEFAULT_CRON_EXPRESSION)
    public static final String PROP_EXPR = "cq5.services.scheduler.sitewidepromo.cron.expression";

    @Property(value = SitewidePromoScheduler.DEFAULT_RUN_CONDITION, description = "should the dispatcher be run")
    public static final String PROP_COND = "cq5.services.scheduler.sitewidepromo.run.condition";

    public static final String DEFAULT_CRON_EXPRESSION = "0 0/1 * * * ?";

    public static final String DEFAULT_RUN_CONDITION = "true";

    private final Logger log = LoggerFactory.getLogger(SitewidePromoScheduler.class);

    @Reference
    private PageSearchService pageSearchService;

    @Reference
    private Replicator replicator;

    @Reference
    private SlingRepository repository;

    @Reference
    private Scheduler scheduler;

    private boolean runAgent = true;

    private Session adminSession = null;

    private Map<SitewidePromoSettings, PromoData> pathMap = new HashMap<SitewidePromoSettings, PromoData>();

    @Override
    public void run() {
        log.info("SitewidePromoScheduler started");

        ReplicationOptions opts = new ReplicationOptions();
        opts.setFilter(new AgentFilter() {
            public boolean isIncluded(final Agent agent) {
                return agent.isCacheInvalidator() && agent.isEnabled() && agent.isValid();
            }
        });

        try {
            String activePromoPagePath = "";

            Map<SitewidePromoSettings, List<String>> sitesPromoPages = pageSearchService
                    .findActivePromoPages(getRepositorySession());
            if (MapUtils.isNotEmpty(sitesPromoPages)) {
                for (Entry<SitewidePromoSettings, List<String>> mapEntry : sitesPromoPages.entrySet()) {
                    SitewidePromoSettings settings = mapEntry.getKey();
                    List<String> sitePromoPages = mapEntry.getValue();

                    if (CollectionUtils.isNotEmpty(sitePromoPages)) {
                        activePromoPagePath = sitePromoPages.get(0);
                    }

                    flushWebPages(settings, activePromoPagePath, opts);
                }
            }
        } catch (RepositoryException e) {
            log.error("Error at searching for pages", e);
        } catch (ReplicationException e) {
            log.error("Error at invalidating cache", e);
        } catch (SiteWidePromoSettingsException e) {
            log.error("Error at finding active promo pages", e);
        } finally {
            resetRepositorySession();
        }

        log.info("SitewidePromoScheduler finished");
    }

    private void flushWebPages(SitewidePromoSettings settings, String activePromoPagePath, ReplicationOptions opts)
            throws RepositoryException, ReplicationException {

        String currentPath = "";
        Date lastModified = null;

        if (pathMap.containsKey(settings)) {
            PromoData promoData = pathMap.get(settings);

            currentPath = promoData.getPromoPagePath();
            lastModified = promoData.getLastModified();
        }

        Date promoLastModified = null;

        if (StringUtils.isNotBlank(activePromoPagePath)) {

            Node node = getRepositorySession().getNode(activePromoPagePath + "/jcr:content");
            if (node.hasProperty("cq:lastModified")) {
                promoLastModified = node.getProperty("cq:lastModified").getDate().getTime();
            }
        }

        if (StringUtils.isNotBlank(currentPath)) {

            if (StringUtils.isNotBlank(activePromoPagePath)) {
                if (!currentPath.equals(activePromoPagePath) || !(lastModified.equals(promoLastModified))) {
                    List<String> pagesWithModules = pageSearchService.findPagesWithDesignModules(
                            settings.getSitePagesPath(), getRepositorySession());

                    PromoData newData = new PromoData();
                    newData.setLastModified(promoLastModified);
                    newData.setPromoPagePath(activePromoPagePath);

                    pathMap.put(settings, newData);

                    runFlushAgent(pagesWithModules, opts);
                }
            } else {
                List<String> pagesWithModules = pageSearchService.findPagesWithDesignModules(
                        settings.getSitePagesPath(), getRepositorySession());

                pathMap.remove(settings);

                runFlushAgent(pagesWithModules, opts);

            }
        } else if (StringUtils.isNotBlank(activePromoPagePath)) {
            List<String> pagesWithModules = pageSearchService.findPagesWithDesignModules(settings.getSitePagesPath(),
                    getRepositorySession());


            PromoData newData = new PromoData();
            newData.setLastModified(promoLastModified);
            newData.setPromoPagePath(activePromoPagePath);

            pathMap.put(settings, newData);

            runFlushAgent(pagesWithModules, opts);

        }
    }

    private void runFlushAgent(List<String> pagePaths, ReplicationOptions opts) throws ReplicationException,
            RepositoryException {
        try {
            if (runAgent) {
                for (String path : pagePaths) {
                    log.debug("run flush agent for {}", path);
                    replicator.replicate(getRepositorySession(), ReplicationActionType.ACTIVATE, path, opts);
                }
            }
        } catch (AgentNotFoundException e) {
            log.error("Error at invoking flush agent", e);
        }
    }

    private Session getRepositorySession() throws RepositoryException {
        if (adminSession != null && !adminSession.isLive()) {
            adminSession.logout();
            adminSession = null;
        }
        if (adminSession == null) {
            adminSession = repository.loginAdministrative(null);
        }

        return adminSession;
    }

    private void resetRepositorySession() {
        if (adminSession != null) {
            adminSession.logout();
            adminSession = null;
        }
    }

    protected void activate(ComponentContext ctx) throws Exception {

        @SuppressWarnings("unchecked")
        Dictionary<String, Object> properties = ctx.getProperties();

        Object runAgentObj = properties.get(PROP_COND);
        if (runAgentObj != null) {
            String runAgentStr = runAgentObj.toString();
            runAgent = Boolean.parseBoolean(runAgentStr);
        }

        Object cronExpressionObj = properties.get(PROP_EXPR);
        if (cronExpressionObj != null) {
            String cronExpression = cronExpressionObj.toString();
            HashMap<String, Serializable> config = new HashMap<String, Serializable>();
            scheduler.addJob(SITEWIDEPROMO_ID, this, config, cronExpression, false);
        }
    }

    protected void deactivate(ComponentContext ctx) {
        resetRepositorySession();
    }
}
