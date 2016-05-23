package com.cqecom.cms.components.linkEditor;


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

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Loads list of links for the rsLinkEditor component configured on a page
 */
@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.paths", value = "/cqecom/linkeditorlinks.json")
})
public class LinkEditorLinksServlet extends SlingSafeMethodsServlet {

    public static final Logger log = LoggerFactory.getLogger(LinkEditorLinksServlet.class);

    //for example /content/rs/emea/fr_FR/test/linkEditor/jcr:content/parsys/rslinkeditor
    private static final String LINK_EDITOR_INSTANCE_PATH = "path";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            JSONArray array = getLinks(request);
            JSONObject obj = new JSONObject();
            obj.put("links", array);
            response.getWriter().append(obj.toString());
        } catch (Exception e) {
            log.error("Error", e);
            response.getWriter().append("{'error':'").append(e.getMessage()).append("'}");
        }
    }

    private JSONArray getLinks(SlingHttpServletRequest request) throws ServletException, JSONException {
        String linkEditorPath = request.getParameter(LINK_EDITOR_INSTANCE_PATH);
        JSONArray list = new JSONArray();
        if (linkEditorPath != null) {
            Resource linkEditorResource = request.getResourceResolver().getResource(linkEditorPath);
            if (linkEditorResource == null) {
                throw new ServletException("Cannot find resource by path " + linkEditorPath);
            }
            LinkEditor linkEditor = linkEditorResource.adaptTo(LinkEditor.class);
            if (linkEditor == null) {
                throw new ServletException("Resource " + linkEditorPath + " cannot be adapted to LinkEditor");
            }
            if (linkEditor.getLinks() != null && !linkEditor.getLinks().isEmpty()) {
                int index = 1;
                for (Link link : linkEditor.getLinks()) {
                    JSONObject obj = linkToJsonObject(link, index);
                    list.put(obj);
                    index++;
                }
            }

        }
        return list;
    }

    private JSONObject linkToJsonObject(Link link, int index) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("id", index);
        obj.put("name", link.getName());
        obj.put("title", link.getTitle());
        obj.put("url", link.getUrl());
        obj.put("linkClass", link.getLinkClass());
        obj.put("rel", link.getRel());
        obj.put("target", link.getTarget());
        return obj;
    }
}

