package com.cqecom.cms.components.catalogproduct;
import com.cqecom.cms.components.commons.CommonUtils;
import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.LanguageSettings;
import com.cqecom.cms.components.commons.LanguageSettingsReader;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.sling.jcr.api.SlingRepository;

import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.ResourceResolver; 



public class CatalogProductViewHelper 
{
	private Map<String,Object> product;
	private Map<String,Object> productOsub3;
	private Map<String,Object> productOsub6;
	private Map<String,Object> acItem;
	private ArrayList oProducts;
	private ArrayList products;
	private CatalogProductCommand catalogProduct;
	private HashMap[]   onlineProducts;
	private String linkTrackingOnReviews;
	private String linkTrackingOnCustReviews;


	private Session session;
	private GlobalSettings globalSettings;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	boolean showComparisonLink = true;


	public CatalogProductViewHelper(CatalogProductCommand catalogProduct,Map<String,Object> model,Session session,GlobalSettings globalSettings) throws Exception
	{
		product=(Map<String,Object>)model.get("product");
		products=(ArrayList)model.get("products");
		acItem=(Map<String,Object>)model.get("acitem");
		oProducts=(ArrayList)model.get("oproducts");
		this.catalogProduct=catalogProduct;
		onlineProducts=(HashMap[])model.get("onlineproducts");
		linkTrackingOnReviews=StringEscapeUtils.escapeJavaScript(String.format("var s=s_gi(repsuiteid);s.tl(this,\"o\",\"Review-%s-%s\");", catalogProduct.getLanguage(), catalogProduct.getLevel()));
		linkTrackingOnCustReviews=StringEscapeUtils.escapeJavaScript(String.format("var s=s_gi(repsuiteid);s.tl(this,\"o\",\"%s-%s : Customer Reviews Link %s\");", catalogProduct.getEdition(), catalogProduct.getLanguage(), catalogProduct.getLevel()));
		this.globalSettings=globalSettings;
		this.session=session;
	}

	public String getViewTemplate(SlingHttpServletRequest request, SlingHttpServletResponse response) throws Exception
	{
		//Set the Request Attribute for the Analytics 
		request.setAttribute("oproducts", oProducts);
		request.setAttribute("products", products);

		String page="";
		String catalogPath=this.getCatalogPath(catalogProduct.getEdition(),catalogProduct.getProdVersion());
		HashMap<String,String> prodURLMap=this.fetchProductUrl(request,response,catalogProduct);
		String sharedContent=  this.getSharedContent(this.catalogProduct,catalogPath);
		String currentPath=request.getResource().getPath();
		page=this.formPage(sharedContent,catalogProduct,currentPath,prodURLMap);
		if(page.equals(""))
		{
			throw new Exception("Page Cannot be prepared ");
		}

		return page; 
	}   

	public String formPage(String sharedContent,CatalogProductCommand catalogProduct,String currentPath,HashMap<String,String> prodURLMap) throws Exception
	{
		String prodType=catalogProduct.getProductType();
		String level=catalogProduct.getLevel();
		String languageCode=catalogProduct.getLanguage();
		String tmpText=sharedContent;
		LanguageSettingsReader languageSettingsReader=new LanguageSettingsReader();
		LanguageSettings languageSettings=languageSettingsReader.findLanguageSettings(session, currentPath, languageCode);
                String cartURL = "";
                if (CommonUtils.isProduction())
		        cartURL=this.globalSettings.getGlobalSecureDomain()+this.globalSettings.getGlobalCartUrl();
                else
                        cartURL=this.globalSettings.getGlobalStagingDomain()+this.globalSettings.getGlobalCartUrl();
                

		tmpText = tmpText.replaceAll("\\[LANGUAGE\\]", languageSettings.getLongName());
		tmpText = tmpText.replaceAll("\\[LANGUAGE_APPEND_EN\\]", languageSettings.getLongNameEn());
		tmpText = tmpText.replaceAll("\\[CART_URL\\]",cartURL );
		tmpText = tmpText.replaceAll("\\[LANGUAGE_SHORT\\]",languageSettings.getName());
		tmpText = tmpText.replaceAll("\\[LANGUAGE_SHORT_APPEND_EN\\]", languageSettings.getNameEn());
		tmpText = tmpText.replaceAll("\\[IL-LANGUAGE\\]", languageSettings.getIlLanguageName());
        tmpText = tmpText.replaceAll("\\[NO-IL-LANGUAGE\\]", languageSettings.getNoIlLanguageName());
		if(prodType.equals("OSUB") || prodType.equals("OTB") || prodType.equals("RF") || prodType.equals("SEOB"))
		{
			if(prodType.equals("OSUB") || prodType.equals("SEOB")){
				tmpText = tmpText + "<script type='text/javascript'> prices['[SKU_06]']='[PRICE_06]'; prices['[SKU_12]']='[PRICE_12]';";
				tmpText = tmpText + "prices['[SKU_03]']='[PRICE_03]'; if(document.getElementById('osub-select')) {  ";
				tmpText = tmpText + "setPrice(document.getElementById('osub-select').options[document.getElementById('osub-select').options.selectedIndex].value);";
				tmpText = tmpText + "} </script>";
			}
			if(onlineProducts!=null)    
			{
				for (HashMap tmpProduct : onlineProducts) {
					product = tmpProduct;

					if (!product.isEmpty()) {
						String version = product.get("duration").toString();
						if (version.equals("03")) {
							productOsub3 = tmpProduct;
						}
						else if (version.equals("06")) {
							productOsub6 = tmpProduct;
						}
						Integer price = ((Integer)product.get("list_price_with_vat")) / 100;
						tmpText = tmpText.replaceAll("\\[PRICE_" + version + "\\]",CommonUtils.formatPrice(this.globalSettings.getGlobalSite(),this.globalSettings.getGlobalCurrency(), price, true));
						tmpText = tmpText.replaceAll("\\[SKU_" + version + "\\]", product.get("sku").toString());
					}
				}
			}

			if(acItem!=null)
			{
				if (!acItem.isEmpty()) {
					//product = acItem;
					Integer price = ((Integer)acItem.get("list_price_with_vat")) / 100;
					tmpText = tmpText.replaceAll("\\[AC_PRICE\\]", CommonUtils.formatPrice(this.globalSettings.getGlobalSite(),this.globalSettings.getGlobalCurrency(), price, true));
					tmpText = tmpText.replaceAll("\\[AC_SKU\\]", acItem.get("sku").toString());
				}
			}
			if( prodURLMap.get(prodType)!=null)
			{
				tmpText = tmpText.replaceAll("http://%5BPRODUCT_URL%5D", prodURLMap.get(prodType).toString());
				tmpText = tmpText.replaceAll("%5BPRODUCT_URL%5D", prodURLMap.get(prodType).toString());
			}

		}
		else
		{
			if(product!=null){
				if (!product.isEmpty()) {
					Integer price = ((Integer)product.get("list_price_with_vat")) / 100;
					tmpText = tmpText.replaceAll("\\[PRICE\\]", CommonUtils.formatPrice(this.globalSettings.getGlobalSite(),this.globalSettings.getGlobalCurrency(), price, true));
					String totaleSku = product.get("sku").toString();
					tmpText = tmpText.replaceAll("\\[SKU\\]", totaleSku );

				}
			}


		}


		if(product!=null)
		{
			if(product.get("list_price_with_vat")!=null) 
			{
				Integer price = ((Integer)product.get("list_price_with_vat")) / 100;
				tmpText = tmpText.replaceAll("\\[PRICE\\]",CommonUtils.formatPrice(this.globalSettings.getGlobalSite(), this.globalSettings.getGlobalCurrency(), price, true));
			}         
			if(product.get("your_price_with_vat")!=null) 
			{
				Integer dynamicPrice = ((Integer)product.get("your_price_with_vat")) / 100;
				tmpText = tmpText.replaceAll("\\[TEST_PRICE\\]", CommonUtils.formatPrice(this.globalSettings.getGlobalSite(), globalSettings.getGlobalCurrency(), dynamicPrice, true));
			}


			if(product.get("upsell_value_with_vat") != null) {
				Integer upsell_value = ((Integer)product.get("upsell_value_with_vat")) / 100;
				tmpText = tmpText.replaceAll("\\[UPSELL_VALUE\\]", CommonUtils.formatPrice(this.globalSettings.getGlobalSite(),globalSettings.getGlobalCurrency(), upsell_value, true));
			}


			if(product.get("sku") != null)
			{
				String itemSku = product.get("sku").toString();
				tmpText = tmpText.replaceAll("\\[SKU\\]", itemSku);
				String globalAppName=this.globalSettings.getGlobalAppName();
				if(globalAppName.equals(PageConstants.CQECOMUS)||globalAppName.equals(PageConstants.CQECOMUK) )
				{
					tmpText=getRatings(tmpText);
				}
			}
		}

		if(prodURLMap.get(prodType)!=null)
		{
			tmpText = tmpText.replaceAll("http://%5BPRODUCT_URL%5D", prodURLMap.get(prodType).toString());
			tmpText = tmpText.replaceAll("%5BPRODUCT_URL%5D", prodURLMap.get(prodType).toString());
			if(prodURLMap.containsKey(prodType+"-OLD")){ 
				tmpText = tmpText.replaceAll("http://%5BLB_URL%5D", prodURLMap.get(prodType+"-OLD").toString()); 
				tmpText = tmpText.replaceAll("%5BLB_URL%5D", prodURLMap.get(prodType+"-OLD").toString()); 
			}

		}
		if(prodURLMap.get(level)!=null)
		{
			tmpText = tmpText.replaceAll("http://%5BPRODUCT_URL%5D", prodURLMap.get(level).toString());
			tmpText = tmpText.replaceAll("%5BPRODUCT_URL%5D", prodURLMap.get(level).toString());
			if(prodURLMap.containsKey(level+"-OLD")){
				tmpText = tmpText.replaceAll("http://%5BLB_URL%5D", prodURLMap.get(level+"-OLD").toString()); 
				tmpText = tmpText.replaceAll("%5BLB_URL%5D", prodURLMap.get(level+"-OLD").toString()); 
			}

		}

		if(this.showComparisonLink==true )
		{
			String comparisonLink=prodURLMap.get("comparisonLink");
			tmpText = tmpText.replaceAll("\\[COMPARISON_LINK\\]", (showComparisonLink ? comparisonLink : ""));
		}



		return tmpText;
	}


	private String  getRatings(String tmpText) throws Exception
	{
		String tmpTextWithReviewRatings="";
		String bvContentPath="";
		try {
			//session=this.repository.loginAdministrative(null);
			bvContentPath=this.globalSettings.getBvContentPath();
			javax.jcr.Node testNode =null;
			if(this.session.getRootNode().hasNode(bvContentPath)) 
			{
				testNode= this.session.getRootNode().getNode(bvContentPath);
			}
			
			
			String itemSku="",reviewRating="";
			if(product.get("sku")!=null)
			{
				itemSku=product.get("sku").toString();
			}
			if (testNode!=null && (testNode.hasNode(itemSku) || (productOsub3 != null && testNode.hasNode(productOsub3.get("sku").toString()))) ) {
				Float ratingValue = new Float(0.0);
				if (productOsub3 != null && productOsub6 != null) { // For OSub/TOSub products
					String rating = "";
					int totalSubProducts = 0;
					if (testNode.hasNode(productOsub3.get("sku").toString())) {
						Node bvNode = testNode.getNode(productOsub3.get("sku").toString());
						rating = bvNode.getProperty("rating").getString();
						ratingValue += new Float(rating);
						totalSubProducts++;
					}
					if (testNode.hasNode(productOsub6.get("sku").toString())) {
						Node bvNode = testNode.getNode(productOsub6.get("sku").toString());
						rating = bvNode.getProperty("rating").getString();
						ratingValue += new Float(rating);
						totalSubProducts++;
					}
					if (totalSubProducts > 0) {
						ratingValue = (float) ratingValue/totalSubProducts;
					}
				}
				else {
					Node bvNode = testNode.getNode(itemSku);
					String rating = bvNode.getProperty("rating").getString();
					ratingValue = new Float(rating);      
				}
				String levelTitleText = product.get("level").toString();
				if (levelTitleText.toLowerCase().equals("na")) {
					levelTitleText = "TOT";
				}
				
				ratingValue = (float) Math.round(ratingValue * 10); 
				ratingValue = ratingValue /10;  
				if (this.catalogProduct.getEdition().equals("PE") && this.catalogProduct.isLightBox()){
					reviewRating = "<a id='bv-stars-img' class='bv-stars' alt='Customer Reviews' href='%5BPRODUCT_URL%5D' title='" + levelTitleText + "' onclick='" + linkTrackingOnReviews + "'><span class='rating-";
				}
				else {
					reviewRating = "<a id='bv-stars-img' href='%5BPRODUCT_URL%5D?reviews=true' onclick='" + linkTrackingOnReviews + "'><span class='rating-";
				}

				reviewRating = reviewRating + ratingValue.toString().replaceAll("\\.", "_" ) + "'></span></a><br/><span class='bv-rate'><cite>(";
				reviewRating = reviewRating +  ratingValue.toString() + " out of 5) </cite></span><br>";

				if (this.catalogProduct.isFeatured()) {
					levelTitleText = product.get("level").toString();
					if (levelTitleText.toLowerCase().equals("na")) {
						levelTitleText = "TOT";
					}
					if (this.catalogProduct.getEdition().equals("PE")){             
						if (this.catalogProduct.isLightBox()){
							reviewRating = reviewRating  + "<a  rel='facebox' alt='Customer Reviews' href='%5BPRODUCT_URL%5D' title='" + levelTitleText + "' onclick='" + linkTrackingOnCustReviews + "'>Customer Reviews</a><span class='lb-url' style='display:none'>%5BLB_URL%5D</span>" ;
						}
						else {
							reviewRating = reviewRating  + "<a href='%5BPRODUCT_URL%5D?reviews=true' onclick='" + linkTrackingOnCustReviews + "'>Customer Reviews</a><span class='lb-url' style='display:none'>%5BLB_URL%5D</span>" ;
						}
					}
					else {                                 
						reviewRating = reviewRating  + "<a href='%5BPRODUCT_URL%5D?reviews=true' onclick='" + linkTrackingOnReviews + "'>See All Reviews</a>";
					}
				}
				else{
					levelTitleText = product.get("level").toString();
					if (levelTitleText.toLowerCase().equals("na")) {
						levelTitleText = "TOT";
					}
					if (this.catalogProduct.getEdition().equals("PE") && this.catalogProduct.isLightBox()){ 
						reviewRating = reviewRating + "<a alt='Customer Reviews' href='%5BPRODUCT_URL%5D' title='" + levelTitleText + "' onclick='" + linkTrackingOnCustReviews + "' class='review-link'>Customer Reviews</a><span class='lb-url' style='display:none'>%5BLB_URL%5D</span>"; 
					}else { 
						reviewRating ="<a href='%5BPRODUCT_URL%5D?reviews=true' onclick='" + linkTrackingOnCustReviews + "' class='review-link'>Customer Reviews</a><span class='lb-url' style='display:none'>%5BLB_URL%5D</span>" +  reviewRating; 
					}   
				}

			}

			if (this.catalogProduct.isBest()){
				tmpText = tmpText.replaceAll("\\[PRODUCT_VALUE\\]", "best");
			}
			else if (this.catalogProduct.isGreat()){
				tmpText = tmpText.replaceAll("\\[PRODUCT_VALUE\\]", "great");
			}
			else {
				tmpText = tmpText.replaceAll("\\[PRODUCT_VALUE\\]", "");                    
			}

			tmpText = tmpText.replaceAll("\\[REVIEW_RATING\\]",  reviewRating.toString());
			tmpTextWithReviewRatings=tmpText;	
			return tmpTextWithReviewRatings;

		} 
		catch (PathNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Path Not found at"+bvContentPath);
		}
		catch (RepositoryException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Repository Exception");
		}
	}

	public  HashMap<String,String> fetchProductUrl(SlingHttpServletRequest request, SlingHttpServletResponse response,CatalogProductCommand catalogProductCommand) throws Exception
	{

		HashMap<String,String>  prodURLMap=new HashMap<String,String> ();
		Resource currentResource=request.getResource();
		ResourceResolver resourceResolver=request.getResourceResolver();
		PageManager pageManager = (PageManager)resourceResolver.adaptTo(PageManager.class);
		Page currentPage=pageManager.getContainingPage(currentResource);
		boolean isLightBoxLink=catalogProductCommand.isLightBox();
		String level=catalogProductCommand.getLevel();
		String globalAppname=this.globalSettings.getGlobalAppName();
		Resource res=request.getResource();
		if (globalAppname.equals(PageConstants.CQECOMDE))
		{
			prodURLMap.put("L1", currentPage.getPath() + "/stufe-1.html");
			prodURLMap.put("L2", currentPage.getPath() + "/stufe-2.html");
			prodURLMap.put("L3", currentPage.getPath() + "/stufe-3.html");
			prodURLMap.put("L4", currentPage.getPath() + "/stufe-4.html");
			prodURLMap.put("L5", currentPage.getPath()+ "/stufe-5.html");
			prodURLMap.put("S2", currentPage.getPath() + "/stufe-1-2.html");
			prodURLMap.put("S3", currentPage.getPath() + "/stufe-1-2-3.html");
			prodURLMap.put("B45", currentPage.getPath()  + "/stufe-4-5.html");
			prodURLMap.put("S5", currentPage.getPath() + "/stufe-1-2-3-4-5.html");
			prodURLMap.put("OSUB", currentPage.getPath() + "/online-abo.html");
		}
		else if (globalAppname.equals(PageConstants.CQECOMUS)||globalAppname.equals(PageConstants.CQECOMUK)||
					globalAppname.equals(PageConstants.CQECOMIT)||globalAppname.equals(PageConstants.CQECOMES)||
					globalAppname.equals(PageConstants.CQECOMEU)||globalAppname.equals(PageConstants.CQECOMFR)||
					globalAppname.equals(PageConstants.CQECOMKR))
		{
			Resource rootRes = request.getResourceResolver().getResource("/"+this.globalSettings.getSiteContentPath() + "/" +currentPage.getName());
			Page rootPage = rootRes == null ? null : rootRes.adaptTo(Page.class);
			if (rootRes != null && !isLightBoxLink){
				if(globalAppname.equals(PageConstants.CQECOMUS) && this.catalogProduct.getEdition().equals("PE")){
					String language = request.getRequestURI().replaceAll("/"+this.globalSettings.getSiteContentPath()+"/learn-","").replaceAll(".html","").toLowerCase();
					prodURLMap.put("L1", "/reviews/" + language + "/level-1");
					prodURLMap.put("L2", "/reviews/" + language + "/level-2");
					prodURLMap.put("L3", "/reviews/" + language + "/level-3");
					prodURLMap.put("L4", "/reviews/" + language + "/level-4");
					prodURLMap.put("L5", "/reviews/" + language + "/level-5");
					prodURLMap.put("S2", "/reviews/" + language + "/level-1-2");
					prodURLMap.put("S3", "/reviews/" + language + "/level-1-2-3");
					prodURLMap.put("S5", "/reviews/" + language + "/level-1-2-3-4-5");
					prodURLMap.put("OSUB", "/reviews/" + language + "/osub");
					prodURLMap.put("TOT", "/reviews/" + language +"/totale");
					prodURLMap.put("L1-OLD", rootRes.getPath() + "/level-1.html"); 
					prodURLMap.put("L2-OLD", rootRes.getPath() + "/level-2.html"); 
					prodURLMap.put("L3-OLD", rootRes.getPath() + "/level-3.html"); 
					prodURLMap.put("L4-OLD", rootRes.getPath() + "/level-4.html");
					prodURLMap.put("L5-OLD", rootRes.getPath() + "/level-5.html");
					prodURLMap.put("S2-OLD", rootRes.getPath() + "/level-1-2.html");
					prodURLMap.put("S3-OLD", rootRes.getPath() + "/level-1-2-3.html");
					prodURLMap.put("S5-OLD", rootRes.getPath() + "/level-1-2-3-4-5.html");
					prodURLMap.put("OT-OLD", rootRes.getPath() + "/totale.html");
					prodURLMap.put("OSUB-OLD", rootRes.getPath() + "/online.html");
					prodURLMap.put("TOT-OLD", rootRes.getPath() + "/totale.html");
				}
				else
				{
					prodURLMap.put("L1", rootRes.getPath() + "/level-1.html");
					prodURLMap.put("L2", rootRes.getPath() + "/level-2.html");
					prodURLMap.put("L3", rootRes.getPath() + "/level-3.html");
					prodURLMap.put("L4", rootRes.getPath() + "/level-4.html");
					prodURLMap.put("L5", rootRes.getPath() + "/level-5.html");
					prodURLMap.put("S2", rootRes.getPath() + "/level-1-2.html");
					prodURLMap.put("S3", rootRes.getPath() + "/level-1-2-3.html");
					prodURLMap.put("S5", rootRes.getPath() + "/level-1-2-3-4-5.html");
					prodURLMap.put("OT", rootRes.getPath() + "/totale.html");
					prodURLMap.put("TOT", rootRes.getPath() + "/totale.html");		
					prodURLMap.put("OSUB", rootRes.getPath() + "/online.html");
				}
			}
			if (rootRes != null && isLightBoxLink){
				if (level != null && CommonUtils.getGlobalEdition(currentPage.getPath())!= null && catalogProductCommand.getProdVersion() != null && catalogProductCommand.getEdition().equals("PE")) {
					if(globalAppname.equals(PageConstants.CQECOMUS)){
						String language = request.getRequestURI().replaceAll("/"+this.globalSettings.getSiteContentPath()+"/learn-","").replaceAll(".html","").toLowerCase();
							if(level.equals("TOT")){								
								prodURLMap.put(catalogProductCommand.getProductType(), "/reviews/"+language+"/totale.html");
								String tempVersion="v"+catalogProductCommand.getProdVersion();
								prodURLMap.put(catalogProductCommand.getProductType()+"-OLD", "/" + CommonUtils.getGlobalEdition(currentPage.getPath()).toLowerCase()  + "/"+tempVersion+"/" + level.toLowerCase()); 
							} 
							else if(catalogProductCommand.getProductType().equals("OSUB")){
								prodURLMap.put(catalogProductCommand.getProductType(), "/reviews/"+language+"/osub.html");
								String tempVersion="v"+catalogProductCommand.getProdVersion();
								prodURLMap.put(catalogProductCommand.getProductType()+"-OLD", "/" + catalogProductCommand.getEdition().toLowerCase()  + "/"+tempVersion+"/online"); 
							} 
							else
							{   
								String levelString = "";
								if(level.charAt(0)=='L')
								  levelString = "level-" + String.valueOf(level.charAt(1));
								else if(level.equals("S2"))
								   levelString = "level-1-2";
								else if(level.equals("S3"))
									levelString = "level-1-2-3";
								else if(level.equals("S5"))
									levelString = "level-1-2-3-4-5";
								prodURLMap.put(level, "/reviews/"+language+"/"+levelString+".html");
								String tempVersion="v"+catalogProductCommand.getProdVersion(); 
								prodURLMap.put(level+"-OLD", "/" + CommonUtils.getGlobalEdition(currentPage.getPath()).toLowerCase() + "/"+tempVersion+"/" + level.toLowerCase());
							}
					}
					else{
							if(level.equals("TOT")){
								String tempVersion="v"+catalogProductCommand.getProdVersion();
								prodURLMap.put(catalogProductCommand.getProductType(), "/" + this.globalSettings.getSiteContentPath() + "/" + CommonUtils.getGlobalEdition(currentPage.getPath()).toLowerCase()  + "/"+tempVersion+"/" + level.toLowerCase() + ".html");
							} 
							else if(catalogProductCommand.getProductType().equals("OSUB") || catalogProductCommand.getProductType().equals("RF") || catalogProductCommand.getProductType().equals("SEOB")){
								String tempVersion="v"+catalogProductCommand.getProdVersion();
								String lighboxFileName = (catalogProductCommand.getProductType().equals("OSUB") || catalogProductCommand.getProductType().equals("SEOB")) ? "online" : "reflex";
								prodURLMap.put(catalogProductCommand.getProductType(), "/" + this.globalSettings.getSiteContentPath() + "/" + catalogProductCommand.getEdition().toLowerCase()  + "/"+tempVersion+"/" + lighboxFileName + ".html");
							} 
							else
							{   
								//Forming  the version to substitute in the product URL
								String tempVersion="v"+catalogProductCommand.getProdVersion();
								prodURLMap.put(level, "/" + this.globalSettings.getSiteContentPath() + "/" + CommonUtils.getGlobalEdition(currentPage.getPath()).toLowerCase() + "/"+tempVersion+"/" + level.toLowerCase() + ".html");
							}
					}
				}
			}
		}
		String comparisonLink = " | <a href=" + currentPage.getPath() + "/produktvergleich.html>Vergleichstabelle</a>";
		prodURLMap.put("comparisonLink",comparisonLink );
		return prodURLMap;

	}


	private String getSharedContent(CatalogProductCommand catalogProductCommand,String catalogPath) throws Exception
	{

		String sharedContent="";
		String queryPath="";
		String queryString="";
		String basePath="";
		if(catalogPath.contains(PageConstants.rootPath))
		{
			basePath = catalogPath;
		}
		else
		{
			basePath = PageConstants.rootPath + this.globalSettings.getSiteContentPath() + catalogPath;

		}

		if(catalogProductCommand.getProductType().equals("OSUB") || catalogProductCommand.getProductType().equals("SEOB"))
		{
			queryPath=basePath+"onlineItems";
			queryString=queryPath+"//*[@level='"+ catalogProductCommand.getAcLevel()+"']";
		}
                else if(catalogProductCommand.getProductType().equals("OTB")) {
                       queryPath=basePath+"onlineItems";
                       queryString=queryPath+"//*[@level='"+ catalogProductCommand.getLevel()+"']";
                }
		else if(catalogProductCommand.getProductType().equals("RF")) {
                       queryPath=basePath+"onlineItems";
                       queryString=queryPath+"//*[@level='"+ catalogProductCommand.getAcLevel()+"']";
                }
		else
		{
			if(catalogProductCommand.isFeatured() )
			{
				queryPath=basePath+"featuredProducts";
				queryString=queryPath+"//*[@level='"+ catalogProductCommand.getLevel()+"']" ;

			}
			else
			{

				queryPath=basePath+"levelItems";
				if(globalSettings.getGlobalAppName().equals(PageConstants.CQECOMDE))
				{
					queryString=queryPath+"//*[@level='"+ catalogProductCommand.getLevel()+"']" ;
				}
				else
				{
					queryString=queryPath+"//*[@level='"+ catalogProductCommand.getLevel()+"',@edition='"+catalogProductCommand.getEdition()+"']" ;
				}

			}
		}
		List<String> node=  getNode(queryString);   
		if(node.size()==0)       
		{    
			sharedContent="";
			throw new Exception("No shared Contents");
		}
		sharedContent=node.get(0);
		return sharedContent;
	}

	private String  getCatalogPath(String edition,String version)
	{
		String catalogPath=PageConstants.CATALOGPATH;
		String resourcePath="catalogPath_"+edition+"V"+version;
		if("catalogPath_PEV4".equals(resourcePath)){
			catalogPath=this.globalSettings.getPersonalV4Path();
		}else  if("catalogPath_PEV3".equals(resourcePath)){
			catalogPath=this.globalSettings.getPersonalV3Path();
		}else  if("catalogPath_PEV2".equals(resourcePath)){
			catalogPath=this.globalSettings.getPersonalV2Path();
		}
		else if("catalogPath_HSV3".equals(resourcePath)){
			catalogPath=this.globalSettings.getHomeSchoolV3Path();
		}else if("catalogPath_HSV2".equals(resourcePath)){
			catalogPath=this.globalSettings.getHomeSchoolV2Path();
		}else if("catalogPath_PEV1".equals(resourcePath)){
                        catalogPath=this.globalSettings.getPersonalV1Path();
                }

			

		return catalogPath;

	}

	public List<String>  getNode(String queryString) throws Exception
	{
		List<String> nodes = new ArrayList<String>();
		QueryManager queryManager = session.getWorkspace().getQueryManager();
		Query query = queryManager.createQuery(queryString, Query.XPATH);
		QueryResult result = query.execute();
		NodeIterator iterator = result.getNodes();
		while (iterator.hasNext()) 
		{
			Node node = iterator.nextNode();
			nodes.add(node.getProperty("text").getString());
		}
		return nodes;
	}

}

