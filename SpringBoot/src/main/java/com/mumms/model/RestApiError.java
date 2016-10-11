package com.mumms.model;

import java.util.List;

/**
 * This class holds the error code, message and the erroneous fields data which
 * is to be sent back from the Restful API endpoints to the user.
 * 
 * @author Rahul
 * 
 */
public class RestApiError {

	private Integer code = null;
	private String message = null;
	private List<String> fields = null;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	@Override
	public String toString() {
		return this.message + " " + this.fields;
	}

}