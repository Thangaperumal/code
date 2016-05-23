package com.erp.lead.helper;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

import com.erp.lead.exception.DemoItemNotFoundException;
import com.erp.lead.exception.ExportToEloquaNotSuccessful;
import com.erp.lead.exception.InvalidDataException;
import com.erp.lead.model.Address;
import com.erp.lead.model.Country;
import com.erp.lead.model.Customer;
import com.erp.lead.model.DemoItem;
import com.erp.lead.model.Form;
import com.erp.lead.model.FormEmailTemplate;
import com.erp.lead.model.FormEntry;
import com.erp.lead.model.FormExportLog;
import com.erp.lead.model.Localization;
import com.erp.lead.model.Order;
import com.erp.lead.model.OrderLine;
import com.erp.lead.model.Promo;
import com.erp.lead.model.Site;
import com.erp.lead.model.Vertical;
import com.erp.lead.model.impl.AddressImpl;
import com.erp.lead.model.impl.CustomerImpl;
import com.erp.lead.model.impl.DemoItemImpl;
import com.erp.lead.model.impl.FormEmailTemplateImpl;
import com.erp.lead.model.impl.FormEntryImpl;
import com.erp.lead.model.impl.FormExportLogImpl;
import com.erp.lead.model.impl.FormImpl;
import com.erp.lead.model.impl.LocalizationImpl;
import com.erp.lead.model.impl.OrderImpl;
import com.erp.lead.model.impl.OrderLineImpl;
import com.erp.lead.model.impl.SiteImpl;
import com.erp.lead.service.AddressLocalServiceUtil;
import com.erp.lead.service.CountryLocalServiceUtil;
import com.erp.lead.service.CustomerLocalServiceUtil;
import com.erp.lead.service.DemoItemLocalServiceUtil;
import com.erp.lead.service.EcOrderIdSequenceLocalServiceUtil;
import com.erp.lead.service.FormEmailTemplateLocalServiceUtil;
import com.erp.lead.service.FormEntryLocalServiceUtil;
import com.erp.lead.service.FormEntryServiceUtil;
import com.erp.lead.service.FormExportLogLocalServiceUtil;
import com.erp.lead.service.FormLocalServiceUtil;
import com.erp.lead.service.LocalizationLocalServiceUtil;
import com.erp.lead.service.OrderLineLocalServiceUtil;
import com.erp.lead.service.OrderLocalServiceUtil;
import com.erp.lead.service.PromoLocalServiceUtil;
import com.erp.lead.service.SiteLocalServiceUtil;
import com.erp.lead.service.VerticalLocalServiceUtil;
import com.liferay.portal.kernel.util.HttpUtil;

import java.io.OutputStream;
import java.io.IOException;
//import javax.ws.rs.core.HttpHeaders;
import java.nio.charset.Charset;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.servlet.PortletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.util.PortalUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.ArrayList;
//import org.apache.commons.httpclient.util.HttpURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.Message;

import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil; 
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.util.mail.MailEngine;






import java.sql.*;


public class ExporterHelper{
	public static final Map<String,String> SITE_LANG_HASH = new HashMap<String,String>();
	static{
		SITE_LANG_HASH.put("US_WEBSITE","ENG");
		SITE_LANG_HASH.put("UK_WEBSITE","EBR");
		SITE_LANG_HASH.put("DE_WEBSITE","DEU");
		SITE_LANG_HASH.put("FR_WEBSITE","FRA");
		SITE_LANG_HASH.put("IT_WEBSITE","ITA");
		SITE_LANG_HASH.put("ES_WEBSITE","ESP");
		SITE_LANG_HASH.put("KR_WEBSITE","KOR");
		SITE_LANG_HASH.put("JP_WEBSITE","JPY");
		SITE_LANG_HASH.put("EH_WEBSITE","ESP");
		SITE_LANG_HASH.put("EU_WEBSITE","EBR");
	}
	public static List<Integer> currentlyExportedForms;
    
    public static final List<String> DOVETAIL = Arrays.asList("first_name","last_name","email", "email_notify", "action_date","website", "site_lang", "demo_type", "demo_lang", "newsletter_type", "abandon_cart_lang","trial_lang","trial_start_date","trial_end_date","cid","abandon_sku","abandon_promo_price","abandon_gifter"); 
    
	public static void export(String[] exportersArray,Map map){
		//Iterator it = map.entrySet().iterator();
		try{
		for(int i=0;i<exportersArray.length;i++){
			if(map.get(exportersArray[i]) == null){
				continue;
			}
			Map pair = (Map)map.get(exportersArray[i]);
	        //System.out.println("The entries for the exporter " + exportersArray[i] + " :: " + pair);
	        /*List<TreeMap> values = new ArrayList<TreeMap>(pair.values());
			for(Map request:values){
				prepareDataForExport(exportersArray[i],request,Integer.parseInt((String)pair.get("id")));//this method takes care of each exporters
			}*/
			Iterator it1 = ((Map)pair).entrySet().iterator();
	        while(it1.hasNext()){
	        	Map.Entry pair1 = (Map.Entry)it1.next();
	        	//FormData=FormData+(String)pair1.getKey() + " : " + (String)pair1.getValue()+"\r\n";
	        	prepareDataForExport(exportersArray[i],(Map)pair1.getValue(),Integer.parseInt((String)pair1.getKey()));//this method takes care of each exporters
	        }
	        
			
			//the following is just for printing the data for debugging purpose. this has nothing to do with exporting the data
	        Iterator it2 = ((Map)pair).entrySet().iterator();
	        while(it2.hasNext()){
	        	Map.Entry pair2 = (Map.Entry)it2.next();
	        	//System.out.println(pair1.getKey() + " = " + pair1.getValue());
	        }
		}
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	}
	
	public static void prepareDataForExport(String exporter, Map pair,int formEntryId){
		//System.out.println("The data for the exporter ::::: "+ exporter);
		Map<String,String> inputHash = new HashMap<String,String>();
		try{
			if(exporter.equalsIgnoreCase("dovetail") || exporter.equalsIgnoreCase("email")){
				
				exportToDoveTail(exporter,inputHash,pair,formEntryId);
				
			}
			else if(exporter.equalsIgnoreCase("eloqua")){
				exportToEloqua(pair,exporter,formEntryId);
				//exportToDoveTail(exporter,inputHash,pair);//for testing purpose only. please please change it
			}
			else if(exporter.toLowerCase().contains("demo")){
				exportToDemoCD(pair,exporter,formEntryId);
			}
			else if(exporter.toLowerCase().contains("auto_reply")){
				sendAutoReply(pair,exporter,formEntryId);
				
			}
			
			//System.out.println(inputHash);
			//System.out.println("9999999999999999999999999999999999999999999999999999999999999999");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void exportToDoveTail(String exporter,Map<String,String> inputHash,Map pair,int formEntryId){
		try{
			for(String field:DOVETAIL){
				inputHash.put(field,(String)pair.get(field));
			}
			inputHash.put("action_date", "to_date('"+(String)pair.get("date")+"', 'YYYY-MM-DD HH24:MI:SS')");
			inputHash.put("cis_name",(String)pair.get("cis_name"));
			
			if (inputHash.get("form_type") == null || inputHash.get("form_type").equals("")){
				if(inputHash.get("email_notify")!=null && (inputHash.get("email_notify").equalsIgnoreCase("false") || inputHash.get("email_notify").equalsIgnoreCase("0") || inputHash.get("email_notify").equalsIgnoreCase("no")))
					inputHash.put("email_notify","0");
				else
					inputHash.put("email_notify","1");
			}
		    else if(inputHash.get("email_notify") == null || inputHash.get("email_notify").equals("")){
		    	inputHash.put("email_notify","1");
		    }
				
			if (inputHash.get("form_type") == null && inputHash.get("cis_name")!=null && !(inputHash.get("cis_name").equals(""))){
				inputHash.put("cis_name",inputHash.get("cis_name").toLowerCase());
			    if(!(inputHash.get("cis_name").contains("newsletter")))
			      if(inputHash.get("cis_name").contains("education") || inputHash.get("cis_name").contains("school"))
			        inputHash.put("demo_type","school");
			      else if(inputHash.get("cis_name").contains("homeschool"))
			    	inputHash.put("demo_type","HS");
			      else if(inputHash.get("cis_name").contains("enterprise") || inputHash.get("cis_name").contains("institution") || inputHash.get("cis_name").contains("corporate"))
			    	inputHash.put("demo_type","corporate");
			      else
			    	inputHash.put("demo_type","PE");
			    
			    if(inputHash.get("cis_name").contains("newsletter") && inputHash.get("cis_name").contains("homeschool"))
			      inputHash.put("newsletter_type","HS");
			    else if(inputHash.get("cis_name").contains("newsletter"))
			      inputHash.put("newsletter_type","PE");
			}
			inputHash.put("site_lang",SITE_LANG_HASH.get(inputHash.get("website")));
			inputHash.put("demo_lang", (inputHash.get("language_of_interest") == null || inputHash.get("language_of_interest").equals("")) ? inputHash.get("language")!=null ? inputHash.get("language").toUpperCase() : inputHash.get("demo_lang")  : inputHash.get("language_of_interest"));
			if(exporter.equalsIgnoreCase("dovetail")){
			
	
				if(inputHash.get("form_type") == null || inputHash.get("form_type").equals(""))
				{
					      inputHash.put("demo_lang", (inputHash.get("language_of_interest") == null || inputHash.get("language_of_interest").equals("")) ? inputHash.get("language")!=null ? inputHash.get("language").toUpperCase() : inputHash.get("demo_lang")  : inputHash.get("language_of_interest"));
					      if(inputHash.get("email_notify") == null){
					    	  inputHash.put("email_notify", (inputHash.get("add_me_to_your_mailing_list") == null || inputHash.get("add_me_to_your_mailing_list").equals("")) ? inputHash.get("opt_in") : inputHash.get("add_me_to_your_mailing_list"));
					      }
				}
			    
			}
			
			Class.forName ("oracle.jdbc.driver.OracleDriver");
			   //System.out.println("hello");
			   //Connection conn = DriverManager.getConnection
			   //  ("jdbc:oracle:thin:@10.130.248.11:1534/phobos", "e_commerce", "oran10es");
			Connection conn = DriverManager.getConnection(PrefsPropsUtil.getString("dovetail.export.url"),PrefsPropsUtil.getString("dovetail.export.user"),PrefsPropsUtil.getString("dovetail.export.password"));
			   
			   
			   
			   try {
				   
			     Statement stmt = conn.createStatement();
			    // System.out.println("hi");
			     try {
			    	 //String query = "insert into bolinf.flt_extra_web_data (first_name,last_name,email,email_notify,action_date,website,site_lang,demo_type,demo_lang,newsletter_type,abandon_cart_lang,trial_lang,trial_start_date,trial_end_date,cid,abandon_sku,abandon_promo_price,abandon_gifter) values('"+inputHash.get("first_name")+"','"+inputHash.get("last_name")+"','"+inputHash.get("email")+"','"+inputHash.get("email_notify")+"',"+inputHash.get("action_date")+",'"+inputHash.get("website")+"','"+inputHash.get("site_lang")+"','"+inputHash.get("demo_type")+"','"+inputHash.get("demo_lang")+"','"+inputHash.get("newsletter_type")+"','"+inputHash.get("abandon_cart_lang")+"')";
			    	 String query = "insert into bolinf.flt_extra_web_data (";
			    	 StringBuffer sb = new StringBuffer(query);
			    	 String prefix="";
			    	 for(String field:DOVETAIL){
			    		 sb.append(prefix);
		    			 sb.append(field);
		    			 prefix=",";
			    	 }
			    	 sb.append(") values(");
			    	 prefix="";
			    	 for(String field:DOVETAIL){
			    		 sb.append(prefix);
			    		 String temp = inputHash.get(field);
			    		 if(temp!=null && !(field.contains("date")))
			    			 temp=temp.replaceAll("'", " ");
			    		 if(field.contains("date")){
			    			 sb.append(temp);
			    		 }
			    		 else{
			    			 sb.append(temp==null ? "null" : "'"+temp+"'");
			    		 }
		    			 prefix=",";
			    	 }
			    	 sb.append(")");
			    	 System.out.println("The query to insert as part of Dovetail exporter :: " +sb.toString());
			    	 recordSuccessLog(pair,exporter,formEntryId);
			    	 //remove the below comment to enable the exporter
			       /*ResultSet rset = stmt.executeQuery(sb.toString());
			       try {
			         
			    	   if(exporter.equalsIgnoreCase("email")){
							sendEmail(pair,exporter,formEntryId);
						}
			    	   recordSuccessLog(pair,exporter,formEntryId);
			       }
			       catch(Exception ex){
				    	 ex.printStackTrace();
				    	 recordFailureLog(pair,exporter, ex,formEntryId);
				     }
			       finally {
			          try { rset.close(); } catch (Exception ignore) {}
			       }*/
			     } 
			     catch(Exception ex){
			    	 ex.printStackTrace();
			    	 recordFailureLog(pair,exporter, ex,formEntryId);
			     }
			     finally {
			       try { stmt.close(); } catch (Exception ignore) {}
			     }
			   } 
			   catch(Exception ex){
					ex.printStackTrace();
					recordFailureLog(pair,exporter, ex,formEntryId);
				}
			   finally {
			     try { conn.close(); } catch (Exception ignore) {}
			   }
		
		}
		catch(Exception ex){
			ex.printStackTrace();
			recordFailureLog(pair,exporter, ex,formEntryId);
		}
		
	}
	public static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
	public static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                urlEncodeUTF8(entry.getKey().toString()),
                urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();       
    }
	public static void sendEmail(Map pair,String exporter,int FormEntryId) throws Exception{
		String subject="";
		String body="";
		String language="";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		 try{
			 	FormEntry formentry= new FormEntryImpl();
			 	formentry=FormEntryLocalServiceUtil.getFormEntry(FormEntryId);
			 	
			 	int FormId=formentry.getFormId();
			 	String formurl=formentry.getFormUrl();
			 	
			 	Date date = new Date();
			 	date=formentry.getCreatedAt();
				
			 	Form form= new FormImpl();
				form=FormLocalServiceUtil.getForm(FormId);
				int TemplateId=form.getFormEmailTemplateId();
				String templateName="";
				int sendEmailAsCus=0;
				System.out.println("Inside send email:template id:"+TemplateId);
				
				if(TemplateId != 0){
					System.out.println("Inside send email:template id:"+TemplateId);
					FormEmailTemplate template =new FormEmailTemplateImpl();
					template=FormEmailTemplateLocalServiceUtil.getFormEmailTemplate(TemplateId);
					templateName=template.getName().toLowerCase();
					sendEmailAsCus=template.getSendEmailAsCustomer();
				}
				
				
				
				
				String FormCode=form.getFormCode();
				String FormCodeHumanized=humanize(FormCode);
				
				int SiteId=form.getSiteId();
				Site site=new SiteImpl();
				site=SiteLocalServiceUtil.getSite(SiteId);
				String website=site.getDescription();
				
				String email=form.getEmail();
				String [] emails=email.split(",");
				
			    for (int i = 0; i < emails.length; i++)
			    {
			        emails[i] = emails[i].trim();
			        
			        System.out.println(emails[i]);
			    }
				
				for(String i:emails){
				
				
				
				if(templateName.contains("elp")){
					subject = loadResource("/templates/ELP_Inquiry_sub.tmpl");
					 
					if (pair.containsKey("language_name")) {
						language=(String)pair.get("language_name");
					    }
					else if(pair.containsKey("language")) {
						language=(String)pair.get("language");
					}
					else if(pair.containsKey("language_of_interest")) {
						language=(String)pair.get("language_of_interest") ; 
					}
					else if(pair.containsKey("languages_interested_in")) {
						language=(String)pair.get("languages_interested_in");
					}
					else if(pair.containsKey("lang")) {
						language=(String)pair.get("lang");
					}
					else if(pair.containsKey("selectedLang")) {
						language=(String)pair.get("selectedLang");
					}
					 
					subject = subject.replace("LANGUAGE", language);
					
					
					body = loadResource("/templates/ELP_Inquiry_body.tmpl");
					body = body.replaceAll("FORMCODE", FormCodeHumanized);
					
					body = body.replaceAll("WEBSITE",website);
					pair.remove("website");
					
					body = body.replace("CREATEDATE",df.format(date));
					pair.remove("date");
					
					body = body.replace("FORMURL", formurl);
					pair.remove("formurl");
					
					String FormData="";
					  Iterator it1 = ((Map)pair).entrySet().iterator();
				        while(it1.hasNext()){
				        	Map.Entry pair1 = (Map.Entry)it1.next();
				        	FormData=FormData+(String)pair1.getKey() + " : " + (String)pair1.getValue()+"\r\n";
				        }
					body = body.replace("FORMDATA", FormData);
					
				}
				else if(templateName.contains("customer")){
					subject = loadResource("/templates/Customer_Support_sub.tmpl");
					subject = subject.replace("FORMCODE", FormCodeHumanized);
					
					body = loadResource("/templates/Customer_Suppoert_body.tmpl");
					body = body.replace("FORMCODE", FormCodeHumanized);
					String FormData="";
					  Iterator it1 = ((Map)pair).entrySet().iterator();
				        while(it1.hasNext()){
				        	Map.Entry pair1 = (Map.Entry)it1.next();
				        	FormData=FormData+(String)pair1.getKey() + " : " + (String)pair1.getValue()+"\r\n";
				        }
				    body = body.replace("FIELDENTRY", FormData);
					
					
				}
				else if(templateName.contains("contact")){
					
					subject = loadResource("/templates/Contact_Us_sub.tmpl");
					String education="";
					String edorcor= ((String)pair.get("cis_name")).toLowerCase();
					if (edorcor.contains("education")||edorcor.contains("school")){
						education="Education";
					}
					else if(edorcor.contains("homeschool")|| edorcor.contains("newsletter")){
						education="HS";
					}
					else if(edorcor.contains("enterprise")|| edorcor.contains("institution")||edorcor.contains("corporate")){
						education="Corporate";
					}
					subject = subject.replace("EDUCATIONORCORPORATE", education);
					
					body = loadResource("/templates/Contact_Us_body.tmpl");
					body = body.replace("FORMURL",formurl);
					pair.remove("formurl");
					String FormData="";
					  Iterator it1 = ((Map)pair).entrySet().iterator();
				        while(it1.hasNext()){
				        	Map.Entry pair1 = (Map.Entry)it1.next();
				        	FormData=FormData+(String)pair1.getKey() + " : " +(String)pair1.getValue()+"\r\n";
				        }
				    body = body.replace("FIELDENTRY", FormData);
									
									
									
				}
				else if(templateName.contains("doug")){
					
					subject = loadResource("/templates/Doug_sub.tmpl");
					subject = subject.replace("FORMCODE", FormCodeHumanized);
					
					body = loadResource("/templates/Doug_body.tmpl");
					String FormData="";
					  Iterator it1 = ((Map)pair).entrySet().iterator();
				        while(it1.hasNext()){
				        	Map.Entry pair1 = (Map.Entry)it1.next();
				        	FormData=FormData+(String)pair1.getKey() + " : " +(String)pair1.getValue()+"\r\n";
				        }
					body = body.replace("FIELDENTRY", FormData);
					body = body.replace("FORMCODE", FormCodeHumanized);
					
					
					
				}
				else if(templateName.contains("eschool")){
					subject = loadResource("/templates/eschool_sub.tmpl");
					body = loadResource("/templates/eschool_body.tmpl");
					
					body = body.replaceAll("FORMCODE", FormCodeHumanized);
					
					body = body.replaceAll("WEBSITE",website);
					
					body = body.replace("CREATEDATE",df.format(date));
					
					body = body.replace("FORMURL", formurl);
					if(pair.containsKey("order_id"))
						body = body.replace("ORDERID", (String)pair.get("order_id"));
					else
						body = body.replace("ORDERID", "");
					if(pair.containsKey("first_name"))
						body = body.replace("FIRSTNAME", (String)pair.get("first_name"));
					else
						body = body.replace("FIRSTNAME", "");
					if(pair.containsKey("last_name"))
						body = body.replace("LASTNAME", (String)pair.get("last_name"));
					else
						body = body.replace("LASTNAME", "");
					if(pair.containsKey("daytime_phone"))
						body = body.replace("DAYTIMEPH", (String)pair.get("daytime_phone"));
					else
						body = body.replace("DAYTIMEPH", "");
					if(pair.containsKey("evening_phone"))
						body = body.replace("EVEPH", (String)pair.get("evening_phone"));
					else
						body = body.replace("EVEPH", "");
					if(pair.containsKey("email"))
						body = body.replace("EMAIL", (String)pair.get("email"));
					else
						body = body.replace("EMAIL", "");
					if(pair.containsKey("internet_connection"))
						body = body.replace("NETC", (String)pair.get("internet_connection"));
					else
						body = body.replace("NETC", "");
					if(pair.containsKey("first_preference_day"))
						body = body.replace("DAY1", (String)pair.get("first_preference_day"));
					else
						body = body.replace("DAY1", "");
					if(pair.containsKey("first_preference_time"))
						body = body.replace("TIME1", (String)pair.get("first_preference_time"));
					else
						body = body.replace("TIME1", "");
					if(pair.containsKey("second_preference_day"))
						body = body.replace("DAY2", (String)pair.get("second_preference_day"));
					else
						body = body.replace("DAY2", "");
					if(pair.containsKey("second_preference_time"))
						body = body.replace("TIME2", (String)pair.get("second_preference_time"));
					else
						body = body.replace("TIME2", "");
					if(pair.containsKey("third_preference_day"))
						body = body.replace("DAY3", (String)pair.get("third_preference_day"));
					else
						body = body.replace("DAY3", "");
					if(pair.containsKey("third_preference_time"))
						body = body.replace("TIME3", (String)pair.get("third_preference_time"));
					else
						body = body.replace("TIME3", "");
					
				}
				
				
				
				else if(templateName.contains("select")){
				
					
					subject = loadResource("/templates/Default_sub.tmpl");
					 
					subject = subject.replace("FORMCODE", FormCodeHumanized);
					
					
					body = loadResource("/templates/Default_body.tmpl");
					body = body.replaceAll("FORMCODE", FormCodeHumanized);
					body = body.replaceAll("WEBSITE",website);
					pair.remove("website");
					body = body.replace("CREATEDATE",df.format(date));
					pair.remove("date");
					body = body.replace("FORMURL",formurl);
					pair.remove("formurl");
					String FormData="";
					  Iterator it1 = ((Map)pair).entrySet().iterator();
				        while(it1.hasNext()){
				        	Map.Entry pair1 = (Map.Entry)it1.next();
				        	FormData=FormData+(String)pair1.getKey() + " : " + (String)pair1.getValue()+"\r\n";
				        }
					body = body.replace("FORMDATA", FormData);
					
					
				}
				else if(templateName.contains("test de")){
					body = loadResource("/templates/test_de_body.tmpl");
					subject = loadResource("/templates/test_de_sub.tmpl");
					
				}
				else if(templateName.contains("test")){
					body = loadResource("/templates/test_body.tmpl");
					subject = loadResource("/templates/test_sub.tmpl");
					
					
				}
				
			 
		         int SMTP_HOST_PORT=Integer.parseInt(PrefsPropsUtil.getString("mail.session.mail.smtp.port"));
		         String SMTP_HOST_NAME=PrefsPropsUtil.getString("mail.session.mail.smtp.host");
		         String SMTP_AUTH_USER="do_not_reply@erpleadst.com";
				 String SMTP_AUTH_PWD=PrefsPropsUtil.getString("mail.session.mail.smtp.password");
				 if(sendEmailAsCus==1){
					 if(pair.get("email")!=null){
						 SMTP_AUTH_USER=(String)pair.get("email");  
					 }
					 
				}
		    
		         Properties props = new Properties();
		         props.put("mail.transport.protocol", "smtp");
		         props.put("mail.smtp.host", SMTP_HOST_NAME);
		         props.put("mail.smtp.user", SMTP_AUTH_USER);
		         props.put("mail.smtp.auth", "false");
		         
		         System.out.println("The subject as part of Email exporter :: " +subject);
		         System.out.println("The body as part of Email exporter :: " +body);
		         //uncomment below to enable the exporter
		         /*Session mailSession = Session.getInstance(props);
			     mailSession.setDebug(true);
			     Transport transport = mailSession.getTransport();
			     MimeMessage message = new MimeMessage(mailSession);
			     message.setSubject(subject);
			     message.setContent(body, "text/plain");
			
			     message.addRecipient(Message.RecipientType.TO,new InternetAddress(i));
			     message.setFrom(new InternetAddress("do_not_reply@erpleadst.com"));
			
			    transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER,SMTP_AUTH_PWD);
			
			    transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
			    transport.close();*/
				}
			    recordSuccessLog(pair,exporter,FormEntryId);
		  }catch(Exception e){
		        e.printStackTrace();
		        recordFailureLog(pair,exporter, e,FormEntryId);
		        throw e;
		   }
		 
				
		
	}
	public static void sendAutoReply(Map pair,String exporter,int FormEntryId){
		 try{
			 FormEntry formentry= new FormEntryImpl();
		 	formentry=FormEntryLocalServiceUtil.getFormEntry(FormEntryId);
		 	
		 	int FormId=formentry.getFormId();
		 	String formurl=formentry.getFormUrl();
		 	
		 	Date date = new Date();
		 	date=formentry.getCreatedAt();
		 	
			Form form= new FormImpl();
			form=FormLocalServiceUtil.getForm(FormId);
			
			
			
			 int SiteId=form.getSiteId();
			 Site site=new SiteImpl();
			 site=SiteLocalServiceUtil.getSite(SiteId);
			 
			 String website=site.getCode();
			 
			 if(website.indexOf("JP_")>-1){
	
	            int SMTP_HOST_PORT=Integer.parseInt(PrefsPropsUtil.getString("mail.session.mail.smtp.port"));
	            String SMTP_HOST_NAME=PrefsPropsUtil.getString("mail.session.mail.smtp.host");
	            String SMTP_AUTH_USER="do_not_reply@erpleadst.com";
				String SMTP_AUTH_PWD=PrefsPropsUtil.getString("mail.session.mail.smtp.password");
	       
	            Properties props = new Properties();
	            props.put("mail.transport.protocol", "smtp");
	            props.put("mail.smtp.host", SMTP_HOST_NAME);
	            props.put("mail.smtp.user", SMTP_AUTH_USER);
	            props.put("mail.smtp.auth", "false");
	            
	            
	            
	            //uncomment below to enable the exporter
	            /*Session mailSession = Session.getInstance(props);
			     mailSession.setDebug(true);
			     Transport transport = mailSession.getTransport();
			     MimeMessage message = new MimeMessage(mailSession);
			     message.setSubject("Testing SMTP-SSL");
			     message.setContent("This is a test", "text/plain");
			
			     message.addRecipient(Message.RecipientType.TO,
			     new InternetAddress((String) pair.get("email")));
				 message.setFrom(new InternetAddress("do_not_reply@erpleadst.com"));
			
			    transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER,SMTP_AUTH_PWD);
			
			    transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
			    transport.close();*/
			    recordSuccessLog(pair,exporter,FormEntryId);
			 }
		  }catch(Exception e){
		        e.printStackTrace();
		        recordFailureLog(pair,exporter, e,FormEntryId);
		   }
		 	
		
	}
	public static void exportToEloqua(Map pair,String exporter,int formEntryId){
		try{
			/*int SMTP_HOST_PORT=Integer.parseInt(PrefsPropsUtil.getString("mail.session.mail.smtp.port"));
	         String SMTP_HOST_NAME=PrefsPropsUtil.getString("mail.session.mail.smtp.host");
	         String SMTP_AUTH_USER="do_not_reply@erpleadst.com";
			 String SMTP_AUTH_PWD=PrefsPropsUtil.getString("mail.session.mail.smtp.password");*/
			//String url = "http://now.eloqua.com/e/f2.aspx";
			String url = PrefsPropsUtil.getString("eloqua.export.url");
			URL obj = new URL(url);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("ashproxy.lan.flt", 3128));
			HttpURLConnection con = (HttpURLConnection) obj.openConnection(proxy);
	
			//add reuqest header
			con.setRequestMethod("POST");
			//con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("User-Agent", "");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	
			String urlParameters = urlEncodeUTF8(pair);
			//String urlParameters = "C_Comments_Large_Text1=Sample comments&C_Receive_the_Latest_News_From_RS1=1&Submit.x=37&Submit.y=1&augment_professional_development_of_employees=1&c_role1=Human Resources&c_sub_sector1=Foundation&city=Town&country_iso=826&elqFormName=OrganizationsContactFormUK&elqSiteID=1294&email=test@test.com&first_name=mohit&formurl=/content/erpleadstuk/uk/government/form/demo.html&import_source=inst-form org uk&improve_effectiveness_of_employee_communications=1&improve_engagement_of_diverse_employees=1&industry=Non Profit&last_name=kumar&leadsource=Web&organization_name=organisation&other=1&phone_number=90000000000&prepare_employees_for_global_assignments=1&street_address=test&success_path=/content/erpleadstuk/uk/enterprise/totale.html&zip_code=767677";
			System.out.println("Post parameters as part of eloqua exporter :: " + urlParameters);
			
			recordSuccessLog(pair,exporter,formEntryId);
			//uncomment below to enable the exporter
			// Send post request
			/*con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	
			int responseCode = con.getResponseCode();
			if(responseCode/100 == 2 || responseCode/100 == 3){
				recordSuccessLog(pair,exporter,formEntryId);
			}
			else{
			}*/
		}
		catch(Exception ex){
			ex.printStackTrace();
			recordFailureLog(pair,exporter, ex,formEntryId);
		}
		//recordSuccessLog(pair,exporter,formEntryId);
	}
	
	public static void exportToDemoCD(Map pair,String exporter, int formEntryId){
		try{
			//System.out.println("Printing inside exportToDemoCD " + pair);
		Order order = new OrderImpl();
		
		order.setFormWhereEntered("DEMO");
		if(pair.get("language_of_interest")!=null)
			order.setLanguageOfInterest((String)pair.get("language_of_interest"));
		if(pair.get("organization_id")!=null)
			order.setOrganizationId(Integer.parseInt((String)pair.get("organization_id")));
		order.setVertical((String)pair.get("vertical"));
		order.setCreatedAt(new Date());//set the created at explicitly
		order.setUpdatedAt(new Date());
		order.setOrderedDate(new Date());
		order.setCreditCardId(44);//look for any alternate. I have just hardcode as foreign key constraint fails. credit card should not be set at all.
		
		FormEntry formentry= new FormEntryImpl();
	 	formentry=FormEntryLocalServiceUtil.getFormEntry(formEntryId);
	 	int FormId=formentry.getFormId();
	
		Form form= new FormImpl();
		form=FormLocalServiceUtil.getForm(FormId);
		int site_id=form.getSiteId();
		
		Site site=new SiteImpl();
		site=SiteLocalServiceUtil.getSite(site_id);

		String from_call_center = (String) pair.get("from_call_center");
		
		/*List<Site> sites = new ArrayList<Site>();
		if(from_call_center!=null && !(from_call_center.equals(""))){
			sites = SiteLocalServiceUtil.getSiteByCode(from_call_center);
		}
		else{
			sites = SiteLocalServiceUtil.getSiteByCode((String) pair.get("website"));
		}
		if(sites!=null && sites.size()>0){
			site_id=sites.get(0).getId();
			
		}*/
		
		order.setOrganizationId(site.getOrganizationId());
		order.setSiteId(site_id);
		
				
		String sales_rep = (String)pair.get("sales_rep");
		
		if(sales_rep!=null && !(sales_rep.equals(""))){
			order.setSalesrepId(Integer.parseInt(sales_rep));
		}
		else{
			order.setSalesrepId(100000020);
		}
		
		String promo_code = (String)pair.get("promo_code");
		
		
		List<Promo> promos = new ArrayList<Promo>();
		if(promo_code!=null && !(promo_code.equals(""))){
			promos = PromoLocalServiceUtil.getPromoByCode(promo_code);
			if(promos!=null && promos.size()>0){
				order.setPromoId(promos.get(0).getId());
			}
			
		}
		else{
			order.setPromoId(12694);
		}
		order.setWireTransferId(1);
		Customer customer = new CustomerImpl();
		
		customer.setFirstName((String) pair.get("first_name"));
		customer.setEmail((String) pair.get("email"));
		//customer.setPrimaryKey((Int) pair.get("pk")); this is auto increment
		//customer.setId(Integer.parseInt((String)pair.get("id")));
		customer.setFullName((String) pair.get("full_name"));
		customer.setEmail((String) pair.get("email"));
		customer.setPassword((String) pair.get("password"));
		if(pair.get("you_can_spam_me")!=null)
			customer.setYouCanSpamMe(Integer.parseInt((String)pair.get("you_can_spam_me")));
		if(pair.get("affiliates_can_spam_me")!=null)
			customer.setAffiliatesCanSpamMe(Integer.parseInt((String)pair.get("affiliates_can_spam_me")));
		customer.setLeadCode((String) pair.get("lead_code"));
		customer.setLocale((String) pair.get("locale"));
		customer.setAffiliateCode((String) pair.get("affiliate_code"));
		customer.setAbSplit((String) pair.get("abSplit"));
		customer.setCreatedAt(new Date());//set explicitly
		customer.setUpdatedAt(new Date());
		customer.setOrganizationName((String) pair.get("organization_name"));
		customer.setFirstName((String) pair.get("first_name"));
		customer.setLastName((String) pair.get("last_name"));
		customer.setPhoneNumber((String) pair.get("phone_number"));
		customer.setGenericLeadCode((String) pair.get("generic_lead_code"));
		customer.setDnisTelephoneNumber((String) pair.get("dnis_telephone_number"));
		//customer.setSsn((String) pair.get("ssn"));
		//customer.setDateOfBirth((String) pair.get("date_of_birth"));
		customer.setReferringDomain((String) pair.get("referring_domain"));
		//customer.setGuid((String) pair.get("guid"));
		customer.setLastReferringDomain((String) pair.get("last_referring_domain"));
		customer.setLearnerEmail((String) pair.get("learner_email"));
		customerValidation(customer,formEntryId);
		
		Address address = new AddressImpl();

		
		address.setFullName((String) pair.get("full_name"));
		address.setAddress1((String) pair.get("address1"));
		address.setAddress2((String) pair.get("address2"));
		
		address.setAddress3((String) pair.get("address3"));
		address.setAddress4((String) pair.get("address4"));
		address.setCity((String) pair.get("city"));
		address.setCounty((String) pair.get("county"));
		address.setState((String) pair.get("state"));
		address.setProvince((String) pair.get("province"));
		address.setPostalCode((String) pair.get("postal_code"));
		//address.setCountryCode((String) pair.get("country_code"));
		address.setPhoneNumber((String) pair.get("phone_number"));
		address.setCreatedAt(new Date());//set it explicitly
		address.setUpdatedAt(new Date());
		address.setPage((String) pair.get("page"));

		address.setFirstName((String) pair.get("first_name"));
		address.setLastName((String) pair.get("last_name"));
		address.setFirstNamePhonetic((String) pair.get("first_name_phonetic"));
		address.setLastNamePhonetic((String) pair.get("last_name_phonetic"));
		address.setZip4((String) pair.get("zip4"));
		String countryCode = "";
		if((String)pair.get("country_iso")!=null){
			List<Country> countryList = CountryLocalServiceUtil.getCountryByISO((String)pair.get("country_iso"));
			if(countryList!=null && countryList.size()>0){
				
				countryCode = countryList.get(0).getCode();
			}
			else{
				
				countryCode = CountryLocalServiceUtil.getCountry(site.getCountryId()).getCode();
			}
		}
		else{
			
			countryCode = CountryLocalServiceUtil.getCountry(site.getCountryId()).getCode();
		}
		address.setCountryCode(countryCode);
		addressValidation(address);
		order.setVatEstimationCountryCode(countryCode);
		//uncomment below
		//customer=CustomerLocalServiceUtil.addCustomer(customer);
		//address=AddressLocalServiceUtil.addAddress(address);
		List<Vertical> verticalList = new ArrayList<Vertical>();
		verticalList=VerticalLocalServiceUtil.getVerticalByDescription((String)pair.get("vertical"));
		int verticalId = 0;
		if(verticalList == null || verticalList.size()==0){
			throw new DemoItemNotFoundException("cannot find vertical with the code :" + order.getVertical());
		}
		verticalId= verticalList.get(0).getId();
		
		
		

		List<Localization> localizationList = new ArrayList<Localization>();
		//
		Localization localization = LocalizationLocalServiceUtil.getLocalization(site.getLocalizationId());
		int localization_id = 0;
		if(localization.getCode().equalsIgnoreCase("US.LA")){
			localizationList = LocalizationLocalServiceUtil.getLocalizationByTypeCode("Localization","US");
			localization_id = site.getLocalizationId();
		}
		else{
			localization_id = site.getLocalizationId();
		}
		List<Localization> languageList = new ArrayList<Localization>();
		languageList = LocalizationLocalServiceUtil.getLocalizationByTypeCode("Language",order.getLanguageOfInterest());
		if(languageList == null || languageList.size()==0){
			throw new DemoItemNotFoundException("cannot find language with the key :" + order.getLanguageOfInterest());
		}
		

		List<DemoItem> DIList=new ArrayList<DemoItem>();
		DIList=DemoItemLocalServiceUtil.getDemoItemByAllIds(site.getOrganizationId(),verticalId,localization_id,languageList.get(0).getId());
		OrderLine orderLine = new OrderLineImpl();
		if(DIList != null && DIList.size()>0){
			DemoItem di = DIList.get(0);
			orderLine.setItemId(di.getInventoryItemId());
			orderLine.setQuantity(1);
			orderLine.setCurrencyId(site.getCurrencyId());
			orderLine.setListPricePerItem(0.0);
			orderLine.setYourPricePerItem(0.0);
			orderLine.setTax(0.0);
			
		}
		else{
			throw new DemoItemNotFoundException("cannot find demo item for  : " + site.getOrganizationId() + " and vertical "+verticalId + " and language "+ languageList.get(0).getCode());
		}
		
		order.setCustomerId(customer.getId());
		//order.setShipAddressId(address.getId());
		//order.setBillAddressId(address.getId());
		//order=OrderLocalServiceUtil.addOrder(order);
		//order.setEcOrderId(getEcOrderId());
		//order=OrderLocalServiceUtil.addOrder(order);
		
		//orderLine.setOrderId(order.getId());
		//OrderLineLocalServiceUtil.addOrderLine(orderLine);
		//System.out.println("printing all the values at the last : " + order.getId() + " line id is " + orderLine.getId() + " customer is " + customer.getId() + " address is "+ address.getId());
		recordSuccessLog(pair,exporter,formEntryId);
		
		}catch(Exception e){
			e.printStackTrace();
			recordFailureLog(pair,exporter, e,formEntryId);
			
		}
		
		
	}
	
public static String humanize(String FormCode){
		
		FormCode=FormCode.replaceAll("\\s+", " ").trim();
		FormCode=FormCode.replaceAll("_"," ");
		FormCode=FormCode.replaceAll("-"," ");
		String[] temp=FormCode.split(" ");
		FormCode="";
		for(String k:temp){
			k= k.substring(0,1).toUpperCase() + k.substring(1).toLowerCase();
			FormCode=FormCode+k+" ";
		}
		return FormCode;
		}
	
	public static int getEcOrderId(){
		return EcOrderIdSequenceLocalServiceUtil.getEcOrderId();
	}
	
	public static void recordFailureLog(Map pair,String exporter, Exception ex,int formEntryId){
		try{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy - HH:mm a");
			Date date = Calendar.getInstance().getTime();
			String startDate = df.format(date);
			java.sql.Date CreatedAt = new java.sql.Date(df.parse(startDate).getTime());
			//System.out.println("Printing inside recordFailureLog " + pair);
			FormExportLog log;
			List<FormExportLog> logList = FormExportLogLocalServiceUtil.getLogsByExporterFormEntryId(exporter,formEntryId);
			if(logList!=null && logList.size()>0){
				log = logList.get(0);
				log.setSuccessful(0);
				log.setText(ex.getMessage());
				FormExportLogLocalServiceUtil.updateFormExportLog(log);
			}
			else{
				log = new FormExportLogImpl();
				log.setFormEntryId(formEntryId);
				log.setExporter(exporter);
				log.setCreatedAt(CreatedAt);
				log.setSuccessful(0);
				log.setText(ex.getMessage());
				FormExportLogLocalServiceUtil.addFormExportLog(log);
			}
			
			
		} catch(Exception e){
			e.printStackTrace();
			
		}
		
		
	}

	public static void recordSuccessLog(Map pair,String exporter,int formEntryId){
		try{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy - HH:mm a");
			Date date = Calendar.getInstance().getTime();
			String startDate = df.format(date);
			java.sql.Date CreatedAt = new java.sql.Date(df.parse(startDate).getTime());
			
			FormExportLog log;
			List<FormExportLog> logList = FormExportLogLocalServiceUtil.getLogsByExporterFormEntryId(exporter,formEntryId);
			if(logList!=null && logList.size()>0){
				log = logList.get(0);
				log.setSuccessful(1);
				log.setCreatedAt(CreatedAt);
				log.setText("Export successful");
				FormExportLogLocalServiceUtil.updateFormExportLog(log);
			}
			else{
				log = new FormExportLogImpl();
				log.setFormEntryId(formEntryId);
				log.setExporter(exporter);
				log.setCreatedAt(CreatedAt);
				log.setSuccessful(1);
				log.setText("Export successful");
				FormExportLogLocalServiceUtil.addFormExportLog(log);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}


	}
	
    public static final String loadResource(String fileName) { 
        ClassLoader clPortal = PortalClassLoaderUtil.getClassLoader(); 
        ClassLoader clThread = Thread.currentThread().getContextClassLoader(); 
               
            InputStream is = clThread.getResourceAsStream(fileName); 
            if (is == null) { 
                is = clPortal.getResourceAsStream(fileName); 
                    if (is == null) 
                            return ""; 
                    } 
        InputStreamReader reader = new InputStreamReader(is); 
            try { 
                char[] chr = new char[1024]; 
                StringBuilder sb = new StringBuilder(); 
                int readone; 
                while ( (readone = reader.read(chr)) != -1) { 
                            sb.append(chr, 0, readone); 
                    } 
            return sb.toString(); 
            } catch (IOException e) { 
               
               return ""; 
            } finally { 
            try { 
                  is.close(); 
            } catch (IOException ignore) { 
                    } 
            } 
    }

    public static void addressValidation(Address address) throws InvalidDataException{
  
		  String postalCode= address.getPostalCode();
		  String address2=address.getAddress2();
		  String address1= address.getAddress1();
		  String address3= address.getAddress3();
		  String address4= address.getAddress4();
		  String country_code=address.getCountryCode();
		  
		  String first_name_phonetic=address.getFirstNamePhonetic();
		  String last_name_phonetic=address.getLastNamePhonetic();
		  String first_name=address.getFirstName();
		  String last_name=address.getLastName();
		  String city=address.getCity();
	      String phone_number=address.getPhoneNumber();
		  //Pattern regex=Pattern.compile("[<>]");
		  /*String[] key= pair.keySet().toArray(); // returns an array of keys
		  String[] value=pair.values().toArray();
		  int index = 0;
		  int length =pair.size();
		  for (index=0;index<length;index++)
			 {
			    if(value[index] != "")
			    {
			      if (value[index].matches(".*" + regex + ".*")){
		          throw new InvalidDataException("HTML Tags found in data, hence export failed");
		           }
			     }
			 
			 }*/
			 
		 /*String[] keys = new String[pair.size()];
	     Object[] values = new Object[pair.size()];
	     int index = 0;
	     for (Map.Entry<String, Object> mapEntry : map.entrySet()) {
		     keys[index] = mapEntry.getKey();
		     values[index] = mapEntry.getValue();
		     index++;
		 }*/
		  if(country_code != "IE" ){
		    if (postalCode ==null){
		     throw new InvalidDataException("postal code is  empty");
		     
		    }
		   }
		 
		 if(address1 == null ){
			 throw new InvalidDataException(" address1 is empty");

		 }
		 if(country_code == null ){
			 throw new InvalidDataException(" country code is empty");
		 }
		 /*if (postalCode!=null && postalCode.matches("[A-Za-z]+")){
			  throw new InvalidDataException("postal code is invalid since it has alphanumeric characters");
		 }
		 if (phone_number != null && phone_number.matches("[-0-9 ()+\.xX]")){
		 	 throw new InvalidDataException("phone number is not valid");
		 }*/
		 
		 if (address3!=null && address3.length()>50)
		 {
		  	throw new InvalidDataException("export failed as the address3 crossed the max wordlength of 50");
		 }
		 if (address4!=null &&  address4.length()>50)
		 {
		 	 throw new InvalidDataException("export failed as the address4 crossed the max wordlength of 50");
		 }
		 if (country_code!=null && country_code.length()>50)
		 {
		  	throw new InvalidDataException("export failed as the country code crossed the max wordlength of 50");
		 }
		 if (postalCode!=null && postalCode.length()>50)
		 {
		  	throw new InvalidDataException("export failed as the postal code length crossed the max wordlength of 50");
		 }
		 if (phone_number!=null && phone_number.length()>40)
		 {
		  	throw new InvalidDataException("export failed as the phone number crossed the max wordlength of 40");
		 }
		 if (first_name!=null && first_name.length()>150)
		 {
		  	throw new InvalidDataException("export failed as the first name crossed the max wordlength of 150");
		 }
		 if (last_name!=null && last_name.length()>150)
		 {
		  	throw new InvalidDataException("export failed as the last name crossed the max wordlength of 150");
		 }
		 
		  if (first_name_phonetic!=null && first_name_phonetic.length()>150)
		 {
		  	throw new InvalidDataException("export failed as the first name phonetic crossed the max wordlength of 150");
		 }
		 
		  if (last_name_phonetic !=null && last_name_phonetic.length()>150)
		 {
		  	throw new InvalidDataException("export failed as the last name phonetic crossed the max wordlength of 150");
		 }
		  if (address1!=null && address1.length()>240)
		 {
		  	throw new InvalidDataException("export failed as the address crossed the max wordlength of 240");
		 }
		  if (address2!=null &&  address2.length()>240)
		 {
		  	throw new InvalidDataException("export failed as the address2 crossed the max wordlength of 240");
		 }
		 
		 if (city!=null && city.length()>60)
		 {
		  	throw new InvalidDataException("export failed as the city crossed the max wordlength of 60");
		 }
 
 	} 
    
    public static void customerValidation(Customer customer,int FormEntryId) throws InvalidDataException{
    	/*try{*/
    		
    		String FirstName=customer.getFirstName();
    		String LastName=customer.getLastName();
    		String DnisTelNo=customer.getDnisTelephoneNumber();
    		String Email=customer.getEmail();
    		String PhoneNumber=customer.getPhoneNumber();
    		String regex="[<>]";
    		String regexemail="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    		String regextel="^[-0-9 ()+\\.xX]*$";
    		if(FirstName != "" ){
    			
    				if (FirstName.matches(".*" + regex + ".*")){
    					throw new InvalidDataException("HTML Tags found in data, hence export failed");
    					
    				}
    		}
    			
    		if(LastName != "" ){
    				if (LastName.matches(".*" + regex + ".*")){
    					throw new InvalidDataException("HTML Tags found in data, hence export failed");
    					
    				}
    			}
    		if(DnisTelNo != "" ){
    			
    				if (!DnisTelNo.matches(regextel)){
    					throw new InvalidDataException("Invalid DNIS telephone number hence export failed");
    					
    				}
    				else if(DnisTelNo.length() > 30){
    					throw new InvalidDataException("DNIS Telephone number length is greater than 30, hence export failed");
    				}
    				
    		}
    	
    		
    		if( Email != "" ){
    			if (!Email.matches(regexemail)){
    				throw new InvalidDataException("Invalid Email found, hence export failed");
    				//pair.put("email",Email.trim());
    				
    			}
    	}
    		if(PhoneNumber != ""){
    			if (!PhoneNumber.matches(regextel)){
    				throw new InvalidDataException("Invalid number, hence export failed");
    				
    			}
    			
    		}
    		/*}catch(Exception e){
    		e.printStackTrace();
    		recordFailureLog(pair,exporter, e,FormEntryId);
    		}*/
    		
    		
    			
    	}
    
    public static boolean isCurrentlyExported(int form_id){
	    	 System.out.println(currentlyExportedForms);
	    	 if(currentlyExportedForms!=null && currentlyExportedForms.contains(form_id)){
	    		 return true;
	    	 }
	    	 else{
	    		 return false;
	    	 }
    }
    	
    
    public static void setCurrentlyExportedForms(int form_id){
	    	 if(currentlyExportedForms == null){
	    	  currentlyExportedForms = new ArrayList<Integer>();
	    	 }
	    	 currentlyExportedForms.add(form_id);
	   }
    
    
    public static void removeCurrentlyExportedForms(int form_id){
    	 currentlyExportedForms.remove(currentlyExportedForms.indexOf(form_id));
   	}
    	
}
