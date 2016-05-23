package com.erp.lead.service.impl;

import com.erp.lead.model.Country;
import com.erp.lead.service.base.CountryLocalServiceBaseImpl;
import com.erp.lead.service.persistence.CountryUtil;

import java.util.List;
import java.util.ArrayList;

/**
 * The implementation of the Country local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.CountryLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.CountryLocalServiceUtil} to access the Country local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Thangaperumal
 * @see com.erp.lead.service.base.CountryLocalServiceBaseImpl
 * @see com.erp.lead.service.CountryLocalServiceUtil
 */
public class CountryLocalServiceImpl extends CountryLocalServiceBaseImpl {
	public java.util.List<Country> getCountryByCode(String code){
		java.util.List<Country> countries = null;
		try{
		countries = CountryUtil.findByCode(code);
		}
		catch(Exception e){
			System.out.println("Caught exception in getCountryByCode in VerticalLocalServiceImpl");
		}
		return countries;
	}
	public java.util.List<Country> getCountryByISO(String iso){
		java.util.List<Country> countries = null;
		try{
		countries = CountryUtil.findByISO(iso);
		}
		catch(Exception e){
			System.out.println("Caught exception in getCountryByCode in VerticalLocalServiceImpl");
		}
		return countries;
	}
}
