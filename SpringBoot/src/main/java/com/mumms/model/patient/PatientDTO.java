package com.mumms.model.patient;

import java.util.List;

import com.mumms.model.Phone;

public class PatientDTO {

	private String id;
	private String patientNumber;
	private String ssn;
	private String salutation;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String suffix;
	private String address;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String dob;
	private String maritalStatus;
	private String gender;
	private String race;
	private String ethnicity;
	private List<Phone> phones;
	private String program;
	private String veteran;
	private String atRisk;
	private String doNotResuscitate;

	public void setId(String string) {
		this.id = string;
	}

	public String getId() {
		return id;
	}

	public String getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(String patientNumber) {
		this.patientNumber = patientNumber;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getVeteran() {
		return veteran;
	}

	public void setVeteran(String veteran) {
		this.veteran = veteran;
	}

	public String getAtRisk() {
		return atRisk;
	}

	public void setAtRisk(String atRisk) {
		this.atRisk = atRisk;
	}

	public String getDoNotResuscitate() {
		return doNotResuscitate;
	}

	public void setDoNotResuscitate(String doNotResuscitate) {
		this.doNotResuscitate = doNotResuscitate;
	}

	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PatientDTO {\n");
		sb.append("    patientNumber: ").append(toIndentedString(patientNumber)).append("\n");
		sb.append("    ssn: ").append(toIndentedString(ssn)).append("\n");
		sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
		sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
		sb.append("    middleInitial: ").append(toIndentedString(middleInitial)).append("\n");
		sb.append("    suffix: ").append(toIndentedString(suffix)).append("\n");
		sb.append("    address: ").append(toIndentedString(address)).append("\n");
		sb.append("    address2: ").append(toIndentedString(address2)).append("\n");
		sb.append("    city: ").append(toIndentedString(city)).append("\n");
		sb.append("    state: ").append(toIndentedString(state)).append("\n");
		sb.append("    zip: ").append(toIndentedString(zip)).append("\n");
		sb.append("    dob: ").append(toIndentedString(dob)).append("\n");
		sb.append("    maritalStatus: ").append(toIndentedString(maritalStatus)).append("\n");
		sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
		sb.append("    race: ").append(toIndentedString(race)).append("\n");
		sb.append("    ethnicity: ").append(toIndentedString(ethnicity)).append("\n");
		sb.append("    phones: ").append(toIndentedString(phones)).append("\n");
		sb.append("    program: ").append(toIndentedString(program)).append("\n");
		sb.append("    veteran: ").append(toIndentedString(veteran)).append("\n");
		sb.append("    atRisk: ").append(toIndentedString(atRisk)).append("\n");
		sb.append("    doNotResuscitate: ").append(toIndentedString(doNotResuscitate)).append("\n");
		sb.append("}");
		return sb.toString();
	}
}
