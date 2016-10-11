package com.mumms.model;

import java.util.List;

public interface DataTransformer<T> {

	FileData<T> populateModel(FileData<T> data);

	List<FileData<T>> populateModelList(List<FileData<T>> data);
}
