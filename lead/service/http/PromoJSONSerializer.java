package com.erp.lead.service.http;

import com.erp.lead.model.Promo;
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
public class PromoJSONSerializer {
    public static JSONObject toJSONObject(Promo model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("code", model.getCode());
        jsonObj.put("descriptionEn", model.getDescriptionEn());
        jsonObj.put("currencyId", model.getCurrencyId());
        jsonObj.put("active", model.getActive());
        jsonObj.put("automatic", model.getAutomatic());
        jsonObj.put("AllowInHSStore", model.getAllowInHSStore());
        jsonObj.put("AllowInINDStore", model.getAllowInINDStore());
        jsonObj.put("genericLead", model.getGenericLead());
        jsonObj.put("specificLead", model.getSpecificLead());

        Date endDate = model.getEndDate();

        String endDateJSON = StringPool.BLANK;

        if (endDate != null) {
            endDateJSON = String.valueOf(endDate.getTime());
        }

        jsonObj.put("endDate", endDateJSON);

        Date startDate = model.getStartDate();

        String startDateJSON = StringPool.BLANK;

        if (startDate != null) {
            startDateJSON = String.valueOf(startDate.getTime());
        }

        jsonObj.put("startDate", startDateJSON);

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Promo[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Promo model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Promo[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Promo[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.erp.lead.model.Promo> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Promo model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
