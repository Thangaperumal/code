package com.cqecom.cms.components.htmlcode;

import java.util.Map;

import javax.servlet.Servlet;

import com.cqecom.cms.components.AbstractMvcServlet;
import com.day.cq.wcm.api.WCMMode;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes",
                value = "cqecom/components/content/rsHtmlCode"),
        @Property(name = "sling.servlet.extensions", value = "html")
})
public class HtmlCodeServlet extends AbstractMvcServlet<HtmlCode> {

    private static final long serialVersionUID = 1L;

    @Override
    protected HtmlCode loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        HtmlCode code = request.getResource().adaptTo(HtmlCode.class);
        return code;
    }

    @Override
    protected String getValueObjectName() {
        return "htmlCode";
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
                                 HtmlCode htmlCode, Map<String, Object> model) {
        return "rsHtmlCode";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsHtmlCode/views";
    }

    @Override
    protected boolean isConfigured(SlingHttpServletRequest request, SlingHttpServletResponse response,
                                   HtmlCode code) {
        if ((code.getCode() == null) && (WCMMode.fromRequest(request) == WCMMode.EDIT)) {
            return false;
        }
        return true;
    }

}
