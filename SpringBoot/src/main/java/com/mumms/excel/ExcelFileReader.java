package com.mumms.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import com.mumms.model.FileData;
import com.mumms.utils.FileUtil;

@Component
public class ExcelFileReader<T> {

	private final Logger log = Logger.getLogger(ExcelFileReader.class.getName());

	private FileInputStream file;
	private Workbook workbook;

	private void init(String fileName) throws IOException {
		this.file = new FileInputStream(new File(fileName));
		try {
			this.workbook = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException ede) {
			log.warning(ede.getMessage());
		} catch (InvalidFormatException ife) {
			log.warning(ife.getMessage());
		}
	}

	public List<FileData<T>> readData(String fileName, String sheetName) throws IOException {
		log.info("Reading data from the file..");
		init(fileName);
		Sheet sheet = this.workbook.getSheet(sheetName);
		List<FileData<T>> entities = readSheetContents(sheet);
		log.info("Total records :"+entities.size());
		close();
		log.info("Data read complete..");
		return entities;
	}

	private List<FileData<T>> readSheetContents(Sheet sheet) {
		List<FileData<T>> sheetData = new ArrayList<FileData<T>>();
		if (sheet == null) {
			return sheetData;
		}
		for (Iterator<Row> iterator = sheet.iterator(); iterator.hasNext();) {
			Row nextRow = iterator.next();
			if (!FileUtil.isDataRow(nextRow)) {
				continue;
			}
			FileData<T> data = new FileData<T>();
			data.setRowNumber(nextRow.getRowNum());
			for (Iterator<Cell> cellIterator = nextRow.cellIterator(); cellIterator.hasNext();) {
				Cell cell = cellIterator.next();
				Object cellData = FileUtil.getCellValue(cell);
				data.addRowData(cell.getColumnIndex(), cellData);
			}
			sheetData.add(data);
		}
		return sheetData;
	}

	private void close() {
		try {
			this.workbook.close();
			this.file.close();
		} catch (IOException e) {
			log.warning(e.getMessage());
		} finally {
			this.workbook = null;
			this.file = null;
		}
	}
}
