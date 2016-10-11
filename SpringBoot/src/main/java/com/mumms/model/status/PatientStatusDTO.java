package com.mumms.model.status;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PatientStatusDTO {

	private String id;
	@JsonIgnore
	private String patientNumber;
	private String programSiteId;
	private String status;
	private String admitDate;
	private String referralDate;
	private String rejectionDate;
	private String dischargeDate;
	private String effectiveDate;
	private String dischargeReason;
	private String rejectReason;
	private String reasonForChange;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(String patientNumber) {
		this.patientNumber = patientNumber;
	}

	public String getProgramSiteId() {
		return programSiteId;
	}

	public void setProgramSiteId(String programSiteId) {
		this.programSiteId = programSiteId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAdmitDate() {
		return admitDate;
	}

	public void setAdmitDate(String admitDate) {
		this.admitDate = admitDate;
	}

	public String getReferralDate() {
		return referralDate;
	}

	public void setReferralDate(String referralDate) {
		this.referralDate = referralDate;
	}

	public String getRejectionDate() {
		return rejectionDate;
	}

	public void setRejectionDate(String rejectionDate) {
		this.rejectionDate = rejectionDate;
	}

	public String getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(String dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getDischargeReason() {
		return dischargeReason;
	}

	public void setDischargeReason(String dischargeReason) {
		this.dischargeReason = dischargeReason;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getReasonForChange() {
		return reasonForChange;
	}

	public void setReasonForChange(String reasonForChange) {
		this.reasonForChange = reasonForChange;
	}

}
