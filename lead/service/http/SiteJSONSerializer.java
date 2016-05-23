package com.erp.lead.service.http;

import com.erp.lead.model.Site;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author    Thangaperumal
 * @generated
 */
public class SiteJSONSerializer {
    public static JSONObject toJSONObject(Site model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("code", model.getCode());
        jsonObj.put("description", model.getDescription());
        jsonObj.put("currencyId", model.getCurrencyId());
        jsonObj.put("organizationId", model.getOrganizationId());
        jsonObj.put("countryId", model.getCountryId());
        jsonObj.put("url", model.getUrl());
        jsonObj.put("callCenter", model.getCallCenter());
        jsonObj.put("languageIso2", model.getLanguageIso2());
        jsonObj.put("localizationId", model.getLocalizationId());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.Site[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Site model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.Site[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Site[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.rosettastone.cis.model.Site> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Site model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
