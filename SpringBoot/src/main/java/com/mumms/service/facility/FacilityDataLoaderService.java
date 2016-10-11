package com.mumms.service.facility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import com.mumms.model.facility.FacilityDTO;
import com.mumms.model.facility.FacilityDataTransformer;
import com.mumms.rest.HBRestClient;
import com.mumms.service.DataLoaderService;
import com.mumms.service.ResultExtractionService;
import com.mumms.utils.DataLoaderConstants;
import com.mumms.utils.HBUtil;

@Service
public class FacilityDataLoaderService implements DataLoaderService {

	@Autowired
	private ExcelFileReader<FacilityDTO> excelFileReader;

	@Autowired
	private ExcelFileWriter<FacilityDTO> excelFileWriter;

	@Autowired
	private FacilityDataTransformer facilityDataTransformer;

	@Autowired
	private HBRestClient<FacilityDTO> hbRestClient;

	@Autowired
	private ResultExtractionService<FacilityDTO> resultExtractionService;

	@Autowired
	private Environment env;

	private final static Logger log = Logger.getLogger(FacilityDataLoaderService.class.getName());

	private final int BATCH_SIZE = 10;

	@Override
	public void loadData(String fileName, String sheetName) throws RestLoaderException, IOException {

		// Initialize
		init();

		// Read
		List<FileData<FacilityDTO>> facilities = excelFileReader.readData(fileName, sheetName);

		// Transform
		log.info("Transforming the data read..");
		facilities = facilityDataTransformer.populateModelList(facilities);
		log.info("Data transformation complete..");

		// Load
		log.info("Loading the data into PIM..");
		List<FileData<FacilityDTO>> post = facilities.stream()
				.filter(data -> DataLoaderConstants.POST_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		List<FileData<FacilityDTO>> put = facilities.stream()
				.filter(data -> DataLoaderConstants.PUT_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		post = doPost(post);

		List<FileData<FacilityDTO>> putList = new ArrayList<>();
		put.forEach(dataSheet -> {
			try {
				addPathParam(env.getProperty(DataLoaderConstants.FACILITY_ID_KEY), dataSheet.getEntity().getId());
				putList.add(doPut(dataSheet));
			} catch (RestLoaderException e) {
			}
		});

		facilities = Stream.concat(post.stream(), putList.stream()).collect(Collectors.toList());
		log.info("Data loading complete..");

		// Write
		excelFileWriter.writeResult(fileName, facilities, sheetName);
	}

	private FileData<FacilityDTO> doPut(FileData<FacilityDTO> entity) throws RestLoaderException {
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
			entity.setResult(DataLoaderConstants.UNKNOWN_ERROR_MSG);
		}
		return entity;
	}

	private void init() {
		setResource(DataLoaderConstants.FACILITIES_API_ENDPOINT);
		addQueryParam(DataLoaderConstants.AGENCY_PARAM_KEY, env.getProperty(DataLoaderConstants.AGENCY_PARAM_KEY));
	}

	private List<FileData<FacilityDTO>> doPost(List<FileData<FacilityDTO>> entities) throws RestLoaderException {
		List<FileData<FacilityDTO>> allPostedEntities = new ArrayList<>();
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
			List<FileData<FacilityDTO>> facilitiesBatch = entities.subList(fromIndex, toIndex);
			List<FacilityDTO> facilities = facilitiesBatch.stream().map(entity -> extractFacilityDTO(entity))
					.collect(Collectors.toCollection(ArrayList::new));
			Response response = hbRestClient.doPost(facilities);
			try {
				switch (response.getStatus()) {
				case 201:
					GenericType<List<PayloadStatus>> type = new GenericType<List<PayloadStatus>>() {
					};
					List<PayloadStatus> result = response.readEntity(type);
					resultExtractionService.processSuccessURI(facilitiesBatch, result);
					break;
				case 207:
					MultiStatusError mse = response.readEntity(MultiStatusError.class);
					resultExtractionService.processMultiStatusResult(facilitiesBatch, mse);
					break;
				case 400:
					RestApiError rae = response.readEntity(RestApiError.class);
					resultExtractionService.extractBadRequestResult(facilitiesBatch, rae);
					break;
				default:
				}
			} catch (MessageBodyProviderNotFoundException e) {
				log.warning(e.getMessage());
				facilitiesBatch.forEach(entity -> {
					entity.setSuccess(false);
					entity.setResult("Error : Reason Unknown");
				});
			}
			allPostedEntities.addAll(facilitiesBatch);
			listSize = listSize - BATCH_SIZE;
			fromIndex = toIndex;
		}
		return allPostedEntities;
	}

	private static FacilityDTO extractFacilityDTO(FileData<FacilityDTO> entity) {
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