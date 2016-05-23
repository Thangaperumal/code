package com.cqecom.cms.components.htmltext;

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
                value = {"cqecom/components/content/rsHtmlText", "cqecom/components/content/rsSharedModule"}),
        @Property(name = "sling.servlet.extensions", value = "html")
})
public class HtmlTextServlet extends AbstractMvcServlet<HtmlText> {

    private static final long serialVersionUID = 1L;

    @Override
    protected HtmlText loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        HtmlText text = request.getResource().adaptTo(HtmlText.class);
        return text;
    }

    @Override
    protected String getValueObjectName() {
        return "htmlText";
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
                                 HtmlText htmlText, Map<String, Object> model) {
        return "rsHtmlText";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsHtmlText/views";
    }

    @Override
    protected boolean isConfigured(SlingHttpServletRequest request, SlingHttpServletResponse response,
                                   HtmlText text) {
        if ((text.getText() == null) && (WCMMode.fromRequest(request) == WCMMode.EDIT)) {
            return false;
        }
        return true;
    }

}
