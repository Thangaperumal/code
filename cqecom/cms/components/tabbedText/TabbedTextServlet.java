package com.cqecom.cms.components.tabbedText;

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
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsTabbedText"),
        @Property(name = "sling.servlet.extensions", value = "html")
})
public class TabbedTextServlet extends AbstractMvcServlet<TabbedText>{

    @Override
    protected TabbedText loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Resource resource = request.getResource();
        return resource.adaptTo(TabbedText.class);
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response, TabbedText valueObject,
                                 Map<String, Object> model) {
        return "TabbedText";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsTabbedText/views";
    }

    @Override
    protected String getValueObjectName() {
        return "tabbedText";
    }

    @Override
    protected boolean isConfigured(SlingHttpServletRequest request, SlingHttpServletResponse response, TabbedText tabbedText) {
        return (tabbedText.getHeaderText() != null)
                || (tabbedText.getDescription() != null
                || !tabbedText.getTabs().isEmpty());
    }
}
