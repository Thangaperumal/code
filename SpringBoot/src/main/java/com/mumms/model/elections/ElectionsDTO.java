package com.mumms.model.elections;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ElectionsDTO {
	private String id = null;
	@JsonIgnore
	private String patientId= null;
	private String insuranceCoverageId= null;
	private String electionPeriod= null;
	private String carrierId= null;
	private String startDate= null;
	private String endDate= null;
	private CertificationDTO certification = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getInsuranceCoverageId() {
		return insuranceCoverageId;
	}
	public void setInsuranceCoverageId(String insuranceCoverageId) {
		this.insuranceCoverageId = insuranceCoverageId;
	}
	public String getElectionPeriod() {
		return electionPeriod;
	}
	public void setElectionPeriod(String electionPeriod) {
		this.electionPeriod = electionPeriod;
	}
	public String getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public CertificationDTO getCertification() {
		return certification;
	}
	public void setCertification(CertificationDTO certification) {
		this.certification = certification;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class NewElection {\n");
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    patientNumber: ").append(toIndentedString(patientId)).append("\n");
		sb.append("    insuranceCoverageId: ").append(toIndentedString(insuranceCoverageId)).append("\n");
		sb.append("    carrierId: ").append(toIndentedString(carrierId)).append("\n");
		sb.append("    electionPeriod: ").append(toIndentedString(electionPeriod)).append("\n");
		sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
		sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
		sb.append("    certifications: ").append(toIndentedString(certification.toString())).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
