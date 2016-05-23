package com.erp.lead.service.impl;

import com.erp.lead.model.FormExportLog;
import com.erp.lead.service.base.FormExportLogLocalServiceBaseImpl;
import com.erp.lead.service.persistence.FormExportLogFinderUtil;
import com.erp.lead.service.persistence.FormExportLogUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * The implementation of the Form Export Logs local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.FormExportLogLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.FormExportLogLocalServiceUtil} to access the Form Export Logs local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Thangaperumal Ltd.
 * @see com.erp.lead.service.base.FormExportLogLocalServiceBaseImpl
 * @see com.erp.lead.service.FormExportLogLocalServiceUtil
 */
public class FormExportLogLocalServiceImpl
    extends FormExportLogLocalServiceBaseImpl {
	
	/*
	 * isSuccessful
	 * To determine if a form entry has atleast one unsuccessful export
	 * takes the parameters - formEntryId
	 * returns String
	 */
	public String isSuccessful(int formEntryId){
		try{
			return FormExportLogFinderUtil.isSuccessful(formEntryId);
		}
		catch(Exception e){
			System.out.println("caught an exception at isSuccessful in FormExportLogLocalServiceImpl" + e.getMessage());
		}
		return "false";
	}
	
	/*
	 * fetchExportLogs
	 * To fetch the form export logs for a given form entry
	 * takes the parameters - formEntryId
	 * returns JSONArray
	 */
	public JSONArray fetchExportLogs(int formEntryId){
		JSONArray finalReturn = new JSONArray();
		List<FormExportLog> logList = null;
		try{
			logList = FormExportLogUtil.findByFormEntryId(formEntryId);
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy - HH:mm a");
			for(FormExportLog log:logList){
				JSONObject logJson = new JSONObject();
				logJson.put("id",log.getId());
				logJson.put("exporter",log.getExporter());
				logJson.put("successful",log.getSuccessful());
				logJson.put("externalId",log.getExternalId());
				logJson.put("createdAt",df.format(log.getCreatedAt()));
				logJson.put("text",log.getText());
				finalReturn.add(logJson);
			}
		}
		catch(Exception ex){
			System.out.println("Caught exception in findForm local service impl " + ex.getMessage());
		}
		finally{
			return finalReturn;
		}
	}
	
	public List<FormExportLog> getLogsByExporterFormEntryId(String exporter,int formEntryId){
		List<FormExportLog> logList = null;
		try{
			logList = FormExportLogUtil.findByExporterFormEntryId(exporter,formEntryId);
		}
		catch(Exception ex){
			System.out.println("Caught exception in getLogsByExporterFormEntryId local service impl " + ex.getMessage());
		}
		finally{
			return logList;
		}
	}
}
