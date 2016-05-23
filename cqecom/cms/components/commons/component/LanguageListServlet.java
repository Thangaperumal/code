package com.cqecom.cms.components.commons.component;

import com.cqecom.cms.components.commons.LanguageSettings;
import com.cqecom.cms.components.commons.LanguageSettingsReader;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * This servlet returns list of languages in json format
 */

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.paths", value = "/cqecom/languagesList.json")
})
public class LanguageListServlet extends SlingSafeMethodsServlet {

    public static final Logger Log = LoggerFactory.getLogger(LanguageListServlet.class);

    private static final String PAGE_PARAMETER = "globalSite";

    @Override
    protected void service(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
        try {

            String langName = request.getParameter(PAGE_PARAMETER);
            List<LanguageSettings> languageSettingsList = LanguageSettingsReader.getLanguageSettingsList(langName, request.getResourceResolver());
            Collections.sort(languageSettingsList, new Comparator<LanguageSettings>() {
                @Override
                public int compare(LanguageSettings o1, LanguageSettings o2) {
                    return o1.getLongName().compareTo(o2.getLongName());
                }
            });

            JSONArray languagesJsonArray = new JSONArray();
            for (LanguageSettings lg : languageSettingsList) {
                JSONObject option = new JSONObject();
                option.put("longName", lg.getLongName());
                option.put("langCode", lg.getCode());
                option.put("levels", lg.getLevels());
                option.put("version",lg.getVersion());
                languagesJsonArray.put(option);
            }

            JSONObject resultJsonObject = new JSONObject();
            resultJsonObject.put("languagesList", languagesJsonArray);

            response.getWriter().append(resultJsonObject.toString());
        } catch (Exception e) {
            Log.error("Error", e);

            response.getWriter().append("{'error':'").append(e.getMessage()).append("'}");
        }
    }
}
