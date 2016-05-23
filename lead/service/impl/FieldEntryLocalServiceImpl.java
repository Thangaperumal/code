package com.erp.lead.service.impl;

import com.erp.lead.model.FieldEntry;
import com.erp.lead.model.FormEntry;
import com.erp.lead.service.FieldEntryLocalServiceUtil;
import com.erp.lead.service.FormEntryLocalServiceUtil;
import com.erp.lead.service.base.FieldEntryLocalServiceBaseImpl;
import com.erp.lead.service.persistence.FieldEntryUtil;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import com.erp.lead.service.persistence.FormEntryUtil;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * The implementation of the Field Entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.FieldEntryLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.FieldEntryLocalServiceUtil} to access the Field Entry local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author RosettaStone Ltd.
 * @see com.erp.lead.service.base.FieldEntryLocalServiceBaseImpl
 * @see com.erp.lead.service.FieldEntryLocalServiceUtil
 */
public class FieldEntryLocalServiceImpl extends FieldEntryLocalServiceBaseImpl {
	
	/*
	 * fetchFieldEntriesByFormEntryId
	 * To fetch the field entries captured for a form entry
	 * takes the parameters - formEntryId
	 * returns JSONArray
	 */
	public Map<String,Object> fetchFieldEntriesByFormEntryId(int formEntryId){
		Map<String,Object> finalReturnList = new HashMap<String,Object>();
		List<FieldEntry> fieldEntries = null;
		JSONArray finalReturn = new JSONArray();
		try{
			fieldEntries = FieldEntryUtil.findByFormEntryId(formEntryId);
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy - HH:mm a");
			for(FieldEntry fieldEntry:fieldEntries){
				JSONObject fieldEntryJson = new JSONObject();
				fieldEntryJson.put("id",fieldEntry.getId());
				fieldEntryJson.put("name",convertFieldName(fieldEntry.getName()));
				fieldEntryJson.put("value",fieldEntry.getValue());
				finalReturn.add(fieldEntryJson);
			}
			finalReturnList.put("field_entries",finalReturn);
			FormEntry formEntry = FormEntryLocalServiceUtil.getFormEntry(formEntryId);
			finalReturnList.put("formUrl",formEntry.getFormUrl());
			finalReturnList.put("createdAt",df.format(formEntry.getCreatedAt()));
		}
		catch(Exception e){
			System.out.println("Caught exception in fetchFieldEntriesByFormId in FieldEntryLocalServiceImpl" + e.getMessage());
			e.printStackTrace();
		}
		return finalReturnList;
	}
	
	private String convertFieldName(String name){
		name=name.replaceAll("_", " ");
		return name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
	}
	
	/*
	 * updateFieldEntry
	 * To update the value field of a field entry
	 * takes the parameters - value (the string to be updated), id of the field entry
	 * returns JSONArray
	 */
	public JSONArray updateFieldEntry(String value,int id){
		JSONArray finalReturn = new JSONArray();
		FieldEntry fieldEntry = null;
		try{
			fieldEntry = FieldEntryUtil.findByPrimaryKey(id);
			fieldEntry.setValue(value);
			fieldEntry=FieldEntryLocalServiceUtil.updateFieldEntry(fieldEntry);
			JSONObject fieldEntryJson = new JSONObject();
			fieldEntryJson.put("id",fieldEntry.getId());
			fieldEntryJson.put("name",fieldEntry.getName());
			fieldEntryJson.put("value",fieldEntry.getValue());
	        finalReturn.add(fieldEntryJson);
		}
		catch(Exception ex){
			System.out.println("Caught exception in createForm local service impl " + ex.getMessage());
		}
		return finalReturn;
	}
}
