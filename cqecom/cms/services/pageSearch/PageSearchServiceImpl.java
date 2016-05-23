package com.cqecom.cms.services.pageSearch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.services.pageSearch.settings.SiteWidePromoSettingsException;
import com.cqecom.cms.services.pageSearch.settings.SitewidePromoSettings;
import com.cqecom.cms.services.pageSearch.settings.SitewidePromoSettingsReader;

@Component
@Service(PageSearchService.class)
public class PageSearchServiceImpl implements PageSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(PageSearchServiceImpl.class);

    @Reference
    private SlingRepository repository;

    @Override
    public Map<SitewidePromoSettings, List<String>> findActivePromoPages(Session session) throws RepositoryException,
            SiteWidePromoSettingsException {
        LOG.debug("findActivePromo pages");
        Map<SitewidePromoSettings, List<String>> activePromo = new HashMap<SitewidePromoSettings, List<String>>();

        boolean searchOnlyPublishedPages = false;
        List<SitewidePromoSettings> allSettings = findAllPromoSettings(session);
        for (SitewidePromoSettings settings : allSettings) {
            String pathToPromo = settings.getSitewidePromoPagesPath();
            Integer offset = settings.getTimezoneOffset();
            FindActivePromoPages findPromoPages =
                new FindActivePromoPages(pathToPromo, offset, searchOnlyPublishedPages);
            List<String> paths = findPromoPages.search(session);
            activePromo.put(settings, paths);
        }
        return activePromo;
    }

    @Override
    public List<String> findActivePromoPages(String siteName) throws RepositoryException,
            SiteWidePromoSettingsException {
        LOG.debug("findActivePromo pages");
        Session session = null;
        try {
            session = repository.loginAdministrative(null);
            SitewidePromoSettings settings = findSitePromoSettings(session, siteName);
            if (settings == null) {
                throw new SiteWidePromoSettingsException("Unable to find sitewide promotion settings for site "
                        + siteName);
            }
            boolean searchOnlyPublishedPages = false;
            FindActivePromoPages findPromoPages = new FindActivePromoPages(settings.getSitewidePromoPagesPath(),
                    settings.getTimezoneOffset(), searchOnlyPublishedPages);
            return findPromoPages.search(session);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    @Override
    public List<String> findActivePromoPages(String siteName, Session session)
            throws RepositoryException, SiteWidePromoSettingsException {
        LOG.debug("findActivePromo pages");
        SitewidePromoSettings settings = findSitePromoSettings(session, siteName);
        if (settings == null) {
            throw new SiteWidePromoSettingsException("Unable to find sitewide promotion settings for site "
                    + siteName);
        }
        boolean searchOnlyPublishedPages = false;
        FindActivePromoPages findPromoPages = new FindActivePromoPages(settings.getSitewidePromoPagesPath(),
                settings.getTimezoneOffset(), searchOnlyPublishedPages);
        return findPromoPages.search(session);
    }

    @Override
    public List<String> findActivePromoPagesInRange(String siteName, Date start, Date end)
            throws RepositoryException, SiteWidePromoSettingsException {
        LOG.debug("findActivePromo pages in date range");
        Session session = null;
        try {
            session = repository.loginAdministrative(null);
            SitewidePromoSettings settings = findSitePromoSettings(session, siteName);
            if (settings == null) {
                throw new SiteWidePromoSettingsException("Unable to find sitewide promotion settings for site " + siteName);
            }
            FindActivePromoPagesInRange findPromoPages = new FindActivePromoPagesInRange(settings.getSitewidePromoPagesPath(),
                    settings.getTimezoneOffset(), start, end);
            return findPromoPages.search(session);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    @Override
    public List<String> findPagesWithDesignModules(String sitePagesPath, Session session) throws RepositoryException {
        LOG.debug("findPagesWithDesignModules");
        FindPagesWithDesignModule findPagesService = new FindPagesWithDesignModule(sitePagesPath);
        return findPagesService.search(session);
    }

    private List<SitewidePromoSettings> findAllPromoSettings(Session session) throws RepositoryException,
            SiteWidePromoSettingsException {
        SitewidePromoSettingsReader settingsReader = new SitewidePromoSettingsReader();
        return settingsReader.findAllSettings(session);
    }

    private SitewidePromoSettings findSitePromoSettings(Session session, String siteName) throws RepositoryException,
            SiteWidePromoSettingsException {
        SitewidePromoSettingsReader settingsReader = new SitewidePromoSettingsReader();
        return settingsReader.findSiteSettings(session, siteName);
    }
}
