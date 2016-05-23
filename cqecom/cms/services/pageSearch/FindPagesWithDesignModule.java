package com.cqecom.cms.services.pageSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindPagesWithDesignModule {

    public static final Logger LOG = LoggerFactory.getLogger(FindPagesWithDesignModule.class);

    private String pathToPages;

    public FindPagesWithDesignModule(String pathToPages) {
        if (pathToPages == null) {
            throw new IllegalArgumentException("Path to pages cannot be null");
        }
        pathToPages = pathToPages.trim();
        if (pathToPages.endsWith("/")) {
            pathToPages = StringUtils.chop(pathToPages);
        }
        this.pathToPages = pathToPages;
        LOG.debug("path to pages {}", pathToPages);
    }

    public List<String> search(Session session) throws RepositoryException {
        String condition = "/jcr:content//*[@sling:resourceType='cqecom/components/content/rsDesignModule']";
        StringBuilder builder = new StringBuilder("/jcr:root");
        builder.append(pathToPages)
                .append("//element(*,cq:Page)")
                .append(condition);
        String queryString = builder.toString();
        LOG.debug(queryString);
        QueryManager queryManager = session.getWorkspace().getQueryManager();
        Query query = queryManager.createQuery(queryString, Query.XPATH);
        QueryResult result = query.execute();
        NodeIterator iterator = result.getNodes();

        //find parent page for each module nodes, cannot do it in one XPath query because of JCR XPath limitations
        HashSet<String> paths = new HashSet<String>();
        while (iterator.hasNext()) {
            Node moduleNode = iterator.nextNode();
            Node parentNode = findParentPageNode(moduleNode);
            if (parentNode != null) {
                paths.add(parentNode.getPath());
                LOG.debug("found page {}", parentNode.getPath());
            }
        }

        //check if base page has rsDesign modules
        StringBuilder basePageBuilder = new StringBuilder("/jcr:root");
        basePageBuilder.append(pathToPages)
                .append(condition);
        LOG.debug(basePageBuilder.toString());
        query = queryManager.createQuery(basePageBuilder.toString(), Query.XPATH);
        NodeIterator basePageIterator = query.execute().getNodes();
        if (basePageIterator.hasNext()) {
            paths.add(pathToPages);
        }

        LOG.info("found {} pages", paths.size());
        return new ArrayList<String>(paths);
    }

    private Node findParentPageNode(Node childNode) throws RepositoryException {
        Node parent = childNode.getParent();
        if (!parent.getPrimaryNodeType().getName().equals("cq:Page")) {
            if (parent.getDepth() > 0) {
                parent = findParentPageNode(parent);
            } else {
                parent = null;
            }
        }
        return parent;
    }
}
