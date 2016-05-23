package com.erp.lead.service.http;

import com.erp.lead.model.Order;
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
public class OrderJSONSerializer {
    public static JSONObject toJSONObject(Order model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("id", model.getId());
        jsonObj.put("orderNumber", model.getOrderNumber());
        jsonObj.put("customerId", model.getCustomerId());
        jsonObj.put("shipMethodId", model.getShipMethodId());
        jsonObj.put("promoId", model.getPromoId());
        jsonObj.put("shipAddressId", model.getShipAddressId());
        jsonObj.put("billAddressId", model.getBillAddressId());
        jsonObj.put("creditCardId", model.getCreditCardId());
        jsonObj.put("certitaxTrxnId", model.getCertitaxTrxnId());
        jsonObj.put("currencyId", model.getCurrencyId());
        jsonObj.put("subtotal", model.getSubtotal());
        jsonObj.put("subtotalVat", model.getSubtotalVat());
        jsonObj.put("shipping", model.getShipping());
        jsonObj.put("tax", model.getTax());
        jsonObj.put("total", model.getTotal());
        jsonObj.put("noSignatureRequired", model.getNoSignatureRequired());
        jsonObj.put("creditCardApprovalCode", model.getCreditCardApprovalCode());
        jsonObj.put("creditCardRetries", model.getCreditCardRetries());
        jsonObj.put("organizationId", model.getOrganizationId());
        jsonObj.put("formWhereEntered", model.getFormWhereEntered());
        jsonObj.put("vertical", model.getVertical());
        jsonObj.put("vatEstimationCountryCode",
            model.getVatEstimationCountryCode());

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

        Date orderedDate = model.getOrderedDate();

        String orderedDateJSON = StringPool.BLANK;

        if (orderedDate != null) {
            orderedDateJSON = String.valueOf(orderedDate.getTime());
        }

        jsonObj.put("orderedDate", orderedDateJSON);
        jsonObj.put("salesrepId", model.getSalesrepId());
        jsonObj.put("siteId", model.getSiteId());
        jsonObj.put("shippingHeaderAmount", model.getShippingHeaderAmount());
        jsonObj.put("payments", model.getPayments());
        jsonObj.put("wireTransferId", model.getWireTransferId());
        jsonObj.put("showNoCostsOnSlip", model.getShowNoCostsOnSlip());
        jsonObj.put("shippingInstructions", model.getShippingInstructions());
        jsonObj.put("bisextorderid", model.getBisextorderid());
        jsonObj.put("languageOfInterest", model.getLanguageOfInterest());
        jsonObj.put("ecOrderId", model.getEcOrderId());
        jsonObj.put("giftMsg", model.getGiftMsg());
        jsonObj.put("paypalTransactionId", model.getPaypalTransactionId());
        jsonObj.put("billingAgreementId", model.getBillingAgreementId());
        jsonObj.put("checkOrMo", model.getCheckOrMo());
        jsonObj.put("packingInstructions", model.getPackingInstructions());
        jsonObj.put("complimentary", model.getComplimentary());
        jsonObj.put("readyForExport", model.getReadyForExport());
        jsonObj.put("bisextproofxml", model.getBisextproofxml());
        jsonObj.put("maestro3dSecure", model.getMaestro3dSecure());
        jsonObj.put("bisextcustomerip", model.getBisextcustomerip());
        jsonObj.put("bisextcvvresult", model.getBisextcvvresult());
        jsonObj.put("bisextavsresult", model.getBisextavsresult());
        jsonObj.put("orderNotes", model.getOrderNotes());
        jsonObj.put("bisextfraudresult", model.getBisextfraudresult());
        jsonObj.put("bisextfraudcode", model.getBisextfraudcode());
        jsonObj.put("poNumber", model.getPoNumber());
        jsonObj.put("paymentType", model.getPaymentType());
        jsonObj.put("dropShip", model.getDropShip());
        jsonObj.put("customerNumber", model.getCustomerNumber());
        jsonObj.put("repeatCustomer", model.getRepeatCustomer());
        jsonObj.put("bisextattemptid", model.getBisextattemptid());
        jsonObj.put("bisextpaymentproductid", model.getBisextpaymentproductid());
        jsonObj.put("directDebitId", model.getDirectDebitId());
        jsonObj.put("bisstoreid", model.getBisstoreid());
        jsonObj.put("bisextpaymentmethodid", model.getBisextpaymentmethodid());
        jsonObj.put("bisextstatusid", model.getBisextstatusid());
        jsonObj.put("bisextamount", model.getBisextamount());
        jsonObj.put("rtbtId", model.getRtbtId());
        jsonObj.put("paymentTermId", model.getPaymentTermId());
        jsonObj.put("unauthorisedOrder", model.getUnauthorisedOrder());
        jsonObj.put("amsStatus", model.getAmsStatus());
        jsonObj.put("addressOverride", model.getAddressOverride());
        jsonObj.put("billSameAsShip", model.getBillSameAsShip());
        jsonObj.put("masterGuid", model.getMasterGuid());
        jsonObj.put("saveCreditCard", model.getSaveCreditCard());
        jsonObj.put("priceListId", model.getPriceListId());
        jsonObj.put("bisvendor", model.getBisvendor());
        jsonObj.put("bisaddref", model.getBisaddref());
        jsonObj.put("bisexteffortid", model.getBisexteffortid());
        jsonObj.put("bisextpaymentreference", model.getBisextpaymentreference());
        jsonObj.put("kcpResponseId", model.getKcpResponseId());
        jsonObj.put("promoOffer", model.getPromoOffer());
        jsonObj.put("instCustomer", model.getInstCustomer());
        jsonObj.put("partnerGuid", model.getPartnerGuid());

        Date startDate = model.getStartDate();

        String startDateJSON = StringPool.BLANK;

        if (startDate != null) {
            startDateJSON = String.valueOf(startDate.getTime());
        }

        jsonObj.put("startDate", startDateJSON);

        Date endDate = model.getEndDate();

        String endDateJSON = StringPool.BLANK;

        if (endDate != null) {
            endDateJSON = String.valueOf(endDate.getTime());
        }

        jsonObj.put("endDate", endDateJSON);

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Order[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Order model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.erp.lead.model.Order[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Order[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.erp.lead.model.Order> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (Order model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
