package com.cqecom.cms.services.priceCacher;

import java.util.Iterator;
import org.slf4j.Logger;
import com.day.cq.wcm.api.WCMMode;
import com.day.cq.wcm.api.components.DropTarget;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.jackrabbit.util.Text;
import com.cqecom.webdev.ec.JawsClient;
import com.cqecom.webdev.ec.Clients;
import java.util.*;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

@Component
@Service(PriceCacher.class)
public class PriceCacherImpl implements PriceCacher {
  private static final Logger logger = LoggerFactory.getLogger(PriceCacherImpl.class);

    public String getString(){
        return "Price Cacher Works Here as well";
    }
    
    public javax.jcr.Node cacheItemPrices(javax.jcr.Node promoCode, javax.jcr.Node vertical, javax.jcr.Node langCode, javax.jcr.Node level, String lang, String[] levelList, String globalEdition, String siteCode, String globalCurrency, String paStatus, Logger log ) throws Exception{
        String promo = promoCode.getName().toString(); 
        JawsClient jc = Clients.getJawsClient();
        for(int i=0;i<levelList.length;i++){
            HashMap<String, String> categoryHash = new HashMap<String, String>();
            categoryHash = getCategoryHash(categoryHash, levelList, globalEdition, siteCode, lang, i);
            level = createSubNodesInsidePromo(promoCode, vertical, langCode, level, levelList, globalEdition, lang, i, paStatus);
            vertical = promoCode.getNode(globalEdition.toString());
            langCode = vertical.getNode(lang.toString());    
            HashMap products = new HashMap();
            products = jc.findItembyCategory(siteCode, categoryHash, promo);
            if (products != null){
                addValuesToNodes(products, level, globalCurrency);
                level.getSession().save(); 
            }
        } //end for loop
        return langCode;
    }
    
    private void addNoPriceValueToNode(HashMap products, javax.jcr.Node level, String priceDescription) throws Exception{
        level.setProperty(priceDescription,"No Price"); 
        products.put(priceDescription, "NO Price");
    }
    
    private void addFormatedPriceValueToNode(HashMap products, javax.jcr.Node level, String priceDescription, Float price, String globalCurrency) throws Exception{
        price = new Float((Integer)products.get(priceDescription));
        price = (Float)price.floatValue()/100;
        price = new Float(Round(price.floatValue(), 2));
        level.setProperty(priceDescription, formatFloatPrice(globalCurrency, price));
        products.put(priceDescription, formatFloatPrice(globalCurrency, price));
    }
    
    private void addPriceValueToNode(HashMap products, javax.jcr.Node level, String priceDescription, Float price, String globalCurrency) throws Exception{
        if(products.get(priceDescription) == null){
            addNoPriceValueToNode(products, level, priceDescription);
        }else{
            addFormatedPriceValueToNode(products, level, priceDescription, price, globalCurrency);
        }
    }
    
    private void addSkuValueToNode(HashMap products, javax.jcr.Node level) throws Exception{
        if(products.get("sku") == null){
            level.setProperty("sku", "NO SKU");
            products.put("sku", "NO SKU");
        }else{
            level.setProperty("sku", products.get("sku").toString());
            products.put("sku", products.get("sku").toString());
        }
    }
    
    private void addValuesToNodes(HashMap products, javax.jcr.Node level, String globalCurrency) throws Exception{
        Float price = new Float(0.0);
        Float dynamicPrice = new Float(0.0);
        Float upsell_value = new Float(0.0);
        addPriceValueToNode(products, level, "list_price_with_vat", price, globalCurrency);
        addPriceValueToNode(products, level, "your_price_with_vat", dynamicPrice, globalCurrency);
        addPriceValueToNode(products, level, "upsell_value_with_vat", upsell_value, globalCurrency);
        addSkuValueToNode(products, level);
    }
    
    private HashMap<String, String> getCategoryHash(HashMap<String, String> categoryHash, String[] levelList, String globalEdition, String siteCode, String lang, int i) throws Exception{
        categoryHash.put("level", levelList[i]);
        categoryHash.put("edition", globalEdition);
        categoryHash.put("language", lang.toString());
        categoryHash = setProductVersionAndTypeForCategoryHash(categoryHash, siteCode, lang);
        return categoryHash;
    }
    
    private Boolean isV2Product(String lang){
      return lang.toString().matches(".*(THA|CYM|PAS|IND|KIS|DAN).*");
    }
    
    private Boolean isV3Product(String siteCode, String lang, String globalEdition){
      return lang.toString().matches(".*(LAT).*") || siteCode.equals("DE_WEBSITE") || globalEdition.equals("HS");
    }
    
    private String getProductVersion(String siteCode, String lang, String globalEdition){
        if(isV2Product(lang)){
            return "2";
        }else if(isV3Product(siteCode, lang, globalEdition)){
            return "3";
        }else{
            return "4";
        }
    }
    
    private String getProductType(String siteCode, String lang, String globalEdition){
        if(isV2Product(lang)){
            return "FG";
        }else if(isV3Product(siteCode, lang, globalEdition)){
            return "ACB";
        }else{
            return "TO";
        }
    }
    
    private HashMap<String, String> setProductVersionAndTypeForCategoryHash(HashMap<String, String> categoryHash, String siteCode, String lang) throws Exception{
        String globalEdition = categoryHash.get("edition");
        categoryHash.put("product_version", getProductVersion(siteCode, lang, globalEdition));
        categoryHash.put("product_type", getProductType(siteCode,lang, globalEdition));
        return categoryHash;
    }
    
    private javax.jcr.Node getIfExistsElseCreateNode(javax.jcr.Node parentNode, String childNodeName) throws Exception{
        javax.jcr.Node childNode = null;
        if(!parentNode.hasNode(childNodeName)){
            childNode = parentNode.addNode(childNodeName, "nt:unstructured");
            childNode.getSession().save();
        }else{
            childNode = parentNode.getNode(childNodeName);
        }
        return childNode;
    }
    
    private javax.jcr.Node setPriceApproverStatus(javax.jcr.Node node, String paStatus) throws Exception{
        node.setProperty("pas", paStatus);
        node.getSession().save();
        return node;
    }
    
    private javax.jcr.Node createSubNodesInsidePromo(javax.jcr.Node promoCode, javax.jcr.Node vertical, javax.jcr.Node langCode, javax.jcr.Node level, String[] levelList, String globalEdition, String lang, int i, String paStatus) throws Exception{
        vertical = getIfExistsElseCreateNode(promoCode, globalEdition);
        langCode = getIfExistsElseCreateNode(vertical, lang);
        langCode = setPriceApproverStatus(langCode, paStatus);
        level = getIfExistsElseCreateNode(langCode, levelList[i]);
        return level;
    }
          
    private float Round(float Rval, int Rpl){
       float p = (float)Math.pow(10,Rpl);
       Rval = Rval * p;
       float tmp = Math.round(Rval);
       return (float)tmp/p;
    }
    
    private String formatFloatPrice(String currency, Float price){
       String floatPrice = price.toString();
       String[] priceArray = floatPrice.split("\\.");
       if (priceArray[1].length () < 2){
         floatPrice += "0";
       }
	   if(currency.equals("&euro;")){
 	     floatPrice = floatPrice.replace(".",",");
	   }
       return currency + floatPrice;
    }
    
    public javax.jcr.Node cacheQBItemPrices(javax.jcr.Node languageNode, javax.jcr.Node productTypeNode, javax.jcr.Node verticalNode, javax.jcr.Node versionNode, javax.jcr.Node levelNode, String lang, String productType, String[] levelList, String globalEdition, String siteCode, String paStatus, long version, String promo) throws Exception
{
        
       JawsClient jc = Clients.getJawsClient();
       for(int i=0;i<levelList.length;i++)
       {
           HashMap categoryHash = new HashMap();
           HashMap categoryV4Hash = new HashMap();
           Float price = new Float(0.0);
           Float dynamicPrice = new Float(0.0);
           Float upsell_value = new Float(0.0);
           Integer productPrice = new Integer(0);

	    if(siteCode.equals("KR_WEBSITE"))
            {
	      categoryHash = buildCategoryHashForKR(lang, levelList[i], globalEdition, version, productType, categoryHash);
	    }
	    if(siteCode.equals("FR_WEBSITE") || siteCode.equals("ES_WEBSITE") || siteCode.equals("IT_WEBSITE"))
            {
              categoryHash = buildCategoryHashForFIST(lang, levelList[i], globalEdition, version, productType, categoryHash);
            }
	    if(siteCode.equals("UK_WEBSITE"))
        {
	      categoryHash = buildCategoryHashForUK(lang, levelList[i], globalEdition, version, productType, categoryHash);
	    }
		if(siteCode.equals("US_WEBSITE"))
        {
	      categoryHash = buildCategoryHashForUS(lang, levelList[i], globalEdition, version, productType, categoryHash);
	    }
		
		if(siteCode.equals("DE_WEBSITE"))
        {
	      categoryHash = buildCategoryHashForDE(lang, levelList[i], globalEdition, version, productType, categoryHash);
	    }
		
		if(siteCode.equals("EU_WEBSITE"))
        {
	      categoryHash = buildCategoryHashForEU(lang, levelList[i], globalEdition, version, productType, categoryHash);
	    }
			
	
            HashMap products = new HashMap();
            HashMap productsV4 = new HashMap();
           if (!languageNode.hasNode(productType))
             {
              productTypeNode = languageNode.addNode(productType.toString(), "nt:unstructured");
              productTypeNode.getSession().save();
             }else
             {
              productTypeNode = languageNode.getNode(productType.toString());
             }
             if (!productTypeNode.hasNode(globalEdition.toString()))
             {
              verticalNode = productTypeNode.addNode(globalEdition.toString(), "nt:unstructured");
              verticalNode.getSession().save();
             }
             else
             {
              verticalNode = productTypeNode.getNode(globalEdition.toString());
             }
             if (!verticalNode.hasNode(String.valueOf(version)))
             {
              versionNode = verticalNode.addNode(String.valueOf(version), "nt:unstructured");
              versionNode.getSession().save();

             }else
             {
               versionNode = verticalNode.getNode(String.valueOf(version));
             }
             if (!versionNode.hasNode(levelList[i].toString()))
             {
              levelNode = versionNode.addNode(levelList[i].toString(), "nt:unstructured");
              levelNode.getSession().save();

             }else
             {
               levelNode = versionNode.getNode(levelList[i].toString());
             }
             products = jc.findItembyCategory(siteCode, categoryHash, promo);

            if (products != null){
              if (products.get("list_price_with_vat") == null)
              {
               levelNode.setProperty("list_price_with_vat","No Price"); 
               products.put("list_price_with_vat", "NO Price");
              }else{
              price = new Float((Integer)products.get("list_price_with_vat"));
              price = (Float)price.floatValue()/100;
              price = new Float(Round(price.floatValue(), 2));
	      levelNode.setProperty("sku", (String) products.get("sku"));	
              levelNode.setProperty("list_price_with_vat", price);
              products.put("list_price_with_vat", price);
             }
             if (products.get("your_price_with_vat") == null)
              {
               levelNode.setProperty("your_price_with_vat","No Price"); 
               products.put("your_price_with_vat", "NO Price");
              }else{ 
               if (promo != null && !promo.equals("")){
              dynamicPrice = new Float((Integer)products.get("your_price_with_vat"));
              dynamicPrice = (Float)dynamicPrice.floatValue()/100;
              dynamicPrice = new Float(Round(dynamicPrice.floatValue(), 2));
              levelNode.setProperty(promo, dynamicPrice);
	      levelNode.setProperty("your_price_with_vat", price);	
              //level.setProperty("your_price_with_vat", formatFloatPrice(globalCurrency, dynamicPrice, siteCode));
              products.put("your_price_with_vat", dynamicPrice);
              }else{ 
                
                levelNode.setProperty("your_price_with_vat", dynamicPrice); 
		}
              }
             if (products.get("upsell_value_with_vat") == null)
             {
              levelNode.setProperty("upsell_value_with_vat","No Price"); 
              products.put("upsell_value_with_vat", "NO Price");
             }
             else
             {
              upsell_value = new Float((Integer)products.get("upsell_value_with_vat"));
              upsell_value = (Float)upsell_value.floatValue()/100;
              upsell_value = new Float(Round(upsell_value.floatValue(), 2));
              levelNode.setProperty("upsell_value_with_vat", upsell_value);
              products.put("upsell_value_with_vat", upsell_value);
             }
             levelNode.getSession().save(); 
            }// end v3 if 
       } //end for loop
    return languageNode;
  }
  private HashMap buildCategoryHashForKR(String lang, String levelListElement, String globalEdition, long version, String productType, HashMap categoryHash)
{
           categoryHash.put("level", levelListElement);
           categoryHash.put("product_type", "ACB");
           categoryHash.put("edition", globalEdition);
           categoryHash.put("language", lang.toString());
	   if(version == 4)
            {
            categoryHash.put("product_version", "4");
            categoryHash.put("product_type", "TO");
	    }
            else if(version == 3)
	    {
				if(levelListElement.toString().matches(".*(06|12).*"))
				{
				categoryHash.put("product_version", "3");
				categoryHash.put("product_type", "SEOB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}else
				{
			     categoryHash.put("product_version", "3");
			     categoryHash.put("product_type", "ACB");
				}
			}
			else if(version == 2)
			{
				if(levelListElement.toString().matches(".*(06).*") && lang.toString().matches(".*(DAN|THA|CYM).*"))
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "SEOB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}
				else
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "FG");
				}
			}
 			else if(version == 1){
                                if(levelListElement.toString().matches(".*(01|03|06|12).*") && lang.toString().matches(".*(ENG).*"))
				{
                                categoryHash.put("product_version", "1");
                                categoryHash.put("product_type", "RF");
                                categoryHash.put("level", "NA");
                                categoryHash.put("duration", levelListElement.toString());
                                }
                        }
		   		   return categoryHash;
   }

private HashMap buildCategoryHashForFIST(String lang, String levelListElement, String globalEdition, long version, String productType, HashMap categoryHash)
{
           categoryHash.put("level", levelListElement);
           categoryHash.put("product_type", "ACB");
           categoryHash.put("edition", globalEdition);
           categoryHash.put("language", lang.toString());
            if(version == 3)
	    {
				if(levelListElement.toString().matches(".*(06|12).*"))
				{
				categoryHash.put("product_version", "3");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}else
				{
			     categoryHash.put("product_version", "3");
			     categoryHash.put("product_type", "ACB");
				}
	    }else if(version == 2)
		{
		if(levelListElement.toString().matches(".*(06|12).*"))
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}
				else
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "FG");
				}
		}
		   		   return categoryHash;
   }
   
   
   private HashMap buildCategoryHashForDE(String lang, String levelListElement, String globalEdition, long version, String productType, HashMap categoryHash)
{
           categoryHash.put("level", levelListElement);
           categoryHash.put("product_type", "ACB");
           categoryHash.put("edition", globalEdition);
           categoryHash.put("language", lang.toString());
            if(version == 3)
	    {
				if(levelListElement.toString().matches(".*(06|12).*"))
				{
				categoryHash.put("product_version", "3");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}else
				{
			     categoryHash.put("product_version", "3");
			     categoryHash.put("product_type", "ACB");
				}
	    }else if(version == 2)
		{
		if(levelListElement.toString().matches(".*(06|12).*"))
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}
				else
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "FG");
				}
		}
		   		   return categoryHash;
   }
   
   
   private HashMap buildCategoryHashForEU(String lang, String levelListElement, String globalEdition, long version, String productType, HashMap categoryHash)
{
           categoryHash.put("level", levelListElement);
           categoryHash.put("product_type", "ACB");
           categoryHash.put("edition", globalEdition);
           categoryHash.put("language", lang.toString());
            if(version == 3)
	    {
				if(levelListElement.toString().matches(".*(06|12).*"))
				{
				categoryHash.put("product_version", "3");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}else
				{
			     categoryHash.put("product_version", "3");
			     categoryHash.put("product_type", "ACB");
				}
	    }else if(version == 2)
		{
		if(levelListElement.toString().matches(".*(06|12).*"))
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}
				else
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "FG");
				}
		}
		   		   return categoryHash;
   }
   
   
  private HashMap buildCategoryHashForUK(String lang, String levelListElement, String globalEdition, long version, String productType, HashMap categoryHash)
{
           categoryHash.put("level", levelListElement);
           categoryHash.put("product_type", "ACB");
           categoryHash.put("edition", globalEdition);
           categoryHash.put("language", lang.toString());
			if(version == 4)
			{
				if(levelListElement.toString().matches(".*(03|06|12).*")) 
				{
					categoryHash.put("product_version", "3");
					categoryHash.put("product_type", "OTB");
					categoryHash.put("level", "TOT");
					categoryHash.put("duration", levelListElement.toString());
				 }  
				else {
					categoryHash.put("product_version", "4");
					categoryHash.put("product_type", "TO");
				}
			}
				else if(version == 3)
			{
				if(levelListElement.toString().matches(".*(03|06).*"))
				{
				categoryHash.put("product_version", "3");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}else
				{
			     categoryHash.put("product_version", "3");
			     categoryHash.put("product_type", "ACB");
				}
			}
			else if(version == 2)
			{
				if(levelListElement.toString().matches(".*(06).*") && lang.toString().matches(".*(DAN|THA|CYM).*"))
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}
				else
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "FG");
				}
			}
 			else if(version == 1){
                                if(levelListElement.toString().matches(".*(01|03|06|12).*") && lang.toString().matches(".*(ENG).*"))
				{
                                categoryHash.put("product_version", "1");
                                categoryHash.put("product_type", "RF");
                                categoryHash.put("level", "NA");
                                categoryHash.put("duration", levelListElement.toString());
                                }
                        }
		   		   return categoryHash;
   }
   
   private HashMap buildCategoryHashForUS(String lang, String levelListElement, String globalEdition, long version, String productType, HashMap categoryHash)
{
           categoryHash.put("level", levelListElement);
           categoryHash.put("product_type", "ACB");
           categoryHash.put("edition", globalEdition);
           categoryHash.put("language", lang.toString());
			if(version == 4)
			{
				if(levelListElement.toString().matches(".*(03|06|12).*")) 
				{
					categoryHash.put("product_version", "3");
					categoryHash.put("product_type", "OTB");
					categoryHash.put("level", "TOT");
					categoryHash.put("duration", levelListElement.toString());
				 }  
				else {
					categoryHash.put("product_version", "4");
					categoryHash.put("product_type", "TO");
				}
			}
				else if(version == 3)
			{
				if(levelListElement.toString().matches(".*(03|06).*"))
				{
				categoryHash.put("product_version", "3");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}else
				{
			     categoryHash.put("product_version", "3");
			     categoryHash.put("product_type", "ACB");
				}
			}
			else if(version == 2)
			{
				if(levelListElement.toString().matches(".*(06).*") && lang.toString().matches(".*(DAN|THA|CYM).*"))
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "OSUB");
				categoryHash.put("level", "NA");
				categoryHash.put("duration", levelListElement.toString());
				}
				else
				{
				categoryHash.put("product_version", "2");
				categoryHash.put("product_type", "FG");
				}
			}
 			else if(version == 1){
                                if(levelListElement.toString().matches(".*(01|03|06|12).*") && lang.toString().matches(".*(ENG).*"))
				{
                                categoryHash.put("product_version", "1");
                                categoryHash.put("product_type", "RF");
                                categoryHash.put("level", "NA");
                                categoryHash.put("duration", levelListElement.toString());
                                }
                        }
		   		   return categoryHash;
   }
	
}
