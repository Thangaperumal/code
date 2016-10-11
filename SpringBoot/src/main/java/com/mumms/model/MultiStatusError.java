package com.mumms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds multiple error codes, messages and the erroneous fields data
 * which is to be sent back from the Restful API endpoints to the user.
 * 
 * @author Rahul
 * 
 */
public class MultiStatusError {

	private Integer code = null;
	private String resourceType = null;
	private String operation = null;
	private String message = null;
	private List<PayloadStatus> status = new ArrayList<>();

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<PayloadStatus> getStatus() {
		return status;
	}

	public void setStatus(List<PayloadStatus> status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MultiStatusError [code=" + code + ", resourceType=" + resourceType + ", operation=" + operation
				+ ", message=" + message + ", status=" + status + "]";
	}
}