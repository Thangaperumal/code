package com.mumms.utils;

public class DataLoaderConstants {

	// App wide constants
	public static final String FILE_NAME_PROPERTY_NAME = "fileName";

	public static final String PUT_OPERATION = "PUT";
	public static final String POST_OPERATION = "POST";

	public final static String AGENCY_PARAM_KEY = "agency";
	public final static String SITE_PARAM_KEY = "site";
	public final static String POST_URL_KEY = ".post.url.path";
	public final static String PUT_URL_KEY = ".put.url.path";
	public final static String STATUS_COLUMN_HEADER = "Status (system-generated)";

	public final static String SUCCESS_MSG = "Success:";
	public final static String ERROR_MSG = "Error: ";
	public final static String UNKNOWN_ERROR_MSG = ERROR_MSG + "Reason Unknown";

	// Sheet Names
	public final static String PERSONS_SHEET_NAME = "Persons";
	public final static String PATIENTS_SHEET_NAME = "Patients";
	public final static String FACILITIES_SHEET_NAME = "Facilities";
	public final static String INSURANCE_CARRIERS_SHEET_NAME = "Insurance Carriers";
	public final static String PATIENT_STATUSES_SHEET_NAME = "Patient Statuses";
	public final static String DIAGNOSES_SHEET_NAME = "Diagnoses";
	public final static String TEAMS_SHEET_NAME = "IDG Teams";
	public final static String PATIENT_VISITS_SHEET_NAME = "Patient Visits";
	public final static String ELECTIONS_SHEET_NAME = "Elections";

	// Endpoint Names
	public final static String PERSONS_API_ENDPOINT = "persons";
	public final static String PATIENTS_API_ENDPOINT = "patients";
	public final static String FACILITIES_API_ENDPOINT = "facilities";
	public final static String INSURANCE_CARRIERS_API_ENDPOINT = "insuranceCarriers";
	public final static String PATIENT_STATUSES_API_ENDPOINT = "patientStatuses";
	public final static String DIAGNOSES_API_ENDPOINT = "diagnoses";
	public final static String TEAMS_API_ENDPOINT = "teams";
	public final static String PATIENT_VISITS_API_ENDPOINT = "visits";
	public final static String ELECTIONS_API_ENDPOINT = "elections";

	// Property file ID Key
	public final static String PERSON_ID_KEY = "persons.id.key";
	public final static String PATIENT_ID_KEY = "patients.id.key";
	public final static String FACILITY_ID_KEY = "facilities.id.key";
	public final static String INSURANCE_CARRIER_ID_KEY = "insuranceCarriers.id.key";
	public final static String PATIENT_STATUS_ID_KEY = "patientStatuses.id.key";
	public final static String DIAGNOSE_ID_KEY = "diagnoses.id.key";
	public final static String TEAMS_ID_KEY = "teams.id.key";
	public final static String PATIENT_VISIT_ID_KEY = "visits.id.key";
	public final static String ELECTIONS_ID_KEY = "elections.id.key";
	public final static String COVERAGES_ID_KEY = "coverages.id.key";

	// Number column name, if present
	public final static String PATIENT_NUMBER = "patientNumber";
	public final static String PERSON_NUMBER = "personNumber";
	public final static String TEAM_NUMBER = "teamNumber";

}
