package com.erp.lead.helper;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.util.dao.orm.CustomSQLUtil;
import java.util.List;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

public class CustomSQLHelper extends BasePersistenceImpl{
	public List<Object> executeQuery(String input, String replace_str){
		Session session = null;
		List<Object> result = null;
	    try {
	        session = openSession();

	        String sql = CustomSQLUtil.get(input);
	        if(replace_str!=null){
	        	sql = sql.replaceAll("exporter_name",replace_str);
	        }
	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        QueryPos qPos = QueryPos.getInstance(q);
	        result = (List) QueryUtil.list(q, getDialect(),0,1);
	        /*String serilizeString=null;
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
	        }*/
	        return result;
	    } catch (Exception e) {
	        try {
	            throw new SystemException(e);
	        } catch (SystemException se) {
	            se.printStackTrace();
	        }
	    } finally {
	        closeSession(session);
	    }
		return result;
	}
}