package com.erp.lead.service.persistence;

import com.erp.lead.model.FieldEntry;
import com.erp.lead.model.Form;
import com.erp.lead.model.FormEntry;
import com.erp.lead.model.FormExportLog;
import com.erp.lead.model.impl.FieldEntryImpl;
import com.erp.lead.model.impl.FormEntryImpl;
import com.erp.lead.model.impl.FormExportLogImpl;
import com.erp.lead.model.impl.FormImpl;
import com.erp.lead.service.FormExportLogLocalServiceUtil;
import com.erp.lead.service.persistence.FormEntryFinder;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.List;
import java.util.ArrayList;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.util.dao.orm.CustomSQLUtil;
//import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Type;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigInteger;

public class FormEntryFinderImpl extends BasePersistenceImpl<FormEntry> implements FormEntryFinder {

	public static final String FIND_MAX_COUNT = FormEntryFinder.class.getName() + ".findMaxCount";
	public static final String FETCH_FORM_ENTRIES = FormEntryFinder.class.getName() + ".fetchFormEntries";
	public static final String FETCH_FORM_ENTRIES_ERRORS = FormEntryFinder.class.getName() + ".fetchFormEntriesErrors";
	public static final String FETCH_FORM_ENTRIES_COUNT = FormEntryFinder.class.getName() + ".fetchFormEntriesCount";
	public static final String FETCH_FORM_ENTRIES_COUNT_ERRORS = FormEntryFinder.class.getName() + ".fetchFormEntriesCountErrors";
	public static final String FETCH_FIELD_ENTRIES_CSV = FormEntryFinder.class.getName() + ".fetchFieldEntriesCSV";
	public static final String FETCH_FIELD_ENTRIES_CSV_ERRORS = FormEntryFinder.class.getName() + ".fetchFieldEntriesCSVErrors";
	public static final String FETCH_FORMS_TO_EXPORT = FormEntryFinder.class.getName() + ".fetchFormEntriesExporter";
	
	/*public List<String> getDataToExport(
		    String exporterName) {

		    Session session = null;
		    try {
		        session = openSession();

		        String sql = CustomSQLUtil.get(
		            FIND_MAX_COUNT);
		        String sql_1 = CustomSQLUtil.get(
			            FETCH_FORMS_TO_EXPORT);
		        //String sql = "SELECT max(d.form_entry_id) FROM forms a JOIN form_entries b join form_export_logs d ON a.id = b.form_id and b.id = d.form_entry_id WHERE (a.salesforce = 1) and (d.exporter = 'salesforce')";
		        sql_1 = sql_1.replaceAll("exporter_name",exporterName);
		        sql = sql.replaceAll("exporter_name",exporterName);

		        SQLQuery q = session.createSQLQuery(sql);
		        q.setCacheable(false);
		        SQLQuery q1 = session.createSQLQuery(sql_1);
		        q1.addScalar("value",Type.STRING);
		        q1.setCacheable(false);
		        //q.addEntity("Event_Event", EventImpl.class);

		        QueryPos qPos = QueryPos.getInstance(q);
		        //qPos.add(exporterName);
		        QueryPos qPos1 = QueryPos.getInstance(q1);
		        
		        List<Object> result = (List) QueryUtil.list(q, getDialect(),0,1);
		        qPos1.add(result.get(0));
		        List<Object> result1 = (List) QueryUtil.list(q1, getDialect(),0,10000);
		        
		        String serilizeString=null;
		        JSONArray empoyeeJsonArray=null;
		        
		        List<String> finalReturn = new ArrayList<String>();
		        for(Object elemnetObject:result1){
		        serilizeString=JSONFactoryUtil.serialize(elemnetObject);
		        empoyeeJsonArray=JSONFactoryUtil.createJSONArray(serilizeString);
		        System.out.println("Printing the returned four values: " + empoyeeJsonArray.getString(0));
		        finalReturn.add(empoyeeJsonArray.getString(0));
		        }
		        for(Object str:result1){
		        	finalReturn.add((String)str);
		        }
		        return finalReturn;
		    } catch (Exception e) {
		        try {
		            throw new SystemException(e);
		        } catch (SystemException se) {
		            se.printStackTrace();
		        }
		    } finally {
		        closeSession(session);
		    }

		    return null;
		}*/

	/*
	 * fetchFormEntries
	 * To find the form entries for a given form in specific date range
	 * takes the parameters - formId, date range, rows range, onlyErrors
	 * onlyErrors - if true then form entries with unsuccessful exports alone will be returned
	 * returns JSONArray
	 */
	public JSONArray fetchFormEntries(int formId, String startDate, String endDate,int start, int end, String onlyErrors) {

	    Session session = null;
	    List<FormEntry> formEntryList = null;
	    JSONArray finalReturn=null;
	    try {
	        session = openSession();

	        String sql = CustomSQLUtil.get(FETCH_FORM_ENTRIES);
	        if(onlyErrors.equalsIgnoreCase("true")){
	        	sql = CustomSQLUtil.get(FETCH_FORM_ENTRIES_ERRORS);
	        }
	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(startDate);
	        qPos.add(endDate);
	        qPos.add(formId);
	        q.addScalar("id",Type.INTEGER);
        	q.addScalar("email",Type.STRING);
        	q.addScalar("date",Type.STRING);
	        if(!(onlyErrors.equalsIgnoreCase("true"))){
	        	q.addScalar("export_status",Type.INTEGER);
	        	q.addScalar("log_present",Type.INTEGER);
	        }
	        List<Object> result = (List) QueryUtil.list(q, getDialect(),start,end);

	        String serilizeString=null;
	        com.liferay.portal.kernel.json.JSONArray empoyeeJsonArray=null;
	        finalReturn = new JSONArray();
	        
	        for(Object elemnetObject:result){
		        serilizeString=JSONFactoryUtil.serialize(elemnetObject);
		        empoyeeJsonArray=JSONFactoryUtil.createJSONArray(serilizeString);
		        JSONObject json = new JSONObject();
		        String exportStatus = "";
		        if(!(onlyErrors.equalsIgnoreCase("true"))){
		        	//exportStatus = FormExportLogLocalServiceUtil.isSuccessful(empoyeeJsonArray.getInt(0));
		        	int exportStatusFromDB = empoyeeJsonArray.getInt(3);
		        	int logPresent = empoyeeJsonArray.getInt(4);
		        	if(logPresent == 1){
		        		exportStatus = (exportStatusFromDB == 1 ? "true" : "false");
		        	}
		        	else{
		        		exportStatus = "";
		        	}
		        }
		        else{
		        	exportStatus = "false";
		        }
		        /*if(onlyErrors!=null && onlyErrors.equals("true") && (exportStatus.equals("") || exportStatus.equalsIgnoreCase("true"))){
		        	continue;
		        }*/
		        json.put("id",empoyeeJsonArray.getInt(0));
		        json.put("email",empoyeeJsonArray.getString(1));
		        json.put("date",empoyeeJsonArray.getString(2));
		        json.put("exportStatus",exportStatus);
		        finalReturn.add(json);
	        }
	    } catch (Exception e) {
	        try {
	            throw new SystemException(e);
	        } catch (SystemException se) {
	            se.printStackTrace();
	        }
	    } finally {
	        closeSession(session);
	    }

	    return finalReturn;
	}
	
	/*
	 * fetchFormEntriesCount
	 * To find the count of form entries for a given form in specific date range
	 * takes the parameters - formId, date range, rows range, onlyErrors
	 * onlyErrors - if true then form entries with unsuccessful exports alone will be returned
	 * returns int
	 */
	public int fetchFormEntriesCount(int formId, String startDate, String endDate, String onlyErrors) {

	    Session session = null;
	    List<FormEntry> formEntryList = null;
	    JSONArray finalReturn=null;
	    int count = 0;
	    try {
	        session = openSession();
	        String sql = CustomSQLUtil.get(FETCH_FORM_ENTRIES_COUNT);
	        if(onlyErrors.equalsIgnoreCase("true")){
	        	sql = CustomSQLUtil.get(FETCH_FORM_ENTRIES_COUNT_ERRORS);
	        }
	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(startDate);
	        qPos.add(endDate);
	        qPos.add(formId);
	        List<Object> result = (List) QueryUtil.list(q, getDialect(),0,1);
	        if(result!=null && result.size()>0){
	        	count = ((BigInteger)result.get(0)).intValue();
	        }
	    } catch (Exception e) {
	        try {
	            throw new SystemException(e);
	        } catch (SystemException se) {
	            se.printStackTrace();
	        }
	    } finally {
	        closeSession(session);
	    }

	    return count;
	}
	
	/*
	 * fetchFieldEntriesCSV
	 * To find the field entries belonging to form entries for a given form in specific date range
	 * takes the parameters - formId, date range, rows range, onlyErrors
	 * returns JSONArray
	 */
	public JSONArray fetchFieldEntriesCSV(int formId, String startDate, String endDate, String onlyErrors) {

	    Session session = null;
	    List<FormEntry> formEntryList = null;
	    JSONArray finalReturn=null;
	    try {
	        session = openSession();
	        String sql = CustomSQLUtil.get(FETCH_FIELD_ENTRIES_CSV);
	        if(onlyErrors.equalsIgnoreCase("true")){
	        	sql = CustomSQLUtil.get(FETCH_FIELD_ENTRIES_CSV_ERRORS);
	        }
	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(startDate);
	        qPos.add(endDate);
	        qPos.add(formId);
	        List<Object> result = (List) QueryUtil.list(q, getDialect(),0,100000);
	        String serilizeString=null;
	        com.liferay.portal.kernel.json.JSONArray empoyeeJsonArray=null;
	        finalReturn = new JSONArray();
	        
	        for(Object elemnetObject:result){
		        serilizeString=JSONFactoryUtil.serialize(elemnetObject);
		        empoyeeJsonArray=JSONFactoryUtil.createJSONArray(serilizeString);
		        JSONObject json = new JSONObject();
		        json.put("id",empoyeeJsonArray.getInt(0));
		        json.put("date",empoyeeJsonArray.getString(1));
		        json.put("name",empoyeeJsonArray.getString(2));
		        json.put("value",empoyeeJsonArray.getString(3));
		        finalReturn.add(json);
	        }
	        
	    } catch (Exception e) {
	        try {
	            throw new SystemException(e);
	        } catch (SystemException se) {
	            se.printStackTrace();
	        }
	    } finally {
	        closeSession(session);
	    }

	    return finalReturn;
	}
	
	/*
	 * fetchFormEntriesExport
	 * To find the field entries belonging to form entries for a given form
	 * takes the parameters - formId
	 * returns JSONArray
	 */
	public JSONArray fetchFormEntriesExport(int formId,String exporter) {

	    Session session = null;
	    List<FormEntry> formEntryList = null;
	    JSONArray finalReturn=null;
	    try {
	        session = openSession();
	        String sql = CustomSQLUtil.get(FETCH_FORMS_TO_EXPORT);
	        //sql=sql.replaceAll("--EXPORTERS--","('"+exporters.replaceAll(",","','")+"')");
	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(formId);
//	        System.out.println("printing in form local service impl " + exporter + sql);
	        qPos.add(exporter);
	        q.addScalar("id",Type.INTEGER);
        	q.addScalar("name",Type.STRING);
        	q.addScalar("value",Type.STRING);
        	q.addScalar("date",Type.STRING);
        	q.addScalar("cis_name",Type.STRING);
        	q.addScalar("website",Type.STRING);
	        List<Object> result = (List) QueryUtil.list(q, getDialect(),0,100000);
	        String serilizeString=null;
	        com.liferay.portal.kernel.json.JSONArray empoyeeJsonArray=null;
	        finalReturn = new JSONArray();
	        
	        for(Object elemnetObject:result){
		        serilizeString=JSONFactoryUtil.serialize(elemnetObject);
		        empoyeeJsonArray=JSONFactoryUtil.createJSONArray(serilizeString);
		        JSONObject json = new JSONObject();
		        json.put("id",empoyeeJsonArray.getInt(0));
		        json.put("name",empoyeeJsonArray.getString(1));
		        json.put("value",empoyeeJsonArray.getString(2));
		        json.put("date",empoyeeJsonArray.getString(3));
		        json.put("cis_name",empoyeeJsonArray.getString(4));
		        json.put("website",empoyeeJsonArray.getString(5));
		        finalReturn.add(json);
	        }
	        
	    } catch (Exception e) {
	        try {
	            throw new SystemException(e);
	        } catch (SystemException se) {
	            se.printStackTrace();
	        }
	    } finally {
	        closeSession(session);
	    }

	    return finalReturn;
	}
}