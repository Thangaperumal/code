package com.erp.lead.service.http;

import com.erp.lead.model.Country;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author    Thangaperumal
 * @generated
 */
public class CountryJSONSerializer {
    public static JSONObject toJSONObject(Country model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("code", model.getCode());
        jsonObj.put("descriptionEn", model.getDescriptionEn());
        jsonObj.put("region", model.getRegion());
        jsonObj.put("isoNumericCode", model.getIsoNumericCode());
        jsonObj.put("euCode", model.getEuCode());
        jsonObj.put("active", model.getActive());
        jsonObj.put("denied", model.getDenied());
        jsonObj.put("restricted", model.getRestricted());
        jsonObj.put("organizationId", model.getOrganizationId());
        jsonObj.put("defaultCurrencyId", model.getDefaultCurrencyId());
        jsonObj.put("whyInactive", model.getWhyInactive());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Country[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Country model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Country[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Country[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.erp.lead.model.Country> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Country model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
