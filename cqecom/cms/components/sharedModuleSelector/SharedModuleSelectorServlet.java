package com.cqecom.cms.components.sharedModuleSelector;

import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;
import com.cqecom.cms.components.htmltext.HtmlText;
import com.cqecom.cms.components.htmltext.HtmlTextServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import javax.jcr.Node;
import javax.jcr.query.Query;
import javax.servlet.Servlet;

import java.util.Iterator;


@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsSharedModuleSelector"),
        @Property(name = "sling.servlet.extensions", value = "html")
})
public class SharedModuleSelectorServlet extends HtmlTextServlet {
    
    public static final String SOURCEPAGE_ATTRIBUTE = "sourcePageName";
    public static final String SOURCEPAGE_MODULE_ATTRIBUTE = "sourceComponentIdName";

    @Override
    protected HtmlText loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        HtmlText text = null;
        
        String sourcePageName = getSourcePageAttributeName(request);
        if (StringUtils.isBlank(sourcePageName)) {
            sourcePageName = "sourcePage";
        }
        
        String sourcePageComponentIdName = getSourceComponentIdName(request);
        
        if (StringUtils.isBlank(sourcePageComponentIdName)) {
            sourcePageComponentIdName = "sourceComponentId";
        }

        String defaultSourcePage = getDefaultSourcePage(request);

        if (!super.isConfiguredResource(request, response)) {
            if (StringUtils.isNotBlank(defaultSourcePage)) {
                text = getFirstElementFromDefaultSourcePage(request, defaultSourcePage);
            } else {
                text = getDefaultHtmlText(request);
            }
        } else {
            ValueMap values = request.getResource().adaptTo(ValueMap.class);
            String sourcePage = values.get(sourcePageName, String.class);
            String sourceComponentId = values.get(sourcePageComponentIdName, String.class);

            if (StringUtils.isNotBlank(sourcePage) || StringUtils.isNotBlank(defaultSourcePage)) {
                if (StringUtils.isBlank(sourcePage)) {
                    sourcePage = defaultSourcePage;
                }

                Resource componentResource = getComponentResource(request, sourcePage, sourceComponentId);
                if (componentResource != null) {
                    text = componentResource.adaptTo(HtmlText.class);
                } else {
                    text = new HtmlText();
                    text.setText("<b style=\"color:red;\">Shared component does not exist<b>");
                }
            } else {
                text = getDefaultHtmlText(request);
            }
        }
        return text;
    }

    @Override
    //override to skip default handling when component is not configured
    protected boolean isConfiguredResource(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        return true;
    }

    /**
     * /jcr:root/content/cqecomcom/en/sharedContent/footer/jcr:content
     * //*[@sling:resourceType='cqecom/components/content/rsSharedModule' and @moduleId='my']
     */
    @SuppressWarnings("deprecation")
    private Resource getComponentResource(SlingHttpServletRequest request, String sourcePage, String sourceComponentId) {
        StringBuilder query = new StringBuilder(256);
        query.append("/jcr:root").append(sourcePage)
            .append("//*[@sling:resourceType='cqecom/components/content/rsSharedModule'");

        if (StringUtils.isNotBlank(sourceComponentId)) {
            query.append(" and @moduleId='").append(sourceComponentId).append("'");
        }

        query.append("]");

        Iterator<Resource> iterator = request.getResourceResolver().findResources(query.toString(), Query.XPATH);
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    private HtmlText getDefaultHtmlText(SlingHttpServletRequest request) {
        HtmlText text;
        String defaultText = (String) request.getAttribute("rsSharedModuleSelector.defaultText");
        if (StringUtils.isNotBlank(defaultText)) {
            text = new HtmlText();
            text.setText(defaultText);
        } else {
            text = request.getResource().adaptTo(HtmlText.class);
        }
        return text;
    }

    private HtmlText getFirstElementFromDefaultSourcePage(SlingHttpServletRequest request, String sourcePage) {
        HtmlText text = null;
        Resource componentResource = getComponentResource(request, sourcePage, null);
        if (componentResource != null) {
            text = componentResource.adaptTo(HtmlText.class);
        } else {
            text = new HtmlText();
            text.setText("<b style=\"color:red;\">Shared component does not exist<b>");
        }

        return text;
    }

    private String getDefaultSourcePage(SlingHttpServletRequest request) {
        String defaultSourcePage = "";

        if (request.getAttribute("rsSharedModuleSelector.defaultSourcePage") != null) {
            defaultSourcePage = (String)request.getAttribute("rsSharedModuleSelector.defaultSourcePage");            
            GlobalSettings settings = GlobalSettingsReader.getInstance().findSettings(request.getResource().adaptTo(Node.class));
            StringBuilder sb = new StringBuilder("/");
            sb.append(settings.getSiteContentPath()).append("/sharedContent/").append(defaultSourcePage);
            defaultSourcePage = sb.toString();
        }

        return defaultSourcePage;
    }
    
    private String getSourcePageAttributeName(SlingHttpServletRequest request) {
        if (request.getAttribute(SOURCEPAGE_ATTRIBUTE) != null) {
            return (String)request.getAttribute(SOURCEPAGE_ATTRIBUTE);
        }
        return "";
    }
    
    private String getSourceComponentIdName(SlingHttpServletRequest request) {
        if (request.getAttribute(SOURCEPAGE_MODULE_ATTRIBUTE) != null) {
            return (String)request.getAttribute(SOURCEPAGE_MODULE_ATTRIBUTE);
        }
        return "";
    }
}
