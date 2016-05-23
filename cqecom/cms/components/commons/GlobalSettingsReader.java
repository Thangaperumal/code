package com.cqecom.cms.components.commons;

import java.util.HashMap;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalSettingsReader {

	// Singleton implementation --
	private static GlobalSettingsReader _instance = new GlobalSettingsReader();

	private GlobalSettingsReader() {
	}

	// 26 july 2011 fist -- changing content path -- start
	private static final String SETTINGS_PATH = "/content/global/settings/sites";
	private HashMap<String, GlobalSettings> allSettings = new HashMap<String, GlobalSettings>();
	private static final String SITE_CONTENT_PATH = "contentPath";
	private static final String FLUSH_CACHE = "flushCache";
	// 26 july 2011 fist -- changing content path -- end
	private static final String CONTENT_PATH = "/content";
	private static final String GLOBAL_APPNAME = "globalAppName";
	private static final String SITE_CODE = "siteCode";
	private static final String GLOBAL_SITE = "globalSite";
	private static final String LANGUAGE = "language";
	private static final String GLOBAL_SITE_URL = "globalSiteURL";
	private static final String GLOBAL_DESIGN_PATH = "globalDesignPath";
	private static final String GLOBAL_DOMAIN = "globalDomain";
	private static final String GLOBAL_STAGING_DOMAIN = "globalStagingDomain";
	private static final Logger logger = LoggerFactory
			.getLogger(GlobalSettingsReader.class);
	private static final String GLOBAL_CURRENCY = "globalCurrency";
	private static final String GLOBAL_SECURE_DOMAIN = "globalSecureDomain";
	private static final String GLOBAL_CART_URL = "globalCartUrl";
	private static final String PERSONAL_EDITION_V2PATH = "catalogPath_PEV2";
	private static final String PERSONAL_EDITION_V4PATH = "catalogPath_PEV4";
	private static final String PERSONAL_EDITION_V3PATH = "catalogPath_PEV3";
	private static final String PERSONAL_EDITION_V1PATH = "catalogPath_PEV1";
	private static final String HOMESCHOOL_V3PATH = "catalogPath_HSV3";
	private static final String HOMESCHOOL_V2PATH = "catalogPath_HSV2";
	private static final String SHARED_CONTENT_PATH = "sharedContentPath";
	private static final String BV_CONTENT_PATH = "bvContentPath";
	private static final String GLOBAL_CURRENCY_CODE = "globalCurrencyCode";
	private static final String GLOBAL_EDITION = "globalEdition";
	private static final String GLOBAL_CONTENT_URL = "globalContentUrl";
	private static final String GLOBAL_SITE_CATALYST_ACCOUNT_ID_DEV = "globalSiteCatalystAccountIdDev";
	private static final String GLOBAL_SITE_CATALYST_ACCOUNT_ID_PROD = "globalSiteCatalystAccountIdProd";
	private static final String GLOBAL_GOOGLE_ANALYTICS_ID = "globalGoogleAnalyticsId"; 
	private static final String GLOBAL_SITE_LANGIAGE_CODE = "globalSiteLanguageCode";

	public HashMap<String, GlobalSettings> findAllSettings(Session session)
			throws Exception {

		Node configNode = session.getNode(SETTINGS_PATH);
		NodeIterator iterator = configNode.getNodes();

		if (!flushCheck(configNode))
			if (!allSettings.isEmpty())
				return allSettings;

		while (iterator.hasNext()) {
			Node site = iterator.nextNode();
			GlobalSettings settings = createSettings(site);
			allSettings.put(site.getName(), settings);
		}
		return allSettings;
	}
	
	public GlobalSettings findSettings(Node node) {
		try {
			Session session = node.getSession();			
			String pathArray[] = node.getPath().split("/");

			return findSettings(session, pathArray);

		} catch (Exception ex) {
			logger.error("------------------------------->Global Settings cannot be retrieved!!!!"
					+ ex.getMessage());
			return null;
		}
	}

    public GlobalSettings findSettings(Session session, String[] pathArray) throws Exception {
        findAllSettings(session);

        for (int i = 0; i <= pathArray.length; i++) {
            if (allSettings.containsKey(pathArray[i]))
                return allSettings.get(pathArray[i]);
        }
        logger.error("------------------------------->Global Settings not found!!!!");
        return null;
    }
	
	private Boolean flushCheck(Node node) {
		try {
			if (node.getProperty(FLUSH_CACHE).getBoolean())
				return true;

			return false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	private GlobalSettings createSettings(Node siteNode)
			throws Exception {
		GlobalSettings settings = new GlobalSettings();

		try {
			// 27 july 2011 fist -- sites content path -- start
			settings.setSiteContentPath(siteNode.getProperty(SITE_CONTENT_PATH)
					.getString());

			String tempContentPath = "/jcr:root/"
					+ siteNode.getProperty(SITE_CONTENT_PATH).getString();
			
			// 27 july 2011 fist -- sites content path -- end

			settings.setGlobalAppName(siteNode.getProperty(GLOBAL_APPNAME)
					.getString());

			settings.setGlobalDesignPath(siteNode.getProperty(
					GLOBAL_DESIGN_PATH).getString());
			settings.setGlobalDomain(siteNode.getProperty(GLOBAL_DOMAIN)
					.getString());
			settings.setGlobalStagingDomain(siteNode.getProperty(
					GLOBAL_STAGING_DOMAIN).getString());
			settings.setGlobalSite(siteNode.getProperty(GLOBAL_SITE)
					.getString());
			settings.setGlobalSiteurl(siteNode.getProperty(GLOBAL_SITE_URL)
					.getString());
			settings.setSiteCode(siteNode.getProperty(SITE_CODE).getString());
			settings.setLanguage(siteNode.getProperty(LANGUAGE).getString());
			settings.setGlobalCurrency(siteNode.getProperty(GLOBAL_CURRENCY)
					.getString());
			settings.setGlobalSecureDomain(siteNode.getProperty(
					GLOBAL_SECURE_DOMAIN).getString());
			settings.setGlobalCartUrl(siteNode.getProperty(GLOBAL_CART_URL)
					.getString());
			// 27 july 2011 fist -- sites content path -- start
			if (siteNode.hasProperty(PERSONAL_EDITION_V1PATH)) {
				settings.setPersonalV1Path(tempContentPath
						+ siteNode.getProperty(PERSONAL_EDITION_V1PATH)
								.getString());
			}

			settings.setPersonalV2Path(tempContentPath
					+ siteNode.getProperty(PERSONAL_EDITION_V2PATH).getString());

			settings.setPersonalV3Path(tempContentPath
					+ siteNode.getProperty(PERSONAL_EDITION_V3PATH).getString());

			settings.setPersonalV4Path(tempContentPath
					+ siteNode.getProperty(PERSONAL_EDITION_V4PATH).getString());

			settings.setHomeSchoolV3Path(tempContentPath
					+ siteNode.getProperty(HOMESCHOOL_V3PATH).getString());

			settings.setHomeSchoolV2Path(tempContentPath
					+ siteNode.getProperty(HOMESCHOOL_V2PATH).getString());

			settings.setSharedContentPath(siteNode.getProperty(SITE_CONTENT_PATH).getString()
					+ siteNode.getProperty(SHARED_CONTENT_PATH).getString());

			// 27 july 2011 fist -- sites content path -- end

			settings.setBvContentPath(siteNode.getProperty(BV_CONTENT_PATH)
					.getString());

			settings.setGlobalCurrencyCode(siteNode.getProperty(
					GLOBAL_CURRENCY_CODE).getString());
			settings.setGlobalEdition(siteNode.getProperty(GLOBAL_EDITION)
					.getString());
			settings.setGlobalContentUrl(siteNode.getProperty(
					GLOBAL_CONTENT_URL).getString());
			settings.setGlobalSiteCatalystAccountIdDev(siteNode.getProperty(
					GLOBAL_SITE_CATALYST_ACCOUNT_ID_DEV).getString());
			settings.setGlobalSiteCatalystAccountIdProd(siteNode.getProperty(
					GLOBAL_SITE_CATALYST_ACCOUNT_ID_PROD).getString());
			settings.setGlobalGoogleAnalyticsId(siteNode.getProperty(
					GLOBAL_GOOGLE_ANALYTICS_ID).getString());
			settings.setGlobalSiteLanguageCode(siteNode.getProperty(
					GLOBAL_SITE_LANGIAGE_CODE).getString());

		} catch (PathNotFoundException e) {
			logger.error("Unable to find settings property for " + settings, e);
			throw new Exception(
					"Some settings for catalog Product  for this site are missing. "
							+ "Please check them at " + SETTINGS_PATH + "/"							
							+ siteNode.getName(), e);
		}
		return settings;
	}

	public static GlobalSettingsReader getInstance() {
		return _instance;
	}
}
