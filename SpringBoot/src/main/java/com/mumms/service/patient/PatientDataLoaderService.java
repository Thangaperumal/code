package com.mumms.service.patient;

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
import com.mumms.model.patient.PatientDTO;
import com.mumms.model.patient.PatientDataTransformer;
import com.mumms.rest.HBRestClient;
import com.mumms.service.DataLoaderService;
import com.mumms.service.ResultExtractionService;
import com.mumms.utils.DataLoaderConstants;

@Service
public class PatientDataLoaderService implements DataLoaderService {

	@Autowired
	private ExcelFileReader<PatientDTO> excelFileReader;

	@Autowired
	private ExcelFileWriter<PatientDTO> excelFileWriter;

	@Autowired
	private PatientDataTransformer patientDataTransformer;

	@Autowired
	private HBRestClient<PatientDTO> hbRestClient;

	@Autowired
	private ResultExtractionService<PatientDTO> resultExtractionService;

	@Autowired
	private Environment env;

	private final static Logger log = Logger.getLogger(PatientDataLoaderService.class.getName());

	private final int BATCH_SIZE = 10;

	@Override
	public void loadData(String fileName, String sheetName) throws RestLoaderException, IOException {

		// Initialize
		init();

		// Read
		List<FileData<PatientDTO>> patients = excelFileReader.readData(fileName, sheetName);

		// Transform
		log.info("Transforming the data read..");
		patients = patientDataTransformer.populateModelList(patients);
		log.info("Data transformation complete..");

		// Load
		log.info("Loading the data into PIM..");
		List<FileData<PatientDTO>> post = patients.stream()
				.filter(data -> DataLoaderConstants.POST_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		List<FileData<PatientDTO>> put = patients.stream()
				.filter(data -> DataLoaderConstants.PUT_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		post = doPost(post);

		List<FileData<PatientDTO>> putList = new ArrayList<>();
		put.forEach(dataSheet -> {
			try {
				addPathParam(env.getProperty(DataLoaderConstants.PATIENT_ID_KEY),
						dataSheet.getEntity().getPatientNumber());
				putList.add(doPut(dataSheet));
			} catch (RestLoaderException e) {
			}
		});

		patients = Stream.concat(post.stream(), putList.stream()).collect(Collectors.toList());
		log.info("Data loading complete..");

		// Write
		excelFileWriter.writeResult(fileName, patients, sheetName);
	}

	private void init() {
		setResource(DataLoaderConstants.PATIENTS_API_ENDPOINT);
		addQueryParam(DataLoaderConstants.AGENCY_PARAM_KEY, env.getProperty(DataLoaderConstants.AGENCY_PARAM_KEY));
		addQueryParam(DataLoaderConstants.SITE_PARAM_KEY, env.getProperty(DataLoaderConstants.SITE_PARAM_KEY));
	}

	private FileData<PatientDTO> doPut(FileData<PatientDTO> entity) throws RestLoaderException {
		Response response = hbRestClient.doPut(entity.getEntity());
		try {
			switch (response.getStatus()) {
			case 200:
				GenericType<Map<String, Object>> type = new GenericType<Map<String, Object>>() {
				};
				Map<String, Object> result = response.readEntity(type);
				entity.setSuccess(true);
				entity.setResult(DataLoaderConstants.SUCCESS_MSG + result.get("id").toString() + ":"
						+ result.get(DataLoaderConstants.PATIENT_NUMBER).toString());
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

	public List<FileData<PatientDTO>> doPost(List<FileData<PatientDTO>> entities) throws RestLoaderException {
		List<FileData<PatientDTO>> allPostedEntities = new ArrayList<>();
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
			List<FileData<PatientDTO>> patientsBatch = entities.subList(fromIndex, toIndex);
			List<PatientDTO> patients = patientsBatch.stream().map(entity -> extractPatientDTO(entity))
					.collect(Collectors.toCollection(ArrayList::new));
			Response response = hbRestClient.doPost(patients);
			try {
				switch (response.getStatus()) {
				case 201:
					GenericType<List<Map<String, Object>>> type = new GenericType<List<Map<String, Object>>>() {
					};
					List<Map<String, Object>> result = response.readEntity(type);
					resultExtractionService.processSuccessResult(patientsBatch, result,
							DataLoaderConstants.PATIENT_NUMBER);
					break;
				case 207:
					MultiStatusError mse = response.readEntity(MultiStatusError.class);
					resultExtractionService.processMultiStatusResult(patientsBatch, mse,
							DataLoaderConstants.PATIENT_NUMBER);
					break;
				case 400:
					RestApiError rae = response.readEntity(RestApiError.class);
					resultExtractionService.extractBadRequestResult(patientsBatch, rae);
					break;
				case 404:
					log.severe("The REST Api Server is responding with 404 error. Unable to proceed.");
					break;
				default:
				}
			} catch (MessageBodyProviderNotFoundException e) {
				log.warning(e.getMessage());
				patientsBatch.forEach(entity -> {
					entity.setSuccess(false);
					entity.setResult("Error : Reason Unknown");
				});
			}
			allPostedEntities.addAll(patientsBatch);
			listSize = listSize - BATCH_SIZE;
			fromIndex = toIndex;
		}
		return allPostedEntities;
	}

	private static PatientDTO extractPatientDTO(FileData<PatientDTO> entity) {
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
}