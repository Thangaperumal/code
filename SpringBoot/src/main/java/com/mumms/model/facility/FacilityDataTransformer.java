package com.mumms.model.facility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mumms.model.DataTransformer;
import com.mumms.model.FileData;
import com.mumms.model.Phone;
import com.mumms.utils.HBUtil;

@Component
public class FacilityDataTransformer implements DataTransformer<FacilityDTO> {

	@Override
	public FileData<FacilityDTO> populateModel(FileData<FacilityDTO> dataSheet) {
		FacilityDTO facility = new FacilityDTO();
		int count = 1;

		Map<Integer, Object> data = dataSheet.getRowData();
		// Fields are set based on their column index in the file.
		facility.setId(HBUtil.convertNumberToString(data.get(count++)));
		facility.setName((String) data.get(count++));
		facility.setBillForRB(Boolean.valueOf((String) data.get(count++)));
		facility.setContact((String) data.get(count++));
		facility.setFlatRate(HBUtil.convertNumberToString(data.get(count++)));
		facility.setNotes((String) data.get(count++));
		facility.setNpi((String) data.get(count++));
		facility.setAddress((String) data.get(count++));
		facility.setState((String) data.get(count++));
		facility.setCity((String) data.get(count++));
		facility.setZip(HBUtil.convertNumberToString(data.get(count++)));
		facility.setRateTierGroupId(HBUtil.convertNumberToString(data.get(count++)));
		facility.setSiteId(HBUtil.convertNumberToString(data.get(count++)));
		List<Phone> phones = new ArrayList<Phone>();
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
		facility.setPhones(phones);
		facility.setFacilityTypes(Arrays.asList((String) data.get(count++)));
		String offices = (String) data.get(count++);
		if (HBUtil.isNotBlank(offices)) {
			facility.setOffices(Arrays.asList(offices.split(",")));
		}
		dataSheet.setEntity(facility);
		return dataSheet;
	}

	@Override
	public List<FileData<FacilityDTO>> populateModelList(List<FileData<FacilityDTO>> sheetDataList) {
		sheetDataList.forEach(rowData -> {
			populateModel(rowData);
		});
		return sheetDataList;
	}
}
