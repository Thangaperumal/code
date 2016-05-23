package com.erp.lead.service.http;

import com.erp.lead.model.Vertical;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author    Thangaperumal
 * @generated
 */
public class VerticalJSONSerializer {
    public static JSONObject toJSONObject(Vertical model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("description", model.getDescription());
        jsonObj.put("edition", model.getEdition());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Vertical[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Vertical model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Vertical[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Vertical[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.erp.lead.model.Vertical> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Vertical model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
