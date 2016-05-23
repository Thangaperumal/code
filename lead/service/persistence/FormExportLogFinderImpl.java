package com.erp.lead.service.persistence;

import com.erp.lead.model.FieldEntry;
import com.erp.lead.model.Form;
import com.erp.lead.model.FormExportLog;
import com.erp.lead.model.impl.FieldEntryImpl;
import com.erp.lead.model.impl.FormExportLogImpl;
import com.erp.lead.model.impl.FormImpl;
import com.erp.lead.service.persistence.FormExportLogFinder;
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
import java.lang.Boolean;

public class FormExportLogFinderImpl extends BasePersistenceImpl<FormExportLog> implements FormExportLogFinder {

	public static final String FIND_MAX_COUNT = FormExportLogFinder.class.getName() + ".findMaxCount";

	/*
	 * isSuccessful
	 * To determine if a formentry has at least one unsuccessful export
	 * takes the parameters - formEntryId
	 * return true if all records are successfully exported
	 * return false if at least one exporter has failed
	 * return "" if there are no export logs - needed for a case in UI
	 * returns String
	 */
	public String isSuccessful(int formEntryId) {

		    Session session = null;
		    try {
		        session = openSession();
		        String sql = CustomSQLUtil.get(FIND_MAX_COUNT);
		        SQLQuery q = session.createSQLQuery(sql);
		        q.setCacheable(false);
		        QueryPos qPos = QueryPos.getInstance(q);
		        qPos.add(formEntryId);
		        List<Boolean> result = (List) QueryUtil.list(q, getDialect(),0,100);
		        if(result!=null && result.size()>0){
		        	for(Boolean obj:result){
		        		boolean cond = (obj).booleanValue();
		        		if(!cond){
		        			return "false";
		        		}
		        	}
		        	return "true";
		        }
		        else{
		        	return "";
		        }
		    } catch (Exception e) {
		        try {
		            throw new SystemException(e);
		        } catch (SystemException se) {
		            se.printStackTrace();
		        }
		    }

		    return "";
		}
}