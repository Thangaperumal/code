package com.erp.lead.service.http;

import com.erp.lead.model.FormEmailTemplate;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author    Thangaperumal
 * @generated
 */
public class FormEmailTemplateJSONSerializer {
    public static JSONObject toJSONObject(FormEmailTemplate model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("name", model.getName());
        jsonObj.put("subject", model.getSubject());
        jsonObj.put("body", model.getBody());
        jsonObj.put("sendEmailAsCustomer", model.getSendEmailAsCustomer());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.FormEmailTemplate[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FormEmailTemplate model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.FormEmailTemplate[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FormEmailTemplate[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.erp.lead.model.FormEmailTemplate> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (FormEmailTemplate model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
