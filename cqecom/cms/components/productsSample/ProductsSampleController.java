package com.cqecom.cms.components.productsSample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.components.AbstractMvcServlet;
import com.cqecom.cms.components.commons.CommonUtils;
import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;



/**
 * @scr.component metatype="no"
 * @scr.service interface="javax.servlet.Servlet"
 * @scr.property name="svendorervice." value="Day Management AG"
 * @scr.property name="service.name" value="Controller Bundle"
 * @scr.property name="service.description" value="This is the product sample Controller Servlet"
 * @scr.property name="sling.servlet.resourceTypes" value="cqecom/components/content/productsSample"
 * @scr.property name="sling.servlet.extensions" value="html"
 */

public class ProductsSampleController extends AbstractMvcServlet<ProductsSampleCommand> {
	/** @scr.reference */
	private SlingRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ProductsSampleViewHelper viewHelper;
	
	public ProductsSampleController(){	
	}
	
	@Override
	protected ProductsSampleCommand loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {	
		ProductsSampleCommand productsSampleCommandObj = request.getResource().adaptTo(ProductsSampleCommand.class);
		return productsSampleCommandObj;
	}
	@Override
	protected String getValueObjectName() {
		return ProductsSampleConstants.valueObjectname;
	}

	@Override
	protected String getViewName(SlingHttpServletRequest request,
			SlingHttpServletResponse response,
			ProductsSampleCommand valueObject, Map<String, Object> model) {	
		return ProductsSampleConstants.viewName;
	}

	@Override
	protected String getGetJspPath() {		
		return ProductsSampleConstants.jspPath;
	}
	
	@Override
	protected boolean isConfiguredResource(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		return true;
	}
	
	@Override
	protected void  initModel(SlingHttpServletRequest request, SlingHttpServletResponse response,ProductsSampleCommand productsSampleCommandObj, Map<String, Object> model){ 
	
		Session session = null;
		try{
				GlobalSettings globalSettings = GlobalSettingsReader.getInstance().findSettings(request.getResource().adaptTo(Node.class));
				ProductsSampleService productSampleServiceObj = new ProductsSampleService(); 
				HashMap<String,Object> hashModel=(HashMap<String,Object>) model;
				String preparedPage = null;
				//To send to service for accessing lang settings
				session = repository.loginAdministrative(null);

				if(productsSampleCommandObj.getLanguage().length() > 0){
					productSampleServiceObj.getproductsSample(request,productsSampleCommandObj, hashModel, globalSettings, session);								
					preparedPage =  prepareView( request,response,productsSampleCommandObj,hashModel,globalSettings);
					//For Analytics.. setting the products in the request
					ArrayList<HashMap> productList = (ArrayList<HashMap>) hashModel.get("prod");
					request.setAttribute("product", productList);
					
				}		
				
				//If the Prepared view is null the show empty
				if(preparedPage == null){			
					preparedPage = "";
				}else{
					request.setAttribute("text", preparedPage);
				}
			
		}catch(Exception e){
			//Handle the exception
			CommonUtils.logException(e, logger);   
		}finally{
			if(session != null)
				session.logout();
        }
		
	}	
	
	protected  String prepareView(SlingHttpServletRequest request,SlingHttpServletResponse response,ProductsSampleCommand productsSampleCommandObj,Map<String,Object> model,GlobalSettings globalSettings) throws Exception
	{
		Session session=null;
		try
		{
			session=repository.loginAdministrative(null);
			viewHelper=new ProductsSampleViewHelper(productsSampleCommandObj,model,session,globalSettings);
			return viewHelper.getViewTemplate(request,response);
		}
		finally
		{
			session.logout();

		}

	}
	
	
	
}
