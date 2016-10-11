package com.mumms.model.diagnoses;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DiagnosesDTO {

	private String id = null;
	@JsonIgnore
	private String patientNumber;
	private String code = null;
	private String description = null;
	private String effectiveDate = null;
	private String rank = null;
	private String icd10diagnosis = null;
	private String icd10description = null;
	private String icd10ShortDescription = null;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getIcd10diagnosis() {
		return icd10diagnosis;
	}

	public void setIcd10diagnosis(String icd10diagnosis) {
		this.icd10diagnosis = icd10diagnosis;
	}

	public String getIcd10description() {
		return icd10description;
	}

	public void setIcd10description(String icd10description) {
		this.icd10description = icd10description;
	}

	public String getIcd10ShortDescription() {
		return icd10ShortDescription;
	}

	public void setIcd10ShortDescription(String icd10ShortDescription) {
		this.icd10ShortDescription = icd10ShortDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DiagnosesDTO diagnosisApiDTO = (DiagnosesDTO) o;
		return Objects.equals(id, diagnosisApiDTO.id) && Objects.equals(code, diagnosisApiDTO.code)
				&& Objects.equals(description, diagnosisApiDTO.description)
				&& Objects.equals(effectiveDate, diagnosisApiDTO.effectiveDate)
				&& Objects.equals(rank, diagnosisApiDTO.rank)
				&& Objects.equals(icd10diagnosis, diagnosisApiDTO.icd10diagnosis)
				&& Objects.equals(icd10description, diagnosisApiDTO.icd10description)
				&& Objects.equals(icd10ShortDescription, diagnosisApiDTO.icd10ShortDescription);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code, description, effectiveDate, rank, icd10diagnosis, icd10description,
				icd10ShortDescription);
	}

}
