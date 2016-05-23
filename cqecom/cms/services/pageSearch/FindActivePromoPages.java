package com.cqecom.cms.services.pageSearch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import com.day.cq.replication.ReplicationStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FindActivePromoPages {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";

    private final Logger log = LoggerFactory.getLogger(FindActivePromoPages.class);

    private Boolean searchOnlyPublishedPages;

    private final String sitewidePromoPagesPath;

    private final Integer timezoneOffset;

    /**
     * @param sitewidePromoPagesPath path to folder with promo pages
     * @param timezoneOffset timezone offset in minutes
     * @param searchOnlyPublishedPages search only for published (activated) pages
     */
    public FindActivePromoPages(String sitewidePromoPagesPath, Integer timezoneOffset, Boolean searchOnlyPublishedPages) {
        //remove backslash from path
        sitewidePromoPagesPath = sitewidePromoPagesPath.trim();
        if (sitewidePromoPagesPath.endsWith("/")) {
            sitewidePromoPagesPath = StringUtils.chop(sitewidePromoPagesPath);
        }
        this.sitewidePromoPagesPath = sitewidePromoPagesPath;
        this.timezoneOffset = timezoneOffset;
        this.searchOnlyPublishedPages = searchOnlyPublishedPages;
        if (log.isDebugEnabled()) {
            log.debug("search for active promo, path {} timezoneOffset {}", sitewidePromoPagesPath, timezoneOffset);
        }
    }

    /*
    * Find nodes by XPath query
    * @return list of nodes
    */
    public List<String> search(Session session) throws RepositoryException {
        String queryString = getQuery();
        log.debug(queryString);
        QueryManager queryManager = session.getWorkspace().getQueryManager();
        Query query = queryManager.createQuery(queryString, Query.XPATH);
        QueryResult result = query.execute();
        NodeIterator iterator = result.getNodes();
        List<String> nodes = new ArrayList<String>();
        while (iterator.hasNext()) {
            Node node = iterator.nextNode();
            nodes.add(node.getPath());
            log.debug("active promo: {}", node.getPath());
        }
        log.info("found {} active promo", nodes.size());
        return nodes;
    }

    public String getSitewidePromoPagesPath() {
        return sitewidePromoPagesPath;
    }

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    protected String getQuery() {
        Calendar calendar = Calendar.getInstance();
        TimeZone serverTimeZone = calendar.getTimeZone();

        //convert date/time to site timezone offset
        calendar.add(Calendar.MILLISECOND, -serverTimeZone.getOffset(calendar.getTimeInMillis()));
        calendar.add(Calendar.MINUTE, timezoneOffset);
        log.debug("site time {} ", calendar.getTime().toString());

        String dateString = DateFormatUtils.format(calendar.getTime(), DATE_FORMAT);
        StringBuilder queryBuilder = new StringBuilder(512);
        queryBuilder.append("/jcr:root").append(sitewidePromoPagesPath).append("//element(*, cq:Page)")
                .append("[jcr:content/@startdate < xs:dateTime('").append(dateString).append("') and ")
                .append("jcr:content/@enddate > xs:dateTime('").append(dateString).append("')");
        if (searchOnlyPublishedPages) {
            queryBuilder.append(" and jcr:content/@").append(ReplicationStatus.NODE_PROPERTY_LAST_REPLICATION_ACTION)
                    .append("=").append("'Activate'");
        }
        queryBuilder.append("]");
        return queryBuilder.toString();
    }
}
