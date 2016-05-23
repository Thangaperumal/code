package com.erp.lead.service.http;

import com.erp.lead.model.FormEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;
import java.util.List;

/**
 * @author    Thangaperumal
 * @generated
 */
public class FormEntryJSONSerializer {
    public static JSONObject toJSONObject(FormEntry model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("formId", model.getFormId());
        jsonObj.put("formUrl", model.getFormUrl());

        Date createdAt = model.getCreatedAt();

        String createdAtJSON = StringPool.BLANK;

        if (createdAt != null) {
            createdAtJSON = String.valueOf(createdAt.getTime());
        }

        jsonObj.put("createdAt", createdAtJSON);

        Date updatedAt = model.getUpdatedAt();

        String updatedAtJSON = StringPool.BLANK;

        if (updatedAt != null) {
            updatedAtJSON = String.valueOf(updatedAt.getTime());
        }

        jsonObj.put("updatedAt", updatedAtJSON);

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.FormEntry[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FormEntry model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.FormEntry[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FormEntry[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.rosettastone.cis.model.FormEntry> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FormEntry model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
