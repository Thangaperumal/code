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



@Component(immediate = true, label = "Trial OSUB Email", description = "Trial OSUB Email", metatype = false)
@Service(Servlet.class)
@Properties({
@Property(name = "service.description", value = "RS Trial OSUB Email"),
@Property(name = "service.vendor", value = "CqEcom"),
@Property(name = "sling.servlet.paths", value = "/content/trialOsubEmail") })


public class TrialOsubEmailController extends SlingAllMethodsServlet {

	private Session session;
    @Reference
    private SlingRepository repository;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Reference
    private MailService ms;


	public TrialOsubEmailController(){
	}

	public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response){
        try {
            String strLicenseName="";
            String strLicensePassword="";
            String strLanguageName="";
            String strEndsAt="";
            String strLanguageSlug="";
            String strTrialUrl="";
            String strOfferPromo="";
            String strEmailTemplateUrl="";
            String strFollowupEmailTemplateUrl="";
            String strEmailSubject="";
            String strEmailFrom="";
            String followupEmail="";
            String strFollowupEmailSubject="";

            if(request!=null) {
                strLicenseName = getParamValue(request,"license_name");
                strLicensePassword = getParamValue(request,"license_password");
                strLanguageName = getParamValue(request,"language_name");
                strEndsAt = getParamValue(request,"ends_at");
                strLanguageSlug = getParamValue(request,"language_slug");
                strTrialUrl = getParamValue(request,"trial_url");
                strOfferPromo = getParamValue(request,"offer_promo");
                strEmailTemplateUrl = getParamValue(request,"email_template_url");
                strFollowupEmailTemplateUrl=  getParamValue(request,"followup_email_template_url");
                strEmailSubject = getParamValue(request,"email_subject");
                strFollowupEmailSubject = getParamValue(request,"followup_email_subject");
                strEmailFrom = getParamValue(request,"email_from");
                followupEmail =  getParamValue(request,"followup_email");
            }

            if(followupEmail.equals("true")){
              strEmailTemplateUrl = strFollowupEmailTemplateUrl;
              strEmailSubject = strFollowupEmailSubject;
            }

            session = repository.loginAdministrative(null);
            String pageHtml = session.getRootNode().getNode(strEmailTemplateUrl+"/jcr:content/content").getProperty("text").getValue().getString();

            pageHtml = replaceToken(pageHtml,"license_name",strLicenseName);
            pageHtml = replaceToken(pageHtml,"license_password",strLicensePassword);
            pageHtml = replaceToken(pageHtml,"language_name",strLanguageName);
            pageHtml = replaceToken(pageHtml,"ends_at",strEndsAt);
            pageHtml = replaceToken(pageHtml,"language_slug",strLanguageSlug);
            pageHtml = replaceToken(pageHtml,"trial_url",strTrialUrl);
            pageHtml = replaceToken(pageHtml,"offer_promo",strOfferPromo);
            Email emailObj=new HtmlEmail();
            emailObj.setContent(pageHtml.toString(),"text/html");

            if(!strEmailFrom.equals(""))
            emailObj.setFrom(strEmailFrom,"CqEcom "+strLanguageName+" Trial");

            if(!strLicenseName.equals(""))
            emailObj.addTo(strLicenseName);

            if(!strEmailSubject.equals(""))
            emailObj.setSubject(strEmailSubject);
            ms.sendEmail(emailObj);
            logger.info("Mail sent to => "+strLicenseName);
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
