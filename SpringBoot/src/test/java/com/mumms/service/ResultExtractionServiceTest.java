package com.mumms.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.mumms.model.FileData;
import com.mumms.model.MultiStatusError;
import com.mumms.model.PayloadStatus;
import com.mumms.model.RestApiError;
import com.mumms.utils.DataLoaderConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ResultExtractionServiceTest {

	private Logger log = Logger.getLogger(ResultExtractionServiceTest.class.getName());
	private List<FileData<String>> entities;
	private final int quantity = 5;
	private final static String SUCCESS_URI = "http://api.mumms.com/api/internal/module/%d?agency=test";
	private final static String FAILURE_REASON = "Error";

	@Autowired
	private ResultExtractionService<String> resultExtractionService;

	@Before
	public void beforeTest() {
		// This runs before each test method is called
		entities = constructBlankEntities(quantity);
		log.info("----------- Test: START -----------");
	}

	@After
	public void afterTest() {
		// This runs after each test method is completed
		log.info("----------- Test: COMPLETE -----------");
	}

	@Test
	public void processSuccessResultTest() {
		log.info("Method: processSuccessResultTest, Class: ResultExtractionServiceTest");
		// Construct the entities
		List<Map<String, Object>> results = Stream.generate(LinkedHashMap<String, Object>::new).limit(quantity)
				.collect(Collectors.toList());
		List<Integer> idList = IntStream.range(0, quantity).mapToObj(i -> i++).collect(Collectors.toList());
		// Populate the result
		idList.forEach(index -> {
			results.get(index).put("id", index);
		});
		resultExtractionService.processSuccessResult(entities, results);
		idList.forEach(index -> {
			FileData<String> entity = entities.get(index);
			assertThat(entity.getSuccess()).isTrue();
			assertThat(entity.getResult()).isEqualTo(DataLoaderConstants.SUCCESS_MSG + index);
		});
	}

	@Test
	public void processSuccessResultWithNumberTest() {
		log.info("Method: processSuccessResultWithNumberTest, Class: ResultExtractionServiceTest");
		// Construct the entities
		List<Map<String, Object>> results = Stream.generate(LinkedHashMap<String, Object>::new).limit(quantity)
				.collect(Collectors.toList());
		List<Integer> idList = IntStream.range(0, quantity).mapToObj(i -> i++).collect(Collectors.toList());
		// Populate the result
		idList.forEach(index -> {
			results.get(index).put("id", index);
			results.get(index).put(DataLoaderConstants.PERSON_NUMBER, index * 10);
			entities.get(index).setHasNumberColumn(true);
		});
		resultExtractionService.processSuccessResult(entities, results, DataLoaderConstants.PERSON_NUMBER);
		idList.forEach(index -> {
			FileData<String> entity = entities.get(index);
			assertThat(entity.getSuccess()).isTrue();
			assertThat(entity.getResult()).isEqualTo(DataLoaderConstants.SUCCESS_MSG + index + ":" + index * 10);
		});
	}

	@Test
	public void processSuccessURITest() {
		log.info("Method: processSuccessURITest, Class: ResultExtractionServiceTest");
		// Construct the entities
		List<PayloadStatus> results = Stream.generate(PayloadStatus::new).limit(quantity).collect(Collectors.toList());
		List<Integer> idList = IntStream.range(0, quantity).mapToObj(i -> i++).collect(Collectors.toList());
		// Populate the result
		idList.forEach(index -> {
			results.get(index).setIdentifier(String.format(SUCCESS_URI, index));
		});
		resultExtractionService.processSuccessURI(entities, results);
		idList.forEach(index -> {
			FileData<String> entity = entities.get(index);
			assertThat(entity.getSuccess()).isTrue();
			assertThat(entity.getResult()).isEqualTo(DataLoaderConstants.SUCCESS_MSG + index);
		});
	}

	@Test
	public void extractBadRequestResultTest() {
		log.info("Method: extractBadRequestResultTest, Class: ResultExtractionServiceTest");
		// Construct the entities
		List<String> fields = Stream.of("0", "1", "2", "3", "4").collect(Collectors.toList());
		RestApiError result = new RestApiError();
		result.setMessage(FAILURE_REASON);
		result.setFields(fields);
		resultExtractionService.extractBadRequestResult(entities, result);
		fields.forEach(index -> {
			FileData<String> entity = entities.get(Integer.valueOf(index));
			assertThat(entity.getSuccess()).isFalse();
			assertThat(entity.getResult()).isEqualTo(DataLoaderConstants.ERROR_MSG + result.toString());
		});
	}

	@Test
	public void processMultiStatusResultTest() {
		log.info("Method: processMultiStatusResultTest, Class: ResultExtractionServiceTest");
		// Construct the entities
		List<PayloadStatus> results = Stream.generate(PayloadStatus::new).limit(quantity).collect(Collectors.toList());
		List<Integer> idList = IntStream.range(0, quantity).mapToObj(i -> i++).collect(Collectors.toList());
		// Populate the result
		idList.forEach(index -> {
			if (index % 2 == 1) {
				results.get(index).setIdentifier(String.format(SUCCESS_URI, index));
				results.get(index).setStatus(Status.CREATED.name());
			} else {
				results.get(index).setReason(FAILURE_REASON);
			}
		});
		MultiStatusError result = new MultiStatusError();
		result.setStatus(results);
		resultExtractionService.processMultiStatusResult(entities, result);
		idList.forEach(index -> {
			FileData<String> entity = entities.get(index);
			if (index % 2 == 1) {
				assertThat(entity.getSuccess()).isTrue();
				assertThat(entity.getResult()).isEqualTo(DataLoaderConstants.SUCCESS_MSG + index);
			} else {
				assertThat(entity.getSuccess()).isFalse();
				assertThat(entity.getResult()).isEqualTo(DataLoaderConstants.ERROR_MSG + FAILURE_REASON);
			}
		});
	}

	@Test
	public void processMultiStatusResultWithNumberTest() {
		log.info("Method: processMultiStatusResultWithNumberTest, Class: ResultExtractionServiceTest");
		// Construct the entities
		List<PayloadStatus> results = Stream.generate(PayloadStatus::new).limit(quantity).collect(Collectors.toList());
		List<Integer> idList = IntStream.range(0, quantity).mapToObj(i -> i++).collect(Collectors.toList());
		// Populate the result
		idList.forEach(index -> {
			if (index % 2 == 1) {
				results.get(index).setIdentifier(String.format(SUCCESS_URI, index));
				results.get(index).setStatus(Status.CREATED.name());
				Map<String, Object> entity = new LinkedHashMap<>();
				entity.put(DataLoaderConstants.PERSON_NUMBER, index * 10);
				results.get(index).setEntity(entity);
				entities.get(index).setHasNumberColumn(true);
			} else {
				results.get(index).setReason(FAILURE_REASON);
			}
		});
		MultiStatusError result = new MultiStatusError();
		result.setStatus(results);
		resultExtractionService.processMultiStatusResult(entities, result, DataLoaderConstants.PERSON_NUMBER);
		idList.forEach(index -> {
			FileData<String> entity = entities.get(index);
			if (index % 2 == 1) {
				assertThat(entity.getSuccess()).isTrue();
				assertThat(entity.getResult()).isEqualTo(DataLoaderConstants.SUCCESS_MSG + index + ":" + index * 10);
			} else {
				assertThat(entity.getSuccess()).isFalse();
				assertThat(entity.getResult()).isEqualTo(DataLoaderConstants.ERROR_MSG + FAILURE_REASON);
			}
		});
	}

	private List<FileData<String>> constructBlankEntities(int quantity) {
		return Stream.generate(FileData<String>::new).limit(quantity).collect(Collectors.toList());
	}

}
