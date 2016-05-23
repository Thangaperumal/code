package com.erp.lead.service.impl;

import java.util.List;

import com.erp.lead.model.EcOrderIdSequence;
import com.erp.lead.service.EcOrderIdSequenceLocalServiceUtil;
import com.erp.lead.service.base.EcOrderIdSequenceLocalServiceBaseImpl;
import com.erp.lead.service.persistence.EcOrderIdSequenceUtil;

import java.util.List;


/**
 * The implementation of the EcOrderIdSequence local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.EcOrderIdSequenceLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.EcOrderIdSequenceLocalServiceUtil} to access the EcOrderIdSequence local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Thangaperumal
 * @see com.erp.lead.service.base.EcOrderIdSequenceLocalServiceBaseImpl
 * @see com.erp.lead.service.EcOrderIdSequenceLocalServiceUtil
 */
public class EcOrderIdSequenceLocalServiceImpl
    extends EcOrderIdSequenceLocalServiceBaseImpl {
	public int getEcOrderId(){
		int order_id = 0;
		try{
			List<EcOrderIdSequence> logList = EcOrderIdSequenceUtil.findAll();
			EcOrderIdSequence ecOrderId = logList.get(0);
			order_id = ecOrderId.getId();
			ecOrderId.setId(order_id+1);
			EcOrderIdSequenceLocalServiceUtil.updateEcOrderIdSequence(ecOrderId);
			
		}
		catch(Exception ex){
			System.out.println("Caught exception in getEcOrderId  EcOrderIdSequenceLocalServiceImpl " + ex.getMessage());
		}
		finally{
			return order_id;
		}
	}
	/*public void setEcOrderId(int ecOrderId){
		List<EcOrderIdSequence> logList = null;
		try{
			logList = EcOrderIdSequenceUtil.findAll();
			
		}
		catch(Exception ex){
			System.out.println("Caught exception in getEcOrderId  EcOrderIdSequenceLocalServiceImpl " + ex.getMessage());
		}
		finally{
			return logList.get(0).getId();;
		}
	}*/
}
