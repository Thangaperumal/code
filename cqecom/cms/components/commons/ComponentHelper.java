package com.cqecom.cms.components.commons;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;

/**
 * Utility class for Components
 */
public class ComponentHelper {

    private static final Logger log = LoggerFactory.getLogger(ComponentHelper.class);

    private static final String GLOBAL_COMPONENTS = "/content/global/components";

    /**
     * Looks for default resource for the component.
     * Such resource contains default configuration properties for the component
     * and can be used if component is not configured.
     *
     * @return resource or null if nothing is found
     */
    public static Resource findDefaultResource(String componentName, SlingHttpServletRequest request) {
        Resource defaultComponentResource = null;
        Resource resource = request.getResource();
        GlobalSettings globalSettings = GlobalSettingsReader.getInstance().findSettings(request.getResource().adaptTo(Node.class));
        if (globalSettings == null) {
            log.error("Cannot find global settings for resource {}", resource.getPath());
        } else {
            String globalSite = globalSettings.getGlobalSite();
            StringBuilder builder = new StringBuilder();
            builder.append(GLOBAL_COMPONENTS).append("/").append(globalSite).append("/").append(componentName);
            log.info("Looking for default resource at {}", builder.toString());
            defaultComponentResource = request.getResourceResolver().getResource(builder.toString());
        }
        return defaultComponentResource;
    }
}
