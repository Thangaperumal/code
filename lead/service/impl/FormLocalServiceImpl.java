package com.erp.lead.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.erp.lead.model.FieldEntry;
import com.erp.lead.model.Form;
import com.erp.lead.model.FormEntry;
import com.erp.lead.model.impl.FieldEntryImpl;
import com.erp.lead.model.impl.FormEntryImpl;
import com.erp.lead.model.impl.FormEntryModelImpl;
import com.erp.lead.model.impl.FormImpl;
import com.erp.lead.service.FieldEntryLocalServiceUtil;
import com.erp.lead.service.FormEntryLocalServiceUtil;
import com.erp.lead.service.FormLocalServiceUtil;
import com.erp.lead.service.base.FormLocalServiceBaseImpl;
import com.erp.lead.service.persistence.FieldEntryPersistence;
import com.erp.lead.service.persistence.FieldEntryUtil;
import com.erp.lead.service.persistence.FormEntryPersistence;
import com.erp.lead.service.persistence.FormEntryUtil;
import com.erp.lead.service.persistence.FormFinderUtil;
import com.erp.lead.service.persistence.FormUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * The implementation of the Form local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.FormLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.FormLocalServiceUtil} to access the Form local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Thangaperumal Ltd.
 * @see com.erp.lead.service.base.FormLocalServiceBaseImpl
 * @see com.erp.lead.service.FormLocalServiceUtil
 */
public class FormLocalServiceImpl extends FormLocalServiceBaseImpl {
	
	public Form fetchForm(String formCode, int siteId,String vertical){
		System.out.println("inside the fetchForm"+formCode+vertical);
		List<Form> formList = null;
		Form form = null;
		try{
			if(vertical==null || vertical.equals("")){
				formList = FormUtil.findByFormCodeSiteId(formCode,siteId);
			}
			else{
				formList = FormUtil.findByFormCodeSiteIdVertical(formCode,siteId,vertical);
			}
		
		if (formList.size()==0)
		{
			//long formId = counterLocalService.increment(Form.class.getName());
			form = new FormImpl();
			//form.setId((int)formId);
			form.setFormCode(formCode);
			form.setSiteId(siteId);
			form.setFormEmailTemplateId(1);
			form.setCreatedAt(new java.util.Date());
			form.setUpdatedAt(new java.util.Date());
			form=FormLocalServiceUtil.addForm(form);
		}
		else{
			form = formList.get(0);
		}
		return form;
		}
		catch(Exception ex){
			System.out.println("Caught exception in fetchForm local service impl " + ex.getMessage());
		}
		return null;
	}
	
	public boolean postData(String website,String cis_name,String form_url,Map<String, String> values){
		boolean _return = false;
		Form form = null;
		try{
			form = fetchForm(cis_name,Integer.parseInt(website),null);
			//long formEntryId = counterLocalService.increment(FormEntry.class.getName());

			FormEntry formEntry = new FormEntryImpl();
			//formEntry.setId((int)formEntryId);
		    formEntry.setFormId(form.getId());
			formEntry.setFormUrl(form_url);
			formEntry.setCreatedAt(new java.util.Date());
			formEntry.setUpdatedAt(new java.util.Date());
			
			formEntry = FormEntryLocalServiceUtil.addFormEntry(formEntry);
			
			for (Map.Entry<String, String> entry : values.entrySet())
			{
			    //long fieldEntryId = counterLocalService.increment(FieldEntry.class.getName());
			    FieldEntry fieldEntry = new FieldEntryImpl();
			    //fieldEntry.setId((int)fieldEntryId);
			    fieldEntry.setName(entry.getKey());
			    fieldEntry.setValue(entry.getValue());
			    fieldEntry.setFormEntryId(formEntry.getId());
			    
				FieldEntryLocalServiceUtil.addFieldEntry(fieldEntry);
			}
			_return = true;
		}
		catch(Exception ex){
			System.out.println("Caught exception in postData form local service impl " + ex.getMessage());
		}
		return _return;
	}

	/*
	 * authenticate
	 * To authenticate an User
	 * takes the parameters - username, password
	 * returns boolean
	 */
	public JSONObject authenticate(String username, String password){
		String result = "";
		String login_result = com.erp.lead.helper.LDAPHelper.validateLogin(username,password);
		result = login_result.split(";")[0];
		JSONObject obj = new JSONObject();
		if(result.equals("true")){
			obj.put("errorMessage","Login Successful");
			obj.put("status","true");
			obj.put("display_name",login_result.split(";")[1]);
		}
		else if(result.equals("false")){
			obj.put("errorMessage","Invalid Credentials");
			obj.put("status","false");
		}
		else {
			obj.put("errorMessage","You are not authorized to view this page");
			obj.put("status","false");
		}
		return obj;
	}
	
	/*
	 * createForm
	 * To create a Form
	 * takes the parameters - action, id and form elements
	 * action takes values create or update
	 * id of the form in case of update
	 * returns JSONArray
	 */
	public Map<String,Object> createOrUpdateForm(String action, String formCode, int siteId,String vertical, String businessOwner, int formEmailTemplateId, 
			int salesforce, int dovetail, int demo_cd, String email, int trial_osub, int trial_osub_followup, int eloqua, int auto_reply,
			int pure360,int osub_cancellation,int id){
		Map<String,Object> finalReturnList = new HashMap<String,Object>();
		JSONArray finalReturn = new JSONArray();
		List<Form> formList = null;
		Form form = null;
		//JSONObject _return = null;
		try{
				//long formId = counterLocalService.increment(Form.class.getName());
			List<Form> formListTemp = FormUtil.findByFormCodeSiteId(formCode,siteId);
			if(formListTemp!=null && formListTemp.size()>0){
				if(action.equals("update")){
					if(formListTemp.get(0).getId() != id){
						finalReturnList.put("status","false");
						finalReturnList.put("errorMessage","Already a form exists with given Form code and website");
						return finalReturnList;
					}
				}
				else{
					finalReturnList.put("status","false");
					finalReturnList.put("errorMessage","Already a form exists with given Form code and website");
					return finalReturnList;
				}
			}
			if(action.equals("update")){
				form = FormUtil.findByPrimaryKey(id);
			}
			else{
				form = new FormImpl();
			}
			//form.setId((int)formId);
			form.setFormCode(formCode);
			form.setSiteId(siteId);
			form.setFormEmailTemplateId(formEmailTemplateId);
			form.setUpdatedAt(new java.util.Date());
			form.setBusinessOwner(businessOwner);
			form.setVertical(vertical);
			form.setSalesforce(salesforce);
			form.setDovetail(dovetail);
			form.setDemoCd(demo_cd);
			form.setEmail(email);
			form.setTrialOsub(trial_osub);
			form.setOsubCancellation(osub_cancellation);
			form.setTrialOsubFollowup(trial_osub_followup);
			form.setEloqua(eloqua);
			form.setAutoReply(auto_reply);
			form.setPure360(pure360);
			if(action.equals("update")){
				form=FormLocalServiceUtil.updateForm(form);
			}
			else{
				form.setCreatedAt(new java.util.Date());
				form=FormLocalServiceUtil.addForm(form);
			}
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy - HH:mm a");
			JSONObject formJson = new JSONObject();
			formJson.put("id",form.getId());
			formJson.put("formCode",form.getFormCode());
			formJson.put("siteId",form.getSiteId());
			formJson.put("formEmailTemplateId",form.getFormEmailTemplateId());
			formJson.put("createdAt",df.format(form.getCreatedAt()));
			formJson.put("updatedAt",df.format(form.getUpdatedAt()));
			formJson.put("businessOwner",form.getBusinessOwner());
			formJson.put("vertical",form.getVertical());
			formJson.put("salesforce",form.getSalesforce());
			formJson.put("dovetail",form.getDovetail());
			formJson.put("demo_cd",form.getDemoCd());
			formJson.put("email",form.getEmail());
			formJson.put("trial_osub",form.getTrialOsub());
			formJson.put("osub_cancellation",form.getOsubCancellation());
			formJson.put("trial_osub_followup",form.getTrialOsubFollowup());
			formJson.put("eloqua",form.getEloqua());
			formJson.put("auto_reply",form.getAutoReply());
			formJson.put("pure360",form.getPure360());
	        finalReturn.add(formJson);
	        finalReturnList.put("form",finalReturn);
	        finalReturnList.put("status","true");
	        
		}
		catch(Exception ex){
			System.out.println("Caught exception in create Form local service impl " + ex.getMessage());
			/*if(ex.getMessage().contains("ConstraintViolationException")){
				System.out.println("inside the contains if condition");
				finalReturnList.put("status","false");
				finalReturnList.put("errorMessage","Already a form exists with given form_code and website");
			}*/
		}
		return finalReturnList;
	}
	
	/*
	 * getFormById
	 * To fetch a Form
	 * takes the parameters - id of the form
	 * returns JSONArray
	 */
	public JSONArray getFormById(int id){

		JSONArray finalReturn = new JSONArray();
		List<Form> formList = null;
		Form form = null;
		//JSONObject _return = null;
		try{
			form = FormUtil.findByPrimaryKey(id);
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy - HH:mm a");
			JSONObject formJson = new JSONObject();
			formJson.put("id",form.getId());
			formJson.put("formCode",form.getFormCode());
			formJson.put("siteId",form.getSiteId());
			formJson.put("formEmailTemplateId",form.getFormEmailTemplateId());
			formJson.put("createdAt",df.format(form.getCreatedAt()));
			formJson.put("updatedAt",df.format(form.getUpdatedAt()));
			formJson.put("businessOwner",form.getBusinessOwner());
			formJson.put("vertical",form.getVertical());
			formJson.put("salesforce",form.getSalesforce());
			formJson.put("dovetail",form.getDovetail());
			formJson.put("demo_cd",form.getDemoCd());
			formJson.put("email",form.getEmail());
			formJson.put("trial_osub",form.getTrialOsub());
			formJson.put("osub_cancellation",form.getOsubCancellation());
			formJson.put("trial_osub_followup",form.getTrialOsubFollowup());
			formJson.put("eloqua",form.getEloqua());
			formJson.put("auto_reply",form.getAutoReply());
			formJson.put("pure360",form.getPure360());
	        finalReturn.add(formJson);
		}
		catch(Exception ex){
			System.out.println("Caught exception in getFormById local service impl " + ex.getMessage());
		}
		return finalReturn;
	}
	
	/*
	 * findForm
	 * To find forms
	 * takes the parameters - formCode(form name),siteId(website id),vertical,perPage,pageNum
	 * This includes pagination
	 * returns Map of String and Object
	 */
	public Map<String,Object> findForm(String formCode, String[] siteIds,String vertical, int perPage, int pageNum){
		Map<String,Object> finalReturnList = new HashMap<String,Object>();
		JSONArray finalReturn = new JSONArray();
		List<Form> formList = null;
		List<Object> result = null;
		int totalRows = 0;
		try{
			if(vertical==null || vertical.equals("")){
				//totalRows = FormFinderUtil.countByFormCodeSiteIds(formCode,siteIds);
				//formList = FormFinderUtil.findByFormCodeSiteIds(formCode,siteIds,(pageNum-1)*perPage,pageNum*perPage);
				DynamicQuery dynamicQuery= DynamicQueryFactoryUtil.forClass(Form.class, PortletClassLoaderUtil.getClassLoader());
				DynamicQuery dynamicQuery1= DynamicQueryFactoryUtil.forClass(Form.class, PortletClassLoaderUtil.getClassLoader());
		        
		        Criterion criterion6 = null;
		        List<Object> input = new ArrayList<Object>();
		        int i=0;
		        for(String str:siteIds){
		        	input.add(Integer.parseInt(str));
		        }
		        criterion6 = RestrictionsFactoryUtil.in("siteId",input);
		        criterion6=RestrictionsFactoryUtil.and(criterion6, RestrictionsFactoryUtil.like("formCode","%"+formCode+"%"));
		        dynamicQuery.add(criterion6);
		        dynamicQuery1.add(criterion6);
		        result=FormLocalServiceUtil.dynamicQuery(dynamicQuery,(pageNum-1)*perPage,pageNum*perPage);
		        totalRows = (int) FormLocalServiceUtil.dynamicQueryCount(dynamicQuery1);
		        formList = new ArrayList<Form>();
		        for(Object obj:result){
		        	formList.add((Form)obj);
		        }
			}
			else{
				//totalRows = FormUtil.countByFormCodeSiteIdVertical(formCode,siteId,vertical);
				//formList = FormUtil.findByFormCodeSiteIdVertical(formCode,siteId,vertical,((pageNum-1)*perPage)+1,pageNum*perPage);
			}
			int totalPages = totalRows/perPage;
			if(totalRows%perPage != 0){
				totalPages += 1;
			}
			finalReturnList.put("totalPages",totalPages);
			finalReturnList.put("pageNum",pageNum);
			finalReturnList.put("perPage",perPage);
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy - HH:mm a");
			for(Form form:formList){
				JSONObject formJson = new JSONObject();
				formJson.put("id",form.getId());
				formJson.put("formCode",form.getFormCode());
				formJson.put("siteId",form.getSiteId());
				formJson.put("formEmailTemplateId",form.getFormEmailTemplateId());
				formJson.put("createdAt",df.format(form.getCreatedAt()));
				formJson.put("updatedAt",df.format(form.getUpdatedAt()));
				formJson.put("businessOwner",form.getBusinessOwner());
				formJson.put("vertical",form.getVertical());
				formJson.put("salesforce",form.getSalesforce());
				formJson.put("dovetail",form.getDovetail());
				formJson.put("demo_cd",form.getDemoCd());
				formJson.put("email",form.getEmail());
				formJson.put("trial_osub",form.getTrialOsub());
				formJson.put("osub_cancellation",form.getOsubCancellation());
				formJson.put("trial_osub_followup",form.getTrialOsubFollowup());
				formJson.put("eloqua",form.getEloqua());
				formJson.put("auto_reply",form.getAutoReply());
				formJson.put("pure360",form.getPure360());
				finalReturn.add(formJson);
			}
			finalReturnList.put("forms",finalReturn);
		}
		catch(Exception ex){
			System.out.println("Caught exception in findForm local service impl " + ex.getMessage());
		}
		finally{
			return finalReturnList;
		}
	}
	
	/*
	 * removeForm
	 * To remove form and its corresponding form entries and field entries
	 * takes the parameters - id of the form
	 * returns boolean
	 */
	public boolean removeForm(int id){
		boolean _return = false;
		try{
			List<FormEntry> formEntries = FormEntryUtil.findByFormId(id);
			for(FormEntry formEntry:formEntries){
				FieldEntryUtil.removeByFormEntryId(formEntry.getId());
			}
			FormEntryUtil.removeByFormId(id);
			FormUtil.remove(id);
			_return = true;
		}
		catch(Exception ex){
			System.out.println("Caught exception in removeForm local service impl " + ex.getMessage());
		}
		finally{
			return _return;
		}
	}
	
	/*
	 * findExporters
	 * To find the enabled exporters for a given form
	 * takes the parameters - id of the form
	 * returns String
	 */
	public String findExporters(int id){
		String _return = "";
		try{
			_return = FormFinderUtil.findExporters(id); 
			/*for(FormEntry formEntry:formEntries){
				FieldEntryUtil.removeByFormEntryId(formEntry.getId());
			}
			FormEntryUtil.removeByFormId(id);
			FormUtil.remove(id);*/
		}
		catch(Exception ex){
			System.out.println("Caught exception in findExporters in form local service impl " + ex.getMessage());
		}
		finally{
			return _return;
		}
	}
	
	/*
	 * findByEmailExporter
	 * To find the enabled exporters for a given form
	 * takes the parameters - id of the form
	 * returns String
	 */
	public List<Integer> findByEmailExporter(){
		List<Form> forms = null;
		List<Integer> _return = new ArrayList<Integer>();
		try{
			forms = FormFinderUtil.findByEmailExporter();
			for(Form form:forms){
				_return.add(form.getId());
//				System.out.println("printing the forms which have email enabled : "+form.getId());
			}
			
			/*for(FormEntry formEntry:formEntries){
				FieldEntryUtil.removeByFormEntryId(formEntry.getId());
			}
			FormEntryUtil.removeByFormId(id);
			FormUtil.remove(id);*/
		}
		catch(Exception ex){
			System.out.println("Caught exception in findByEmailExporter in form local service impl " + ex.getMessage());
		}
		finally{
			return _return;
		}
	}
	
	public List<Form> findByAutoReply(){
		try{
			return FormUtil.findByAutoReply(1);
		}
		catch(Exception ex){
			System.out.println("Caught exception in findByAutoReply in form local service impl " + ex.getMessage());
		}
		return null;
	}
	public List<Form> findByDemoCD(){
		try{
			return FormUtil.findByDemoCD(1);
		}
		catch(Exception ex){
			System.out.println("Caught exception in findByDemoCD in form local service impl " + ex.getMessage());
		}
		return null;
	}
	public List<Form> findByDovetail(){
		try{
			return FormUtil.findByDovetail(1);
		}
		catch(Exception ex){
			System.out.println("Caught exception in findByDovetail in form local service impl " + ex.getMessage());
		}
		return null;
	}
	public List<Form> findByEloqua(){
		try{
			return FormUtil.findByEloqua(1);
		}
		catch(Exception ex){
			System.out.println("Caught exception in findByEloqua in form local service impl " + ex.getMessage());
		}
		return null;
	}
}
