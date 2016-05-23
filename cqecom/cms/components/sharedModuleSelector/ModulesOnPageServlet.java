package com.cqecom.cms.components.sharedModuleSelector;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.components.commons.CommonUtils;
import com.cqecom.cms.components.commons.GlobalSettings;

import javax.jcr.query.Query;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.Iterator;


/**
 * This servlet returns list of shared modules for rsSharedModule component settings dialog.
 */

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.paths", value = "/cqecom/sharedmodules.json")
})
public class ModulesOnPageServlet extends SlingSafeMethodsServlet {

    public static final Logger Log = LoggerFactory.getLogger(ModulesOnPageServlet.class);

    private static final String PAGE_PARAMETER = "page";

    @Override
    protected void service(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
        try {
            JSONArray array = getModules(request);
            response.getWriter().append(array.toString());
        } catch (Exception e) {
            Log.error("Error", e);
            response.getWriter().append("{'error':'" + e.getMessage() + "'}");
        }
    }

    /**
     * @return JSONArray of pages modules
     */
    private JSONArray getModules(SlingHttpServletRequest request) throws JSONException {
        String page = request.getParameter(PAGE_PARAMETER);
        JSONArray list = new JSONArray();
        if (StringUtils.isNotEmpty(page) && isPageInSharedContent(page,request)) {
            StringBuilder query = new StringBuilder(256);
            query.append("/jcr:root").append(page).append("//*[@sling:resourceType='cqecom/components/content/rsSharedModule']");

            Iterator<Resource> iterator = request.getResourceResolver().findResources(query.toString(), Query.XPATH);


            while (iterator.hasNext()) {
                Resource pageResource = iterator.next();
                ValueMap properties = pageResource.adaptTo(ValueMap.class);
                String id = properties.get("moduleId", String.class);
                if (id != null) {
                    JSONObject option = new JSONObject();

                    option.put("text", id);
                    option.put("value", id);
                    list.put(option);
                }
            }
        }
        return list;
    }

    //path should be like /content/rs/us/en_US/sharedContent/pageName
    private boolean isPageInSharedContent(String pagePath,SlingHttpServletRequest request) {
    	if(pagePath!=null)
    	{
	    	GlobalSettings settings = CommonUtils.getGlobalSettings(request,pagePath);
	    	return (pagePath.indexOf(settings.getSiteContentPath() + "/sharedContent") != -1);        
    	}
    	else
    	{
    		Log.error("Error - Request URL misses parameter");
    		return false;
    	}        
    }
}
