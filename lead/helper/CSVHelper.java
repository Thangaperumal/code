package com.erp.lead.helper;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

import com.erp.lead.service.FormEntryLocalServiceUtil;
import com.liferay.portal.kernel.util.HttpUtil;

import java.io.OutputStream;
//import javax.ws.rs.core.HttpHeaders;
import java.nio.charset.Charset;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.servlet.PortletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;

public class CSVHelper{

	
	public static void generateCSV(ResourceResponse response,JSONArray array,String fileName)
			throws Exception {

		List<String> columnNames1 = new ArrayList<String>();
		
		//needed in order to return a csv file
		response.setContentType("text/csv");
		response.setProperty("Content-Disposition", "attachment; filename="+fileName);
		
		try{
			StringBundler sb = new StringBundler();
			Map<String,Map<String,String>> hashMap = new TreeMap<String,Map<String,String>>();
			Map<String,String> innerMap = null;
			for (int i=0; i<(array.size()); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				if(hashMap.containsKey(String.valueOf(obj.get("id")))){
					innerMap = hashMap.get(String.valueOf(obj.get("id")));
					innerMap.put(String.valueOf(obj.get("name")),String.valueOf(obj.get("value")));
					innerMap.put("date",String.valueOf(obj.get("date")));
				}
				else{
					innerMap = new TreeMap<String,String>();
					innerMap.put(String.valueOf(obj.get("name")),String.valueOf(obj.get("value")));
					innerMap.put("date",String.valueOf(obj.get("date")));
					hashMap.put(String.valueOf(obj.get("id")),innerMap);
				}
				if(!(columnNames1.contains(obj.get("name")))){
					columnNames1.add(String.valueOf(obj.get("name")));
				}
			}

			//adding the column names explicitly for ID and Date
			sb.append("ID");
			sb.append(CSV_SEPARATOR);
			sb.append("Date");
			sb.append(CSV_SEPARATOR);
			for (String columnName : columnNames1) {
				sb.append(columnName);
				sb.append(CSV_SEPARATOR);
			}
			sb.setIndex(sb.index() - 1);
			sb.append(CharPool.NEW_LINE);
			
			for (Map.Entry<String, Map<String,String>> entry : hashMap.entrySet())
			{
			    innerMap = entry.getValue();
			    sb.append(entry.getKey());//this gives the id of the form entry
				sb.append(CSV_SEPARATOR);
				sb.append(innerMap.get("date"));
				sb.append(CSV_SEPARATOR);
			    for (String columnName : columnNames1) {
			    	String temp_val = innerMap.get(columnName);
			    	if(temp_val == null){
			    		temp_val = ""; 
			    	}
			    	sb.append(temp_val);
					sb.append(CSV_SEPARATOR);
				}
				sb.setIndex(sb.index() - 1);
				sb.append(CharPool.NEW_LINE);
			}
			
			response.getWriter().write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static final String CSV_SEPARATOR = ",";
}