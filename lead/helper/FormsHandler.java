package com.erp.lead.helper;

import com.erp.lead.model.Form;
import com.erp.lead.model.FormEmailTemplate;
import com.erp.lead.model.Site;
import com.erp.lead.service.FieldEntryLocalServiceUtil;
import com.erp.lead.service.FormEmailTemplateLocalServiceUtil;
import com.erp.lead.service.FormEntryLocalServiceUtil;
import com.erp.lead.service.FormExportLogLocalServiceUtil;
import com.erp.lead.service.FormLocalServiceUtil;
import com.erp.lead.service.SiteLocalServiceUtil;
import com.erp.lead.service.persistence.FormUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;



public class FormsHandler {
	public static final String WEBSITES_BY_CALL_CENTER = FormsHandler.class.getName() + ".getWebsitesByCallCenter";
	
	/*
	 * getListOfWebsites
	 * To get the list of websites
	 */
	public static JSONObject getListOfWebsites(){
		JSONArray _return = null;
		JSONObject _returnObj = null;
		try{
			_return = SiteLocalServiceUtil.getWebsitesByCallCenter();
			_returnObj = new JSONObject();
			_returnObj.put("sites",_return);
		}
		catch(Exception e){
			System.out.println("An exception caught in getListOfWebsites" + e.getMessage());
		}
		return _returnObj;
	}
	
	/*
	 * findForm
	 * To find the form by form name and website id
	 * This includes pagination as well
	 */
	public static JSONObject findForm(ResourceRequest resourceRequest){
		Map<String,Object> _return = null;
		JSONObject _returnObj = null;
		try{
			String formCode = resourceRequest.getParameter("formCode");
			//int siteId = Integer.parseInt(resourceRequest.getParameter("siteId"));
			String[] siteIds = resourceRequest.getParameterValues("siteIds");
			String vertical = resourceRequest.getParameter("vertical");
			int perPage = Integer.parseInt(resourceRequest.getParameter("perPage"));
			int pageNum = Integer.parseInt(resourceRequest.getParameter("pageNum"));
			_return = FormLocalServiceUtil.findForm(formCode, siteIds, vertical,perPage,pageNum);
			_returnObj = new JSONObject();
			_returnObj.put("totalPages",_return.get("totalPages"));
			_returnObj.put("pageNum",_return.get("pageNum"));
			_returnObj.put("perPage",_return.get("perPage"));
			_returnObj.put("forms",_return.get("forms"));
		}
		catch(Exception e){
			System.out.println("An exception caught in findForm" + e.getMessage());
		}
		return _returnObj;
	}
	
	/*
	 * findFormById
	 * To find the form by form name and website id
	 * This includes pagination as well
	 */
	public static JSONObject getFormById(ResourceRequest resourceRequest){
		JSONArray _return = null;
		JSONObject _returnObj = null;
		try{
			int formId = Integer.parseInt(resourceRequest.getParameter("formId"));
			_return = FormLocalServiceUtil.getFormById(formId);
			_returnObj = new JSONObject();
			_returnObj.put("form",_return);
		}
		catch(Exception e){
			System.out.println("An exception caught in getFormById" + e.getMessage());
		}
		return _returnObj;
	}
	
	/*
	 * createOrUpdateForm
	 * To create or update a form
	 */
	public static JSONObject createOrUpdateForm(String action, ResourceRequest resourceRequest){
		Map<String,Object> _returnMap = null;
		JSONArray _return = null;
		JSONObject _returnObj = null;
		try{
			String formCode = resourceRequest.getParameter("formCode");
			int siteId = Integer.parseInt(resourceRequest.getParameter("siteId"));
			String vertical = resourceRequest.getParameter("vertical");
			String businessOwner = resourceRequest.getParameter("businessOwner");
			int formEmailTemplateId = Integer.parseInt(resourceRequest.getParameter("formEmailTemplateId"));
			int salesforce = Integer.parseInt(resourceRequest.getParameter("salesforce"));
			int dovetail = Integer.parseInt(resourceRequest.getParameter("dovetail"));
			int demo_cd = Integer.parseInt(resourceRequest.getParameter("demo_cd"));
			String email = resourceRequest.getParameter("email");
			int trial_osub = Integer.parseInt(resourceRequest.getParameter("trial_osub"));
			int osub_cancellation = Integer.parseInt(resourceRequest.getParameter("osub_cancellation"));
			int trial_osub_followup = Integer.parseInt(resourceRequest.getParameter("trial_osub_followup"));
			int eloqua = Integer.parseInt(resourceRequest.getParameter("eloqua"));
			int auto_reply = Integer.parseInt(resourceRequest.getParameter("auto_reply"));
			int pure360 = Integer.parseInt(resourceRequest.getParameter("pure360"));
			int id=0;
			if(action.equals("update")){
				id=Integer.parseInt(resourceRequest.getParameter("id"));
			}
			_returnMap = FormLocalServiceUtil.createOrUpdateForm(action, formCode, siteId, vertical, businessOwner, formEmailTemplateId, salesforce, dovetail, demo_cd, email, trial_osub, trial_osub_followup, eloqua, auto_reply, pure360, osub_cancellation, id);
			_returnObj = new JSONObject();
			_returnObj.put("form",_returnMap.get("form"));
			_returnObj.put("status",_returnMap.get("status"));
			_returnObj.put("errorMessage",_returnMap.get("errorMessage"));
		}
		catch(Exception e){
			System.out.println("An exception caught in createOrUpdateForm" + e.getMessage());
		}
		return _returnObj;
	}
	
	/*
	 * removeForm
	 * To remove a form and its corresponding child classes
	 */
	public static JSONObject removeForm(ResourceRequest resourceRequest){
		JSONObject _returnObj = null;
		try{
			int id = Integer.parseInt(resourceRequest.getParameter("id"));
			boolean _return = FormLocalServiceUtil.removeForm(id);
			_returnObj = new JSONObject();
			_returnObj.put("status",_return);
		}
		catch(Exception e){
			System.out.println("An exception caught in createOrUpdateForm" + e.getMessage());
		}
		return _returnObj;
	}
	
	/*
	 * exportCSV
	 * To export the user submitted data (stored in field_entries) for a form with specific date range in CSV format
	 */
	public static JSONObject exportCSV(ResourceRequest resourceRequest, ResourceResponse resourceResponse){
		JSONArray _return = null;
		JSONObject _returnObj = null;
		try{
			int formId = Integer.parseInt(resourceRequest.getParameter("formId"));
			String startDate = resourceRequest.getParameter("startDate");
			String endDate = resourceRequest.getParameter("endDate");
			String onlyErrors = resourceRequest.getParameter("onlyErrors");
			String formName = resourceRequest.getParameter("formName");
			_return = FormEntryLocalServiceUtil.fetchFieldEntriesCSV(formId,startDate,endDate,onlyErrors);
			_returnObj = new JSONObject();
			_returnObj.put("field_entries",_return);
			String fileName = formName+"_"+formatDate(startDate)+"_to_"+formatDate(endDate)+".csv";
			CSVHelper.generateCSV(resourceResponse,_return,fileName.replaceAll(" ",""));
		}
		catch(Exception e){
			System.out.println("An exception caught in exportCSV" + e.getMessage());
		}
		return _returnObj;
	}
	
	/*
	 * exportFormEntries
	 * To export the user submitted data (stored in field_entries) for a form to external third parties
	 */
	public static JSONObject exportFormEntries(ResourceRequest resourceRequest){
		JSONArray _return = null;
		JSONObject _returnObj = null;
		try{
			int formId = Integer.parseInt(resourceRequest.getParameter("formId"));
			boolean exportIsHappening=ExporterHelper.isCurrentlyExported(formId);
			if(!exportIsHappening){
			//String startDate = resourceRequest.getParameter("startDate");
			//String endDate = resourceRequest.getParameter("endDate");
			//String onlyErrors = resourceRequest.getParameter("onlyErrors");
			//String formName = resourceRequest.getParameter("formName");
			String exporters = FormLocalServiceUtil.findExporters(formId);//gives you the comma separated list of exporters for this particular form
			System.out.println("printing the exporters " + exporters);
			String[] exportersArray = exporters.split(",");
			_returnObj = new JSONObject();
			Map<String,Map<String,Map<String,String>>> finalMap = new TreeMap<String,Map<String,Map<String,String>>>();
			for(int j=0;j<exportersArray.length;j++){
				_return = FormEntryLocalServiceUtil.fetchFormEntriesExport(formId,exportersArray[j]);
				_returnObj.put(exportersArray[j],_return);
				JSONArray array = _return;
				Map<String,Map<String,String>> hashMap = new TreeMap<String,Map<String,String>>();
				Map<String,String> innerMap = null;
				for (int i=0; i<(array.size()); i++) {
					JSONObject obj = (JSONObject) array.get(i);
					if(hashMap.containsKey(String.valueOf(obj.get("id")))){
						innerMap = hashMap.get(String.valueOf(obj.get("id")));
						innerMap.put(String.valueOf(obj.get("name")),String.valueOf(obj.get("value")));
						innerMap.put("date",String.valueOf(obj.get("date")));
						innerMap.put("cis_name",String.valueOf(obj.get("cis_name")));
						innerMap.put("website",String.valueOf(obj.get("website")));
					}
					else{
						innerMap = new TreeMap<String,String>();
						innerMap.put(String.valueOf(obj.get("name")),String.valueOf(obj.get("value")));
						innerMap.put("date",String.valueOf(obj.get("date")));
						innerMap.put("cis_name",String.valueOf(obj.get("cis_name")));
						innerMap.put("website",String.valueOf(obj.get("website")));
						hashMap.put(String.valueOf(obj.get("id")),innerMap);
					}
				}
				finalMap.put(exportersArray[j],hashMap);
				//System.out.println("printing the hashMap " + finalMap);
			}
			ExporterHelper.setCurrentlyExportedForms(formId);
			ExporterHelper.export(exportersArray,finalMap);
			ExporterHelper.removeCurrentlyExportedForms(formId);
			//String fileName = formName+"_"+formatDate(startDate)+"_to_"+formatDate(endDate)+".csv";
			//CSVHelper.generateCSV(resourceResponse,_return,fileName.replaceAll(" ",""));
			}
			else{
			_returnObj = new JSONObject();
			_returnObj.put("ExportisHappening","1");
			}
		}
		catch(Exception e){
			System.out.println("An exception caught in exportCSV" + e.getMessage());
		}
		return _returnObj;
	}
	
	public static JSONObject exportFormEntriesFromCron(){
		JSONArray _return = null;
		
		//_returnObj = new JSONObject();
		Map<String,Map<String,Map<String,String>>> finalMap = new TreeMap<String,Map<String,Map<String,String>>>();
		try{
		List<Form> formsAutoReply = FormLocalServiceUtil.findByAutoReply();	
		List<Form> formsDemoCD = FormLocalServiceUtil.findByDemoCD();
		List<Form> formsDovetail = FormLocalServiceUtil.findByDovetail();
		List<Form> formsEloqua = FormLocalServiceUtil.findByEloqua();
		List<Integer> formsEmail = FormLocalServiceUtil.findByEmailExporter();
		
		Integer[] formIdsAutoReply = new Integer[formsAutoReply.size()];
		for(int i=0;i<formsAutoReply.size();i++){
			formIdsAutoReply[i]=((Form)formsAutoReply.get(i)).getId();
		}
		Integer[] formIdsDemoCD = new Integer[formsDemoCD.size()];
		for(int i=0;i<formsDemoCD.size();i++){
			formIdsDemoCD[i]=((Form)formsDemoCD.get(i)).getId();
		}
		Integer[] formIdsDovetail = new Integer[formsDovetail.size()];
		for(int i=0;i<formsDovetail.size();i++){
			formIdsDovetail[i]=((Form)formsDovetail.get(i)).getId();
		}
		Integer[] formIdsEloqua = new Integer[formsEloqua.size()];
		for(int i=0;i<formsEloqua.size();i++){
			formIdsEloqua[i]=((Form)formsEloqua.get(i)).getId();
		}
		Integer[] formIdsEmail = new Integer[formsEmail.size()];
		for(int i=0;i<formsEmail.size();i++){
			formIdsEmail[i]=formsEmail.get(i);
		}
		constructAndExport(formIdsAutoReply,"auto_reply",finalMap);
		constructAndExport(formIdsDemoCD,"demo_cd",finalMap);
		constructAndExport(formIdsDovetail,"dovetail",finalMap);
		constructAndExport(formIdsEloqua,"eloqua",finalMap);
		constructAndExport(formIdsEmail,"email",finalMap);
		ExporterHelper.export(new String[]{"auto_reply","demo_cd","dovetail","eloqua","email"},finalMap);
		//System.out.println(finalMap);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}
	
	public static void constructAndExport(Integer[] forms, String exporter, Map<String,Map<String,Map<String,String>>> finalMap){
		Map<String,Map<String,String>> hashMap = new TreeMap<String,Map<String,String>>();
		JSONObject _returnObj = null;
		for(Integer formId:forms){
			JSONArray array = FormEntryLocalServiceUtil.fetchFormEntriesExport(formId,exporter);
			Map<String,String> innerMap = null;
			for (int i=0; i<(array.size()); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				if(hashMap.containsKey(String.valueOf(obj.get("id")))){
					innerMap = hashMap.get(String.valueOf(obj.get("id")));
					innerMap.put(String.valueOf(obj.get("name")),String.valueOf(obj.get("value")));
					innerMap.put("date",String.valueOf(obj.get("date")));
					innerMap.put("cis_name",String.valueOf(obj.get("cis_name")));
					innerMap.put("website",String.valueOf(obj.get("website")));
				}
				else{
					innerMap = new TreeMap<String,String>();
					innerMap.put(String.valueOf(obj.get("name")),String.valueOf(obj.get("value")));
					innerMap.put("date",String.valueOf(obj.get("date")));
					innerMap.put("cis_name",String.valueOf(obj.get("cis_name")));
					innerMap.put("website",String.valueOf(obj.get("website")));
					hashMap.put(String.valueOf(obj.get("id")),innerMap);
				}
			}

		}
		finalMap.put(exporter,hashMap);
		//return finalMap;
	}
	
	/*
	 * fetchFieldEntries
	 * To fetch the field entries (user submitted data for a form) for a particular form entry
	 */
	public static JSONObject fetchFieldEntries(ResourceRequest resourceRequest){
		Map<String,Object> _returnMap = null;
		JSONArray _return = null;
		JSONObject _returnObj = null;
		try{
			int formEntryId = Integer.parseInt(resourceRequest.getParameter("formEntryId"));
			_returnMap = FieldEntryLocalServiceUtil.fetchFieldEntriesByFormEntryId(formEntryId);
			_returnObj = new JSONObject();
			_returnObj.put("field_entries",_returnMap.get("field_entries"));
			//FormEntry formEntry = FormEntryUtil.findByPrimaryKey(formEntryId);
			_returnObj.put("formUrl",_returnMap.get("formUrl"));
			_returnObj.put("createdAt",_returnMap.get("createdAt"));
		}
		catch(Exception e){
			System.out.println("An exception caught in fetchFieldEntries" + e.getMessage());
			e.printStackTrace();
		}
		return _returnObj;
	}
	
	/*
	 * updateFieldEntry
	 * To update a fieldEntry (a field in data submitted by user for a form)
	 */
	public static JSONObject updateFieldEntry(ResourceRequest resourceRequest){
		JSONArray _return = null;
		JSONObject _returnObj = null;
		try{
			String value = resourceRequest.getParameter("value");
			int id=Integer.parseInt(resourceRequest.getParameter("id"));
			_return = FieldEntryLocalServiceUtil.updateFieldEntry(value,id);
			_returnObj = new JSONObject();
			_returnObj.put("fieldEntry",_return);
		}
		catch(Exception e){
			System.out.println("An exception caught in udpateFieldEntry in FormsHandler" + e.getMessage());
		}
		return _returnObj;
	}
	
	/*
	 * fetchFormEntries
	 * To fetch the form entries submitted for a particular form in specific date range
	 * This includes pagination as well
	 */
	public static JSONObject fetchFormEntries(ResourceRequest resourceRequest){
		Map<String,Object> _return = null;
		JSONObject _returnObj = null;
		try{
			int formId = Integer.parseInt(resourceRequest.getParameter("formId"));
			String startDate = resourceRequest.getParameter("startDate");
			String endDate = resourceRequest.getParameter("endDate");
			int perPage = Integer.parseInt(resourceRequest.getParameter("perPage"));
			int pageNum = Integer.parseInt(resourceRequest.getParameter("pageNum"));
			String onlyErrors = resourceRequest.getParameter("onlyErrors");

			_return = FormEntryLocalServiceUtil.fetchFormEntries(formId,startDate,endDate,perPage,pageNum,onlyErrors);
			_returnObj = new JSONObject();
			_returnObj.put("form_entries",_return.get("form_entries"));
			_returnObj.put("totalPages",_return.get("totalPages"));
			_returnObj.put("pageNum",_return.get("pageNum"));
			_returnObj.put("perPage",_return.get("perPage"));
			//_returnObj.put("forms",_return.get("forms"));
		}
		catch(Exception e){
			System.out.println("An exception caught in fetchFormEntries" + e.getMessage());
		}
		return _returnObj;
	}
	
	/*
	 * getVerticals
	 * To get the list of verticals
	 */
	public static JSONObject getVerticals(ResourceRequest resourceRequest){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObj.put("name","Endangered Language Program");
		jsonObj.put("id","elp");
		jsonArray.add(jsonObj);
		jsonObj = new JSONObject();
		jsonObj.put("name","Institutions");
		jsonObj.put("id","inst");
		jsonArray.add(jsonObj);
		jsonObj = new JSONObject();
		jsonObj.put("name","Individuals");
		jsonObj.put("id","ind");
		jsonArray.add(jsonObj);
		jsonObj = new JSONObject();
		jsonObj.put("name","NewsProgram");
		jsonObj.put("id","news");
		jsonArray.add(jsonObj);
		jsonObj = new JSONObject();
		jsonObj.put("name","Home School");
		jsonObj.put("id","hs");
		jsonArray.add(jsonObj);
		JSONObject _returnObj = new JSONObject();
		_returnObj.put("verticals",jsonArray);
		return _returnObj;
	}
	
	/*
	 * getEmailTemplates
	 * To get the list of Form Email Templates
	 */
	public static JSONObject getEmailTemplates(ResourceRequest resourceRequest){
		List<FormEmailTemplate> _return = null;
		JSONObject _returnObj = null;
		try{
			JSONArray jsonArray = new JSONArray();
			_return = FormEmailTemplateLocalServiceUtil.getAll();
			for(FormEmailTemplate template:_return){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("name",template.getName());
				jsonObj.put("id",template.getId());
				jsonArray.add(jsonObj);
			}
			_returnObj = new JSONObject();
			_returnObj.put("email_templates",jsonArray);
		}
		catch(Exception e){
			System.out.println("An exception caught in getEmailTemplates" + e.getMessage());
		}
		return _returnObj;
	}
	
	/*
	 * fetchExportLogs
	 * To get the Form Export Logs for a particular form entry (export log of user submitted data)
	 */
	public static JSONObject fetchExportLogs(ResourceRequest resourceRequest){
		JSONArray _return = null;
		JSONObject _returnObj = null;
		try{
			int formEntryId = Integer.parseInt(resourceRequest.getParameter("formEntryId"));
			_return = FormExportLogLocalServiceUtil.fetchExportLogs(formEntryId);
			_returnObj = new JSONObject();
			_returnObj.put("export_logs",_return);
		}
		catch(Exception e){
			System.out.println("An exception caught in fetchExportLogs" + e.getMessage());
		}
		return _returnObj;
	}
	
	private static String formatDate(String date){
		date = date.substring(0,10);
		String[] temp_date = date.split("-");
        return temp_date[1] + "_" +temp_date[2]+"_"+temp_date[0];
	}
}