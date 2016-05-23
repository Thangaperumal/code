package com.cqecom.cms.services.pageSearch;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.cqecom.cms.services.pageSearch.settings.SiteWidePromoSettingsException;
import com.cqecom.cms.services.pageSearch.settings.SitewidePromoSettings;

/*
 * Searches repository for pages
 */
public interface PageSearchService {

    /*
     * Find sitewide promotion pages which are active now.
     * @return Map <promo settings, paths to pages for these settings>
     * @throws RepositoryException
     * @throws SiteWidePromoSettingsException if global settings for sites' promotions are incorrect
     */
    Map<SitewidePromoSettings, List<String>> findActivePromoPages(Session session)
        throws RepositoryException, SiteWidePromoSettingsException;

    /*
     * Find sitewide promotion pages which are active now for site and site's language
     * @return list of paths to pages
     * @throws SiteWidePromoSettingsException if global settings for sites' promotions are incorrect.
     */
    List<String> findActivePromoPages(String siteName)
        throws RepositoryException, SiteWidePromoSettingsException;

    /*
     * Find sitewide promotion pages which are active now for site and site's language using the existing
     * session object
     * @return list of paths to pages
     * @throws SiteWidePromoSettingsException if global settings for sites' promotions are incorrect.
     */
    List<String> findActivePromoPages(String siteName, Session session)
        throws RepositoryException, SiteWidePromoSettingsException;

    /*
     * Find sitewide promotion pages which are active in a given date range
     * @throws RepositoryException
     * @throws SiteWidePromoSettingsException if global settings for sites' promotions are incorrect
     */
    List<String> findActivePromoPagesInRange(String siteName, Date start, Date end)
        throws RepositoryException, SiteWidePromoSettingsException;

    /*
     * Find pages which have design modules
     * @param sitePagesPath path to folder where to search for pages
     * @return list of paths to pages
     * @throws RepositoryException
     */
    List<String> findPagesWithDesignModules(String sitePagesPath, Session session) throws RepositoryException;
}
