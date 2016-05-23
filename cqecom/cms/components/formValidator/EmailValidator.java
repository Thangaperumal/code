package com.cqecom.cms.components.formValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;

import org.apache.sling.api.SlingHttpServletRequest;

import java.util.StringTokenizer;
import java.util.HashMap;

import javax.jcr.Node;

public class EmailValidator implements ValidatorInterface
{
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^([^@,;{\\s]+)@((?:[-a-zA-Z0-9]+\\.)+[a-zA-Z]{2,})$";
      //  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	
	public HashMap<String,String> validate(SlingHttpServletRequest formsRequest)
	{
		HashMap hm = null;
		String param_value="";
		pattern = Pattern.compile(EMAIL_PATTERN);
		//Check if email constraint parameter is present in the request
		if(formsRequest.getParameter("fields_with_email_constraint")!=null){
			StringTokenizer field_str = new StringTokenizer(formsRequest.getParameter("fields_with_email_constraint"),",");
		       String str="";
		       hm = new HashMap<String, String>();
			 while(field_str.hasMoreTokens())
		        {
		        	str = field_str.nextToken();
		        	param_value = formsRequest.getParameter(str);	
		        	if(param_value!=null&&param_value.trim().length()>0) {
		        	matcher = pattern.matcher(param_value);			
		        	if(!matcher.matches())
		        	{	
		        		// populate the error hash
		        		GlobalSettings settings = GlobalSettingsReader.getInstance().findSettings(formsRequest.getResource().adaptTo(Node.class));
		        		if(settings.getGlobalAppName().equalsIgnoreCase("cqecomit"))	//(formsRequest.getRequestPathInfo().getResourcePath().contains("cqecomit"))
		        			hm.put(str,"Si prega di inserire una mail valida");
		        		else if (ValidatorUtils.isBrazil(formsRequest)){
                            hm.put(str,"Please enter a valid email");
                        } else {
		        			hm.put(str,"Please enter a valid email");
                        }
		        	}else {
		        		if(param_value.trim().length()>50)
		        		{
                            if (ValidatorUtils.isBrazil(formsRequest)) {
                                hm.put(str,"Este campo não pode conter mais do que 50 caracteres");
                            } else {
		        			    hm.put(str,"Cannot exceed 50 characters in length");
                            }
		        		}
		        	}
		        } 
		        }
			 return hm;
		} 
		else
			return null;
	}
	
}
