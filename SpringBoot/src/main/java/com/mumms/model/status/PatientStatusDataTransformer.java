package com.mumms.model.status;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mumms.model.DataTransformer;
import com.mumms.model.FileData;
import com.mumms.utils.HBUtil;

@Component
public class PatientStatusDataTransformer implements DataTransformer<PatientStatusDTO> {

	@Override
	public FileData<PatientStatusDTO> populateModel(FileData<PatientStatusDTO> dataSheet) {
		PatientStatusDTO patientStatus = new PatientStatusDTO();
		int count = 1;

		Map<Integer, Object> data = dataSheet.getRowData();
		// Fields are set based on their column index in the file.
		patientStatus.setId(HBUtil.convertNumberToString(data.get(count++)));
		patientStatus.setPatientNumber((String) data.get(count++));
		patientStatus.setProgramSiteId(HBUtil.convertNumberToString(data.get(count++)));
		patientStatus.setStatus((String) data.get(count++));
		patientStatus.setAdmitDate((String) data.get(count++));
		patientStatus.setReferralDate((String) data.get(count++));
		patientStatus.setRejectionDate((String) data.get(count++));
		patientStatus.setDischargeDate((String) data.get(count++));
		patientStatus.setEffectiveDate((String) data.get(count++));
		patientStatus.setDischargeReason((String) data.get(count++));
		patientStatus.setRejectReason((String) data.get(count++));
		patientStatus.setReasonForChange((String) data.get(count++));
		dataSheet.setEntity(patientStatus);
		return dataSheet;
	}

	@Override
	public List<FileData<PatientStatusDTO>> populateModelList(List<FileData<PatientStatusDTO>> sheetDataList) {
		sheetDataList.forEach(rowData -> {
			populateModel(rowData);
		});
		return sheetDataList;
	}
}
