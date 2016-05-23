package com.erp.lead.service.http;

import com.erp.lead.model.OrderLine;
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
public class OrderLineJSONSerializer {
    public static JSONObject toJSONObject(OrderLine model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("itemId", model.getItemId());
        jsonObj.put("orderId", model.getOrderId());
        jsonObj.put("quantity", model.getQuantity());
        jsonObj.put("currencyId", model.getCurrencyId());
        jsonObj.put("listPricePerItem", model.getListPricePerItem());
        jsonObj.put("yourPricePerItem", model.getYourPricePerItem());
        jsonObj.put("tax", model.getTax());

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
        jsonObj.put("shippingLineAmount", model.getShippingLineAmount());
        jsonObj.put("applicationGuid", model.getApplicationGuid());
        jsonObj.put("eschoolGuid", model.getEschoolGuid());
        jsonObj.put("premiumCommunityGuid", model.getPremiumCommunityGuid());
        jsonObj.put("userGuid", model.getUserGuid());
        jsonObj.put("serialNumber", model.getSerialNumber());
        jsonObj.put("activationId", model.getActivationId());
        jsonObj.put("lotusGuid", model.getLotusGuid());
        jsonObj.put("language", model.getLanguage());
        jsonObj.put("applicationRefId", model.getApplicationRefId());
        jsonObj.put("eschoolRefId", model.getEschoolRefId());
        jsonObj.put("premiumCommunityRefId", model.getPremiumCommunityRefId());
        jsonObj.put("lotusRefId", model.getLotusRefId());
        jsonObj.put("eschoolGroupSessionsGuid",
            model.getEschoolGroupSessionsGuid());
        jsonObj.put("eschoolOneOnOneSessionsGuid",
            model.getEschoolOneOnOneSessionsGuid());
        jsonObj.put("languageSwitchingGuid", model.getLanguageSwitchingGuid());
        jsonObj.put("eschoolGroupSessionsRefId",
            model.getEschoolGroupSessionsRefId());
        jsonObj.put("eschoolOneOnOneSessionsRefId",
            model.getEschoolOneOnOneSessionsRefId());
        jsonObj.put("languageSwitchingRefId", model.getLanguageSwitchingRefId());
        jsonObj.put("autoRenew", model.getAutoRenew());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.OrderLine[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (OrderLine model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.rosettastone.cis.model.OrderLine[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (OrderLine[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.rosettastone.cis.model.OrderLine> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (OrderLine model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
