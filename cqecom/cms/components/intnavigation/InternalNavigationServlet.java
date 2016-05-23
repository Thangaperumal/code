package com.cqecom.cms.components.intnavigation;

import java.util.Iterator;
import java.util.LinkedList;
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
import org.apache.sling.api.resource.ResourceUtil;

import com.cqecom.cms.components.AbstractMvcServlet;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsInternalNavigation"),
        @Property(name = "sling.servlet.extensions", value = "html")
})
public class InternalNavigationServlet extends AbstractMvcServlet<Navigation> {

    private static final String COMPONENT_CONTEXT = "com.day.cq.wcm.componentcontext";

    @Override
    protected Navigation loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Navigation navigation = request.getResource().adaptTo(Navigation.class);

        ComponentContext componentContext = (ComponentContext) request.getAttribute(COMPONENT_CONTEXT);
        Page currentPage = componentContext.getPage();
        Iterator<Page> iterator;
        LinkedList<NavigationLink> list = new LinkedList<NavigationLink>();
        if (navigation.getLevel().equals(Navigation.Level.DESCENDANTS)) {
            iterator = currentPage.listChildren();
            if (!navigation.isHideCurrentPage()) {
                list.add(createNavigationLink(currentPage, null, true));
            }
        } else {
            if (currentPage.getParent() != null) {
                iterator = currentPage.getParent().listChildren();
                if (!navigation.isHideParent()) {
                    if (StringUtils.isNotBlank(navigation.getParentTabName())) {
                        list.add(createNavigationLink(currentPage.getParent(), navigation.getParentTabName(), false));
                    } else {
                        list.add(createNavigationLink(currentPage.getParent(), null, false));
                    }
                }
            } else {
                LinkedList<Page> dummyList = new LinkedList<Page>();
                iterator = dummyList.iterator();
            }
        }
        String stripStyle = navigation.getStripStyle();
        if (stripStyle != null && stripStyle.contains("partWidthBackground")) {
            navigation.setMenuStyle("partWidthMenuBackground");
        } else {
            navigation.setMenuStyle("fullWidthMenuBackground");
        }

        while (iterator.hasNext()) {
            Page page = iterator.next();

            if (page.isValid() && (page.getTemplate() != null)) {
                String templateName = page.getTemplate().getName();
                if ((templateName != null) && (templateName.equals("rsFullWidthPageTemplate")
                        || templateName.equals("rsTwoColumnTemplate")
                        || templateName.equals("rsFullWidthPageTemplateV2")
                        || templateName.equals("rsTwoColumnTemplateV2"))) {
                    Resource res = page.getContentResource("intnavigation");
                    if (res != null && !ResourceUtil.isNonExistingResource(res)) {
                        Navigation pageNav = res.adaptTo(Navigation.class);
                        if (pageNav != null && !pageNav.isHideCurrentPage()) {
                            boolean isCurrent = page.getPath().equals(currentPage.getPath());
                            NavigationLink link = createNavigationLink(page, null, isCurrent);
                            list.add(link);
                        }
                    }
                }
            }
        }
        navigation.setLinks(list);
        return navigation;
    }

    private NavigationLink createNavigationLink(Page page, String tabName, boolean isCurrent) {
        String pageTitle = "";

        if (StringUtils.isNotBlank(tabName)) {
            pageTitle = tabName;
        } else {
            pageTitle = page.getTitle();

            Resource res = page.getContentResource("intnavigation");

            if (res != null && !ResourceUtil.isNonExistingResource(res)) {
                Navigation pageNav = res.adaptTo(Navigation.class);
                if (pageNav != null && StringUtils.isNotBlank(pageNav.getTabName())) {
                    pageTitle = pageNav.getTabName();
                }
            }
        }

        NavigationLink link = new NavigationLink();

        link.setLabel(pageTitle);
        link.setCurrent(isCurrent);
        link.setUrl(fixLinkUrl(page.getPath()));
        return link;
    }

    @Override
    protected String getValueObjectName() {
        return "navigation";
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response, Navigation module,
            Map<String, Object> model) {
        return "rsInternalNavigation";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsInternalNavigation/views";
    }

    private String fixLinkUrl(String url) {
        if (StringUtils.isNotBlank(url) && !url.toLowerCase().endsWith(".html")) {
            return url + ".html";
        } else {
            return url;
        }
    }
}
