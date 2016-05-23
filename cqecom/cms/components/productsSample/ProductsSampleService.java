package com.cqecom.cms.components.productsSample;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.Session;

import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.LanguageSettings;
import com.cqecom.cms.components.commons.LanguageSettingsReader;
import com.cqecom.webdev.ec.Clients;
import com.cqecom.webdev.ec.JawsClient;

public class ProductsSampleService {

	JawsClient jc; 
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	HashMap<String,Object> categoryHash;
	String[] osubDuration; 
	 
	public ProductsSampleService(){	
		jc=Clients.getJawsClient();
		osubDuration = new String[]{"03", "06", "12"};		
		categoryHash=new HashMap<String,Object>();		
	}
	
	public void getproductsSample(SlingHttpServletRequest request,ProductsSampleCommand productsSampleCommandObj,HashMap<String,Object> modelHash,GlobalSettings globalSettings,Session session) throws Exception{
		
		//OSUB fix - getting it from lang settings
		String languageCode = productsSampleCommandObj.getLanguage(); 
		String currentPath = request.getResource().getPath();
		LanguageSettingsReader languageSettingsReader = new LanguageSettingsReader();
        LanguageSettings languageSettings = languageSettingsReader.findLanguageSettings(session, currentPath, languageCode);
		if(languageSettings != null){
			String osubLevels = languageSettings.getOSUBLevels();
			if(osubLevels != null)
				osubDuration = osubLevels.split(",");
		}
		HashMap[] osubProducts = new HashMap[osubDuration.length];
		HashMap splits = new HashMap();
		String promo = "";
		this.populateCategoryHash(productsSampleCommandObj);
		ArrayList<HashMap> prod = new ArrayList<HashMap>();
		String prodType = productsSampleCommandObj.getProductType();
		String title = productsSampleCommandObj.getLanguage();
		HashMap acProduct = new HashMap();
		HashMap product = new HashMap();
		boolean figses = false;
		String globalAppName = globalSettings.getGlobalAppName();
		 if ("OSUB".equals(prodType)){
			 
	           for(int i = 0;i < osubDuration.length; i++){
	              categoryHash.put("duration", osubDuration[i]);	              
	              osubProducts[i] = jc.findItembyCategory(globalSettings.getSiteCode(), categoryHash,splits,promo);
	              // Analytics: Add all osubs to the list and add to request
	              prod.add(osubProducts[i]);	             
	           }
	           
	           if ("3".equals(categoryHash.get("product_version"))) {	        	   
	              categoryHash.remove("duration");
	              categoryHash.put("product_type", "AC");
		      categoryHash.put("product_version", "4");	
	              if(title.matches("FRA|ITA|DEU|ESC|ESP|ENG|EBR")) {
	                categoryHash.put("level", "S5");
	                figses = true;
	              }
	              else if(("cqecomde").equals(globalAppName) && title.matches("DAR|KIS|IND|PAS|URD|LAT")) {
                        categoryHash.put("product_version", "3"); 
                        if(title.matches("LAT")){
		        categoryHash.put("level", "S3");}
                        else{
                        categoryHash.put("level", "L1");}
		      }
	              else{
	                 categoryHash.put("level", "S3");
	              }
	              acProduct = jc.findItembyCategory(globalSettings.getSiteCode(), categoryHash,splits,promo);
		      categoryHash.put("product_version", "3");	
	           }
	       }else {
	           categoryHash.put("level",productsSampleCommandObj.getLevel());
	           product =  jc.findItembyCategory(globalSettings.getSiteCode(), categoryHash,splits,promo);           
	           // Analytics: Add the hard product to the list and add to request
	           prod.add(product);
	       }
		 
		//For analytics - Todo - check if the analytics can be set outside the loop and if this is correct.
         //modelHash.put("product", product);
		 modelHash.put("productSample", product);
         modelHash.put("prod", prod);
         modelHash.put("categoryHash", categoryHash);
		 modelHash.put("figSes", figses);
		 modelHash.put("osubProducts", osubProducts);
		 modelHash.put("acProduct", acProduct);
		 
		 logger.debug("---------figses----------"+modelHash.get("figses"));
		 logger.debug("---------categoryHash----------"+modelHash.get("categoryHash"));
		 logger.debug("---------product----------"+modelHash.get("product"));
		 logger.debug("---------prod----------"+modelHash.get("prod"));
		 logger.debug("---------osubProducts----------"+modelHash.get("osubProducts"));
		 logger.debug("---------acProduct----------"+modelHash.get("acProduct"));
	}
	
	public void populateCategoryHash(ProductsSampleCommand productsSampleCommandObj){	
		categoryHash.put("language", productsSampleCommandObj.getLanguage());		
		categoryHash.put("product_type",productsSampleCommandObj.getProductType());
		categoryHash.put("edition",productsSampleCommandObj.getEdition());    
		categoryHash.put("product_version",productsSampleCommandObj.getProdVersion());
	}
	
}
