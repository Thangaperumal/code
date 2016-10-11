package com.mumms.service.diagnoses;

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
import com.mumms.model.diagnoses.DiagnosesDTO;
import com.mumms.model.diagnoses.DiagnosesDataTransformer;
import com.mumms.rest.HBRestClient;
import com.mumms.service.DataLoaderService;
import com.mumms.service.ResultExtractionService;
import com.mumms.utils.DataLoaderConstants;
import com.mumms.utils.HBUtil;

@Service
public class DiagnosesDataLoaderService implements DataLoaderService {

	@Autowired
	private ExcelFileReader<DiagnosesDTO> excelFileReader;

	@Autowired
	private ExcelFileWriter<DiagnosesDTO> excelFileWriter;

	@Autowired
	private DiagnosesDataTransformer diagnosesDataTransformer;

	@Autowired
	private HBRestClient<DiagnosesDTO> hbRestClient;

	@Autowired
	private ResultExtractionService<DiagnosesDTO> resultExtractionService;

	@Autowired
	private Environment env;

	private final static Logger log = Logger.getLogger(DiagnosesDataLoaderService.class.getName());

	private final int BATCH_SIZE = 10;

	@Override
	public void loadData(String fileName, String sheetName) throws RestLoaderException, IOException {

		// Initialize
		init();

		// Read
		List<FileData<DiagnosesDTO>> diagnoses = excelFileReader.readData(fileName, sheetName);

		// Transform
		log.info("Transforming the data read..");
		diagnoses = diagnosesDataTransformer.populateModelList(diagnoses);
		log.info("Data transformation complete..");

		// Load
		log.info("Loading the data into PIM..");
		List<FileData<DiagnosesDTO>> post = diagnoses.stream()
				.filter(data -> DataLoaderConstants.POST_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		List<FileData<DiagnosesDTO>> put = diagnoses.stream()
				.filter(data -> DataLoaderConstants.PUT_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		Map<String, List<FileData<DiagnosesDTO>>> groupedPostData = convertToMap(post);
		post = doPost(groupedPostData);

		List<FileData<DiagnosesDTO>> putList = new ArrayList<>();
		put.forEach(dataSheet -> {
			try {
				addPathParam(env.getProperty(DataLoaderConstants.PATIENT_ID_KEY),
						dataSheet.getEntity().getPatientNumber());
				addPathParam(env.getProperty(DataLoaderConstants.DIAGNOSE_ID_KEY), dataSheet.getEntity().getId());
				putList.add(doPut(dataSheet));
			} catch (RestLoaderException e) {
				e.printStackTrace();
			}
		});

		diagnoses = Stream.concat(post.stream(), putList.stream()).collect(Collectors.toList());
		log.info("Data loading complete..");

		// Write
		excelFileWriter.writeResult(fileName, diagnoses, sheetName);
	}

	private FileData<DiagnosesDTO> doPut(FileData<DiagnosesDTO> entity) throws RestLoaderException {
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
				RestApiError rae = response.readEntity(RestApiError.class);
				entity.setSuccess(false);
				entity.setResult(rae.toString());
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
			entity.setResult(e.getMessage());
		}
		return entity;
	}

	private void init() {
		setResource(DataLoaderConstants.DIAGNOSES_API_ENDPOINT);
		addQueryParam(DataLoaderConstants.AGENCY_PARAM_KEY, env.getProperty(DataLoaderConstants.AGENCY_PARAM_KEY));
	}

	private List<FileData<DiagnosesDTO>> doPost(Map<String, List<FileData<DiagnosesDTO>>> entitiesMap)
			throws RestLoaderException {
		List<FileData<DiagnosesDTO>> allPostedEntities = new ArrayList<>();
		entitiesMap.forEach((k, v) -> {
			List<FileData<DiagnosesDTO>> entities = v;
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
				List<FileData<DiagnosesDTO>> diagnoseBatch = entities.subList(fromIndex, toIndex);
				List<DiagnosesDTO> diagnoses = diagnoseBatch.stream().map(entity -> extractDiagnosesDTO(entity))
						.collect(Collectors.toCollection(ArrayList::new));
				try {
					addPathParam(env.getProperty(DataLoaderConstants.PATIENT_ID_KEY),
							diagnoses.get(0).getPatientNumber());
					Response response = hbRestClient.doPost(diagnoses);
					switch (response.getStatus()) {
					case 201:
						GenericType<List<PayloadStatus>> type = new GenericType<List<PayloadStatus>>() {
						};
						List<PayloadStatus> result = response.readEntity(type);
						resultExtractionService.processSuccessURI(diagnoseBatch, result);
						break;
					case 207:
						MultiStatusError mse = response.readEntity(MultiStatusError.class);
						resultExtractionService.processMultiStatusResult(diagnoseBatch, mse);
						break;
					case 400:
						RestApiError rae = response.readEntity(RestApiError.class);
						resultExtractionService.extractBadRequestResult(diagnoseBatch, rae);
						break;
					default:
					}
				} catch (MessageBodyProviderNotFoundException e) {
					log.warning(e.getMessage());
					diagnoseBatch.forEach(entity -> {
						entity.setSuccess(false);
						entity.setResult(e.getMessage());
					});
				} catch (RestLoaderException e) {
					log.warning(e.getMessage());
				}
				allPostedEntities.addAll(diagnoseBatch);
				listSize = listSize - BATCH_SIZE;
				fromIndex = toIndex;
			}
		});
		return allPostedEntities;
	}

	private static DiagnosesDTO extractDiagnosesDTO(FileData<DiagnosesDTO> entity) {
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

	public Map<String, List<FileData<DiagnosesDTO>>> convertToMap(List<FileData<DiagnosesDTO>> postList) {
		Map<String, List<FileData<DiagnosesDTO>>> result = postList.stream()
				.collect(Collectors.groupingBy(f -> f.getEntity().getPatientNumber()));
		return result;
	}
}