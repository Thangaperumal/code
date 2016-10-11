package com.mumms.service.team;

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
import com.mumms.model.team.TeamApiDTO;
import com.mumms.model.team.TeamDataTransformer;
import com.mumms.rest.HBRestClient;
import com.mumms.service.DataLoaderService;
import com.mumms.service.ResultExtractionService;
import com.mumms.utils.DataLoaderConstants;

@Service
public class TeamDataLoaderService implements DataLoaderService {

	@Autowired
	private ExcelFileReader<TeamApiDTO> excelFileReader;

	@Autowired
	private ExcelFileWriter<TeamApiDTO> excelFileWriter;

	@Autowired
	private TeamDataTransformer teamDataTransformer;

	@Autowired
	private HBRestClient<TeamApiDTO> hbRestClient;

	@Autowired
	private ResultExtractionService<TeamApiDTO> resultExtractionService;

	@Autowired
	private Environment env;

	private final static Logger log = Logger.getLogger(TeamDataLoaderService.class.getName());

	private final int BATCH_SIZE = 10;

	@Override
	public void loadData(String fileName, String sheetName) throws RestLoaderException, IOException {

		// Initialize
		init();

		// Read
		List<FileData<TeamApiDTO>> teams = excelFileReader.readData(fileName, sheetName);

		// Transform
		log.info("Transforming the data read..");
		teams = teamDataTransformer.populateModelList(teams);
		log.info("Data transformation complete..");

		// Load
		log.info("Loading the data into PIM..");
		List<FileData<TeamApiDTO>> post = teams.stream()
				.filter(data -> DataLoaderConstants.POST_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		List<FileData<TeamApiDTO>> put = teams.stream()
				.filter(data -> DataLoaderConstants.PUT_OPERATION.equalsIgnoreCase(data.getOperationType()))
				.collect(Collectors.toCollection(ArrayList::new));

		post = doPost(post);

		List<FileData<TeamApiDTO>> putList = new ArrayList<>();
		put.forEach(dataSheet -> {
			try {
				addPathParam(env.getProperty(DataLoaderConstants.TEAMS_ID_KEY), dataSheet.getEntity().getId());
				putList.add(doPut(dataSheet));
			} catch (RestLoaderException e) {
			}
		});

		teams = Stream.concat(post.stream(), putList.stream()).collect(Collectors.toList());
		log.info("Data loading complete..");

		// Write
		excelFileWriter.writeResult(fileName, teams, sheetName);
	}

	private void init() {
		setResource(DataLoaderConstants.TEAMS_API_ENDPOINT);
		addQueryParam(DataLoaderConstants.AGENCY_PARAM_KEY, env.getProperty(DataLoaderConstants.AGENCY_PARAM_KEY));
	}

	private FileData<TeamApiDTO> doPut(FileData<TeamApiDTO> entity) throws RestLoaderException {
		Response response = hbRestClient.doPut(entity.getEntity());
		try {
			switch (response.getStatus()) {
			case 200:
				GenericType<Map<String, Object>> type = new GenericType<Map<String, Object>>() {
				};
				Map<String, Object> result = response.readEntity(type);
				entity.setSuccess(true);
				entity.setResult(DataLoaderConstants.SUCCESS_MSG + result.get("id").toString() + ":"
						+ result.get(DataLoaderConstants.TEAM_NUMBER).toString());
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

	private List<FileData<TeamApiDTO>> doPost(List<FileData<TeamApiDTO>> entities) throws RestLoaderException {
		List<FileData<TeamApiDTO>> allPostedEntities = new ArrayList<>();
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
			List<FileData<TeamApiDTO>> teamsBatch = entities.subList(fromIndex, toIndex);
			List<TeamApiDTO> teams = teamsBatch.stream().map(entity -> extractTeamApiDTO(entity))
					.collect(Collectors.toCollection(ArrayList::new));
			Response response = hbRestClient.doPost(teams);
			try {
				switch (response.getStatus()) {
				case 201:
					GenericType<List<Map<String, Object>>> type = new GenericType<List<Map<String, Object>>>() {
					};
					List<Map<String, Object>> result = response.readEntity(type);
					resultExtractionService.processSuccessResult(teamsBatch, result, DataLoaderConstants.TEAM_NUMBER);
					break;
				case 207:
					MultiStatusError mse = response.readEntity(MultiStatusError.class);
					resultExtractionService.processMultiStatusResult(teamsBatch, mse, DataLoaderConstants.TEAM_NUMBER);
					break;
				case 400:
					RestApiError rae = response.readEntity(RestApiError.class);
					resultExtractionService.extractBadRequestResult(teamsBatch, rae);
					break;
				default:
				}
			} catch (MessageBodyProviderNotFoundException e) {
				log.warning(e.getMessage());
				teamsBatch.forEach(entity -> {
					entity.setSuccess(false);
					entity.setResult("Error : Reason Unknown");
				});
			}
			allPostedEntities.addAll(teamsBatch);
			listSize = listSize - BATCH_SIZE;
			fromIndex = toIndex;
		}
		return allPostedEntities;
	}

	private static TeamApiDTO extractTeamApiDTO(FileData<TeamApiDTO> entity) {
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