package com.mumms.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.mumms.utils.DataLoaderConstants;
import com.mumms.utils.FileUtil;
import com.mumms.utils.HBUtil;

@Component
public class ExcelFileWriter<T> {

	private final Logger log = Logger.getLogger(ExcelFileWriter.class.getName());

	private FileInputStream fis;
	private String fileName;
	private Workbook workbook;
	private Integer columnCount;

	private void init(String fileName) throws IOException {
		this.columnCount = 0;
		this.fileName = fileName;
		this.fis = new FileInputStream(new File(fileName));
		try {
			this.workbook = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
	}

	public void writeResult(String fileName, List<FileData<T>> result, String sheetName) throws IOException {
		log.info("Writing the results back to the file..");
		init(fileName);
		writeResultColumn(result, sheetName);
		close();
		log.info("Writing results complete..");
	}

	private void writeResultColumn(List<FileData<T>> result, String sheetName) {
		Sheet sheet = this.workbook.getSheet(sheetName);

		Row headerRow = sheet.getRow(0);
		Iterator<Cell> headerCellIterator = headerRow.cellIterator();
		while (headerCellIterator.hasNext()) {
			Cell cell = headerCellIterator.next();
			if (!FileUtil.isStatusCell(cell)) {
				columnCount++;
			}
		}
		Cell prevHeaderCell = headerRow.getCell(columnCount - 1);
		Cell statusHeaderCell = headerRow.getCell(columnCount, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		statusHeaderCell.setCellStyle(prevHeaderCell.getCellStyle());
		statusHeaderCell.setCellValue(DataLoaderConstants.STATUS_COLUMN_HEADER);

		result.forEach(data -> {
			Row dataRow = sheet.getRow(data.getRowNumber());
			Cell previousDataCell = dataRow.getCell(columnCount - 1);
			Cell statusDataCell = dataRow.getCell(columnCount, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			statusDataCell.setCellStyle(previousDataCell.getCellStyle());
			Cell idDataCell = dataRow.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			idDataCell.setCellStyle(previousDataCell.getCellStyle());
			String res = data.getResult();
			if (HBUtil.isNotBlank(res) && res.contains(DataLoaderConstants.SUCCESS_MSG)) {
				String[] splitResult = res.split(":");
				statusDataCell.setCellValue(splitResult[0]);
				idDataCell.setCellValue(splitResult[1]);
				if (data.getHasNumberColumn()) {
					Cell numberDataCell = dataRow.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					numberDataCell.setCellValue(splitResult[2]);
				}
			} else {
				statusDataCell.setCellValue(res);
				// Enable this if we need to clear the ID field during an error
				// idDataCell.setCellValue("");
			}
		});
		try {
			fis.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			FileOutputStream updatedFile = new FileOutputStream(new File(fileName));
			this.workbook.write(updatedFile);
			updatedFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			this.workbook.close();
			this.fis.close();
		} catch (IOException e) {
			log.warning(e.getMessage());
		} finally {
			this.workbook = null;
			this.fis = null;
			this.fileName = null;
			this.columnCount = 0;
		}
	}
}