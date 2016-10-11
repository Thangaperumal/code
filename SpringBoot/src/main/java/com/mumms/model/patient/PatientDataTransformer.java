package com.mumms.model.patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mumms.model.DataTransformer;
import com.mumms.model.FileData;
import com.mumms.model.Phone;
import com.mumms.utils.HBUtil;

@Component
public class PatientDataTransformer implements DataTransformer<PatientDTO> {

	@Override
	public FileData<PatientDTO> populateModel(FileData<PatientDTO> dataSheet) {
		PatientDTO patient = new PatientDTO();
		int count = 1;

		Map<Integer, Object> data = dataSheet.getRowData();

		// Fields are set based on their column index in the file.
		patient.setId(HBUtil.convertNumberToString(data.get(count++)));
		patient.setPatientNumber((String) data.get(count++));
		patient.setSalutation((String) data.get(count++));
		patient.setFirstName((String) data.get(count++));
		patient.setLastName((String) data.get(count++));
		patient.setAddress((String) data.get(count++));
		patient.setAddress2((String) data.get(count++));
		patient.setCity((String) data.get(count++));
		patient.setState((String) data.get(count++));
		patient.setZip(HBUtil.convertNumberToString(data.get(count++)));
		patient.setDob((String) data.get(count++));
		patient.setSsn(HBUtil.convertNumberToString(data.get(count++)));
		patient.setMiddleInitial((String) data.get(count++));
		patient.setGender((String) data.get(count++));
		patient.setMaritalStatus((String) data.get(count++));
		patient.setEthnicity((String) data.get(count++));
		patient.setRace((String) data.get(count++));
		patient.setProgram((String) data.get(count++));
		patient.setVeteran((String) data.get(count++));
		patient.setAtRisk((String) data.get(count++));
		patient.setDoNotResuscitate((String) data.get(count++));

		List<Phone> phones = new ArrayList<Phone>();
		String cellNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(cellNumber)) {
			Phone cell = new Phone(cellNumber, "CELL");
			phones.add(cell);
		}
		String homeNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(homeNumber)) {
			Phone home = new Phone(homeNumber, "HOME");
			phones.add(home);
		}
		String workNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(workNumber)) {
			Phone work = new Phone(workNumber, "WORK");
			phones.add(work);
		}
		String officeNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(officeNumber)) {
			Phone office = new Phone(officeNumber, "OFFICE");
			phones.add(office);
		}
		String faxNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(faxNumber)) {
			Phone fax = new Phone(faxNumber, "FAX");
			phones.add(fax);
		}
		String otherNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(otherNumber)) {
			Phone other = new Phone(otherNumber, "OTHER");
			phones.add(other);
		}
		patient.setPhones(phones);
		dataSheet.setEntity(patient);
		dataSheet.setHasNumberColumn(true);
		return dataSheet;
	}

	@Override
	public List<FileData<PatientDTO>> populateModelList(List<FileData<PatientDTO>> sheetDataList) {
		sheetDataList.forEach(rowData -> {
			populateModel(rowData);
		});
		return sheetDataList;
	}
}