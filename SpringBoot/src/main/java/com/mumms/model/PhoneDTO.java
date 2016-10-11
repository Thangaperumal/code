package com.mumms.model;

public class PhoneDTO {

	private String phoneNumber;
	private String phoneType;

	public PhoneDTO(String number, String phoneType) {
		this.phoneNumber = number;
		this.phoneType = phoneType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
}