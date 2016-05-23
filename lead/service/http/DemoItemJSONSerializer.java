package com.erp.lead.service.http;

import com.erp.lead.model.DemoItem;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author    Thangaperumal
 * @generated
 */
public class DemoItemJSONSerializer {
    public static JSONObject toJSONObject(DemoItem model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("languageId", model.getLanguageId());
        jsonObj.put("inventoryItemId", model.getInventoryItemId());
        jsonObj.put("organizationId", model.getOrganizationId());
        jsonObj.put("verticalId", model.getVerticalId());
        jsonObj.put("localizationId", model.getLocalizationId());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.DemoItem[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (DemoItem model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.DemoItem[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (DemoItem[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.rosettastone.cis.model.DemoItem> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (DemoItem model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
