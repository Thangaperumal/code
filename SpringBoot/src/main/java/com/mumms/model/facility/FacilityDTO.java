package com.mumms.model.facility;

import java.util.ArrayList;
import java.util.List;

import com.mumms.model.Phone;

public class FacilityDTO {

	private String id;
	private String name;
	private Boolean billForRB;
	private String contact;
	private String flatRate;
	private String notes;
	private String npi;
	private String address;
	private String state;
	private String city;
	private String zip;
	private String rateTierGroupId;
	private String siteId;
	private List<Phone> phones = new ArrayList<Phone>();
	private List<String> facilityTypes = new ArrayList<String>();
	private List<String> offices = new ArrayList<String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getBillForRB() {
		return billForRB;
	}

	public void setBillForRB(Boolean billForRB) {
		this.billForRB = billForRB;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getFlatRate() {
		return flatRate;
	}

	public void setFlatRate(String flatRate) {
		this.flatRate = flatRate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNpi() {
		return npi;
	}

	public void setNpi(String npi) {
		this.npi = npi;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getRateTierGroupId() {
		return rateTierGroupId;
	}

	public void setRateTierGroupId(String rateTierGroupId) {
		this.rateTierGroupId = rateTierGroupId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public List<String> getFacilityTypes() {
		return facilityTypes;
	}

	public void setFacilityTypes(List<String> facilityTypes) {
		this.facilityTypes = facilityTypes;
	}

	public List<String> getOffices() {
		return offices;
	}

	public void setOffices(List<String> offices) {
		this.offices = offices;
	}

}
