package com.mumms.model.ic;

import java.util.ArrayList;
import java.util.List;

import com.mumms.model.PhoneDTO;

public class InsuranceCarrierDTO {

	private String id = null;
	private String billingType = null;
	private String carrierName = null;
	private String contactPerson = null;
	private String payerId = null;
	private String payerType = null;
	private String hospice = null;
	private String receiverId = null;
	private String hospiceContractId = null;
	private String hospiceTaxonomy = null;
	private Boolean liveDischargeStatus = null;
	private String notes = null;
	private Boolean perVisitClaim = null;
	private String requireBillingForm = null;
	private Boolean requireBillingQCode = null;
	private Boolean requireContinuousCare = null;
	private Boolean requirePreauthorization = null;
	private Boolean requireProviderInfo = null;
	private String startDate = null;
	private String zirmedPayerId = null;
	private String address = null;
	private String address2 = null;
	private String city = null;
	private String state = null;
	private String zip = null;
	private Boolean signatureRequired = null;
	private String submitterId = null;
	private Boolean requireCarrierBox38 = null;
	private Boolean requireFirstLast = null;
	private Boolean includePhysicanServices = null;
	private List<PhoneDTO> phones = new ArrayList<PhoneDTO>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getHospiceContractId() {
		return hospiceContractId;
	}

	public void setHospiceContractId(String hospiceContractId) {
		this.hospiceContractId = hospiceContractId;
	}

	public String getHospiceTaxonomy() {
		return hospiceTaxonomy;
	}

	public void setHospiceTaxonomy(String hospiceTaxonomy) {
		this.hospiceTaxonomy = hospiceTaxonomy;
	}

	public Boolean getLiveDischargeStatus() {
		return liveDischargeStatus;
	}

	public void setLiveDischargeStatus(Boolean liveDischargeStatus) {
		this.liveDischargeStatus = liveDischargeStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getPayerType() {
		return payerType;
	}

	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}

	public Boolean getPerVisitClaim() {
		return perVisitClaim;
	}

	public void setPerVisitClaim(Boolean perVisitClaim) {
		this.perVisitClaim = perVisitClaim;
	}

	public String getRequireBillingForm() {
		return requireBillingForm;
	}

	public void setRequireBillingForm(String requireBillingForm) {
		this.requireBillingForm = requireBillingForm;
	}

	public Boolean getRequireBillingQCode() {
		return requireBillingQCode;
	}

	public void setRequireBillingQCode(Boolean requireBillingQCode) {
		this.requireBillingQCode = requireBillingQCode;
	}

	public Boolean getRequireContinuousCare() {
		return requireContinuousCare;
	}

	public void setRequireContinuousCare(Boolean requireContinuousCare) {
		this.requireContinuousCare = requireContinuousCare;
	}

	public Boolean getRequirePreauthorization() {
		return requirePreauthorization;
	}

	public void setRequirePreauthorization(Boolean requirePreauthorization) {
		this.requirePreauthorization = requirePreauthorization;
	}

	public Boolean getRequireProviderInfo() {
		return requireProviderInfo;
	}

	public void setRequireProviderInfo(Boolean requireProviderInfo) {
		this.requireProviderInfo = requireProviderInfo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getZirmedPayerId() {
		return zirmedPayerId;
	}

	public void setZirmedPayerId(String zirmedPayerId) {
		this.zirmedPayerId = zirmedPayerId;
	}

	public String getHospice() {
		return hospice;
	}

	public void setHospice(String hospice) {
		this.hospice = hospice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Boolean getSignatureRequired() {
		return signatureRequired;
	}

	public void setSignatureRequired(Boolean signatureRequired) {
		this.signatureRequired = signatureRequired;
	}

	public String getSubmitterId() {
		return submitterId;
	}

	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}

	public Boolean getRequireCarrierBox38() {
		return requireCarrierBox38;
	}

	public void setRequireCarrierBox38(Boolean requireCarrierBox38) {
		this.requireCarrierBox38 = requireCarrierBox38;
	}

	public Boolean getRequireFirstLast() {
		return requireFirstLast;
	}

	public void setRequireFirstLast(Boolean requireFirstLast) {
		this.requireFirstLast = requireFirstLast;
	}

	public Boolean getIncludePhysicanServices() {
		return includePhysicanServices;
	}

	public void setIncludePhysicanServices(Boolean includePhysicanServices) {
		this.includePhysicanServices = includePhysicanServices;
	}

	public List<PhoneDTO> getPhones() {
		return phones;
	}

	public void setPhones(List<PhoneDTO> phones) {
		this.phones = phones;
	}

}
