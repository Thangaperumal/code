
package com.cqecom.cms.components.formValidator;

import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;

import javax.jcr.Node;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.components.commons.CommonUtils;
import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;
import com.cqecom.webdev.ec.Clients;



/**
 * This forms handling servlet accepts POSTs to a form begin paragraph
 * but only if the selector "form" and the extension "html" is used.
 *
 * @scr.component metatype="false"
 * @scr.service interface="javax.servlet.Servlet"
 * @scr.service interface="javax.servlet.Filter"
 * @scr.property name="filter.scope" value="REQUEST" private="true"
 *@scr.property name="filter.order" value="-1000" type="Integer" private="true"
 * @scr.property name="sling.servlet.resourceTypes" values.0="cqecom/components/page/rsTwoColumnFormPage" values.1="cqecom/components/page/rsSingleColumnFormPage" values.2="cqecom/components/page/rsSingleColumnOrganizationsFormPage"
 * @scr.property name="sling.servlet.methods" value="POST"
 * @scr.property name="sling.servlet.extensions" value="html"
 * @scr.property name="service.description" value="Form Validator Servlet"
 */
@SuppressWarnings("serial")
public class FormValidatorServlet
    extends SlingAllMethodsServlet
    implements Servlet, Filter {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

   
    protected static final String EXTENSION = "html";
    
    /** @scr.property name="sling.servlet.selectors" */
    protected static final String SELECTOR = "ecform";
    

    protected static final String ATTR_RESOURCE = FormValidatorServlet.class.getName() + "/resource";
    /**
     * @see org.apache.sling.api.servlets.SlingAllMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
     */
    protected void doPost(SlingHttpServletRequest request,
                          SlingHttpServletResponse response)
    throws ServletException, IOException {
        if ( ResourceUtil.isNonExistingResource(request.getResource())
             || request.getAttribute(ATTR_RESOURCE) == null ) {         
            logger.debug("Received fake request!");
            response.setStatus(500);
            return;
        }
       
        if ( this.logger.isDebugEnabled() ) {
            this.logger.debug("Validating POST request with form definition stored at {}.", request.getResource().getPath());
        }

        SlingHttpServletRequest formsRequest = new FormsHandlingRequest(request);
        SlingHttpServletResponse formsResponse = new FormsHandlingResponse(response);
        HashMap valueMap = new HashMap();
        HashMap hm = null;
        GlobalSettings globalSettings = null;
        final Resource rsrc = (Resource)request.getAttribute(ATTR_RESOURCE);
        request.removeAttribute(ATTR_RESOURCE); 
        String successPath = request.getParameter("success_path");
        String authorReturnPath="";
        String publishReturnPath="";
        try {
        globalSettings = GlobalSettingsReader.getInstance().findSettings(request.getResource().adaptTo(Node.class));
        this.populateValues(formsRequest,valueMap,globalSettings.getSiteContentPath());
        formsRequest.setAttribute("userInput",valueMap);
        //Blank Validation       
        hm = new BlankValidator().validate(formsRequest);
        if(hm==null)
        {       	
        	//Email Validation
        	hm = new EmailValidator().validate(formsRequest);
        	hm = new PhoneValidator().validate(formsRequest,hm);
        	if(hm!=null && hm.size()>0)
        	{
        		//Email validation failed. forward to error page
        		formsRequest.setAttribute("errorMessage",hm);
        		request.getRequestDispatcher(rsrc).forward(formsRequest, response);
        	}
        	else{
        		
        		try {
        			HashMap paramMap = this.populateClientParameters(valueMap);
        			paramMap.remove("required_fields");
        			paramMap.remove("fields_with_email_constraint");
        			paramMap.remove("fields_with_phone_constraint");
        			paramMap.remove("skipvalidation");
					String formurl = formsRequest.getParameter("formurl");
					if(formsRequest.getParameter("form_url")!=null)
					{
						formurl=formsRequest.getParameter("form_url");
					}
            		hm = Clients.getCisClient().submitCISForm(globalSettings.getSiteCode(),paramMap,formsRequest.getParameter("cis_name"),formurl);
            		HashMap errMap = new HashMap(); 
            		if(hm.get("saved").toString()=="false")  {
            		Object[] errMsgEc = (Object[])hm.get("errors");
            		Object[] ob2=null;
            		if(errMsgEc!=null)
            		{
            			for(int i=0; i<errMsgEc.length;i++)
            			{
            				ob2 = (Object[])errMsgEc[i];
            				errMap.put(ob2[0].toString(),ob2[1].toString());
                            logger.error("Error at submitting a form {}:{}", ob2[0].toString(),ob2[1].toString());
            			}
            			formsRequest.setAttribute("errorMessage",errMap);
            		}
            		request.getRequestDispatcher(rsrc).forward(formsRequest, response);
            		
            		}else {
            			//Forward to success page.
            			response.sendRedirect(request.getResourceResolver().map(successPath));         		    
            		}
            	}catch(Exception e)
            	{
            	}
        	}
            
        } else {
        	//Blank Validation Failed.Forward to error page
            formsRequest.setAttribute("errorMessage",hm);    
            request.getRequestDispatcher(rsrc).forward(formsRequest, response);
        }
        
        }catch(Exception e)
        {
        	CommonUtils.logException(e,this.logger);
        }
  
        return;
    }

  
    protected HashMap populateClientParameters(HashMap<String,Object> valueMap)
	{


		HashMap<String,String> clientParameters=new HashMap<String,String>();
		Iterator it = valueMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			String baseKeyName=(String)pairs.getKey();
			if(pairs.getValue() instanceof Object[] || pairs.getValue() instanceof String[]) 
			{

				Object[] values=(Object[])pairs.getValue();
				for(int i=0;i<values.length;++i)
				{
					clientParameters.put(baseKeyName+i,(String) values[i]);
				}
			}
			else
			{
				clientParameters.put(baseKeyName,(String)pairs.getValue());
			}

		}
		return clientParameters;

	}
    
    
    public void populateValues(SlingHttpServletRequest formsRequest, HashMap<String,Object> valueMap, String path)
	{
		//populate all the fields into valueMap
		String param_name="";
		Enumeration enumeration = formsRequest.getParameterNames();
		while(enumeration.hasMoreElements())
		{
			param_name = (String) enumeration.nextElement();
			//These elements need not to be put in the Value Map as it is not entered by the user 
			if(!param_name.equalsIgnoreCase("required_fields") || !param_name.equalsIgnoreCase("fields_with_email_constraint"))
			{
				if(formsRequest.getParameter(param_name)!=null)
				{
					String[] parameterValues=formsRequest.getParameterValues(param_name);
                                        if(param_name.equalsIgnoreCase("success_path"))
                                              parameterValues = new String[]{ parameterValues[0].replace(path,"").replace(".html","").replace("//","/") };
					if(parameterValues.length>1)
					{
						valueMap.put(param_name,parameterValues);
					}
					else
					{
						valueMap.put(param_name,parameterValues[0]);
					}
				
				}
				else
				{
					valueMap.put(param_name,"");
				}

			}
		}

	}
    
    
    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
   public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
    throws IOException, ServletException {
        // check if this is a post to a form
        if ( request instanceof SlingHttpServletRequest ) {
            final SlingHttpServletRequest req = (SlingHttpServletRequest)request;
            if ( "POST".equalsIgnoreCase(req.getMethod()) && req.getParameter("cis_name")!= null && req.getParameter("skipvalidation")==null)  {
            	GlobalSettings globalSettings = GlobalSettingsReader.getInstance().findSettings(req.getResource().adaptTo(Node.class));
            	String globalAppName = "";
            	if(globalSettings != null)
            		globalAppName = globalSettings.getGlobalAppName();
            	if(!req.getResource().getResourceType().contains("usprocess") && !req.getResource().getResourceType().contains("process")
                        && (globalAppName.equalsIgnoreCase("cqecomuk") || globalAppName.equalsIgnoreCase("cqecomit") || globalAppName.equalsIgnoreCase("cqecombr") || globalAppName.equalsIgnoreCase("cqecomfr") || globalAppName.equalsIgnoreCase("cqecomeu") || globalAppName.equalsIgnoreCase("cqecomes")))
            	{
                // store original resource as request attribute
                req.setAttribute(ATTR_RESOURCE, req.getResource());
                final StringBuilder sb = new StringBuilder();
                final String formPath = req.getResource().getPath();
                final StringBuilder forwardPath = new StringBuilder();        
                forwardPath.append(formPath);
                forwardPath.append("/jcr:content");
                forwardPath.append('.');
                forwardPath.append(SELECTOR);
                forwardPath.append('.');
                forwardPath.append(EXTENSION);
                req.getRequestDispatcher(forwardPath.toString()).forward(request, response);
                return;
            	}
            }    
        }

        chain.doFilter(request, response);
        
        
    }
    
    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
        // nothing to do!
    } 
}
