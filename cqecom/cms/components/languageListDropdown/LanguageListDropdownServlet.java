package com.cqecom.cms.components.languageListDropdown;

import com.cqecom.cms.components.AbstractMvcServlet;
import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;
import com.cqecom.cms.model.Configuration;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.servlet.Servlet;

import java.util.*;

/**
 * This servlet returns HTML for rsLanguageListDropdown view
 */

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsLanguageListDropdown"),
        @Property(name = "sling.servlet.extensions", value = "html")
})

public class LanguageListDropdownServlet extends AbstractMvcServlet<LanguageListDropdown> {
    private static final Logger logger = LoggerFactory
			.getLogger(GlobalSettingsReader.class);
     @Override
    protected LanguageListDropdown loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {

        return request.getResource().adaptTo(LanguageListDropdown.class);
    }

    @Override
    protected void initModel(SlingHttpServletRequest request, SlingHttpServletResponse response,
                             LanguageListDropdown valueObject, Map<String, Object> model) {
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

        String languagePath = valueObject.getLocalizationsPath();//pageProperties.get("localizationsPath", String.class);

        GlobalSettings globalSettingsForLocalizationPath= getGlobalSettingsForLocalizationPath(languagePath, request.getResource().adaptTo(Node.class));

        List<Language> languages = loadLanguages(request.getResourceResolver(), languagePath, globalSettingsForLocalizationPath.getGlobalContentUrl(),pageProperties);

        valueObject.setLanguages(languages);

        if (valueObject.getLanguages() == null) {
            model.put("error", "Languages Not Found");
        } else {
        	model.put("languages", valueObject.getLanguages());

	        if (!valueObject.getLanguages().isEmpty()) {
	            model.put("defaultProductLanguage", valueObject.getLanguages().get(0));
	        } else {
	            model.put("defaultProductLanguage", null);
	        }
        }
        model.put("dropdownX", valueObject.getDropdownX());
        model.put("dropdownY", valueObject.getDropdownY());
        model.put("columnsCount", valueObject.getColumnsCount());
        model.put("localizationsPath", valueObject.getLocalizationsPath());
        model.put("dropdownTitleLeft", valueObject.getDropdownTitleLeft());
        model.put("dropdownTitleTop", valueObject.getDropdownTitleTop());
        model.put("dropdownTitle", valueObject.getDropdownTitle());
        model.put("useThreeImage", valueObject.isUseThreeImage());
        model.put("btnImageRef", valueObject.getButtonImage());
        model.put("btnMouseOverImageRef",valueObject.getMouseOverImageRef());
        model.put("btnClickImageRef", valueObject.getButtonClickImageRef());
        model.put("dropdownImageRef", valueObject.getDropdownImage());
        model.put("mouseOverImageRef",valueObject.getMouseOverImageRef());
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response, LanguageListDropdown valueObject, Map<String, Object> model) {
        return "rsLanguageListDropdown";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected String getValueObjectName() {
        return "languageListDropdown";
    }
    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsLanguageListDropdown/views";  //To change body of implemented methods use File | Settings | File Templates.
    }

    private List<Language> loadLanguages(ResourceResolver resourceResolver, String path, String pathForProductPage, ValueMap pageProperties) {
        Resource langResourceParent = resourceResolver.getResource(path);
        if (langResourceParent == null) {
          return null;
        }

        List<Language> languages = new ArrayList<Language>();
        for (Iterator<Resource> i = resourceResolver.listChildren(langResourceParent); i.hasNext(); ) {
            Resource langResource = i.next();
            Language localLanguage = langResource.adaptTo(Language.class);

            mergeWithGlobalLanguage(localLanguage, resourceResolver);

            String pathToProductPage = pathForProductPage + localLanguage.getLink() + ".html";
            Resource productPageResource = resourceResolver.resolve(pathToProductPage);
            localLanguage.setLink(pathToProductPage);

            if(filterLanguage(pageProperties,localLanguage)){
                languages.add(localLanguage);
            }

        }

        Collections.sort(languages);

        return languages;
    }

    private void mergeWithGlobalLanguage(Language localLanguage, ResourceResolver resourceResolver) {
    	String query = "/jcr:root" + Configuration.GLOBAL_PATH + "/languages//*[code= '" + localLanguage.getCode() + "']";
        Iterator<Resource> i = resourceResolver.findResources(query, Query.XPATH);
        if (i.hasNext()) {
            Language globalLang = i.next().adaptTo(Language.class);
            if (localLanguage.getCode() == null) {
                localLanguage.setCode(globalLang.getCode());
            }
            if (localLanguage.getFullName() == null) {
                localLanguage.setFullName(globalLang.getFullName());
            }
            if (localLanguage.getName() == null) {
                localLanguage.setName(globalLang.getName());
            }
            if (localLanguage.getVersion() == null) {
                localLanguage.setVersion(globalLang.getVersion());
            }
        }
    }
    private GlobalSettings getGlobalSettingsForLocalizationPath(String localizationPath, Node node){
       try{
			Session session = node.getSession();
            HashMap<String, GlobalSettings> allSettings = GlobalSettingsReader.getInstance().findAllSettings(session);
            String pathArray[] =localizationPath.split("/");
            for (int i = 0; i <= pathArray.length; i++) {
				if (allSettings.containsKey(pathArray[i]))
					return allSettings.get(pathArray[i]);
            }
           }catch(Exception    e){
                logger.error("getProductLink in LanguageListDropdownServlet: "+e.toString());
           }
        return null;
    }
    private boolean filterLanguage(ValueMap pageProperties, Language language)
    {
        if(!pageProperties.containsKey("lang")){
            return false;
        }
        String strsProps=pageProperties.get("lang").toString();
        String []strPropArr = strsProps.split("\\|");
        for(int i=0;i<strPropArr.length;i++)
        {
            String []strProp=strPropArr[i].split(",");
            if(strProp[0].equals("./lang_"+language.code)){
                return (strProp[1].equals("false"))?false:true;
            }
        }
        return false;
    }
}
