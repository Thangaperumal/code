package com.mumms.model;

public class Phone {

	private String number;
	private String phoneType;

	public Phone(String number, String phoneType) {
		this.number = number;
		this.phoneType = phoneType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
}