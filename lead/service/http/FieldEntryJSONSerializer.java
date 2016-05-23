package com.erp.lead.service.http;

import com.erp.lead.model.FieldEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author    Thangaperumal
 * @generated
 */
public class FieldEntryJSONSerializer {
    public static JSONObject toJSONObject(FieldEntry model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("formEntryId", model.getFormEntryId());
        jsonObj.put("name", model.getName());
        jsonObj.put("value", model.getValue());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.FieldEntry[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FieldEntry model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.FieldEntry[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FieldEntry[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.rosettastone.cis.model.FieldEntry> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FieldEntry model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
