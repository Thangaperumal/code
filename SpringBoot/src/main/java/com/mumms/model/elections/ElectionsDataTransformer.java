package com.mumms.model.elections;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mumms.model.DataTransformer;
import com.mumms.model.FileData;
import com.mumms.utils.HBUtil;

@Component
public class ElectionsDataTransformer implements DataTransformer<ElectionsDTO> {

	@Override
	public FileData<ElectionsDTO> populateModel(FileData<ElectionsDTO> dataSheet) {
		ElectionsDTO election = new ElectionsDTO();
		int count = 1;

		Map<Integer, Object> data = dataSheet.getRowData();
		// Fields are set based on their column index in the file.
		election.setId(HBUtil.convertNumberToString(data.get(count++)));
		election.setPatientId(HBUtil.convertNumberToString(data.get(count++)));
		election.setInsuranceCoverageId(HBUtil.convertNumberToString(data.get(count++)));
		election.setCarrierId(HBUtil.convertNumberToString(data.get(count++)));
		//election.setElectionPeriod(HBUtil.convertNumberToString(data.get(count++)));
		count++;
		//election.setStartDate(HBUtil.convertNumberToString(data.get(count++)));
		count++;
		//election.setEndDate((String)data.get(count++));
		count++;
		
		CertificationDTO certification = new CertificationDTO();
		certification.setId(HBUtil.convertNumberToString(data.get(count++)));
		certification.setF2fVisitId(HBUtil.convertNumberToString(data.get(count++)));
		certification.setF2fVisitPhysicianName((String)data.get(count++));
		certification.setAttendingPhysicianId(HBUtil.convertNumberToString(data.get(count++)));
		certification.setAttendingPhysicianName((String)data.get(count++));
		certification.setAttendingPhysicianVerbalDate((String)data.get(count++));
		certification.setAttendingPhysicianWrittenDate((String)data.get(count++));
		certification.setHospicePhysicianId(HBUtil.convertNumberToString(data.get(count++)));
		certification.setHospicePhysicianName((String)data.get(count++));
		certification.setHospicePhysicianVerbalDate((String)data.get(count++));
		certification.setHospicePhysicianWrittenDate((String)data.get(count++));
		certification.setCertificationType((String)data.get(count++));
		
		election.setCertification(certification);		

		dataSheet.setEntity(election);
		return dataSheet;
	}

	@Override
	public List<FileData<ElectionsDTO>> populateModelList(List<FileData<ElectionsDTO>> sheetDataList) {
		sheetDataList.forEach(rowData -> {
			populateModel(rowData);
		});
		return sheetDataList;
	}
}
