package com.mumms.model;

public class RestLoaderException extends Exception {

	private static final long serialVersionUID = -6079179010649451236L;

	private String field;

	public RestLoaderException(String message, String field) {
		super(message);
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
