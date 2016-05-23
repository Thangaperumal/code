package com.cqecom.cms.dynamicpricing;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.ServletConfig;

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
 * @scr.property name="service.description" value="This is the Pricing  Controller Servlet"
 * @scr.property name="sling.servlet.resourceTypes" value="cqecom/components/content/dyamicPricing"
 * @scr.property name="sling.servlet.extensions" value="html"
 */


public class PricingController  extends AbstractMvcServlet<DynamicPriceCommand> {

	public static String CACHETYPE="NODECACHE";
	/** @scr.reference */
	private SlingRepository repository;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void  initModel(SlingHttpServletRequest request, SlingHttpServletResponse response,DynamicPriceCommand dynamicPriceCommand, Map<String, Object> model) 
	{
		try
		{  
			GlobalSettings globalSettings = GlobalSettingsReader.getInstance().findSettings(request.getResource().adaptTo(Node.class));
			
			PriceFetcher priceFetcher=buildPriceFetcher(request);
			List<PriceItem> priceItems=priceFetcher.getPrice(dynamicPriceCommand,globalSettings);
			model.put("dynamicPrice",priceItems);

		}
		catch(Exception exception)
		{

			CommonUtils.logException(exception, logger);
		}
	}
	@Override
	protected String getGetJspPath() 
	{

		return "dynamicPrice.jsp";
	}

	protected PriceFetcher buildPriceFetcher(SlingHttpServletRequest request)
	{
		Cache cache=null;
		Node node=(Node)request.getResource().adaptTo(Node.class);
		if(CACHETYPE.equals("NODECACHE"))
		{
			cache=new NodeCache(node,repository);
		}
		PriceFetcher priceFetcher=new PriceFetcher(cache);
		return priceFetcher;
	}

	@Override
	protected DynamicPriceCommand loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		DynamicPriceCommand dynamicPriceCommand =new DynamicPriceCommand();  ;
		dynamicPriceCommand.setEdition(request.getParameter("edition"));
		dynamicPriceCommand.setLanguage(request.getParameter("language"));
		dynamicPriceCommand.setPromo(request.getParameter("promo"));
		dynamicPriceCommand.setSplit(request.getParameter("split"));


		return dynamicPriceCommand;
	}

	@Override
	protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
			DynamicPriceCommand valueObject, Map<String, Object> model) {

		return "viewName";
	}


}
