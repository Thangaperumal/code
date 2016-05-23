
package com.cqecom.cms.components.formValidator;

import java.util.HashMap;
import java.util.StringTokenizer;

import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.Node;

public class BlankValidator implements ValidatorInterface
{
	
	public HashMap<String,String> validate(SlingHttpServletRequest formsRequest)
	{
		   HashMap hm = null;
		   String param_name="";
		   //Check if required_fields is present in the request
		   if(formsRequest.getParameter("required_fields")!=null) {
		   StringTokenizer field_str = new StringTokenizer(formsRequest.getParameter("required_fields"),",");
	       String str="";
	       hm = new HashMap<String, String>();
	       while(field_str.hasMoreTokens())
	        {
	        	str = field_str.nextToken();
	        	if((formsRequest.getParameter(str)==null)||(formsRequest.getParameter(str)).trim().length()==0)
	        	{
	        		 // populate the error map.
	        		GlobalSettings settings = GlobalSettingsReader.getInstance().findSettings(formsRequest.getResource().adaptTo(Node.class));
	        		if(settings.getGlobalAppName().equalsIgnoreCase("cqecomit"))	//(formsRequest.getRequestPathInfo().getResourcePath().contains("cqecomit"))
	        			hm.put(str,"Non pu\u00f2 essere vuoto");
	        		else if (ValidatorUtils.isBrazil(formsRequest)) {
                        hm.put(str, "Não pode estar vazio");
                    } else {
	        			hm.put(str,"Cannot be blank");
                    }
	        	} 
	        }
	}
		   
		   if(hm!=null&&hm.size()>0)
			   return hm;
		   else 
			   return null;
	       
	}
	
}
