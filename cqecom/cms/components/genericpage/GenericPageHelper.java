package com.cqecom.cms.components.genericpage;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;

import java.util.HashMap;
import java.util.Map;

public class GenericPageHelper {

    private static final String PROPERTY_NAME = "htmlText";
    private static final String SUFFIX = "/jcr:content/htmlComponent";

    public static Map<String, String> getGenericComponents(ResourceResolver resolver, String appName, String site) {
        Map<String, String> components = new HashMap<String, String>();
        String generalPath = getGeneralPath(appName, site);
        components.put("footer", getHtmlTextProperty(resolver, generalPath + "footer"));
        components.put("utility", getHtmlTextProperty(resolver, generalPath + "utility"));
        components.put("main-nav", getHtmlTextProperty(resolver, generalPath + "main-nav"));
        components.put("callout", getHtmlTextProperty(resolver, generalPath + "callout"));
        components.put("social-media", getHtmlTextProperty(resolver, generalPath + "social-media"));
        return components;
    }


    private static String getGeneralPath(String appName, String site) {
        StringBuilder path = new StringBuilder("/content/");
        path.append(appName).append("/").append(site).append("/shared-content/wrapper/");
        return path.toString();
    }


    private static String getHtmlTextProperty(ResourceResolver resolver, String path) {
        try {
            Resource rsHtmlComponent = resolver.getResource(path + SUFFIX);
            if (rsHtmlComponent != null) {
                ValueMap values = rsHtmlComponent.adaptTo(ValueMap.class);
                return values.get(PROPERTY_NAME, "");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
