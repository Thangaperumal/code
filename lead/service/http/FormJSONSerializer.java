package com.erp.lead.service.http;

import com.erp.lead.model.Form;
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
public class FormJSONSerializer {
    public static JSONObject toJSONObject(Form model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("siteId", model.getSiteId());
        jsonObj.put("formCode", model.getFormCode());
        jsonObj.put("salesforce", model.getSalesforce());
        jsonObj.put("dovetail", model.getDovetail());
        jsonObj.put("demoCd", model.getDemoCd());
        jsonObj.put("email", model.getEmail());

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
        jsonObj.put("vertical", model.getVertical());
        jsonObj.put("trialOsub", model.getTrialOsub());
        jsonObj.put("formEmailTemplateId", model.getFormEmailTemplateId());
        jsonObj.put("osubCancellation", model.getOsubCancellation());
        jsonObj.put("businessOwner", model.getBusinessOwner());
        jsonObj.put("eloqua", model.getEloqua());
        jsonObj.put("trialOsubFollowup", model.getTrialOsubFollowup());
        jsonObj.put("autoReply", model.getAutoReply());
        jsonObj.put("pure360", model.getPure360());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.Form[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Form model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.Form[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Form[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.rosettastone.cis.model.Form> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Form model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
