package com.mumms.model.ic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mumms.model.DataTransformer;
import com.mumms.model.FileData;
import com.mumms.model.PhoneDTO;
import com.mumms.utils.HBUtil;

@Component
public class InsuranceCarrierDataTransformer implements DataTransformer<InsuranceCarrierDTO> {

	@Override
	public FileData<InsuranceCarrierDTO> populateModel(FileData<InsuranceCarrierDTO> dataSheet) {
		InsuranceCarrierDTO carrier = new InsuranceCarrierDTO();
		int count = 1;

		Map<Integer, Object> data = dataSheet.getRowData();

		// Fields are set based on their column index in the file.
		carrier.setId((String) data.get(count++));
		carrier.setBillingType((String) data.get(count++));
		carrier.setCarrierName((String) data.get(count++));
		carrier.setContactPerson((String) data.get(count++));
		carrier.setPayerId((String) data.get(count++));
		carrier.setPayerType((String) data.get(count++));
		// carrier.setHospice((String) data.get(count++));
		carrier.setReceiverId((String) data.get(count++));
		carrier.setHospiceContractId((String) data.get(count++));
		carrier.setHospiceTaxonomy((String) data.get(count++));
		carrier.setLiveDischargeStatus(Boolean.valueOf((String) data.get(count++)));
		carrier.setNotes((String) data.get(count++));
		carrier.setPerVisitClaim(Boolean.valueOf((String) data.get(count++)));
		carrier.setRequireBillingForm((String) data.get(count++));
		carrier.setRequireBillingQCode(Boolean.valueOf((String) data.get(count++)));
		carrier.setRequireContinuousCare(Boolean.valueOf((String) data.get(count++)));
		carrier.setRequirePreauthorization(Boolean.valueOf((String) data.get(count++)));
		carrier.setRequireProviderInfo(Boolean.valueOf((String) data.get(count++)));
		carrier.setStartDate((String) data.get(count++));
		carrier.setZirmedPayerId((String) data.get(count++));
		carrier.setAddress((String) data.get(count++));
		carrier.setAddress2((String) data.get(count++));
		carrier.setCity((String) data.get(count++));
		carrier.setState((String) data.get(count++));
		carrier.setZip(HBUtil.convertNumberToString(data.get(count++)));
		carrier.setSignatureRequired(Boolean.valueOf((String) data.get(count++)));
		carrier.setSubmitterId((String) data.get(count++));
		carrier.setRequireCarrierBox38(Boolean.valueOf((String) data.get(count++)));
		carrier.setRequireFirstLast(Boolean.valueOf((String) data.get(count++)));
		carrier.setIncludePhysicanServices(Boolean.valueOf((String) data.get(count++)));

		List<PhoneDTO> phones = new ArrayList<PhoneDTO>();
		String cellNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(cellNumber)) {
			PhoneDTO cell = new PhoneDTO(cellNumber, "CELL");
			phones.add(cell);
		}
		String homeNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(homeNumber)) {
			PhoneDTO home = new PhoneDTO(homeNumber, "HOME");
			phones.add(home);
		}
		String workNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(workNumber)) {
			PhoneDTO work = new PhoneDTO(workNumber, "WORK");
			phones.add(work);
		}
		String officeNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(officeNumber)) {
			PhoneDTO office = new PhoneDTO(officeNumber, "OFFICE");
			phones.add(office);
		}
		String faxNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(faxNumber)) {
			PhoneDTO fax = new PhoneDTO(faxNumber, "FAX");
			phones.add(fax);
		}
		String otherNumber = HBUtil.convertNumberToString(data.get(count++));
		if (HBUtil.isNotBlank(otherNumber)) {
			PhoneDTO other = new PhoneDTO(otherNumber, "OTHER");
			phones.add(other);
		}
		carrier.setPhones(phones);
		dataSheet.setEntity(carrier);
		return dataSheet;
	}

	@Override
	public List<FileData<InsuranceCarrierDTO>> populateModelList(List<FileData<InsuranceCarrierDTO>> data) {
		data.forEach(rowData -> {
			rowData = populateModel(rowData);
		});
		return data;
	}

}
