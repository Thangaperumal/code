package com.mumms.service;

import java.io.IOException;

import com.mumms.model.RestLoaderException;

public interface DataLoaderService {

	public void loadData(String fileName, String sheetName) throws RestLoaderException, IOException;

}
