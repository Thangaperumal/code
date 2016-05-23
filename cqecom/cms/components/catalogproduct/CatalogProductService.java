package com.cqecom.cms.components.catalogproduct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.LanguageSettings;
import com.cqecom.cms.components.commons.LanguageSettingsReader;
import com.cqecom.webdev.ec.JawsClient;
import com.cqecom.webdev.ec.Clients;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

public class CatalogProductService {
	ArrayList products = new ArrayList();
	ArrayList oProducts = new ArrayList();
	HashMap<String,Object>  acItem ;
	HashMap<String,Object> categoryHash;
	HashMap<String,Object>  product;
	String[] osubs;
	String[] tsubs;
	String[] levels;
	JawsClient jc; 
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public CatalogProductService()
	{
		jc=Clients.getJawsClient();
		osubs=new String[]{"03", "06", "12"};
		tsubs=new String[]{"01", "03", "06", "12"};
		acItem=new HashMap<String,Object>();
		categoryHash=new HashMap<String,Object>();
		product=new HashMap<String,Object>();
	}

	public void  getproductCatalog(SlingHttpServletRequest request, CatalogProductCommand catalogProduct,HashMap<String,Object> modelHash,GlobalSettings globalSettings, Session session) throws Exception
	{
		HashMap[] onlineProducts=null;
		HashMap splits = new HashMap();
		String promo = "";
		String level=catalogProduct.getLevel();
		String prodType=catalogProduct.getProductType();
		String prodVersion=catalogProduct.getProdVersion();
		this.populateCategoryHash(catalogProduct);
		this.populateLevels(catalogProduct, request, session);
		String globalAppname=globalSettings.getGlobalAppName();
		if (prodType.equals("OSUB") || prodType.equals("OTB") || prodType.equals("RF") || prodType.equals("SEOB"))
		{
			onlineProducts = new HashMap[levels.length];

			for(int i = 0; i < levels.length; i++) 
			{
				categoryHash.put("duration", levels[i]);
				onlineProducts[i] = jc.findItembyCategory(globalSettings.getSiteCode(), categoryHash,splits,promo);
				logger.debug("Inside get product dialog"+ onlineProducts[i]);
				oProducts.add(onlineProducts[i]);
			}

			//if(!level.equals("TOT"))
			if(prodType.equals("OSUB") || prodType.equals("SEOB") || prodType.equals("RF")) 
			{
				product = onlineProducts[0];
			}

			//if(!(prodType.equals("OTB")) &&prodVersion.equals("3"))
			if((prodType.equals("OSUB") || prodType.equals("SEOB")) && prodVersion.equals("3"))
			{
				categoryHash.remove("duration");
				if(!catalogProduct.getAcLevel().equalsIgnoreCase("NA") && !catalogProduct.getAcLevel().equalsIgnoreCase("NOAC"))
				{
					categoryHash.put("level",catalogProduct.getAcLevel());
					categoryHash.put("product_type", "AC");
					//This is the quick fix for the problem in Which ac  version is 4 and Osub version is 3 in Oracle
					if(globalAppname.equals(PageConstants.CQECOMIT)||
					   globalAppname.equals(PageConstants.CQECOMFR)||
					   globalAppname.equals(PageConstants.CQECOMES)||
					   globalAppname.equals(PageConstants.CQECOMEU)||
                                           globalAppname.equals(PageConstants.CQECOMDE)
                                           )
					{	
						
                                          
                                         //This is a quickfix for gobot languages in DE website . 
                                        if((globalAppname.equals(PageConstants.CQECOMDE) && ((catalogProduct.getLanguage()).equalsIgnoreCase("DAR") || (catalogProduct.getLanguage()).equalsIgnoreCase("URD") || (catalogProduct.getLanguage()).equalsIgnoreCase("PAS") || (catalogProduct.getLanguage()).equalsIgnoreCase("KIS") || (catalogProduct.getLanguage()).equalsIgnoreCase("IND"))) || (catalogProduct.getLanguage()).equalsIgnoreCase("LAT") )
                                        {                               
                                         categoryHash.put("product_version", "3");
                                        } 
					else
				          categoryHash.put("product_version", "4");
					}
					acItem = jc.findItembyCategory(globalSettings.getSiteCode(),categoryHash,splits,promo);
				}
			}
			
			if((prodVersion.equals("3")) && (globalAppname.equals(PageConstants.CQECOMUS)|| globalAppname.equals(PageConstants.CQECOMUK)) && (prodType.equals("OSUB") || prodType.equals("SEOB"))){    
				product = onlineProducts[0];
			}

		}
		else
		{
			product = jc.findItembyCategory(globalSettings.getSiteCode(),categoryHash,splits,promo);

		}
		products.add(product);
		modelHash.put("oproducts", oProducts);
		modelHash.put("onlineproducts",onlineProducts);
		modelHash.put("product", product);
		modelHash.put("acitem", acItem);
		modelHash.put("products", products);
		//Logiing all the parameters
		logger.debug("---------onlineproducts----------"+modelHash.get("onlineproducts"));
		logger.debug("-------oproducts--------"+modelHash.get("oproducts"));
		logger.debug("-------product---------"+ modelHash.get("product"));
		logger.debug("-----AC Item---------"+ modelHash.get("acitem"));
		logger.debug("----List Price with Vat"+product.get("list_price_with_vat"));
		logger.debug("Upsell price with Vat"+product.get("upsell_value_with_vat"));
		logger.debug("----Site code is"+globalSettings.getSiteCode());
	}


	public void populateCategoryHash(CatalogProductCommand catalogProduct)
	{
		categoryHash.put("language", catalogProduct.getLanguage());
		categoryHash.put("level",catalogProduct.getLevel());
		categoryHash.put("product_type",catalogProduct.getProductType());
		categoryHash.put("edition",catalogProduct.getEdition());    
		categoryHash.put("product_version",catalogProduct.getProdVersion());
	}

	public void populateLevels(CatalogProductCommand catalogProduct, SlingHttpServletRequest request, Session session) throws Exception
	{
		String level = catalogProduct.getLevel();
		String languageCode = catalogProduct.getLanguage(); 
		String currentPath = request.getResource().getPath();
			
		String prodType=catalogProduct.getProductType();
 		LanguageSettingsReader languageSettingsReader=new LanguageSettingsReader();
                LanguageSettings languageSettings=languageSettingsReader.findLanguageSettings(session, currentPath, languageCode);
		if (catalogProduct.getLevel().equals("NA") || level.equals("TOT")) { // fix for OSUB and TSUB MESS   
			if(prodType.equals("OSUB") || prodType.equals("SEOB"))
			{
			    //fetching osub levels from lang settings..worst case, it will take all the osub leves if lang settings are unavailable
				levels=osubs;
				if(languageSettings != null){
					String osubLevels = languageSettings.getOSUBLevels();
					if(osubLevels != null)
						levels = osubLevels.split(",");
				}
			}
			else if(prodType.equals("OTB")) 
			{
				//same logic as in above for osub
				levels=tsubs;
				if(languageSettings != null){
					String otbLevels = languageSettings.getOTBLevels();
					if(otbLevels !=null)
						levels = otbLevels.split(",");
				}
			}
			else if(prodType.equals("RF"))
			{
				if (languageSettings != null){
                		String axrLevels = languageSettings.getAXRLevels();
                		levels = axrLevels.split(",");
                		}
                	}						
		}
	}
}
