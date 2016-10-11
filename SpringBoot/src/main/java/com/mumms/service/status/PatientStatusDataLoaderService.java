package com.mumms.service.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.mumms.excel.ExcelFileReader;
import com.mumms.excel.ExcelFileWriter;
import com.mumms.model.FileData;
import com.mumms.model.MultiStatusError;
import com.mumms.model.RestApiError;
import com.mumms.model.RestLoaderException;
import com.mumms.model.status.PatientStatusDTO;
import com.mumms.model.status.PatientStatusDataTransformer;
import com.mumms.rest.HBRestClient;
import com.mumms.service.DataLoaderService;
import com.mumms.service.ResultExtractionService;
import com.mumms.utils.DataLoaderConstants;

@Service
public class PatientStatusDataLoaderService implements DataLoaderService {

	@Autowired
	private ExcelFileReader<PatientStatusDTO> excelFileReader;

	@Autowired
	private ExcelFileWriter<PatientStatusDTO> excelFileWriter;

	@Autowired
	private PatientStatusDataTransformer patientStatusDataTransformer;

	@Autowired
	private HBRestClient<PatientStatusDTO> hbRestClient;

	@Autowired
	private ResultExtractionService<PatientStatusDTO> resultExtractionService;

	@Autowired
	private Environment env;

	private final static Logger log = Logger.getLogger(PatientStatusDataLoaderService.class.getName());

	private final int BATCH_SIZE = 10;

	@Override
	public void loadData(String fileName, String sheetName) throws RestLoaderException, IOException {

		// Initialize
		init();

		// Read
		List<FileData<PatientStatusDTO>> patientStatuses = excelFileReader.readData(fileName, sheetName);

		// Transform
		log.info("Transforming the data read..");
		patientStatuses = patientStatusDataTransformer.populateModelList(patientStatuses);
		log.info("Data transformation complete..");

		// Load
		log.info("Loading the data into PIM..");
		List<FileData<PatientStatusDTO>> post = patientStatuses.stream()
				.filter(data -> DataLoaderConstants.POST_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		List<FileData<PatientStatusDTO>> put = patientStatuses.stream()
				.filter(data -> DataLoaderConstants.PUT_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		Map<String, List<FileData<PatientStatusDTO>>> groupedPostData = convertToMap(post);
		post = doPost(groupedPostData);

		List<FileData<PatientStatusDTO>> putList = new ArrayList<>();
		put.forEach(dataSheet -> {
			try {
				addPathParam(env.getProperty(DataLoaderConstants.PATIENT_ID_KEY),
						dataSheet.getEntity().getPatientNumber());
				addPathParam(env.getProperty(DataLoaderConstants.PATIENT_STATUS_ID_KEY), dataSheet.getEntity().getId());
				putList.add(doPut(dataSheet));
			} catch (RestLoaderException e) {
			}
		});

		patientStatuses = Stream.concat(post.stream(), putList.stream()).collect(Collectors.toList());
		log.info("Data loading complete..");

		// Write
		excelFileWriter.writeResult(fileName, patientStatuses, sheetName);
	}

	private void init() {
		setResource(DataLoaderConstants.PATIENT_STATUSES_API_ENDPOINT);
		addQueryParam(DataLoaderConstants.AGENCY_PARAM_KEY, env.getProperty(DataLoaderConstants.AGENCY_PARAM_KEY));
		addQueryParam(DataLoaderConstants.SITE_PARAM_KEY, env.getProperty(DataLoaderConstants.SITE_PARAM_KEY));
	}

	private FileData<PatientStatusDTO> doPut(FileData<PatientStatusDTO> entity) throws RestLoaderException {
		Response response = hbRestClient.doPut(entity.getEntity());
		try {
			switch (response.getStatus()) {
			case 200:
				GenericType<Map<String, Object>> type = new GenericType<Map<String, Object>>() {
				};
				Map<String, Object> result = response.readEntity(type);
				entity.setSuccess(true);
				entity.setResult(DataLoaderConstants.SUCCESS_MSG + result.get("id").toString());
				break;
			case 400:
				RestApiError badRequestError = response.readEntity(RestApiError.class);
				entity.setSuccess(false);
				entity.setResult(DataLoaderConstants.ERROR_MSG + badRequestError.toString());
				break;
			case 404:
				RestApiError notFoundError = response.readEntity(RestApiError.class);
				entity.setSuccess(false);
				entity.setResult(DataLoaderConstants.ERROR_MSG + notFoundError.toString());
				break;
			default:
			}
		} catch (MessageBodyProviderNotFoundException e) {
			log.warning(e.getMessage());
			entity.setSuccess(false);
			entity.setResult(DataLoaderConstants.UNKNOWN_ERROR_MSG);
		}
		return entity;
	}

	private List<FileData<PatientStatusDTO>> doPost(Map<String, List<FileData<PatientStatusDTO>>> entitiesMap)
			throws RestLoaderException {
		List<FileData<PatientStatusDTO>> allPostedEntities = new ArrayList<>();
		entitiesMap.forEach((k, v) -> {
			List<FileData<PatientStatusDTO>> entities = v;
			int listSize = 0;
			if (entities != null) {
				listSize = entities.size();
			}
			int fromIndex = 0;
			int toIndex = 0;
			while (listSize > 0) {
				if (listSize >= BATCH_SIZE) {
					toIndex += BATCH_SIZE;
				} else {
					toIndex += listSize;
				}
				List<FileData<PatientStatusDTO>> patientStatusBatch = entities.subList(fromIndex, toIndex);
				List<PatientStatusDTO> patientStatuses = patientStatusBatch.stream()
						.map(entity -> extractPatientStatusDTO(entity))
						.collect(Collectors.toCollection(ArrayList::new));
				try {
					addPathParam(env.getProperty(DataLoaderConstants.PATIENT_ID_KEY),
							patientStatuses.get(0).getPatientNumber());
					Response response = hbRestClient.doPost(patientStatuses);

					switch (response.getStatus()) {
					case 201:
						GenericType<List<Map<String, Object>>> type = new GenericType<List<Map<String, Object>>>() {
						};
						List<Map<String, Object>> result = response.readEntity(type);
						resultExtractionService.processSuccessResult(patientStatusBatch, result);
						break;
					case 207:
						MultiStatusError mse = response.readEntity(MultiStatusError.class);
						resultExtractionService.processMultiStatusResult(patientStatusBatch, mse);
						break;
					case 400:
						RestApiError rae = response.readEntity(RestApiError.class);
						resultExtractionService.extractBadRequestResult(patientStatusBatch, rae);
						break;
					default:
					}
				} catch (MessageBodyProviderNotFoundException e) {
					log.warning(e.getMessage());
					patientStatusBatch.forEach(entity -> {
						entity.setSuccess(false);
						entity.setResult("Error : Reason Unknown");
					});
				} catch (RestLoaderException e) {
					log.warning(e.getMessage());
				}
				allPostedEntities.addAll(patientStatusBatch);
				listSize = listSize - BATCH_SIZE;
				fromIndex = toIndex;
			}
		});
		return allPostedEntities;
	}

	private static PatientStatusDTO extractPatientStatusDTO(FileData<PatientStatusDTO> entity) {
		return entity.getEntity();
	}

	public void setResource(String resource) {
		hbRestClient.setResource(resource);
	}

	public void addPathParam(String key, String value) {
		hbRestClient.addPathParam(key, value);
	}

	public void addQueryParam(String key, String value) {
		hbRestClient.addQueryParam(key, value);
	}

	public Map<String, List<FileData<PatientStatusDTO>>> convertToMap(List<FileData<PatientStatusDTO>> postList) {
		Map<String, List<FileData<PatientStatusDTO>>> result = postList.stream()
				.collect(Collectors.groupingBy(f -> f.getEntity().getPatientNumber()));
		return result;
	}
}