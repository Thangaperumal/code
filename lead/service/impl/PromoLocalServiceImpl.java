package com.erp.lead.service.impl;

import com.erp.lead.model.Promo;
import com.erp.lead.service.base.PromoLocalServiceBaseImpl;
import com.erp.lead.service.persistence.PromoUtil;

import java.util.List;
import java.util.ArrayList;

/**
 * The implementation of the Promo local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.PromoLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.PromoLocalServiceUtil} to access the Promo local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Thangaperumal
 * @see com.erp.lead.service.base.PromoLocalServiceBaseImpl
 * @see com.erp.lead.service.PromoLocalServiceUtil
 */
public class PromoLocalServiceImpl extends PromoLocalServiceBaseImpl {
	
	public java.util.List<Promo> getPromoByCode(String code){
		java.util.List<Promo> promos = null;
		try{
		promos = PromoUtil.findByCode(code);
		}
		catch(Exception e){
			System.out.println("Caught exception in getPromoByCode in PromoLocalServiceImpl");
		}
		return promos;
	}
}
