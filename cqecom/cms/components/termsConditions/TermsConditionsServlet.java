package com.cqecom.cms.components.termsConditions;

import java.util.Map;

import javax.servlet.Servlet;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;

import com.cqecom.cms.components.AbstractMvcServlet;

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsTermsConditions"),
        @Property(name = "sling.servlet.extensions", value = "html")
})
public class TermsConditionsServlet extends AbstractMvcServlet<TermsConditions> {

    @Override
    protected TermsConditions loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Resource resource = request.getResource();
        TermsConditions terms = resource.adaptTo(TermsConditions.class);
        if (terms != null) {
            String linkText = (String) request.getAttribute("rsTermsConditions.termsLinkText");
            if (StringUtils.isNotBlank(linkText)) {
                terms.setLinkText(linkText);
            }
        }
        return terms;
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
            TermsConditions valueObject, Map<String, Object> model) {
        return "TermsConditions";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsTermsConditions/views";
    }

    @Override
    protected String getValueObjectName() {
        return "terms";
    }
}
