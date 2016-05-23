package com.erp.lead.service.impl;

import org.json.simple.JSONObject;

import com.erp.lead.model.Site;
import com.erp.lead.service.base.SiteLocalServiceBaseImpl;
import com.erp.lead.service.persistence.SiteFinderUtil;
import com.erp.lead.service.persistence.SiteUtil;
import com.liferay.portal.kernel.json.JSONArray;

import java.util.List;
import java.util.ArrayList;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

/**
 * The implementation of the Sites local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.SiteLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.SiteLocalServiceUtil} to access the Sites local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author RosettaStone Ltd.
 * @see com.erp.lead.service.base.SiteLocalServiceBaseImpl
 * @see com.erp.lead.service.SiteLocalServiceUtil
 */
public class SiteLocalServiceImpl extends SiteLocalServiceBaseImpl {
	public java.util.List<Site> getSiteByCode(String code){
		java.util.List<Site> sites = null;
		try{
		sites = SiteUtil.findByCode(code);
		}
		catch(Exception e){
			System.out.println("Caught exception in getSiteByCode in SiteLocalServiceImpl");
		}
		return sites;
	}
	
	/*
	 * getWebsitesByCallCenter
	 * To get the list of websites which are not call centers
	 * returns JSONArray
	 */
	public org.json.simple.JSONArray getWebsitesByCallCenter(){
		List<Object> sites = null;
		org.json.simple.JSONArray finalReturn = null;
		try{
			sites = SiteFinderUtil.getWebsitesByCallCenter();
			String serializeString = "";
			JSONArray jsonArray;
			finalReturn = new org.json.simple.JSONArray();
			for(Object site:sites){
		        serializeString=JSONFactoryUtil.serialize(site);
		        jsonArray=JSONFactoryUtil.createJSONArray(serializeString);
				JSONObject siteJson = new JSONObject();
				siteJson.put("id",jsonArray.getString(0));
				siteJson.put("code",jsonArray.getString(1));
				siteJson.put("description",jsonArray.getString(2));
		        finalReturn.add(siteJson);
	        }
		}
		catch(Exception e){
			System.out.println("Caught exception in getWebsitesByCallCenter in SiteLocalServiceImpl");
		}
		return finalReturn;
	}
}
