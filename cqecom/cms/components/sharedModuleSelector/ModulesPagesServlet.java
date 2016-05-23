package com.cqecom.cms.components.sharedModuleSelector;

import com.cqecom.cms.components.commons.CommonUtils;
import com.cqecom.cms.components.commons.GlobalSettings;

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

import javax.jcr.query.Query;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.Iterator;


/**
 * This servlet returns list of rsSharedModulesPage pages for rsSharedModule component settings dialog.
 */

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.paths", value = "/cqecom/sharedmodulespages.json")
})
public class ModulesPagesServlet extends SlingSafeMethodsServlet {

    public static final Logger Log = LoggerFactory.getLogger(ModulesPagesServlet.class);

    private static final String PATH_PARAMETER = "path";
    private static final String JCR_TITLE = "jcr:title";

    @Override
    protected void service(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
        try {
            JSONArray array = getPages(request);
            response.getWriter().append(array.toString());
        } catch (Exception e) {
            Log.error("Error", e);
            response.getWriter().append("{'error':'" + e.getMessage() + "'}");
        }
    }

    /**
     * @return JSONArray of pages modules
     */
    private JSONArray getPages(SlingHttpServletRequest request) throws JSONException {
    	String pagePath = request.getParameter(PATH_PARAMETER);
    	if(pagePath!=null)
    	{
	    	GlobalSettings settings = CommonUtils.getGlobalSettings(request,pagePath);
	    	StringBuilder query = new StringBuilder(256);
	        query.append("/jcr:root/").append(settings.getSiteContentPath()).append("/").append("/sharedContent//*[@sling:resourceType='cqecom/components/page/rsSharedModulesPage']");
	        Iterator<Resource> iterator = request.getResourceResolver().findResources(query.toString(), Query.XPATH);
	        JSONArray list = new JSONArray();
	        while(iterator.hasNext()) {
	            Resource pageResource = iterator.next();
	            ValueMap properties = pageResource.adaptTo(ValueMap.class);
	            JSONObject option = new JSONObject();
	            option.put("text", properties.get(JCR_TITLE, String.class));
	            option.put("value", pageResource.getPath());
	            list.put(option);
	        }
	        return list;        
    	}
    	else
    	{
    		Log.error("Error - Request URL misses parameter");
    		return null;
    	}        
        
    }
}
