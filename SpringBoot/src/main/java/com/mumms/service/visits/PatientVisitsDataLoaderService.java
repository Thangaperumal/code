package com.mumms.service.visits;

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
import com.mumms.model.PayloadStatus;
import com.mumms.model.RestApiError;
import com.mumms.model.RestLoaderException;
import com.mumms.model.visits.PatientVisitDTO;
import com.mumms.model.visits.PatientVisitDataTransformer;
import com.mumms.rest.HBRestClient;
import com.mumms.service.DataLoaderService;
import com.mumms.service.ResultExtractionService;
import com.mumms.utils.DataLoaderConstants;
import com.mumms.utils.HBUtil;

@Service
public class PatientVisitsDataLoaderService implements DataLoaderService {

	@Autowired
	private ExcelFileReader<PatientVisitDTO> excelFileReader;

	@Autowired
	private ExcelFileWriter<PatientVisitDTO> excelFileWriter;

	@Autowired
	private PatientVisitDataTransformer patientVisitDataTransformer;

	@Autowired
	private HBRestClient<PatientVisitDTO> hbRestClient;

	@Autowired
	private ResultExtractionService<PatientVisitDTO> resultExtractionService;

	@Autowired
	private Environment env;

	private final static Logger log = Logger.getLogger(PatientVisitsDataLoaderService.class.getName());

	private final int BATCH_SIZE = 10;

	@Override
	public void loadData(String fileName, String sheetName) throws RestLoaderException, IOException {

		// Initialize
		init();

		// Read
		log.info("Reading data from the file..");
		List<FileData<PatientVisitDTO>> patientVisits = excelFileReader.readData(fileName, sheetName);
		log.info("Data read complete..");

		// Transform
		log.info("Transforming the data read..");
		patientVisits = patientVisitDataTransformer.populateModelList(patientVisits);
		log.info("Data transformation complete..");

		// Load
		log.info("Loading the data into PIM..");
		List<FileData<PatientVisitDTO>> post = patientVisits.stream()
				.filter(data -> DataLoaderConstants.POST_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		List<FileData<PatientVisitDTO>> put = patientVisits.stream()
				.filter(data -> DataLoaderConstants.PUT_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		Map<String, List<FileData<PatientVisitDTO>>> groupedPostData = convertToMap(post);
		post = doPost(groupedPostData);

		List<FileData<PatientVisitDTO>> putList = new ArrayList<>();
		put.forEach(dataSheet -> {
			try {
				addPathParam(env.getProperty(DataLoaderConstants.PATIENT_ID_KEY),
						dataSheet.getEntity().getPatientNumber());
				addPathParam(env.getProperty(DataLoaderConstants.PATIENT_VISIT_ID_KEY), dataSheet.getEntity().getId());
				putList.add(doPut(dataSheet));
			} catch (RestLoaderException e) {
			}
		});

		patientVisits = Stream.concat(post.stream(), putList.stream()).collect(Collectors.toList());
		log.info("Data loading complete..");

		// Write
		log.info("Writing the results back to the file..");
		excelFileWriter.writeResult(fileName, patientVisits, sheetName);
		log.info("Writing results complete..");
	}

	private void init() {
		setResource(DataLoaderConstants.PATIENT_VISITS_API_ENDPOINT);
		addQueryParam(DataLoaderConstants.AGENCY_PARAM_KEY, env.getProperty(DataLoaderConstants.AGENCY_PARAM_KEY));
		addQueryParam(DataLoaderConstants.SITE_PARAM_KEY, env.getProperty(DataLoaderConstants.SITE_PARAM_KEY));
	}

	private FileData<PatientVisitDTO> doPut(FileData<PatientVisitDTO> entity) throws RestLoaderException {
		Response response = hbRestClient.doPut(entity.getEntity());
		try {
			switch (response.getStatus()) {
			case 200:
				PayloadStatus result = response.readEntity(PayloadStatus.class);
				String id = HBUtil.getIdFromURI(result.getIdentifier());
				entity.setSuccess(true);
				entity.setResult(DataLoaderConstants.SUCCESS_MSG + id);
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

	private List<FileData<PatientVisitDTO>> doPost(Map<String, List<FileData<PatientVisitDTO>>> entitiesMap)
			throws RestLoaderException {
		List<FileData<PatientVisitDTO>> allPostedEntities = new ArrayList<>();
		entitiesMap.forEach((k, v) -> {
			List<FileData<PatientVisitDTO>> entities = v;
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
				List<FileData<PatientVisitDTO>> patientVisitBatch = entities.subList(fromIndex, toIndex);
				List<PatientVisitDTO> patientVisits = patientVisitBatch.stream()
						.map(entity -> extractPatientVisitDTO(entity)).collect(Collectors.toCollection(ArrayList::new));
				try {
					addPathParam(env.getProperty(DataLoaderConstants.PATIENT_ID_KEY),
							patientVisits.get(0).getPatientNumber());
					Response response = hbRestClient.doPost(patientVisits);

					switch (response.getStatus()) {
					case 201:
						GenericType<List<PayloadStatus>> type = new GenericType<List<PayloadStatus>>() {
						};
						List<PayloadStatus> result = response.readEntity(type);
						resultExtractionService.processSuccessURI(patientVisitBatch, result);
						break;
					case 207:
						MultiStatusError mse = response.readEntity(MultiStatusError.class);
						resultExtractionService.processMultiStatusResult(patientVisitBatch, mse);
						break;
					case 400:
						RestApiError rae = response.readEntity(RestApiError.class);
						resultExtractionService.extractBadRequestResult(patientVisitBatch, rae);
						break;
					default:
					}
				} catch (MessageBodyProviderNotFoundException e) {
					log.warning(e.getMessage());
					patientVisitBatch.forEach(entity -> {
						entity.setSuccess(false);
						entity.setResult("Error : Reason Unknown");
					});
				} catch (RestLoaderException e) {
					log.warning(e.getMessage());
				}
				allPostedEntities.addAll(patientVisitBatch);
				listSize = listSize - BATCH_SIZE;
				fromIndex = toIndex;
			}
		});
		return allPostedEntities;
	}

	private static PatientVisitDTO extractPatientVisitDTO(FileData<PatientVisitDTO> entity) {
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

	public Map<String, List<FileData<PatientVisitDTO>>> convertToMap(List<FileData<PatientVisitDTO>> postList) {
		Map<String, List<FileData<PatientVisitDTO>>> result = postList.stream()
				.collect(Collectors.groupingBy(f -> f.getEntity().getPatientNumber()));
		return result;
	}
}