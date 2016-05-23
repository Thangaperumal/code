package com.cqecom.cms.components.sitewidemodule;

import java.util.Map;

import javax.servlet.Servlet;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import com.cqecom.cms.components.AbstractMvcServlet;

@Component
@Service(Servlet.class)
@Properties(value = {
    @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsSitewideModule"),
    @Property(name = "sling.servlet.extensions", value = "html")
})
public class SitewideModuleServlet extends AbstractMvcServlet<SitewideModule> {

    @Override
    protected SitewideModule loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        SitewideModule module = request.getResource().adaptTo(SitewideModule.class);
        if (module instanceof StandardModule) {
            updateStandardModule((StandardModule) module, request);
        }
        return module;
    }

    @Override
    protected String getValueObjectName() {
        return "module";
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
            SitewideModule module, Map<String, Object> model) {
        if (module instanceof CatalogStyle) {
            return "CatalogStyle";
        }
        return "Standard";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsSitewideModule/views";
    }

    private void updateStandardModule(StandardModule module, SlingHttpServletRequest request) {
        String termsLinkText = (String) request.getAttribute("rsTermsConditions.termsLinkText");
        if (termsLinkText != null && !termsLinkText.trim().isEmpty()) {
            ((StandardModule) module).setBottomLinkText(termsLinkText);
        }
        String stripStyle = (String) request.getAttribute("rsSitewideModule.stripStyle");
        if (stripStyle != null && !stripStyle.trim().isEmpty()) {
            ((StandardModule) module).setStripStyle(stripStyle);
        }
        module.setBackgroundLink(fixLinkUrl(module.getBackgroundLink()));
        module.setTitleLink(fixLinkUrl(module.getTitleLink()));
    }

    private String fixLinkUrl(String url) {
        if (StringUtils.isNotBlank(url) && !url.toLowerCase().endsWith(".html")) {
            return url + ".html";
        } else {
            return url;
        }
    }
}
