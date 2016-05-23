package com.erp.lead.service.persistence;

import com.erp.lead.model.FieldEntry;
import com.erp.lead.model.Form;
import com.erp.lead.model.FormEntry;
import com.erp.lead.model.FormExportLog;
import com.erp.lead.model.impl.FieldEntryImpl;
import com.erp.lead.model.impl.FormEntryImpl;
import com.erp.lead.model.impl.FormExportLogImpl;
import com.erp.lead.model.impl.FormImpl;
import com.erp.lead.service.FormLocalServiceUtil;
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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Type;

import java.math.BigInteger;

public class FormFinderImpl extends BasePersistenceImpl<Form> implements FormFinder {

	public static final String FIND_MAX_COUNT = FormFinder.class.getName() +".findMaxCount";
	public static final String FIND_BY_FORM_CODE_SITE_ID = FormFinder.class.getName() +".findByFormCodeSiteId";
	public static final String FIND_EXPORTERS = FormFinder.class.getName() +".findExporters";
	public static final String FIND_BY_EMAIL_EXPORTER = FormFinder.class.getName() +".findByEmailExporter";

	/*
	 * countByFormCodeSiteId
	 * To get the count of forms for the given formCode and siteId
	 * takes the parameters - formCode (part of name of the form), siteId (id of the website)
	 * returns int
	 */
	public int countByFormCodeSiteId(String formCode, int siteId) {

	    Session session = null;
	    try {
	        session = openSession();
	        String sql = CustomSQLUtil.get(FIND_MAX_COUNT);
	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add("%"+formCode+"%");
	        qPos.add(siteId);
	        List<Object> result = (List) QueryUtil.list(q, getDialect(),0,1);
	        if(result!=null && result.size()>0){
	        	return ((BigInteger)result.get(0)).intValue();
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

	    return 0;
	}
	
	/*
	 * findByFormCodeSiteId
	 * To find the forms
	 * takes the parameters - formCode (part of name of the form), siteId (id of the website), rows range
	 * returns List of Form
	 */
	public List<Form> findByFormCodeSiteId(String formCode, int siteId,int start, int end) {

	    Session session = null;
	    List<Form> formList = null;
	    try {
	        session = openSession();

	        String sql = CustomSQLUtil.get(FIND_BY_FORM_CODE_SITE_ID);
	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("forms", FormImpl.class);
	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add("%"+formCode+"%");
	        qPos.add(siteId);
	        List<Object> result = (List) QueryUtil.list(q, getDialect(),start,end);
	        formList = new ArrayList<Form>();
	        for(Object obj:result){
	        	formList.add((Form)obj);
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

	    return formList;
	}
	
	/*
	 * findExporters
	 * To find the exporters for a given form
	 * takes the parameters - id
	 * returns String (exporter names)
	 */
	public String findExporters(int id) {

	    Session session = null;
	    List<String> exporterList = null;
	    try {
	        session = openSession();

	        String sql = CustomSQLUtil.get(FIND_EXPORTERS);
	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        //q.addEntity("forms", FormImpl.class);
	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(id);
	        List<Object> result = (List) QueryUtil.list(q, getDialect(),0,100000);
	        /*exporterList = new ArrayList<String>();
	        for(Object obj:result){
	        	exporterList.add((String)obj);
	        }
	        String serilizeString=null;
	        com.liferay.portal.kernel.json.JSONArray empoyeeJsonArray=null;
	        
	        for(Object elemnetObject:result){
		        serilizeString=JSONFactoryUtil.serialize(elemnetObject);
		        empoyeeJsonArray=JSONFactoryUtil.createJSONArray(serilizeString);
		        JSONObject json = new JSONObject();
		        json.put("id",empoyeeJsonArray.getInt(0));
		        json.put("name",empoyeeJsonArray.getString(1));
		        json.put("value",empoyeeJsonArray.getString(2));
		        json.put("date",empoyeeJsonArray.getString(3));
		        exporterList.add(empoyeeJsonArray.getString(0));
	        }*/
	        return (String)result.get(0);
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
	}
	
	/*
	 * findByEmailExporter
	 * To find the forms which have email exporter enabled
	 * returns the list of forms
	 */
	
	public List<Form> findByEmailExporter(){
		Session session = null;
	    List<Form> formList = null;
	    try {
	        session = openSession();

	        String sql = CustomSQLUtil.get(FIND_BY_EMAIL_EXPORTER);
	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("forms", FormImpl.class);
	        List<Object> result = (List) QueryUtil.list(q, getDialect(),0,100000);
	        formList = new ArrayList<Form>();
	        for(Object obj:result){
	        	formList.add((Form)obj);
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

	    return formList;
	}
	
}