package com.cqecom.cms.components.designmodule;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.components.commons.CommonUtils;
import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.HashMap;


/**
 * This servlet returns list of mini modules for rsDesignModule component settings dialog.
 */

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.paths", value = "/cqecom/modules.json")
})
public class ModulesServlet extends SlingSafeMethodsServlet {

    public static final Logger Log = LoggerFactory.getLogger(ModulesServlet.class);

    private static final String PATH_PARAMETER = "path";

    @Override
    protected void service(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
        String path = getPath(request);

        Resource resource = request.getResourceResolver().getResource(path);
        try {
            JSONArray array = getModules(resource.adaptTo(Node.class));
            response.getWriter().append(array.toString());
//            answer.put("options", array);
        } catch (Exception e) {
            Log.error("Error", e);
            response.getWriter().append("{'error':'" + e.getMessage() + "'}");
        }
    }

    /**
     * @return path to node with mini modules
     */
    private String getPath(SlingHttpServletRequest request) {
    	String path = null;
    	if(request.getParameter(PATH_PARAMETER)!=null)
    	{
    		path = request.getParameter(PATH_PARAMETER);
    		GlobalSettings globalSettings = CommonUtils.getGlobalSettings(request,path);
    		if(globalSettings != null)
    			return "/" + globalSettings.getSiteContentPath() + "/sharedContent/miniModules/jcr:content/rightPar";
    		else
    			Log.error("Error - global settings cannot be found");
    	}
    	else
    		Log.error("Error - Path is not specified");
		return null;        
    }

    /**
     * @return JSONArray of mini modules
     */
    private JSONArray getModules(Node parent) throws RepositoryException, JSONException {
        JSONArray list = new JSONArray();
        NodeIterator nodesIterator = parent.getNodes();
        while(nodesIterator.hasNext()) {
            Node node = nodesIterator.nextNode();
            if (node.hasProperty("modName")) {
                String modName = node.getProperty("modName").getValue().getString();
                JSONObject option = new JSONObject();
                option.put("text", modName);
                option.put("value", modName);
                list.put(option);
            }
        }
        return list;
    }
}
