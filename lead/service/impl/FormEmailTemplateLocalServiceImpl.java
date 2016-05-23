package com.erp.lead.service.impl;

import com.erp.lead.model.FormEmailTemplate;
import com.erp.lead.service.base.FormEmailTemplateLocalServiceBaseImpl;
import com.erp.lead.service.persistence.FormEmailTemplateUtil;

import java.util.List;

/**
 * The implementation of the Form Email Templates local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.erp.lead.service.FormEmailTemplateLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.erp.lead.service.FormEmailTemplateLocalServiceUtil} to access the Form Email Templates local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author RosettaStone Ltd.
 * @see com.erp.lead.service.base.FormEmailTemplateLocalServiceBaseImpl
 * @see com.erp.lead.service.FormEmailTemplateLocalServiceUtil
 */
public class FormEmailTemplateLocalServiceImpl
    extends FormEmailTemplateLocalServiceBaseImpl {
	
	/*
	 * getAll
	 * To get all the form email templates
	 * returns List of formEmailTemplates
	 */
	public List<FormEmailTemplate> getAll(){
		try{
			return FormEmailTemplateUtil.findAll();
		}
		catch(Exception e){
			System.out.println("Caught exception at FormEmailTemplateLocalServiceImpl findAll");
		}
		return null;
	}
}
