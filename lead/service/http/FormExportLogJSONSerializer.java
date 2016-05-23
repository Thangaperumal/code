package com.erp.lead.service.http;

import com.erp.lead.model.FormExportLog;
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
public class FormExportLogJSONSerializer {
    public static JSONObject toJSONObject(FormExportLog model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("formEntryId", model.getFormEntryId());
        jsonObj.put("exporter", model.getExporter());
        jsonObj.put("successful", model.getSuccessful());
        jsonObj.put("externalId", model.getExternalId());

        Date createdAt = model.getCreatedAt();

        String createdAtJSON = StringPool.BLANK;

        if (createdAt != null) {
            createdAtJSON = String.valueOf(createdAt.getTime());
        }

        jsonObj.put("createdAt", createdAtJSON);
        jsonObj.put("text", model.getText());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.FormExportLog[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FormExportLog model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.FormExportLog[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FormExportLog[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.erp.lead.model.FormExportLog> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FormExportLog model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
