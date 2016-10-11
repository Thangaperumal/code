package com.mumms.service;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import com.mumms.model.FileData;
import com.mumms.model.MultiStatusError;
import com.mumms.model.PayloadStatus;
import com.mumms.model.RestApiError;
import com.mumms.utils.DataLoaderConstants;
import com.mumms.utils.HBUtil;

@Service
public class ResultExtractionService<T> {

	public void processSuccessResult(List<FileData<T>> entities, List<Map<String, Object>> result) {
		processSuccessResult(entities, result, null);
	}

	public void processSuccessResult(List<FileData<T>> entities, List<Map<String, Object>> result, String fieldName) {
		int rowCount = 0;
		for (Map<String, Object> entry : result) {
			FileData<T> data = entities.get(rowCount++);
			data.setSuccess(true);
			String resultString = DataLoaderConstants.SUCCESS_MSG + entry.get("id").toString();
			if (data.getHasNumberColumn()) {
				resultString += ":" + entry.get(fieldName).toString();
			}
			data.setResult(resultString);
		}
	}

	public void processSuccessURI(List<FileData<T>> entities, List<PayloadStatus> result) {
		int rowCount = 0;
		for (PayloadStatus status : result) {
			FileData<T> data = entities.get(rowCount++);
			String id = HBUtil.getIdFromURI(status.getIdentifier());
			data.setSuccess(true);
			data.setResult(DataLoaderConstants.SUCCESS_MSG + id);
		}
	}

	public void processMultiStatusResult(List<FileData<T>> entities, MultiStatusError result) {
		processMultiStatusResult(entities, result, null);
	}

	public void processMultiStatusResult(List<FileData<T>> entities, MultiStatusError result, String fieldName) {
		int rowCount = 0;
		for (PayloadStatus status : result.getStatus()) {
			FileData<T> data = entities.get(rowCount++);
			if (Status.CREATED.name().equalsIgnoreCase(status.getStatus())
					|| Status.OK.name().equalsIgnoreCase(status.getStatus())) {
				data.setSuccess(true);
				String id = HBUtil.getIdFromURI(status.getIdentifier());
				String resultString = DataLoaderConstants.SUCCESS_MSG + id;
				if (data.getHasNumberColumn()) {
					resultString += ":" + status.getEntity().get(fieldName).toString();
				}
				data.setResult(resultString);
			} else {
				data.setSuccess(false);
				data.setResult(DataLoaderConstants.ERROR_MSG + status.getReason());
			}
		}
	}

	public void extractBadRequestResult(List<FileData<T>> entities, RestApiError result) {
		entities.forEach(data -> {
			data.setSuccess(false);
			data.setResult(DataLoaderConstants.ERROR_MSG + result.toString());
		});
	}
}