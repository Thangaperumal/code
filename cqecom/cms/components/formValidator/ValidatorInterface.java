package com.cqecom.cms.components.formValidator;
import java.util.HashMap;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

public interface ValidatorInterface {
	
	
	public  HashMap<String,String> validate(SlingHttpServletRequest req);
	
	
}