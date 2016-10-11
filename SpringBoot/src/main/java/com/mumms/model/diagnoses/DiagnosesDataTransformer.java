package com.mumms.model.diagnoses;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mumms.model.DataTransformer;
import com.mumms.model.FileData;
import com.mumms.utils.HBUtil;

@Component
public class DiagnosesDataTransformer implements DataTransformer<DiagnosesDTO> {

	@Override
	public FileData<DiagnosesDTO> populateModel(FileData<DiagnosesDTO> dataSheet) {
		DiagnosesDTO diagnose = new DiagnosesDTO();
		int count = 1;

		Map<Integer, Object> data = dataSheet.getRowData();
		// Fields are set based on their column index in the file.
		diagnose.setId(HBUtil.convertNumberToString(data.get(count++)));
		diagnose.setPatientNumber(HBUtil.convertNumberToString(data.get(count++)));
		if (diagnose.getId()==null || diagnose.getId().trim().equals("")) {
			diagnose.setCode((String) data.get(count++));
		}
		else {
			count++;
		}
		diagnose.setDescription((String) data.get(count++));
		diagnose.setEffectiveDate((String) data.get(count++));
		diagnose.setRank(HBUtil.convertNumberToString(data.get(count++)));
		diagnose.setIcd10diagnosis((String) data.get(count++));
		diagnose.setIcd10description((String) data.get(count++));
		diagnose.setIcd10ShortDescription((String) data.get(count++));

		dataSheet.setEntity(diagnose);
		return dataSheet;
	}

	@Override
	public List<FileData<DiagnosesDTO>> populateModelList(List<FileData<DiagnosesDTO>> sheetDataList) {
		sheetDataList.forEach(rowData -> {
			populateModel(rowData);
		});
		return sheetDataList;
	}
}
