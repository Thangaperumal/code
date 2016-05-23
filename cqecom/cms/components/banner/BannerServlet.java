package com.cqecom.cms.components.banner;

import java.io.File;
import java.util.*;

import javax.jcr.Node;
import javax.jcr.query.Query;
import javax.servlet.Servlet;

import com.cqecom.cms.components.AbstractMvcServlet;
import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;
import com.cqecom.cms.components.commons.LanguageSettingsReader;
import com.cqecom.cms.model.Configuration;

import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service(Servlet.class)
@Properties(value = {
    @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsBanner"),
    @Property(name = "sling.servlet.extensions", value = "html")
})
public class BannerServlet extends AbstractMvcServlet<Banner> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * Version
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected Banner loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        return request.getResource().adaptTo(Banner.class);
    }

    @Override
    protected String getValueObjectName() {
        return "banner";
    }

    @Override
    protected boolean isConfigured(SlingHttpServletRequest request, SlingHttpServletResponse response,
            Banner banner) {
        if (banner == null || banner.getBannerType() == null) {
            return false;
        }
        return true;
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
            Banner banner, Map<String, Object> model) {
        if (model.containsKey("error")) {
            return "Error";
        }

        return "rsBanner";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsBanner/views";
    }

    protected void initModel(SlingHttpServletRequest request, SlingHttpServletResponse response,
            Banner banner,
            Map<String, Object> model) {

        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource configGlobal = resourceResolver.getResource(Configuration.GLOBAL_PATH);
        if (configGlobal == null) {
            model.put("error", "Global Not Found");
        }

        Resource pageResource = request.getResource();
        if (pageResource == null) {
            return;
        }
        ValueMap pageProperties = pageResource.adaptTo(ValueMap.class);

        GlobalSettings globalSettings = GlobalSettingsReader.getInstance().findSettings(request.getResource().adaptTo(Node.class));

        String languagePath = LanguageSettingsReader.getLanguagePath(globalSettings.getGlobalSite());

        List<ProductLanguage> languages = loadLanguages(banner, pageResource, request.getResourceResolver(), languagePath, globalSettings);
        if (languages == null) {
            model.put("error", "Languages Not Found");
        } else {
        	model.put("languages", languages);

	        if (!languages.isEmpty()) {
	            model.put("defaultProductLanguage", languages.get(0));
	        } else {
	            model.put("defaultProductLanguage", null);
	        }
        }

        String requestedOfferType = pageProperties.get("offerType", String.class);
        model.put("requestedOfferType", requestedOfferType);

        model.put("topImageHeight", banner.getTopImageHeight());
        model.put("bottomImageHeight", banner.getBottomImageHeight());
        model.put("dropdownX", banner.getDropdownX());
        model.put("dropdownY", banner.getDropdownY());
    }

    private List<ProductLanguage> loadLanguages(Banner banner, Resource pageResource, ResourceResolver resourceResolver, String path, GlobalSettings globalSettings) {
        Resource langResourceParent = resourceResolver.getResource(path);
        if (langResourceParent == null)
          return null;

        List<ProductLanguage> languages = new ArrayList<ProductLanguage>();
        for (Iterator<Resource> i = resourceResolver.listChildren(langResourceParent); i.hasNext(); ) {
            Resource langResource = i.next();
            ProductLanguage localLanguage = langResource.adaptTo(ProductLanguage.class);
            boolean isVisible = banner.getLanguageCodes().isEmpty() || banner.getLanguageCodes().contains(localLanguage.getCode());
            if (isVisible) {
                mergeWithGlobalLanguage(localLanguage, resourceResolver);

                localLanguage.setProductUrl("#");

                if (banner.getUseProductLink()) {
                    String pathToProductPage = globalSettings.getGlobalContentUrl() + localLanguage.getProductLink() + ".html";
                    Resource productPageResource = resourceResolver.resolve(pathToProductPage);

                    if (!ResourceUtil.isNonExistingResource(productPageResource)) {
                        localLanguage.setProductUrl(pathToProductPage);
                    }
                }

                languages.add(localLanguage);
            }
        }

        Collections.sort(languages);

        return languages;
    }

    private void mergeWithGlobalLanguage(ProductLanguage localLanguage, ResourceResolver resourceResolver) {
    	String query = "/jcr:root" + Configuration.GLOBAL_PATH + "/languages//*[code= '" + localLanguage.getCode() + "']";
        Iterator<Resource> i = resourceResolver.findResources(query, Query.XPATH);
        if (i.hasNext()) {
            ProductLanguage globalLang = i.next().adaptTo(ProductLanguage.class);
            if (localLanguage.getCode() == null) {
                localLanguage.setCode(globalLang.getCode());
            }
            if (localLanguage.getLongName() == null) {
                localLanguage.setLongName(globalLang.getLongName());
            }
            if (localLanguage.getName() == null) {
                localLanguage.setName(globalLang.getName());
            }
            if (localLanguage.getVersion() == null) {
                localLanguage.setVersion(globalLang.getVersion());
            }
        }
    }

}
