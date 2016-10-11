package com.mumms.model.person;

import java.util.ArrayList;
import java.util.List;

import com.mumms.model.Phone;

public class PersonDTO {

	private String id;
	private String firstName;
	private String lastName;
	private String salutation;
	private String address;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String zip4;
	private String gender;
	private String ethnicity;
	private String ssn;
	private String licenseNumber;
	private String employmentStartDate;
	private String dob;
	private String email;
	private String credentials;
	private String personNumber;
	private String company;
	private String imageUrl;
	private String county;
	private String cbsa;
	private List<String> emailList = new ArrayList<String>();
	private List<Phone> phones = new ArrayList<Phone>();
	private String race;
	private String employmentEndDate;
	private String npi;
	private Boolean active;
	private String licenseExpDate;
	private Boolean hasLdapProfile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getZip4() {
		return zip4;
	}

	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getEmploymentStartDate() {
		return employmentStartDate;
	}

	public void setEmploymentStartDate(String employmentStartDate) {
		this.employmentStartDate = employmentStartDate;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCbsa() {
		return cbsa;
	}

	public void setCbsa(String cbsa) {
		this.cbsa = cbsa;
	}

	public List<String> getEmailList() {
		return emailList;
	}

	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getEmploymentEndDate() {
		return employmentEndDate;
	}

	public void setEmploymentEndDate(String employmentEndDate) {
		this.employmentEndDate = employmentEndDate;
	}

	public String getNpi() {
		return npi;
	}

	public void setNpi(String npi) {
		this.npi = npi;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getLicenseExpDate() {
		return licenseExpDate;
	}

	public void setLicenseExpDate(String licenseExpDate) {
		this.licenseExpDate = licenseExpDate;
	}

	public Boolean getHasLdapProfile() {
		return hasLdapProfile;
	}

	public void setHasLdapProfile(Boolean hasLdapProfile) {
		this.hasLdapProfile = hasLdapProfile;
	}

	@Override
	public String toString() {
		return "PersonDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", salutation="
				+ salutation + ", address=" + address + ", address2=" + address2 + ", city=" + city + ", state=" + state
				+ ", zip=" + zip + ", zip4=" + zip4 + ", gender=" + gender + ", ethnicity=" + ethnicity + ", ssn=" + ssn
				+ ", licenseNumber=" + licenseNumber + ", employmentStartDate=" + employmentStartDate + ", dob=" + dob
				+ ", email=" + email + ", credentials=" + credentials + ", personNumber=" + personNumber + ", company="
				+ company + ", imageUrl=" + imageUrl + ", county=" + county + ", cbsa=" + cbsa + ", emailList="
				+ emailList + ", phones=" + phones + ", race=" + race + ", employmentEndDate=" + employmentEndDate
				+ ", npi=" + npi + ", active=" + active + ", licenseExpDate=" + licenseExpDate + "]";
	}
}