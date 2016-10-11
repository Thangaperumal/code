package com.mumms.model.team;

import java.util.ArrayList;
import java.util.List;

public class TeamApiDTO {

	private String id;
	private String teamName;
	private String teamNumber;
	private List<TeamOfficeSiteDTO> offices = new ArrayList<>();
	private List<TeamOfficeSiteDTO> sites = new ArrayList<>();
	private List<TeamPersonDTO> persons = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(String teamNumber) {
		this.teamNumber = teamNumber;
	}

	public List<TeamOfficeSiteDTO> getOffices() {
		return offices;
	}

	public void setOffices(List<TeamOfficeSiteDTO> offices) {
		this.offices = offices;
	}

	public List<TeamOfficeSiteDTO> getSites() {
		return sites;
	}

	public void setSites(List<TeamOfficeSiteDTO> sites) {
		this.sites = sites;
	}

	public List<TeamPersonDTO> getPersons() {
		return persons;
	}

	public void setPersons(List<TeamPersonDTO> persons) {
		this.persons = persons;
	}
}
