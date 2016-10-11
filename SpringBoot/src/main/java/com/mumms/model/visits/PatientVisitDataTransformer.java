package com.mumms.model.visits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mumms.model.DataTransformer;
import com.mumms.model.FileData;
import com.mumms.utils.HBUtil;

@Component
public class PatientVisitDataTransformer implements DataTransformer<PatientVisitDTO> {

	@Override
	public FileData<PatientVisitDTO> populateModel(FileData<PatientVisitDTO> dataSheet) {
		PatientVisitDTO patientVisit = new PatientVisitDTO();
		int count = 1;

		Map<Integer, Object> data = dataSheet.getRowData();
		patientVisit.setId((String) data.get(count++));
		patientVisit.setPatientNumber((String) data.get(count++));
		patientVisit.setCareProviderNumber((String) data.get(count++));
		patientVisit.setCareProviderRole((String) data.get(count++));
		patientVisit.setVisitType((String) data.get(count++));
		patientVisit.setDuration(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setStartDate((String) data.get(count++));
		patientVisit.setArriveLat(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setArriveLon(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setDepartLat(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setDepartLon(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setParking(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setTolls(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setTransitFees(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setBillable((String) data.get(count++));
		patientVisit.setTravelMileage(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setTravelDuration(HBUtil.convertNumberToString(data.get(count++)));
		patientVisit.setFaceToFaceVisit(Boolean.valueOf((String) data.get(count++)));

		// Each Physician service constitutes 3 columns in the datasheet. 19
		// columns are present in the sheet before the physician services
		// columns
		int physicianServicesCount = (data.size() - 19) / 3;

		List<PhysicianServiceDTO> physicianServices = new ArrayList<>();
		for (int i = 1; i <= physicianServicesCount; i++) {
			String hcpcsCode = (String) data.get(count++);
			if (HBUtil.isNotBlank(hcpcsCode)) {
				PhysicianServiceDTO physicianService = new PhysicianServiceDTO();
				physicianService.setHcpcsCode(hcpcsCode);
				String diagnoses = (String) data.get(count++);
				if (HBUtil.isNotBlank(diagnoses)) {
					physicianService.setHcpcsDiagnoses(Arrays.asList(diagnoses.split(",")));
				}
				physicianService.setRate(HBUtil.convertNumberToString(data.get(count++)));
				physicianServices.add(physicianService);
			}
		}
		patientVisit.setPhysicianServices(physicianServices);
		dataSheet.setEntity(patientVisit);
		return dataSheet;
	}

	@Override
	public List<FileData<PatientVisitDTO>> populateModelList(List<FileData<PatientVisitDTO>> sheetDataList) {
		sheetDataList.forEach(rowData -> {
			populateModel(rowData);
		});
		return sheetDataList;
	}

}
