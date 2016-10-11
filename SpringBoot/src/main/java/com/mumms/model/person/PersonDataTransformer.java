package com.mumms.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mumms.model.DataTransformer;
import com.mumms.model.FileData;
import com.mumms.model.Phone;
import com.mumms.utils.HBUtil;

@Component
public class PersonDataTransformer implements DataTransformer<PersonDTO> {

	@Override
	public FileData<PersonDTO> populateModel(FileData<PersonDTO> dataSheet) {
		PersonDTO person = new PersonDTO();
		int count = 1;

		Map<Integer, Object> data = dataSheet.getRowData();

		// Fields are set based on their column index in the file.
		person.setId((String) data.get(count++));
		person.setPersonNumber((String) data.get(count++));
		person.setSalutation((String) data.get(count++));
		person.setFirstName((String) data.get(count++));
		person.setLastName((String) data.get(count++));
		person.setAddress((String) data.get(count++));
		person.setAddress2((String) data.get(count++));
		person.setCity((String) data.get(count++));
		person.setState((String) data.get(count++));
		person.setZip(HBUtil.convertNumberToString(data.get(count++)));
		person.setZip4(HBUtil.convertNumberToString(data.get(count++)));
		person.setDob((String) data.get(count++));
		person.setGender((String) data.get(count++));
		person.setEthnicity((String) data.get(count++));
		person.setRace((String) data.get(count++));
		person.setCredentials((String) data.get(count++));
		person.setEmail((String) data.get(count++));
		person.setLicenseNumber((String) data.get(count++));
		person.setLicenseExpDate((String) data.get(count++));
		person.setEmploymentStartDate((String) data.get(count++));
		person.setEmploymentEndDate((String) data.get(count++));
		person.setCompany((String) data.get(count++));
		person.setNpi(HBUtil.convertNumberToString(data.get(count++)));
		person.setActive(Boolean.valueOf((String) data.get(count++)));

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
		person.setPhones(phones);
		dataSheet.setEntity(person);
		dataSheet.setHasNumberColumn(true);
		return dataSheet;
	}

	@Override
	public List<FileData<PersonDTO>> populateModelList(List<FileData<PersonDTO>> sheetDataList) {
		sheetDataList.forEach(rowData -> {
			rowData = populateModel(rowData);
		});
		return sheetDataList;
	}
}