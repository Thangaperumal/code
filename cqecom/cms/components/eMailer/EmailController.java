package com.cqecom.cms.components.eMailer;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Session;
import javax.servlet.Servlet;

import com.day.cq.mailer.MailService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.jcr.api.SlingRepository;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.util.Map;
import java.util.Set;
import java.util.Iterator;


@Component(immediate = true, label = "Emailer", description = "Emailer", metatype = false)
@Service(Servlet.class)
@Properties({
@Property(name = "service.description", value = "RS Emailer"),
@Property(name = "service.vendor", value = "CqEcom"),
@Property(name = "sling.servlet.paths", value = "/content/emailer") })


public class EmailController extends SlingAllMethodsServlet {

    private Session session;
    @Reference
    private SlingRepository repository;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Reference
    private MailService ms;


	public EmailController(){
	}

	public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response){
        try {
            String strToEMailId="";                        
            String strEmailTemplateUrl="";            
            String strEmailSubject="";
            String strEmailFrom="";
            String strEmailFromDesc="";
            

            if(request!=null) {
                strToEMailId = getParamValue(request,"email_id");                                
                strEmailTemplateUrl = getParamValue(request,"email_template_url");                
                strEmailSubject = getParamValue(request,"email_subject");                
                strEmailFrom = getParamValue(request,"email_from");                
		strEmailFromDesc = getParamValue(request,"email_from_desc");
            }

            session = repository.loginAdministrative(null);
            String pageHtml = session.getRootNode().getNode(strEmailTemplateUrl+"/jcr:content/content").getProperty("text").getValue().getString();
             
	    Map params = request.getParameterMap(); 
	    Set names = params.keySet();
	    Iterator iter = names.iterator();
	    while(iter.hasNext()){
		String paramName = (String) iter.next();
		String paramValue = getParamValue(request,paramName); 
	        pageHtml = replaceToken(pageHtml,paramName,paramValue);
	    }
	    logger.info("end");	
            Email emailObj=new HtmlEmail();
            emailObj.setContent(pageHtml.toString(),"text/html");

            if(!strEmailFrom.equals(""))
            emailObj.setFrom(strEmailFrom,strEmailFromDesc);

            if(!strToEMailId.equals(""))
            emailObj.addTo(strToEMailId);

            if(!strEmailSubject.equals(""))
            emailObj.setSubject(strEmailSubject);
            ms.sendEmail(emailObj);
            logger.info("Mail sent to => "+strToEMailId);
        }
        catch(Exception ex) {
            logger.info(ex.getMessage());
        }
        finally {
  	        if(session != null)
		session.logout();
        }
	}

     public String replaceToken(String templateString,String token,String tokenValue) {

         if(!tokenValue.equals(""))
         templateString = templateString.replaceAll("\\["+token+"\\]",tokenValue);

         return templateString;
     }

     public String getParamValue(SlingHttpServletRequest request,String paramName){
         String parameterValue="";

         if(request.getParameter(paramName)!=null)
            parameterValue = request.getParameter(paramName);

         return parameterValue;
     }

}
