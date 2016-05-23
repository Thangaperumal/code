package com.cqecom.cms.components.mainHeader;

import java.util.Iterator;
import java.util.Map;

import javax.jcr.query.Query;
import javax.servlet.Servlet;

import com.cqecom.cms.components.AbstractMvcServlet;
import com.cqecom.cms.components.commons.ComponentHelper;
import com.cqecom.cms.components.htmltext.HtmlText;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsMainHeader"),
        @Property(name = "sling.servlet.extensions", value = {"html"}) })
public class MainHeaderServlet extends AbstractMvcServlet<MainHeader> {

    private static final String DEFAULT_RESOURCE_ATTRIBUTE = "defaultRsMainHeader";

    @Override
    protected MainHeader loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Resource resource = (Resource)request.getAttribute(DEFAULT_RESOURCE_ATTRIBUTE);
        if (resource == null) {
            resource = request.getResource();
        }
        MainHeader mainHeader = resource.adaptTo(MainHeader.class);
        mainHeader.setLogoLink(fixLinkUrl(mainHeader.getLogoLink()));

        SharedModuleBlock utilNav = mainHeader.getUtilNav();
        if (utilNav != null) {
            HtmlText block = getHtmlText(request, response, utilNav.getSourcePage(), utilNav.getSourceModuleId());
            utilNav.setBlock(block);
        }

        SharedModuleBlock topNavigation = mainHeader.getTopNavigation();
        if (topNavigation != null) {
            HtmlText topNavigationBlock = getHtmlText(request, response, topNavigation.getSourcePage(), topNavigation.getSourceModuleId());
            topNavigation.setBlock(topNavigationBlock);
        }

        SharedModuleBlock tfn = mainHeader.getTfn();
        if (tfn != null) {
            HtmlText tfnBlock = getHtmlText(request, response, tfn.getSourcePage(), tfn.getSourceModuleId());
            tfn.setBlock(tfnBlock);
        }

        SharedModuleBlock button = mainHeader.getButton();
        if (button != null) {
            HtmlText buttonBlock = getHtmlText(request, response, button.getSourcePage(), button.getSourceModuleId());
            button.setBlock(buttonBlock);
        }

        SharedModuleBlock subNav = mainHeader.getSubNav();
        if (subNav != null) {
            HtmlText subNavBlock = getHtmlText(request, response, subNav.getSourcePage(), subNav.getSourceModuleId());
            subNav.setBlock(subNavBlock);
        }

        request.removeAttribute(DEFAULT_RESOURCE_ATTRIBUTE);
        return mainHeader;
    }

    @Override
    protected String getValueObjectName() {
        return "rsMainHeader";
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
            MainHeader valueObject, Map<String, Object> model) {
        return "rsMainHeader";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsMainHeader/views/";
    }

    @Override
    protected boolean isConfigured(SlingHttpServletRequest request, SlingHttpServletResponse response,
            MainHeader valueObject) {
        return !((MainHeader)valueObject).isEmpty();
    }

    @Override
    protected boolean isConfiguredResource(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        boolean isConfigured = super.isConfiguredResource(request, response);
        if (!isConfigured) {
            Resource resource = ComponentHelper.findDefaultResource("rsMainHeader", request);
            if (resource != null) {
                isConfigured = isConfiguredResource(resource);
                if (isConfigured) {
                    request.setAttribute(DEFAULT_RESOURCE_ATTRIBUTE, resource);
                }
            }
        }
        return isConfigured;
    }

    private String fixLinkUrl(String url) {
        if (StringUtils.isNotBlank(url) && !url.toLowerCase().endsWith(".html")) {
            return url + ".html";
        } else {
            return url;
        }
    }

    private HtmlText getHtmlText(SlingHttpServletRequest request, SlingHttpServletResponse response,
            String sourcePage, String sourceComponentId) {
        HtmlText text = null;

        if (StringUtils.isNotBlank(sourcePage)) {

            Resource componentResource = getComponentResource(request, sourcePage, sourceComponentId);
            if (componentResource != null) {
                text = componentResource.adaptTo(HtmlText.class);
            } else {
                text = new HtmlText();
                text.setText("<b style=\"color:red;\">Shared component does not exist<b>");
            }
        }

        return text;
    }

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
}
