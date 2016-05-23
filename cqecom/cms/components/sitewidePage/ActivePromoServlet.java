package com.cqecom.cms.components.sitewidePage;

import com.cqecom.cms.services.pageSearch.PageSearchService;

import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Servlet returns list of sitewide promo pages which are active in a given date range
 */
@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.paths", value = "/cqecom/activePromo.json")
})
public class ActivePromoServlet extends SlingSafeMethodsServlet {

    public static final Logger log = LoggerFactory.getLogger(ActivePromoServlet.class);

    private static final String GLOBAL_APP = "app";

    private static final String SITE = "site";

    private static final String START_DATE = "startDate";

    private static final String END_DATE = "endDate";

    private static final String DATE_FORMAT = "MM/dd/yyy HH:mm";

    @Reference
    private PageSearchService pageSearchService;

    @Override
    protected void service(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
        String globalApp = request.getParameter(GLOBAL_APP);
        String site = request.getParameter(SITE);
        String start = request.getParameter(START_DATE);
        String end = request.getParameter(END_DATE);

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        JSONArray array = new JSONArray();
        
        try {
            if ((globalApp == null) || (site == null) || (start == null) || (end == null)) {
                throw new ServletException("Required parameters for servlet are missing");
            }

            Date startDate = format.parse(start);
            Date endDate = format.parse(end);

            List<String> promoPages = pageSearchService.findActivePromoPagesInRange(site, startDate, endDate);
            for (String promoPage : promoPages) {
                Resource resource = request.getResourceResolver().getResource(promoPage + "/jcr:content");
                ValueMap values = resource.adaptTo(ValueMap.class);
                Date promoStart = values.get("startdate", Date.class);
                Date promoEnd = values.get("enddate", Date.class);
                if ((promoStart == null) || (promoEnd == null)) {
                    throw new ServletException("Unable to get start/end dates for sitewide promo page " + promoPage);
                }
                JSONObject obj = new JSONObject();
                obj.put("path", promoPage);
                obj.put("start", format.format(promoStart));
                obj.put("end", format.format(promoEnd));
                array.put(obj);
            }
            response.getWriter().append(array.toString());
        } catch (Exception e) {
            log.error("Error", e);
            response.getWriter().append("{'error':'").append(e.getMessage()).append("'}");
        }
    }
}
