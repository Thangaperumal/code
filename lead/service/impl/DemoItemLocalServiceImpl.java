package com.erp.lead.service.impl;

import com.erp.lead.model.DemoItem;
import com.erp.lead.service.base.DemoItemLocalServiceBaseImpl;
import com.erp.lead.service.persistence.DemoItemUtil;

import java.util.List;
import java.util.ArrayList;

/**
 * The implementation of the DemoItem local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.DemoItemLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.DemoItemLocalServiceUtil} to access the DemoItem local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Thangaperumal
 * @see com.erp.lead.service.base.DemoItemLocalServiceBaseImpl
 * @see com.erp.lead.service.DemoItemLocalServiceUtil
 */
public class DemoItemLocalServiceImpl extends DemoItemLocalServiceBaseImpl {
	
	public java.util.List<DemoItem> getDemoItemByAllIds(int organizationId,int verticalId,int localizationId,int languageId){
		java.util.List<DemoItem> demoItems = null;
		try{
			demoItems = DemoItemUtil.findByAllIds(organizationId, verticalId, localizationId, languageId);
		}
		catch(Exception e){
			System.out.println("Caught exception in getDemoItemByAllIds in demoItemsLocalServiceImpl");
		}
		return demoItems;
	}
}
