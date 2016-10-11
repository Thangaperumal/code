package com.mumms.service.person;

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
import com.mumms.model.person.PersonDTO;
import com.mumms.model.person.PersonDataTransformer;
import com.mumms.rest.HBRestClient;
import com.mumms.service.DataLoaderService;
import com.mumms.service.ResultExtractionService;
import com.mumms.utils.DataLoaderConstants;

@Service
public class PersonDataLoaderService implements DataLoaderService {

	@Autowired
	private ExcelFileReader<PersonDTO> excelFileReader;

	@Autowired
	private ExcelFileWriter<PersonDTO> excelFileWriter;

	@Autowired
	private PersonDataTransformer personDataTransformer;

	@Autowired
	private HBRestClient<PersonDTO> hbRestClient;

	@Autowired
	private ResultExtractionService<PersonDTO> resultExtractionService;

	@Autowired
	private Environment env;

	private final static Logger log = Logger.getLogger(PersonDataLoaderService.class.getName());

	private final int BATCH_SIZE = 10;

	@Override
	public void loadData(String fileName, String sheetName) throws RestLoaderException, IOException {

		// Initialize
		init();

		// Read
		List<FileData<PersonDTO>> persons = excelFileReader.readData(fileName, sheetName);

		// Transform
		log.info("Transforming the data read..");
		persons = personDataTransformer.populateModelList(persons);
		log.info("Data transformation complete..");

		// Load
		log.info("Loading the data into PIM..");
		List<FileData<PersonDTO>> post = persons.stream()
				.filter(data -> DataLoaderConstants.POST_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		List<FileData<PersonDTO>> put = persons.stream()
				.filter(data -> DataLoaderConstants.PUT_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		post = doPost(post);

		List<FileData<PersonDTO>> putList = new ArrayList<>();
		put.forEach(dataSheet -> {
			try {
				addPathParam(env.getProperty(DataLoaderConstants.PERSON_ID_KEY), dataSheet.getEntity().getId());
				putList.add(doPut(dataSheet));
			} catch (RestLoaderException e) {
				log.warning(e.getMessage());
			}
		});

		persons = Stream.concat(post.stream(), putList.stream()).collect(Collectors.toList());
		log.info("Data loading complete..");

		// Write
		excelFileWriter.writeResult(fileName, persons, sheetName);
	}

	private void init() {
		setResource(DataLoaderConstants.PERSONS_API_ENDPOINT);
		addQueryParam(DataLoaderConstants.AGENCY_PARAM_KEY, env.getProperty(DataLoaderConstants.AGENCY_PARAM_KEY));
		addQueryParam(DataLoaderConstants.SITE_PARAM_KEY, env.getProperty(DataLoaderConstants.SITE_PARAM_KEY));
	}

	private FileData<PersonDTO> doPut(FileData<PersonDTO> entity) throws RestLoaderException {
		Response response = hbRestClient.doPut(entity.getEntity());
		try {
			switch (response.getStatus()) {
			case 200:
				GenericType<Map<String, Object>> type = new GenericType<Map<String, Object>>() {
				};
				Map<String, Object> result = response.readEntity(type);
				entity.setSuccess(true);
				entity.setResult(DataLoaderConstants.SUCCESS_MSG + result.get("id").toString() + ":"
						+ result.get(DataLoaderConstants.PERSON_NUMBER).toString());
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

	private List<FileData<PersonDTO>> doPost(List<FileData<PersonDTO>> entities) throws RestLoaderException {
		List<FileData<PersonDTO>> allPostedEntities = new ArrayList<>();
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
			List<FileData<PersonDTO>> personsBatch = entities.subList(fromIndex, toIndex);
			List<PersonDTO> persons = personsBatch.stream().map(entity -> extractPersonDTO(entity))
					.collect(Collectors.toCollection(ArrayList::new));
			Response response = hbRestClient.doPost(persons);
			try {
				switch (response.getStatus()) {
				case 201:
					GenericType<List<Map<String, Object>>> type = new GenericType<List<Map<String, Object>>>() {
					};
					List<Map<String, Object>> result = response.readEntity(type);
					resultExtractionService.processSuccessResult(personsBatch, result,
							DataLoaderConstants.PERSON_NUMBER);
					break;
				case 207:
					MultiStatusError mse = response.readEntity(MultiStatusError.class);
					resultExtractionService.processMultiStatusResult(personsBatch, mse,
							DataLoaderConstants.PERSON_NUMBER);
					break;
				case 400:
					RestApiError rae = response.readEntity(RestApiError.class);
					resultExtractionService.extractBadRequestResult(personsBatch, rae);
					break;
				default:
				}
			} catch (MessageBodyProviderNotFoundException e) {
				log.warning(e.getMessage());
				personsBatch.forEach(entity -> {
					entity.setSuccess(false);
					entity.setResult("Error : Reason Unknown");
				});
			}
			allPostedEntities.addAll(personsBatch);
			listSize = listSize - BATCH_SIZE;
			fromIndex = toIndex;
		}
		return allPostedEntities;
	}

	private static PersonDTO extractPersonDTO(FileData<PersonDTO> entity) {
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