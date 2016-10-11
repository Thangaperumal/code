package com.mumms.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class PayloadStatus {

	private String identifier = null;
	private String status = null;
	private String reason = null;
	private Map<String, Object> entity = new LinkedHashMap<String, Object>();

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Map<String, Object> getEntity() {
		return entity;
	}

	public void setEntity(Map<String, Object> entity) {
		this.entity = entity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PayloadStatus payloadStatus = (PayloadStatus) o;
		return Objects.equals(identifier, payloadStatus.identifier) && Objects.equals(status, payloadStatus.status)
				&& Objects.equals(reason, payloadStatus.reason);
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifier, status, reason);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PayloadStatus {\n");

		sb.append("    identifier: ").append(toIndentedString(identifier)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}