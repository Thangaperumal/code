package com.mumms.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.mumms.excel.ExcelFileReader;
import com.mumms.excel.ExcelFileWriter;
import com.mumms.model.FileData;
import com.mumms.utils.DataLoaderConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ExcelFileWriterTest {

	private Logger log = Logger.getLogger(ExcelFileWriterTest.class.getName());
	private static final String TEST_FILE_PATH = "src/test/resources/test_file.xlsx";
	private final int quantity = 3;

	@Autowired
	private ExcelFileWriter<String> excelFileWriter;

	@Autowired
	private ExcelFileReader<String> excelFileReader;

	@Before
	public void beforeTest() {
		// This runs before each test method is called
		log.info("----------- Test: START -----------");
	}

	@After
	public void afterTest() {
		// This runs after each test method is completed
		log.info("----------- Test: COMPLETE -----------");
	}

	@Test
	public void writeResultColumnTest() {
		log.info("Method: writeResultColumnTest, Class: ExcelFileWriterTest");
		// Construct the entities
		List<FileData<String>> result = Stream.generate(FileData<String>::new).limit(quantity)
				.collect(Collectors.toList());
		List<Integer> idList = IntStream.range(0, quantity).mapToObj(i -> i++).collect(Collectors.toList());
		try {
			// Populate the result
			idList.forEach(index -> {
				FileData<String> data = result.get(index);
				data.setRowNumber(index + 4);
				if (index % 2 == 1) {
					data.setSuccess(true);
					data.setResult(DataLoaderConstants.SUCCESS_MSG + index);
				} else {
					data.setSuccess(false);
					data.setResult(DataLoaderConstants.ERROR_MSG);
				}
			});

			excelFileWriter.writeResult(TEST_FILE_PATH, result, "test");
		} catch (IOException ioe) {
			log.warning(ioe.getMessage());
		}

		try {
			List<FileData<String>> data = excelFileReader.readData(TEST_FILE_PATH, "test");
			idList.forEach(index -> {
				FileData<String> item = data.get(index);
				assertThat(item.getRowData()).isNotNull();
				if (index % 2 == 1) {
					assertThat(item.getRowData().get(1)).isNotNull();
					assertThat(item.getRowData().get(1)).isEqualTo(index.toString());
				} else {
					assertThat(item.getRowData().get(1)).isEqualTo("");
				}
			});
		} catch (IOException ioe) {
			log.warning(ioe.getMessage());
		}
	}

	@Test
	public void writeResultColumnWithNumberTest() {
		log.info("Method: writeResultColumnWithNumberTest, Class: ExcelFileWriterTest");
		// Construct the entities
		List<FileData<String>> result = Stream.generate(FileData<String>::new).limit(quantity)
				.collect(Collectors.toList());
		List<Integer> idList = IntStream.range(0, quantity).mapToObj(i -> i++).collect(Collectors.toList());
		try {
			// Populate the result
			idList.forEach(index -> {
				FileData<String> data = result.get(index);
				data.setRowNumber(index + 4);
				if (index % 2 == 1) {
					data.setSuccess(true);
					data.setResult(DataLoaderConstants.SUCCESS_MSG + index + ":" + index * 10);
					data.setHasNumberColumn(true);
				} else {
					data.setSuccess(false);
					data.setResult(DataLoaderConstants.ERROR_MSG);
				}
			});

			excelFileWriter.writeResult(TEST_FILE_PATH, result, "test");
		} catch (IOException ioe) {
			log.warning(ioe.getMessage());
		}

		try {
			List<FileData<String>> data = excelFileReader.readData(TEST_FILE_PATH, "test");
			idList.forEach(index -> {
				FileData<String> item = data.get(index);
				assertThat(item.getRowData()).isNotNull();
				if (index % 2 == 1) {
					assertThat(item.getRowData().get(1)).isNotNull();
					assertThat(item.getRowData().get(1)).isEqualTo(String.valueOf(index));
					assertThat(item.getRowData().get(2)).isEqualTo(String.valueOf(index * 10));
				} else {
					assertThat(item.getRowData().get(1)).isEqualTo("");
				}
			});
		} catch (IOException ioe) {
			log.warning(ioe.getMessage());
		}
	}
}
