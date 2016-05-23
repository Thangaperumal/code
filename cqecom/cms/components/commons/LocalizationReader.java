package com.cqecom.cms.components.commons;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class LocalizationReader {

    private static final Logger log = LoggerFactory.getLogger(LocalizationReader.class);

    private static final String LOCALIZATION_PATH = "/content/global/localization/";
    private static final String DEFAULT_LOCALIZATION_NODE_PATH = "/content/global/localization/en_US";

    public static ValueMap findLocalizationByRequest(SlingHttpServletRequest request) {
    	 
    	Resource localizationResource = request.getResourceResolver().getResource(LOCALIZATION_PATH);
         if (localizationResource == null) {
             log.error("Language settings not found!");
             return null;
         }
         Resource localeResource = null;
         Iterator<Resource> localeIterator = request.getResourceResolver().listChildren(localizationResource);
         while(localeIterator.hasNext())
         {
         	Resource res = localeIterator.next();
 			try {
 					if(request.getResource().getPath().contains(res.adaptTo(Node.class).getName())) {
 						localeResource = res;
 						break;
 				}
 			} catch (RepositoryException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
         }
        
        //old code
        /*String currentPath = request.getResource().getPath();
        String l10nPath = LOCALIZATION_PATH + CommonUtils.getAppNameByPath(currentPath).shortAppName + "/" + CommonUtils.getLanguageCodeByPath(currentPath);
        Resource l10nResource = request.getResourceResolver().resolve(l10nPath);
		*/
        ValueMap l10nValueMap = null;
        if (localeResource != null) {
            l10nValueMap = localeResource.adaptTo(ValueMap.class);
        } else {
            log.warn("couldn't find localization node for path!");
        }

        Resource defaultI10nResource = request.getResourceResolver().resolve(DEFAULT_LOCALIZATION_NODE_PATH);
        ValueMap defaultL10nValueMap = null;
        if (defaultI10nResource != null) {
            defaultL10nValueMap = defaultI10nResource.adaptTo(ValueMap.class);
        } else {
            log.error("couldn't find default localization node for path: " + DEFAULT_LOCALIZATION_NODE_PATH);
            return null;
        }

        //use default translation if corresponding translation not existed
        HashMap<String, Object> result = new HashMap<String, Object>(defaultL10nValueMap);
        result.putAll(l10nValueMap);

        return new ValueMapDecorator(result);
    }
}
