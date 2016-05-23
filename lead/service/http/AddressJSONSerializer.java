package com.erp.lead.service.http;

import com.erp.lead.model.Address;
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
public class AddressJSONSerializer {
    public static JSONObject toJSONObject(Address model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("fullName", model.getFullName());
        jsonObj.put("address1", model.getAddress1());
        jsonObj.put("address2", model.getAddress2());
        jsonObj.put("address3", model.getAddress3());
        jsonObj.put("address4", model.getAddress4());
        jsonObj.put("city", model.getCity());
        jsonObj.put("county", model.getCounty());
        jsonObj.put("state", model.getState());
        jsonObj.put("province", model.getProvince());
        jsonObj.put("postalCode", model.getPostalCode());
        jsonObj.put("countryCode", model.getCountryCode());
        jsonObj.put("phoneNumber", model.getPhoneNumber());

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
        jsonObj.put("page", model.getPage());
        jsonObj.put("firstName", model.getFirstName());
        jsonObj.put("lastName", model.getLastName());
        jsonObj.put("firstNamePhonetic", model.getFirstNamePhonetic());
        jsonObj.put("lastNamePhonetic", model.getLastNamePhonetic());
        jsonObj.put("zip4", model.getZip4());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Address[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Address model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Address[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Address[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.erp.lead.model.Address> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Address model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
