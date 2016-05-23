package com.cqecom.cms.components.formValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.Node;

import java.util.StringTokenizer;
import java.util.HashMap;


public class PhoneValidator implements ValidatorInterface
{
	private Pattern pattern;
	private Matcher matcher;
	private static final String PHONE_PATTERN =
        "^[-0-9 ()+\\.xX]*$";
    private static final String BRAZIL_PHONE_PATTERN = "^[-0-9 ()]*$";
    public static final String INVALID_PHONE_BR = "Por favor coloque um número de telefone válido";

	public HashMap<String,String> validate(SlingHttpServletRequest formsRequest)
	{
		HashMap<String,String> hm = null;
		String param_value="";
		pattern = Pattern.compile(getPhonePattern(formsRequest));
		//Check if email constraint parameter is present in the request
		if(formsRequest.getParameter("fields_with_phone_constraint")!=null){
			StringTokenizer field_str = new StringTokenizer(formsRequest.getParameter("fields_with_phone_constraint"),",");
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
		        			hm.put(str,"Si prega di inserire un numero di telefono valida");
                        else if (ValidatorUtils.isBrazil(formsRequest)) {
                            hm.put(str, INVALID_PHONE_BR);
                        } else
		        		    hm.put(str,"Please enter a valid phone number");
		        	}
		        }
		        }
			 return hm;
		}
		else
			return null;
	}

	public HashMap<String,String> validate(SlingHttpServletRequest formsRequest, HashMap<String,String>hm)
	{
		String param_value="";
		if(hm==null){
		 hm = new HashMap<String, String>();
		}
		pattern = Pattern.compile(getPhonePattern(formsRequest));
        boolean isBrazil = isBrazil(formsRequest);
		if(formsRequest.getParameter("fields_with_phone_constraint")!=null){
			StringTokenizer field_str = new StringTokenizer(formsRequest.getParameter("fields_with_phone_constraint"),",");
		       String str="";
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
		        			hm.put(str,"Si prega di inserire un numero di telefono valida");
		        		else if (isBrazil) {
                            hm.put(str,"Please enter a valid phone number");
                        } else {
                            hm.put(str, "Please enter a valid phone number");
                        }
                    } else {
                        if (param_value.trim().length() > 30) {
                            if (isBrazil) {
                                hm.put(str, "Este campo não pode conter mais do que 30 caracteres");
                            } else {
                                hm.put(str, "Cannot exceed 30 characters in length");
                            }
                        }
                        if (isBrazil) {
                            brazilPhoneValidation(hm, param_value, str);
                        }
                    }
                }
            }
            return hm;
        } else
            return hm;
    }

    private void brazilPhoneValidation(HashMap<String, String> hm, String param_value, String str) {
        if (param_value.trim().length() < 10) {
            hm.put(str, INVALID_PHONE_BR);
        } else {
            int digitsCount = countDigitsInPhone(param_value);
            if (digitsCount > 12) {
                hm.put(str, INVALID_PHONE_BR);
            }
        }
    }
    private boolean isBrazil(SlingHttpServletRequest formsRequest) {
    	GlobalSettings settings = GlobalSettingsReader.getInstance().findSettings(formsRequest.getResource().adaptTo(Node.class));
        return settings.getGlobalAppName().equalsIgnoreCase("cqecombr");
    }

    private String getPhonePattern(SlingHttpServletRequest formsRequest) {
        if (ValidatorUtils.isBrazil(formsRequest)) {
            return BRAZIL_PHONE_PATTERN;
        }
        return PHONE_PATTERN;
    }

    private int countDigitsInPhone(String phone) {
        int digitsCount = 0;
        for (int i = 0; i < phone.length(); i++) {
            char ch = phone.charAt(i);
            if (Character.isDigit(ch)) {
                digitsCount++;
            }
        }
        return digitsCount;
    }

}
