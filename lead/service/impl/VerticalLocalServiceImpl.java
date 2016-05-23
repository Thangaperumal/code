package com.erp.lead.service.impl;

import com.erp.lead.model.Vertical;
import com.erp.lead.service.base.VerticalLocalServiceBaseImpl;
import com.erp.lead.service.persistence.VerticalUtil;

import java.util.List;
import java.util.ArrayList;

/**
 * The implementation of the Vertical local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.VerticalLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.VerticalLocalServiceUtil} to access the Vertical local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Thangaperumal
 * @see com.erp.lead.service.base.VerticalLocalServiceBaseImpl
 * @see com.erp.lead.service.VerticalLocalServiceUtil
 */
public class VerticalLocalServiceImpl extends VerticalLocalServiceBaseImpl {
	public java.util.List<Vertical> getVerticalByDescription(String desc){
		java.util.List<Vertical> verticals = null;
		try{
		verticals = VerticalUtil.findByDescription(desc);
		}
		catch(Exception e){
			System.out.println("Caught exception in getVerticalByDescription in VerticalLocalServiceImpl");
		}
		return verticals;
	}
}
