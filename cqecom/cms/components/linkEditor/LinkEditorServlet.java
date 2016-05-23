package com.cqecom.cms.components.linkEditor;

import com.cqecom.cms.components.AbstractMvcServlet;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;

import javax.servlet.Servlet;

import java.util.Map;

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsLinkEditor"),
        @Property(name = "sling.servlet.extensions", value = "html")
})
public class LinkEditorServlet  extends AbstractMvcServlet<LinkEditor> {

    @Override
    protected LinkEditor loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Resource resource = request.getResource();
        return resource.adaptTo(LinkEditor.class);
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response, LinkEditor valueObject, Map<String, Object> model) {
        return "rsLinkEditor";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsLinkEditor/views";
    }

    @Override
    protected String getValueObjectName() {
        return "linkEditor";
    }

    @Override
    protected boolean isConfigured(SlingHttpServletRequest request, SlingHttpServletResponse response, LinkEditor valueObject) {
        return valueObject != null && valueObject.getLinks() != null && !valueObject.getLinks().isEmpty();
    }
}
