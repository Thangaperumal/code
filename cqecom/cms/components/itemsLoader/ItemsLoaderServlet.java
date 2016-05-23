package com.cqecom.cms.components.itemsLoader;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Services;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;
import com.cqecom.cms.services.itemsLoader.*;

import java.util.HashMap;

@Component
@Service(Servlet.class)
@Properties(value = {
		@Property(name = "sling.servlet.paths", value = "/content/cqecomcom/en/itemsLoader.html")
})
public class ItemsLoaderServlet extends SlingSafeMethodsServlet{

    public static final Logger Log = LoggerFactory.getLogger(ItemsLoaderServlet.class);
    
    @Reference
    private SlingRepository repository;

    @Override
    protected void service(SlingHttpServletRequest request, 
    					   SlingHttpServletResponse response) throws ServletException, IOException {
    	
    	Session superSession = null;
    	
        try {
        	String approverTimeStamp = request.getParameter("timestamp");
        	superSession = repository.loginAdministrative(null);
        	ItemsLoader loader = new ItemsLoaderImpl();
        	String responseText = "";
        	javax.jcr.Node rootNode = superSession.getRootNode();
            
        	GlobalSettingsReader globalSettingsReader =  GlobalSettingsReader.getInstance();
        	HashMap<String, GlobalSettings> settings =  globalSettingsReader.findAllSettings(superSession);
            if(rootNode.hasNode("content/EcItems/en")){
                loader.setPriceApproverStatus(rootNode, "en", approverTimeStamp, "content/EcItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/EcItems/en/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
                GlobalSettings site_settings = settings.get("en_US");
                String siteContentPath = site_settings.getSiteContentPath();
                loader.deletePricePages(rootNode, siteContentPath);
                responseText = responseText + "DONE : The required content nodes inside content/rs/us/en_US/cache/prices have been deleted.\n";
            }

            if(rootNode.hasNode("content/EcItems/de")){
                loader.setPriceApproverStatus(rootNode, "de", approverTimeStamp, "content/EcItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/EcItems/de/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
                GlobalSettings site_settings = settings.get("de_DE");
                String siteContentPath = site_settings.getSiteContentPath();
                loader.deletePricePages(rootNode, siteContentPath);
                responseText = responseText + "DONE : The required content nodes inside content/rs/de/cache/prices have been deleted.\n";
            }

            if(rootNode.hasNode("content/EcItems/uk")){
                loader.setPriceApproverStatus(rootNode, "uk", approverTimeStamp, "content/EcItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/EcItems/uk/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
                //Please uncomment the next two lines for the productsList component.
                //Commenting it now as there is node called /cache/prices for uk. 
                //loader.deletePricePages(rootNode, "cqecomuk", "uk");
                //responseText = responseText + "DONE : The required content nodes inside content/cqecomde/uk/cache/prices have been deleted.\n";
            }

            if(rootNode.hasNode("content/EcItems/it")){
                loader.setPriceApproverStatus(rootNode, "it", approverTimeStamp, "content/EcItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/EcItems/it/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }
			
            if(rootNode.hasNode("content/EcItems/fr_FR")){
                loader.setPriceApproverStatus(rootNode, "fr_FR", approverTimeStamp, "content/EcItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/EcItems/fr_FR/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }
			
            if(rootNode.hasNode("content/EcItems/es_ES")){
                loader.setPriceApproverStatus(rootNode, "es_ES", approverTimeStamp, "content/EcItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/EcItems/es_ES/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }
			
            if(rootNode.hasNode("content/EcItems/en_GB")){
                loader.setPriceApproverStatus(rootNode, "en_GB", approverTimeStamp, "content/EcItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/EcItems/en_GB/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }
			
            if(rootNode.hasNode("content/global/quickBuyItems/kr")){
                loader.setPriceApproverStatus(rootNode, "kr", approverTimeStamp, "content/global/quickBuyItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/global/quickBuyItems/kr/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }

            if(rootNode.hasNode("content/global/quickBuyItems/fr_FR")){
                loader.setPriceApproverStatus(rootNode, "fr_FR", approverTimeStamp, "content/global/quickBuyItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/global/quickBuyItems/fr_FR/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }
			
			if(rootNode.hasNode("content/global/quickBuyItems/it")){
                loader.setPriceApproverStatus(rootNode, "it", approverTimeStamp, "content/global/quickBuyItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/global/quickBuyItems/it/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }

            if(rootNode.hasNode("content/global/quickBuyItems/es_ES")){
                loader.setPriceApproverStatus(rootNode, "es_ES", approverTimeStamp, "content/global/quickBuyItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/global/quickBuyItems/es_ES/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }

            if(rootNode.hasNode("content/global/quickBuyItems/uk")){
                loader.setPriceApproverStatus(rootNode, "uk", approverTimeStamp, "content/global/quickBuyItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/global/quickBuyItems/uk/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }
			
			if(rootNode.hasNode("content/global/quickBuyItems/en_US")){
                loader.setPriceApproverStatus(rootNode, "en_US", approverTimeStamp, "content/global/quickBuyItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/global/quickBuyItems/en_US/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }
			
			if(rootNode.hasNode("content/global/quickBuyItems/de_DE")){
                loader.setPriceApproverStatus(rootNode, "de_DE", approverTimeStamp, "content/global/quickBuyItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/global/quickBuyItems/de_DE/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }
			if(rootNode.hasNode("content/global/quickBuyItems/en_GB")){
                loader.setPriceApproverStatus(rootNode, "en_GB", approverTimeStamp, "content/global/quickBuyItems/");
                responseText = responseText + "DONE : The time stamp has been updated for content/global/quickBuyItems/en_GB/priceApprover. New Time Stamp ::: " + approverTimeStamp + "\n";
            }
			
			
        	PrintWriter out = response.getWriter();
        	out.println(responseText);
   
        } catch (Exception e) {
            Log.error("Error", e);
            response.getWriter().append("{'error':'" + e.getMessage() + "'}");
        } finally{
        	if(superSession != null){
                superSession.logout();
    		}
        }
    }
}
