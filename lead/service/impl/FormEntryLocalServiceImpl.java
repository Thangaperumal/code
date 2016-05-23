package com.erp.lead.service.impl;

import com.erp.lead.model.FormEntry;
import com.erp.lead.service.base.FormEntryLocalServiceBaseImpl;
import com.erp.lead.service.persistence.FormEntryFinderUtil;
import com.erp.lead.service.persistence.FormEntryUtil;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.json.simple.JSONArray;

/**
 * The implementation of the Form Entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.FormEntryLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.FormEntryLocalServiceUtil} to access the Form Entry local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Thangaperumal Ltd.
 * @see com.erp.lead.service.base.FormEntryLocalServiceBaseImpl
 * @see com.erp.lead.service.FormEntryLocalServiceUtil
 */
public class FormEntryLocalServiceImpl extends FormEntryLocalServiceBaseImpl {
	/*public List<String> getDataToExport(String exporterName){
		return FormEntryFinderUtil.getDataToExport(exporterName);
	}*/
	
	/*
	 * fetchFormEntries
	 * To fetchFormEntries for a given form
	 * takes the parameters - formId, date ranges, perPage, pageNum, onlyErrors
	 * This includes pagination
	 * onlyErrors - if true then formentries with unsuccessful export will only be returned
	 * returns Map of String and Object
	 */
	public Map<String,Object> fetchFormEntries(int formId, String startDate, String endDate, int perPage, int pageNum, String onlyErrors){
		Map<String,Object> finalReturnList = new HashMap<String,Object>();
		JSONArray finalReturn = new JSONArray();
		List<FormEntry> formEntryList = null;
		int totalRows = 0;
		
		try{
			totalRows = FormEntryFinderUtil.fetchFormEntriesCount(formId,startDate,endDate,onlyErrors);
			formEntryList = FormEntryFinderUtil.fetchFormEntries(formId,startDate,endDate,(pageNum-1)*perPage,pageNum*perPage,onlyErrors);

			int totalPages = totalRows/perPage;
			if(totalRows%perPage != 0){
				totalPages += 1;
			}
			finalReturnList.put("totalPages",totalPages);
			finalReturnList.put("pageNum",pageNum);
			finalReturnList.put("perPage",perPage);
			finalReturnList.put("form_entries",formEntryList);
		}
		catch(Exception ex){
			System.out.println("Caught exception in findForm local service impl " + ex.getMessage());
		}
		finally{
			return finalReturnList;
		}
	}
	
	/*
	 * fetchFieldEntriesCSV
	 * To get the data for exporting to CSV (field entries for all form entries submitted for a given form)
	 * takes the parameters - formId, date ranges
	 * returns JSONArray
	 */
	public JSONArray fetchFieldEntriesCSV(int formId, String start, String end, String onlyErrors){
		return FormEntryFinderUtil.fetchFieldEntriesCSV(formId,start,end,onlyErrors);
	}
	
	/*
	 * fetchFormEntriesExport
	 * To get the data for exporting to thirdparties (field entries for all form entries submitted for a given form)
	 * takes the parameters - formId
	 * returns JSONArray
	 */
	public JSONArray fetchFormEntriesExport(int formId, String exporters){
		//System.out.println("printing in form local service impl " + exporters.length + exporters[0]);
		return FormEntryFinderUtil.fetchFormEntriesExport(formId,exporters);
	}
}
