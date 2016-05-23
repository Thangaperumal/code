package com.erp.lead.service.http;

import com.erp.lead.model.Customer;
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
public class CustomerJSONSerializer {
    public static JSONObject toJSONObject(Customer model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("fullName", model.getFullName());
        jsonObj.put("email", model.getEmail());
        jsonObj.put("password", model.getPassword());
        jsonObj.put("youCanSpamMe", model.getYouCanSpamMe());
        jsonObj.put("affiliatesCanSpamMe", model.getAffiliatesCanSpamMe());
        jsonObj.put("leadCode", model.getLeadCode());
        jsonObj.put("locale", model.getLocale());
        jsonObj.put("affiliateCode", model.getAffiliateCode());
        jsonObj.put("abSplit", model.getAbSplit());

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
        jsonObj.put("organizationName", model.getOrganizationName());
        jsonObj.put("firstName", model.getFirstName());
        jsonObj.put("lastName", model.getLastName());
        jsonObj.put("phoneNumber", model.getPhoneNumber());
        jsonObj.put("genericLeadCode", model.getGenericLeadCode());
        jsonObj.put("dnisTelephoneNumber", model.getDnisTelephoneNumber());
        jsonObj.put("referringDomain", model.getReferringDomain());
        jsonObj.put("guid", model.getGuid());
        jsonObj.put("lastReferringDomain", model.getLastReferringDomain());
        jsonObj.put("learnerEmail", model.getLearnerEmail());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.Customer[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Customer model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.Customer[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Customer[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.rosettastone.cis.model.Customer> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Customer model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
