package com.mumms.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.assertj.core.api.Condition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.mumms.excel.ExcelFileReader;
import com.mumms.model.FileData;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ExcelFileReaderTest {

	private Logger log = Logger.getLogger(ExcelFileReaderTest.class.getName());
	private static final String TEST_FILE_PATH = "src/test/resources/test_file.xlsx";
	private final Condition<String> PUT_OR_POST = new Condition<String>() {
		@Override
		public boolean matches(String arg0) {
			return arg0.equalsIgnoreCase("PUT") || arg0.equalsIgnoreCase("POST");
		}
	};

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
	public void readSheetContentsByNameTest() {
		log.info("Method: readSheetContentsBySheetNameTest, Class: ExcelFileReaderTest");
		try {
			// read data from sheet with the name "test"
			List<FileData<String>> data = excelFileReader.readData(TEST_FILE_PATH, "test");
			assertThat(data).isNotNull();
			data.forEach(item -> {
				String val = (String) item.getRowData().get(0);
				assertThat(val).is(PUT_OR_POST);
			});

			// read data from sheet with the name "test1" - invalid name
			data = excelFileReader.readData(TEST_FILE_PATH, "test1");
			assertThat(data).isEmpty();
		} catch (IOException ioe) {
			log.warning(ioe.getMessage());
		}
	}
}
