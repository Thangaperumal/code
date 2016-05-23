package com.cqecom.cms.components.catalogproduct;

import com.cqecom.cms.components.AbstractMvcServlet;
import com.cqecom.cms.components.commons.CommonUtils;
import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;

import java.util.Map;
import java.util.HashMap;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.ServletConfig;


/**
 * @scr.component metatype="no"
 * @scr.service interface="javax.servlet.Servlet"
 * @scr.property name="svendorervice." value="Day Management AG"
 * @scr.property name="service.name" value="Controller Bundle"
 * @scr.property name="service.description" value="This is the Ctalog Controller Servlet"
 * @scr.property name="sling.servlet.resourceTypes" value="cqecom/components/content/catalogProduct"
 * @scr.property name="sling.servlet.extensions" value="html"
 */

public class CatalogProductController extends AbstractMvcServlet<CatalogProductCommand> {

	private static final long serialVersionUID = 1L;
	private CatalogProductViewHelper viewHelper;

	/** @scr.reference */
	private SlingRepository repository;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public CatalogProductController()
	{
	}
	
	@Override
	public void init(ServletConfig config)
	{ 
		


	}

	@Override
	protected CatalogProductCommand loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		CatalogProductCommand catalogProductCommand = request.getResource().adaptTo(CatalogProductCommand.class);
		return catalogProductCommand;
	}


	@Override
	protected String getValueObjectName() {
		return PageConstants.valueObjectname;
	}


	@Override
	protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
			CatalogProductCommand valueObject, Map<String, Object> model) {

		return PageConstants.viewName;
	}

	@Override
	protected String getGetJspPath() 
	{

		return PageConstants.jspPath;
	}

	@Override
	protected boolean isConfiguredResource(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		return true;
	}


	protected  String prepareView(SlingHttpServletRequest request,SlingHttpServletResponse response,CatalogProductCommand catalogProduct,Map<String,Object> model,GlobalSettings globalSettings, Session session) throws Exception
	{
			viewHelper = new CatalogProductViewHelper(catalogProduct,model,session,globalSettings);
			return viewHelper.getViewTemplate(request,response);
	}

	@Override
	protected void  initModel(SlingHttpServletRequest request, SlingHttpServletResponse response,CatalogProductCommand catalogProduct, Map<String, Object> model) 
	{
		Session session=null;
		try
		{  
			//Get the global settings from the Node
			GlobalSettings globalSettings = GlobalSettingsReader.getInstance().findSettings(request.getResource().adaptTo(Node.class));
			CatalogProductService catalogProductservice = new CatalogProductService();
			HashMap<String,Object> hashModel = (HashMap<String,Object>) model;

            session = repository.loginAdministrative(null);

			catalogProductservice.getproductCatalog(request,catalogProduct,hashModel,globalSettings,session);
			String preparedPage=  prepareView(request,response,catalogProduct,hashModel,globalSettings,session);
			//If the Prepared view is  null the show empty
			if(preparedPage == null)
			{
				preparedPage = "";
			}
			else
			{
				request.setAttribute("text", preparedPage);
			}
		}
		catch(Exception e)
		{
			//Handle the exception
			CommonUtils.logException(e, logger);   
		}
		finally
        {
			if(session != null)
				session.logout();
        }

	}
}





