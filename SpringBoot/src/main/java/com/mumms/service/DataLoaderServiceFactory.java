package com.mumms.service;

import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;

import com.mumms.model.RestLoaderException;
import com.mumms.service.diagnoses.DiagnosesDataLoaderService;
import com.mumms.service.election.ElectionsDataLoaderService;
import com.mumms.service.facility.FacilityDataLoaderService;
import com.mumms.service.ic.InsuranceCarriersDataLoaderService;
import com.mumms.service.patient.PatientDataLoaderService;
import com.mumms.service.person.PersonDataLoaderService;
import com.mumms.service.status.PatientStatusDataLoaderService;
import com.mumms.service.team.TeamDataLoaderService;
import com.mumms.service.visits.PatientVisitsDataLoaderService;
import com.mumms.utils.DataLoaderConstants;

public class DataLoaderServiceFactory {

	public static DataLoaderService getServiceByModuleName(ApplicationContext ctx, String moduleName)
			throws RestLoaderException {
		DataLoaderService service = null;
		final Logger log = Logger.getLogger(DataLoaderService.class.getName());
		log.info("--------------------------------------------");		
		log.info("Starting MODULE "+moduleName);
		switch (moduleName) {
		/*case DataLoaderConstants.PERSONS_SHEET_NAME:
			service = ctx.getBean(PersonDataLoaderService.class);
			break;
		case DataLoaderConstants.FACILITIES_SHEET_NAME:
			service = ctx.getBean(FacilityDataLoaderService.class);
			break;*/
		case DataLoaderConstants.PATIENTS_SHEET_NAME:
			service = ctx.getBean(PatientDataLoaderService.class);
			break;
		/*case DataLoaderConstants.INSURANCE_CARRIERS_SHEET_NAME:
			service = ctx.getBean(InsuranceCarriersDataLoaderService.class);
			break;
		case DataLoaderConstants.PATIENT_STATUSES_SHEET_NAME:
			service = ctx.getBean(PatientStatusDataLoaderService.class);
			break;
		case DataLoaderConstants.DIAGNOSES_SHEET_NAME:
			service = ctx.getBean(DiagnosesDataLoaderService.class);
			break;
		case DataLoaderConstants.TEAMS_SHEET_NAME:
			service = ctx.getBean(TeamDataLoaderService.class);
			break;
		case DataLoaderConstants.PATIENT_VISITS_SHEET_NAME:
			service = ctx.getBean(PatientVisitsDataLoaderService.class);
			break;
		case DataLoaderConstants.ELECTIONS_SHEET_NAME:
			service = ctx.getBean(ElectionsDataLoaderService.class);
			break;	*/
		default:
			throw new RestLoaderException("No service found for the module", moduleName);
		}

		return service;
	}

}
