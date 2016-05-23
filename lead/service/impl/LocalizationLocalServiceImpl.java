package com.erp.lead.service.impl;

import com.erp.lead.model.Localization;
import com.erp.lead.service.base.LocalizationLocalServiceBaseImpl;
import com.erp.lead.service.persistence.LocalizationUtil;

import java.util.List;
import java.util.ArrayList;

/**
 * The implementation of the Localization local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.LocalizationLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.LocalizationLocalServiceUtil} to access the Localization local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Thangaperumal
 * @see com.erp.lead.service.base.LocalizationLocalServiceBaseImpl
 * @see com.erp.lead.service.LocalizationLocalServiceUtil
 */
public class LocalizationLocalServiceImpl
    extends LocalizationLocalServiceBaseImpl {
	
	public java.util.List<Localization> getLocalizationByCode(String code){
		java.util.List<Localization> localizations = null;
		try{
			localizations = LocalizationUtil.findByCode(code);
		}
		catch(Exception e){
			System.out.println("Caught exception in getLocalizationByCode in LocalizationLocalServiceImpl");
		}
		return localizations;
	}
	
	public java.util.List<Localization> getLocalizationByTypeCode(String type,String code){
		java.util.List<Localization> localizations = null;
		try{
			localizations = LocalizationUtil.findByTypeCode(type,code);
		}
		catch(Exception e){
			System.out.println("Caught exception in getLocalizationByTypeCode in LocalizationLocalServiceImpl");
		}
		return localizations;
	}
}
