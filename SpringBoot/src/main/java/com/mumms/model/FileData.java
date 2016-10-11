package com.mumms.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.mumms.utils.DataLoaderConstants;
import com.mumms.utils.HBUtil;

public class FileData<T> {

	private Integer rowNumber;
	private String operationType;
	private Map<Integer, Object> rowData = new LinkedHashMap<>();
	private String result;
	private Boolean success;
	private T entity;
	private boolean hasNumberColumn = false;

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Map<Integer, Object> getRowData() {
		return rowData;
	}

	public void setRowData(Map<Integer, Object> rowData) {
		this.rowData = rowData;
	}

	public void addRows(Map<Integer, Object> rowData) {
		this.rowData.putAll(rowData);
	}

	public void addRowData(Integer key, Object value) {
		if (key == 1) {
			if (HBUtil.isNotBlank(value)) {
				this.operationType = DataLoaderConstants.PUT_OPERATION;
			} else {
				this.operationType = DataLoaderConstants.POST_OPERATION;
			}
		}
		this.rowData.put(key, value);
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public boolean getHasNumberColumn() {
		return hasNumberColumn;
	}

	public void setHasNumberColumn(boolean hasNumberColumn) {
		this.hasNumberColumn = hasNumberColumn;
	}

	@Override
	public String toString() {
		return "FileData [rowNumber=" + rowNumber + ", operationType=" + operationType + ", result=" + result
				+ ", success=" + success + "]";
	}
}
