package com.erp.lead.service.http;

import com.erp.lead.model.Localization;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author    Thangaperumal
 * @generated
 */
public class LocalizationJSONSerializer {
    public static JSONObject toJSONObject(Localization model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("code", model.getCode());
        jsonObj.put("type", model.getType());
        jsonObj.put("description", model.getDescription());
        jsonObj.put("structureId", model.getStructureId());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.Localization[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Localization model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.Localization[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Localization[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.rosettastone.cis.model.Localization> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Localization model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
