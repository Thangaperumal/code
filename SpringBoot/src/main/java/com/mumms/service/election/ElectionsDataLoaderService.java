package com.mumms.service.election;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.mumms.excel.ExcelFileReader;
import com.mumms.excel.ExcelFileWriter;
import com.mumms.model.FileData;
import com.mumms.model.PayloadStatus;
import com.mumms.model.RestApiError;
import com.mumms.model.RestLoaderException;
import com.mumms.model.elections.ElectionsDTO;
import com.mumms.model.elections.ElectionsDataTransformer;
import com.mumms.rest.HBRestClient;
import com.mumms.service.DataLoaderService;
import com.mumms.utils.DataLoaderConstants;
import com.mumms.utils.HBUtil;

@Service
public class ElectionsDataLoaderService implements DataLoaderService {

	@Autowired
	private ExcelFileReader<ElectionsDTO> excelFileReader;

	@Autowired
	private ExcelFileWriter<ElectionsDTO> excelFileWriter;

	@Autowired
	private ElectionsDataTransformer electionsDataTransformer;

	@Autowired
	private HBRestClient<ElectionsDTO> hbRestClient;

	@Autowired
	private Environment env;

	private final static Logger log = Logger.getLogger(ElectionsDataLoaderService.class.getName());

	@Override
	public void loadData(String fileName, String sheetName) throws RestLoaderException, IOException {

		// Initialize
		init();

		// Read
		List<FileData<ElectionsDTO>> elections = excelFileReader.readData(fileName, sheetName);

		// Transform
		log.info("Transforming the data read..");
		elections = electionsDataTransformer.populateModelList(elections);
		log.info("Data transformation complete..");

		// Load
		log.info("Loading the data into PIM..");
		
		List<FileData<ElectionsDTO>> put = elections.stream()
				.collect(Collectors.toCollection(ArrayList::new));

		List<FileData<ElectionsDTO>> putList = new ArrayList<>();
		put.forEach(dataSheet -> {
			try {
				addPathParam(env.getProperty(DataLoaderConstants.PATIENT_ID_KEY),
						dataSheet.getEntity().getPatientId());
				addPathParam(env.getProperty(DataLoaderConstants.COVERAGES_ID_KEY),
						dataSheet.getEntity().getInsuranceCoverageId());
				addPathParam(env.getProperty(DataLoaderConstants.ELECTIONS_ID_KEY), dataSheet.getEntity().getId());
				putList.add(doPut(dataSheet));
			} catch (RestLoaderException e) {
				e.printStackTrace();
			}
		});

		elections = putList.stream().collect(Collectors.toList());
		log.info("Data loading complete..");

		// Write
		excelFileWriter.writeResult(fileName, elections, sheetName);
	}

	private FileData<ElectionsDTO> doPut(FileData<ElectionsDTO> entity) throws RestLoaderException {
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
			case 403:
			case 500:
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
		setResource(DataLoaderConstants.ELECTIONS_API_ENDPOINT);
		addQueryParam(DataLoaderConstants.AGENCY_PARAM_KEY, env.getProperty(DataLoaderConstants.AGENCY_PARAM_KEY));
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

	public Map<String, List<FileData<ElectionsDTO>>> convertToMap(List<FileData<ElectionsDTO>> postList) {
		Map<String, List<FileData<ElectionsDTO>>> result = postList.stream()
				.collect(Collectors.groupingBy(f -> f.getEntity().getPatientId()));
		return result;
	}
}