package com.cqecom.cms.components.productsSample;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

/**
 * @scr.component metatype="no"
 * @scr.service interface="AdapterFactory"
 * @scr.property name="svendorervice." value="Day Management AG"
 * @scr.property name="adaptables" value="org.apache.sling.api.resource.Resource"
 * @scr.property name="adapters" value="com.cqecom.cms.components.productsSample.ProductsSampleCommand"
 */

public class ProductsSampleCommandAdapter implements AdapterFactory{ 

	@SuppressWarnings("unchecked")

	public  <AdapterType> AdapterType   getAdapter(Object adaptable, Class<AdapterType> type){        

		ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);
		ProductsSampleCommand cmdProductObj = new ProductsSampleCommand();

		if (values != null){                
			cmdProductObj.setLanguage(values.get("language", String.class));                                        
			cmdProductObj.setEdition(values.get("edition", String.class));
			cmdProductObj.setLevel(values.get("level", String.class));
			cmdProductObj.setLocalization(values.get("localization", String.class));
			cmdProductObj.setProductType(values.get("product_type", String.class));
			cmdProductObj.setProdVersion(values.get("product_version", String.class));
			cmdProductObj.setRootNav(values.get("rootNav", String.class));
			Boolean subNav=values.get("subNav", Boolean.class);
			if(subNav!=null)
			{
				cmdProductObj.setSubNav(subNav);
			}
			else
			{
				cmdProductObj.setSubNav(false);
			}

			cmdProductObj.setRichText(values.get("textIsRich", Boolean.class));
			cmdProductObj.setText(values.get("text",String.class));
		}
		return (AdapterType) cmdProductObj;
	}
}