package com.cqecom.cms.components.homepageBanner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.query.Query;
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
import com.cqecom.cms.components.htmltext.HtmlText;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsHomepageBanner"),
        @Property(name = "sling.servlet.extensions", value = {"html", "rsHomepageBannerView"}) })
public class HomepageBannerServlet extends AbstractMvcServlet<HomepageBanner> {

    private static final String COMPONENT_CONTEXT = "com.day.cq.wcm.componentcontext";

    @Override
    protected HomepageBanner loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Resource resource = request.getResource();
        HomepageBanner banner = resource.adaptTo(HomepageBanner.class);
        if (banner != null) {
            List<BannerImage> images = banner.getImages();
            for (BannerImage image : images) {
                BannerImageOverlay overlay = image.getImageOverlay();
                overlay.setText(getHtmlText(request, response, overlay.getContentBlockSourcePage(),
                        overlay.getContentBlockId()));
            }
        }
        return banner;
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
            HomepageBanner valueObject, Map<String, Object> model) {
        ComponentContext componentContext = (ComponentContext) request.getAttribute(COMPONENT_CONTEXT);
        Page currentPage = componentContext.getPage();
        String path = request.getResource().getPath();
        if (currentPage.isValid() && (currentPage.getTemplate() != null)) {
            String templateName = currentPage.getTemplate().getName();
            if ((StringUtils.isNotBlank(templateName) && templateName.equals("rsSitewideTemplate")) || path.contains("SitewidePromoPages")) {
                return "rsHomepageBanner";
            }
        }
        return "rsHomepageBannerView";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsHomepageBanner/views";
    }

    @Override
    protected String getValueObjectName() {
        return "rsHomepageBanner";
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

