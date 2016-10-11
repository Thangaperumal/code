package com.mumms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FileUtil {

	public static List<String> getAllSheetNames(String fileName)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		FileInputStream xFile = new FileInputStream(new File(fileName));
		final Workbook xWorkbook = WorkbookFactory.create(xFile);
		List<String> sheetNames = new ArrayList<>();
		IntStream.range(0, xWorkbook.getNumberOfSheets()).forEach(index -> {
			sheetNames.add(xWorkbook.getSheetName(index));
		});
		xWorkbook.close();
		xFile.close();
		return sheetNames;
	}

	public static boolean isDataRow(Row row) {
		Iterator<Cell> cellIterator = row.cellIterator();
		if (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			Object cellData = getCellValue(cell);
			return (cell != null && cell.getColumnIndex() == 0 && cellData != null
					&& (DataLoaderConstants.POST_OPERATION.equalsIgnoreCase(cellData.toString())
							|| DataLoaderConstants.PUT_OPERATION.equalsIgnoreCase(cellData.toString())));
		}
		return false;
	}

	public static Object getCellValue(Cell cell) {
		if (cell != null) {
			return cell.toString();
		}
		return null;
	}

	public static boolean isStatusCell(Cell cell) {
		Object cellData = getCellValue(cell);
		return (cell != null && cellData != null
				&& DataLoaderConstants.STATUS_COLUMN_HEADER.equalsIgnoreCase(cellData.toString()));
	}

}