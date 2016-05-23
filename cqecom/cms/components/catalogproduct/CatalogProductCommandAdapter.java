package com.cqecom.cms.components.catalogproduct;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

/**
 * @scr.component metatype="no"
 * @scr.service interface="AdapterFactory"
 * @scr.property name="svendorervice." value="Day Management AG"
 * @scr.property name="adaptables" value="org.apache.sling.api.resource.Resource"
 * @scr.property name="adapters" value="com.cqecom.cms.components.catalogproduct.CatalogProductCommand"
 */

public class CatalogProductCommandAdapter implements AdapterFactory 
{
    
        @SuppressWarnings("unchecked")

        public  <AdapterType> AdapterType   getAdapter(Object adaptable, Class<AdapterType> type)
        {
                ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);
                CatalogProductCommand cmdProduct = new CatalogProductCommand();
                if (values != null) 
                {
                    cmdProduct.setLanguage(values.get("language", String.class));
                    cmdProduct.setAcLevel(values.get("acLevel", String.class));
                    cmdProduct.setBest(values.get("isBest", "false").equals("true"));
                    cmdProduct.setEdition(values.get("edition", String.class));
                    cmdProduct.setFeatured(values.get("isFeatured", "false").equals("true"));
                    cmdProduct.setGreat(values.get("isGreat", "false").equals("true"));
                    cmdProduct.setLevel(values.get("level", String.class));
                    cmdProduct.setLocalization(values.get("localization", String.class));
                    cmdProduct.setProductType(values.get("product_type", String.class));
                    cmdProduct.setProdVersion(values.get("product_version", String.class));
                    cmdProduct.setLightBox(values.get("isLightBox", "false").equals("true"));
                    cmdProduct.setRichText(values.get("textIsRich", "false").equals("true"));    
                }
                return (AdapterType) cmdProduct;
        }
}
