package com.mumms.model.team;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mumms.model.DataTransformer;
import com.mumms.model.FileData;
import com.mumms.utils.HBUtil;

@Component
public class TeamDataTransformer implements DataTransformer<TeamApiDTO> {

	@Override
	public FileData<TeamApiDTO> populateModel(FileData<TeamApiDTO> dataSheet) {
		TeamApiDTO team = new TeamApiDTO();
		int count = 1;

		Map<Integer, Object> data = dataSheet.getRowData();

		// Fields are set based on their column index in the file.
		team.setId((String) data.get(count++));
		team.setTeamNumber((String) data.get(count++));
		team.setTeamName((String) data.get(count++));

		String offices = (String) data.get(count++);
		if (HBUtil.isNotBlank(offices)) {
			Arrays.stream(offices.split(",")).forEach(officeId -> {
				team.getOffices().add(new TeamOfficeSiteDTO(officeId));
			});
		}

		String sites = (String) data.get(count++);
		if (HBUtil.isNotBlank(sites)) {
			Arrays.stream(sites.split(",")).forEach(siteId -> {
				team.getSites().add(new TeamOfficeSiteDTO(siteId));
			});
		}

		String persons = (String) data.get(count++);
		if (HBUtil.isNotBlank(persons)) {
			Arrays.stream(persons.split(",")).forEach(personNumber -> {
				team.getPersons().add(new TeamPersonDTO(personNumber));
			});
		}

		dataSheet.setEntity(team);
		dataSheet.setHasNumberColumn(true);
		return dataSheet;
	}

	@Override
	public List<FileData<TeamApiDTO>> populateModelList(List<FileData<TeamApiDTO>> data) {
		data.forEach(rowData -> {
			rowData = populateModel(rowData);
		});
		return data;
	}
}
