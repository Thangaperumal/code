package com.cqecom.cms.components;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestDispatcherOptions;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

public abstract class AbstractMvcServlet<ValueObjectClass> extends SlingSafeMethodsServlet {

    public static final String ID_GENERATOR = "rsIdGenerator";

    private static final long serialVersionUID = 1L;

    private final String baseViewsPath = "cqecom/components/content/rsBaseViews";


    @Override
    protected void service(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {

        if (!isConfiguredResource(request, response)) {
            renderFailedView(request, response);
        } else {

            ValueObjectClass valueObject = loadValueObject(request, response);

            if (!isConfigured(request, response, valueObject)) {
                renderFailedView(request, response);
            } else {
                request.setAttribute(getValueObjectName(), valueObject);

                Map<String, Object> model = new HashMap<String, Object>();
                initModel(request, response, valueObject, model);
                for (Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }

                initIdGenerator(request);

                renderView(request, response, valueObject, model);

            }
        }
    }

    protected abstract ValueObjectClass loadValueObject(SlingHttpServletRequest request,
            SlingHttpServletResponse response);

    protected void initModel(SlingHttpServletRequest request, SlingHttpServletResponse response,
            ValueObjectClass valueObject, Map<String, Object> model) {
        // Do nothing by default
    }

    protected String getValueObjectName() {
        return "valueObject";
    }


    protected boolean isConfiguredResource(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Resource resource = request.getResource();
        return isConfiguredResource(resource);
    }

    protected boolean isConfiguredResource(Resource resource) {
        ValueMap objProperties = resource.adaptTo(ValueMap.class);
        return !(ResourceUtil.isNonExistingResource(resource) || objProperties == null);
    }

    protected boolean isConfigured(SlingHttpServletRequest request, SlingHttpServletResponse response,
            ValueObjectClass valueObject) {
        return true;
    }

    protected abstract String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
            ValueObjectClass valueObject, Map<String, Object> model);

    protected abstract String getGetJspPath();

    private void renderView(SlingHttpServletRequest request, SlingHttpServletResponse response,
            ValueObjectClass valueObject, Map<String, Object> model) throws ServletException, IOException {
        RequestDispatcherOptions options = null;
        options = new RequestDispatcherOptions();
        options.setForceResourceType(getGetJspPath());

        /*restore value of CONTENT_TYPE: needed for rendering different languages*/
        request.getResponseContentType();

        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getResource().getPath() + "."
                + getViewName(request, response, valueObject, model), options);
        if (dispatcher != null) {
            dispatcher.include(request, response);
        }
    }

    private void renderFailedView(SlingHttpServletRequest request, SlingHttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcherOptions options = null;

        options = new RequestDispatcherOptions();
        options.setForceResourceType(baseViewsPath);

        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getResource().getPath()
                + ".NotConfigured", options);
        if (dispatcher != null) {
            dispatcher.include(request, response);
        }
    }

    private void initIdGenerator(SlingHttpServletRequest request) {
        IdGenerator idGenerator = (IdGenerator) request.getAttribute(ID_GENERATOR);
        if (idGenerator == null) {
            idGenerator = new IdGenerator();
            request.setAttribute(ID_GENERATOR, idGenerator);
        }
    }
}
