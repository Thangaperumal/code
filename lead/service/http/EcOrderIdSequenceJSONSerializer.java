package com.erp.lead.service.http;

import com.erp.lead.model.EcOrderIdSequence;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author    Thangaperumal
 * @generated
 */
public class EcOrderIdSequenceJSONSerializer {
    public static JSONObject toJSONObject(EcOrderIdSequence model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.EcOrderIdSequence[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (EcOrderIdSequence model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.EcOrderIdSequence[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (EcOrderIdSequence[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.rosettastone.cis.model.EcOrderIdSequence> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (EcOrderIdSequence model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
