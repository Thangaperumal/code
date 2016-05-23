package com.cqecom.cms.components.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LanguageSettingsReader {
	private static final String SETTINGS_PATH = "content/global/languages/";
	private static final Logger logger = LoggerFactory.getLogger(LanguageSettingsReader.class);
	private static final String CODE="code";
	private static final String LONG_NAME="longName";
    private static final String IL_LANGUAGE_NAME="ilLanguageName";
    private static final String NO_IL_LANGUAGE_NAME="noIlLanguageName";
	private static final String NAME="name";
	private static final String VERSION="version";
        private static final String LEVELS="levels";
	private static final String AXRLEVELS="axrLevels";
	private static final String OSUBLEVELS="osubLevels";
	private static final String OTBLEVELS="otbLevels";
	public final static String rootPath="/jcr:root/";
	/**
	 * @param session
	 * @param currentPath
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	public LanguageSettings findLanguageSettings(Session session,String currentPath,String languageCode) throws Exception
	{
		String settingsPath = SETTINGS_PATH;
		String currentSite = "";
		Node langNodes = session.getNode("/"+SETTINGS_PATH);
		NodeIterator iterator = langNodes.getNodes();
		String pathArray[] = currentPath.split("/");
		ArrayList<String> sites = new ArrayList<String>();
		while (iterator.hasNext()) {
			String site = iterator.nextNode().getName();
			sites.add(site);			
		}
		for (int i = 0; i <= pathArray.length; i++) {
			if (sites.contains(pathArray[i])){
				currentSite = pathArray[i];
				break;
			}	
		}			
		settingsPath += currentSite;
		String queryString = "";
		//old code
		/*if(currentPath.contains("cqecomcom"))
		{
			if(currentPath.contains("/en"))
			{
				settingsPath+="com/en";
			}
			else
				if(currentPath.contains("/sp"))
				{
					settingsPath+="com/sp";
				}
		}
		else
		if(currentPath.contains("cqecomuk"))
		{
			if(currentPath.contains("/uk") )
			{
				settingsPath+="uk/en";
			}
			else
				if(currentPath.contains("/sp"))
				{
					settingsPath+="uk/sp";
				}
		}
		else
			if(currentPath.contains("cqecomde"))
			{
				if(currentPath.contains("/de"))
				{
					settingsPath+="de/de";

				}
				else
					if(currentPath.contains("/sp"))
					{
						settingsPath+="de/sp";
					}
			}
			else
				if(currentPath.contains("cqecomit"))
				{
					if(currentPath.contains("/it"))
					{
						settingsPath+="it/it";

					}
					
				}
	 else
		if(currentPath.contains("cqecomkr"))
		{
			if(currentPath.contains("/kr"))
			{
				settingsPath+="kr/kr";

			}
			
		}
		*/
		queryString = rootPath + settingsPath + "//*[@code='"+ languageCode.toLowerCase()+"']";
		LanguageSettings languageSettings=getNode(queryString, session);
		if(languageSettings==null)
		{
			throw new Exception("No language settings for--"+languageCode+" Query is "+queryString);

		}
		return languageSettings;


	}

	public LanguageSettings getNode(String queryString,Session session) throws Exception
	{
		List<LanguageSettings> nodes = new ArrayList<LanguageSettings>();
		QueryManager queryManager = session.getWorkspace().getQueryManager();
		Query query = queryManager.createQuery(queryString, Query.XPATH);
		QueryResult result = query.execute();
		NodeIterator iterator = result.getNodes();
		while (iterator.hasNext()) 
		{
			Node node = iterator.nextNode();
			LanguageSettings languageSettings=new LanguageSettings();
			languageSettings.setCode(node.getProperty(CODE).getString());
			languageSettings.setLongName(node.getProperty(LONG_NAME).getString());
			languageSettings.setName(node.getProperty(NAME).getString());
			languageSettings.setIlLanguageName(node.hasProperty(IL_LANGUAGE_NAME) ?
                    node.getProperty(IL_LANGUAGE_NAME).getString() : "");
			languageSettings.setNoIlLanguageName(node.hasProperty(NO_IL_LANGUAGE_NAME) ?
                    node.getProperty(NO_IL_LANGUAGE_NAME).getString() : "");		
			languageSettings.setVersion(node.getProperty(VERSION).getLong());
                        languageSettings.setLevels(node.getProperty(LEVELS).getString());
			if (node.hasProperty(AXRLEVELS)){
				languageSettings.setAXRLevels(node.getProperty(AXRLEVELS).getString());
			}
			if (node.hasProperty(OSUBLEVELS)){
                                languageSettings.setOSUBLevels(node.getProperty(OSUBLEVELS).getString());
                        }
			if (node.hasProperty(OTBLEVELS)){
                                languageSettings.setOTBLevels(node.getProperty(OTBLEVELS).getString());
                        }
			nodes.add(languageSettings);
		}


		return nodes.get(0);
	}

    public static List<LanguageSettings> getLanguageSettingsList(String globalSite, ResourceResolver resourceResolver) {
       
        Resource langListResource = resourceResolver.getResource(getLanguagePath(globalSite));
        if (langListResource == null) {
            logger.error("Language settings not found for langName: " + globalSite);
            return null;
        }

        List<LanguageSettings> languageSettingsList = new ArrayList<LanguageSettings>();
        for (Iterator<Resource> i = resourceResolver.listChildren(langListResource); i.hasNext(); ) {
            LanguageSettings languageSettings = i.next().adaptTo(LanguageSettings.class);
            languageSettingsList.add(languageSettings);
        }

        return languageSettingsList;
    }

    public static String getLanguagePath(String globalSite) {
        return "/" + SETTINGS_PATH + globalSite;
    }
}



