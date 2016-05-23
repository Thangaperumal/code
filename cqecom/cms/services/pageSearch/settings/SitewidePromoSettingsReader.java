package com.cqecom.cms.services.pageSearch.settings;

import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads site's settings for sitewide promo (like timezone, path to sitewide promo pages,
 * etc) from repository.
 */
public class SitewidePromoSettingsReader {

    public static final String TIMEZONE_PROPERTY = "timezone";

    public static final String TIMEZONE_ID_PROPERTY = "timezoneId";

    public static final String SITE_PAGES_PATH_PROPERTY = "sitePagesPath";

    public static final String SITEWIDE_PROMO_PATH_PROPERTY = "sitewidePromoPagesPath";

    private static final Logger LOG = LoggerFactory.getLogger(SitewidePromoSettingsReader.class);

    private static final String SETTINGS_PATH = "/content/global/timezones";


    public List<SitewidePromoSettings> findAllSettings(Session session)
            throws RepositoryException, SiteWidePromoSettingsException {
        List<SitewidePromoSettings> allSettings = new LinkedList<SitewidePromoSettings>();
        Node configNode = session.getNode(SETTINGS_PATH);
        NodeIterator iterator = configNode.getNodes();
        while (iterator.hasNext()) {
            Node node = iterator.nextNode();
            if (!node.getName().equalsIgnoreCase("rep:policy")) {
                Node site = node;
                SitewidePromoSettings settings = createSettings(site);
                allSettings.add(settings);                
            }
        }
        return allSettings;
    }

    @SuppressWarnings("deprecation")
    public SitewidePromoSettings findSiteSettings(Session session, String siteName)
            throws RepositoryException, SiteWidePromoSettingsException {
        StringBuilder queryBuilder = new StringBuilder("/jcr:root");
        queryBuilder.append(SETTINGS_PATH);
        if (siteName != null)
            queryBuilder.append("/").append(siteName);            
        
        QueryManager queryManager = session.getWorkspace().getQueryManager();
        Query query = queryManager.createQuery(queryBuilder.toString(), Query.XPATH);
        QueryResult result = query.execute();
        NodeIterator iterator = result.getNodes();
        if (iterator.hasNext()) {
            Node siteNode = iterator.nextNode();            
            return createSettings(siteNode);
        } else {
            return null;
        }
    }

    protected SitewidePromoSettings createSettings(Node siteNode)
            throws RepositoryException, SiteWidePromoSettingsException {
        SitewidePromoSettings settings = new SitewidePromoSettings();
        settings.setName(siteNode.getName());        
        try {
        	Integer offset = null;
        	if(siteNode.hasProperty(TIMEZONE_ID_PROPERTY)) {
                offset = getTimezoneOffsetById(siteNode.getProperty(TIMEZONE_ID_PROPERTY).getString());
//            	LOG.error("createSettings: Timezone offset loaded from '" + TIMEZONE_ID_PROPERTY + "'. Effective value = " + offset + ". Site node path = " + siteNode.getPath());
        	} else {
                offset = parseTimezoneOffset(siteNode.getProperty(TIMEZONE_PROPERTY).getString());
//            	LOG.error("createSettings: Timezone offset loaded from '" + TIMEZONE_PROPERTY + "'. Effective value = " + offset + ". Site node path = " + siteNode.getPath());
        	}
            settings.setTimezoneOffset(offset);
            settings.setSitewidePromoPagesPath(siteNode.getProperty(SITEWIDE_PROMO_PATH_PROPERTY).getString());
            settings.setSitePagesPath(siteNode.getProperty(SITE_PAGES_PATH_PROPERTY).getString());
        } catch (PathNotFoundException e) {
            LOG.error("Unable to find settings property for " + settings, e);
            throw new SiteWidePromoSettingsException("Some settings for sitewide promotions for this site are missing. "
                    + "Please check them at " + SETTINGS_PATH + "/"
                    + settings.getName(), e);
        }
        LOG.debug(settings.toString());
        return settings;
    }

    protected Integer getTimezoneOffsetById(String id) {
		TimeZone tz = TimeZone.getTimeZone(id);
		return tz.getOffset(System.currentTimeMillis()) / (1000 * 60);
    }

    /**
     * @param timezoneOffset Timezone offset in form "GMT-05:00"
     * @return offset in minutes
     * @throws SiteWidePromoSettingsException if timezone cannot be parsed
     */
    //TODO: add test
    protected Integer parseTimezoneOffset(String timezoneOffset) throws SiteWidePromoSettingsException {
        if (timezoneOffset == null) {
            throw new IllegalArgumentException("Timezone should not be null");
        }
        timezoneOffset = timezoneOffset.trim();
        timezoneOffset = timezoneOffset.toUpperCase();
        timezoneOffset = StringUtils.substringAfter(timezoneOffset, "GMT");
        timezoneOffset = timezoneOffset.trim();
        String[] splitted = timezoneOffset.split(":");
        if (splitted.length != 2) {
            throw new SiteWidePromoSettingsException("Incorrect timezone offset format: " + timezoneOffset);
        }
        String hoursString = splitted[0];
        String minString = splitted[1];
        if ((hoursString.length() != 3) || (minString.length() != 2)) {
            throw new SiteWidePromoSettingsException("Incorrect timezone offset format: " + timezoneOffset);
        }
        boolean isNegative = (hoursString.startsWith("-"));
        hoursString = hoursString.substring(1);
        Integer hours, minutes;
        try {
            hours = Integer.valueOf(hoursString);
            minutes = Integer.valueOf(minString);
        } catch (NumberFormatException e) {
            throw new SiteWidePromoSettingsException("Unable to parse timezone hours/minutes values: "
                    + timezoneOffset);
        }
        Integer offset = hours * 60 + minutes;
        if (isNegative) {
            offset *= -1;
        }
        return offset;
    }
}
