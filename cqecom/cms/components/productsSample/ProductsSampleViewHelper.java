package com.cqecom.cms.components.productsSample;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.components.commons.CommonUtils;
import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.LanguageSettings;
import com.cqecom.cms.components.commons.LanguageSettingsReader;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

public class ProductsSampleViewHelper{
	
	private Session session;
	private GlobalSettings globalSettings;
	private ProductsSampleCommand productsSampleCommandObj;
	private boolean figses;
	private HashMap[] osubProducts;
	HashMap<String,Object> categoryHash;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private HashMap acProduct = new HashMap();	
	HashMap product = new HashMap();
	LanguageSettingsReader languageSettingsReader;
	
	public ProductsSampleViewHelper(ProductsSampleCommand productsSampleCommandObj,Map<String,Object> model,Session session,GlobalSettings globalSettings) throws Exception{
	
		this.productsSampleCommandObj=productsSampleCommandObj;
		this.figses = (Boolean) model.get("figSes");		
		this.globalSettings=globalSettings;
		this.session=session;
		this.categoryHash=(HashMap) model.get("categoryHash");
		this.product =  (HashMap) model.get("productSample");
		this.osubProducts= (HashMap[]) model.get("osubProducts");
		this.acProduct = (HashMap) model.get("acProduct");
		languageSettingsReader=new LanguageSettingsReader();
		
	}	
	
	public String getViewTemplate(SlingHttpServletRequest request, SlingHttpServletResponse response) throws Exception{
		
		String text = "";
		Resource currentResource=request.getResource();
		ResourceResolver resourceResolver=request.getResourceResolver();
		PageManager pageManager = (PageManager)resourceResolver.adaptTo(PageManager.class);
		Page currentPage=pageManager.getContainingPage(currentResource);
		logger.debug("Current Page after resolving" + currentPage.getName());
		String currentPath=request.getResource().getPath();		
		logger.debug("Current Path :" + currentPath);
		text = this.retrieveNodeValues(figses,currentPage,currentPath,text,request);
		return text;
		
	} 
	
	public String retrieveNodeValues(boolean figSesm,Page currentPage, String currentPath,String text,SlingHttpServletRequest request) throws Exception{	   
		
	   String globalAppName = this.globalSettings.getGlobalAppName();
	   String globalSite = this.globalSettings.getGlobalSite();
	   String langCode = productsSampleCommandObj.getLanguage();
	   String level =  productsSampleCommandObj.getLevel();
	   boolean subNav = productsSampleCommandObj.getSubNav();
	   String rootNav = productsSampleCommandObj.getRootNav();
	   boolean isRichText = productsSampleCommandObj.getRichText();
	   String cartURL=this.globalSettings.getGlobalSecureDomain()+this.globalSettings.getGlobalCartUrl();
	   
	   String lang =  "";	
	   String shortLang= "";
	   String sku = "";
	   Integer price = new Integer(0);	   
	   String navstart = currentPage.getPath();
	   
       boolean isProduction= false;
       Boolean isStaging = false;
       
  
	   LanguageSettings languageSettings=languageSettingsReader.findLanguageSettings(session, currentPath,langCode );
	   lang =  languageSettings.getLongName();
       shortLang  = languageSettings.getName();		   		   
	   Node productModule = null;
	   Node metternichModule = null;
	   Node productModuleV2 = null;
	   
	   if (session.getRootNode().hasNode(globalSettings.getSharedContentPath() + currentPage.getName())) {
	     if (figses && (currentPage.getName().equals("online-abo")) ) { 
	    	 productModule = getNodeContent(globalSettings.getSharedContentPath() + currentPage.getName() + "-figses/jcr:content/productModule"); 
	      } else {
		    	if(session.getRootNode().hasNode((globalSettings.getSharedContentPath() + currentPage.getName() +
		    			"/jcr:content/productModule"))){  
		    		productModule = getNodeContent(globalSettings.getSharedContentPath() + 
		    				currentPage.getName() + "/jcr:content/productModule");
		    	}
		        if (session.getRootNode().hasNode(globalSettings.getSharedContentPath() + 
		        		currentPage.getName() + "/jcr:content/metternichProduct")) {
		        	metternichModule = getNodeContent(globalSettings.getSharedContentPath() +
		        			currentPage.getName() + "/jcr:content/metternichProduct");
		        }
	      }
	   }
	   if (session.getRootNode().hasNode(globalSettings.getSharedContentPath() + currentPage.getName() + "-v2")) {
	      productModuleV2 = getNodeContent(globalSettings.getSharedContentPath() + currentPage.getName() + "-v2/jcr:content/productModule");
	   } 
	   //Node acText = currentNode.getSession().getRootNode().getNode("content/" + globalAppName + "/" + globalSite + "/sharedContent/" + currentPage.getName() + "/jcr:content/acText");
	   		   
	   if (productModule != null && "3".equals(categoryHash.get("product_version"))) {
		   text = productModule.getProperty("text").getString().replaceAll("\\[LANGUAGE\\]", lang);  
          
          if (globalAppName.equals("cqecomcom")){
        	  
			if (metternichModule != null && 
					currentPage.getPath().matches(".*(test_design_d|test_design_b|test_design_c|test_design_g|test_design_a|hola_design_f).*")){					
				text = metternichModule.getProperty("text").getString().replaceAll("\\[LANGUAGE\\]", lang); 
			}
			 //else if(currentPage.getParent().getName().matches(".*(learn-french|learn-german|learn-italian|learn-spanish|learn-spanish-spain).*")) 
			 //{
			  //text = metternichModule.getProperty("text").getString().replaceAll("\\[LANGUAGE\\]", lang);  
			 //}
			else {
				text = replaceLanguageValue(globalSettings.getSharedContentPath() + currentPage.getName() + "/jcr:content",text,lang);					
			}
			
			Node patools = getNodeContent(globalSettings.getSharedContentPath()+"tips/pat/jcr:content/learningtips/htmltext");
            String pat = patools.getProperty("text").getString();               
            text = text.replaceAll("\\[PAT\\]", pat);                
			
            if (langCode.matches("ESP|LAT|ENG|EBR") || (langCode.matches("FRA|DEU") && level.matches("L1|L2|L3|S2|S3"))){
            	String semCDtext = "<li>Workbooks &amp; Quizzes</li><li>Tests</li><li>Answer Keys</li>";
                text = text.replaceAll("\\[ADDITIONAL_SEM_CD_CONTENTS\\]", semCDtext);
            } else {
                text = text.replaceAll("\\[ADDITIONAL_SEM_CD_CONTENTS\\]", "");
            }
          }

		 text = text.replaceAll("\\[LANGUAGE_APPEND_EN\\]", languageSettings.getLongNameEn()); 
		 text = text.replaceAll("\\[LANGUAGE_SHORT\\]", languageSettings.getName()); 
		 text = text.replaceAll("\\[LANGUAGE_SHORT_APPEND_EN\\]", languageSettings.getNameEn());
	           
		 
		 Node res = getNodeContent(globalSettings.getSharedContentPath()+"tips/learning-tips-"+ langCode.toLowerCase() +"/jcr:content/learningtips/htmltext");
         String tips = res.getProperty("text").getString();
         text = text.replace("[LEARNING TIPS]", tips);          
	     
	   }else if (productModuleV2 != null  && "2".equals(categoryHash.get("product_version"))) {
	       
           NodeIterator iterator = getNodeContent(globalSettings.getSharedContentPath() + currentPage.getName() + "-v2/jcr:content").getNodes();
           
           while (iterator.hasNext()) {
               Node itemNode = iterator.nextNode();
               if(itemNode.hasProperty("sling:resourceType") && 
            		   itemNode.getProperty("sling:resourceType").getString().equals("cqecom/components/content/htmlText")) {
                   String itemEdition = "PE";
                   if (itemNode.hasProperty("edition")){
                     itemEdition = itemNode.getProperty("edition").getString();
                   }
                   
                   if (productsSampleCommandObj.getProductType().equals("OSUB")){
                       text = itemNode.getProperty("text").getString().replaceAll("\\[LANGUAGE\\]", lang);
                       break;
                   }else{                   
                     if (itemEdition.equals(product.get("edition").toString())) {
                       text = itemNode.getProperty("text").getString().replaceAll("\\[LANGUAGE\\]", lang);
                       break;
                     }
                   }  
                   //Removed the repetitive code 
                   /*if (itemEdition.equals(product.get("edition").toString())) {
                       text = itemNode.getProperty("text").getString().replaceAll("\\[LANGUAGE\\]", lang);
                       break;
                   }   */  
               }
           }

           text = text.replaceAll("\\[LANGUAGE_APPEND_EN\\]", languageSettings.getLongNameEn()); 
           text = text.replaceAll("\\[LANGUAGE_SHORT\\]", languageSettings.getName()); 
           text = text.replaceAll("\\[LANGUAGE_SHORT_APPEND_EN\\]", languageSettings.getNameEn()); 
           text = text.replaceAll("\\[LANGUAGE_LOWER\\]", languageSettings.getName().toLowerCase()); 
                                                 
           if (globalAppName.equals("cqecomcom")){
        	   Node patools = session.getRootNode().getNode(globalSettings.getSharedContentPath()+"tips/pat/jcr:content/learningtips/htmltext");
               String pat = patools.getProperty("text").getString();               
               text = text.replaceAll("\\[PAT\\]", pat);     	                       
           }
           text = text.replaceAll("%5BLANGUAGE_SHORT%5D", shortLang);             
           //This is for images in v2 product pages
           text = text.replaceAll("\\(LANGUAGE_SHORT\\)", shortLang);  
       }else {
           text = productsSampleCommandObj.getText();
       } 	   
	   		   
	   if (globalAppName.equals("cqecomcom")){	          
		  Node bvNode= session.getNode("/"+globalSettings.getSharedContentPath()+"bvscript/jcr:content/rightPar/text");
		  String bvText = bvNode.getProperty("text").getString();        
	      text = text.replace("[BVREVIEWLOAD]", bvText);	                          
          String bvNoScript = "<noscript><iframe src='http://reviews.cqecom.com/bvstaging/5703/[SKU]/reviews.htm?format=noscript' width='100%'></iframe><br/></noscript>";
          text = text.replaceAll("\\[NOSCRIPT\\]", bvNoScript); 
	   }  
	      
	   String tier = System.getProperty("com.cqecom.webdev.tier");
	   if (tier != null && tier.equals("production")) { isProduction = true; }
	   if (tier != null && tier.equals("staging")) { isStaging = true; }
		   
		   // Global Var to switch the inky files
	   String globalInkyExtn = "_inhouse.inky.xml";
	   if(isProduction){
	     globalInkyExtn = ".inky.xml";
	   }
   
       text = text.replaceAll("(_inhouse)*.inky.xml", globalInkyExtn);
       if (!product.isEmpty() && product.size() > 0) {		    	   
           price = Integer.parseInt(product.get("your_price_with_vat").toString()) / 100 ;
           
           text = text.replaceAll("\\[CART_URL\\]", cartURL);
           text = text.replaceAll("\\[PRICE\\]", CommonUtils.formatPrice(globalSite,  this.globalSettings.getGlobalCurrency(), price, true));
           sku =  product.get("sku").toString();
           text = text.replaceAll("\\[SKU\\]", sku);
       }
	           
       text = prepareInternalNav(text, subNav, rootNav, request, navstart, currentPage);
       
       if (osubProducts.length > 0) {
          for(int j = 0;j < osubProducts.length; j++) {
            text = text + "<script type='text/javascript'> prices['[SKU_06]']='[PRICE_06]'; prices['[SKU_12]']='[PRICE_12]';";
            text = text + "if(document.getElementById('osub-select')) {  "; 
            text = text + "setPrice(document.getElementById('osub-select').options[document.getElementById('osub-select').options.selectedIndex].value);";
            text = text + "} </script>";
            if (osubProducts[j] != null && !osubProducts[j].isEmpty() && osubProducts[j].size() > 0){
               String osubDur = osubProducts[j].get("duration").toString();
               price = ((Integer)osubProducts[j].get("list_price_with_vat")) / 100;
               text = text.replaceAll("\\[PRICE_" + osubDur + "\\]", CommonUtils.formatPrice(globalSite, this.globalSettings.getGlobalCurrency(), price, true));
               sku = osubProducts[j].get("sku").toString();
               text = text.replaceAll("\\[SKU_" + osubDur + "\\]", sku );
               text = text.replaceAll("\\[CART_URL\\]", cartURL);
           }
         }
       }
       if (!acProduct.isEmpty() && acProduct.size() > 0) {
           text = text.replaceAll("\\[AC_SKU\\]", acProduct.get("sku").toString());
           price = ((Integer)acProduct.get("list_price_with_vat")) / 100;
           text = text.replaceAll("\\[AC_PRICE\\]", CommonUtils.formatPrice(globalSite, this.globalSettings.getGlobalCurrency(), price, true));
           String crossSellPrice = "S5".equals(acProduct.get("level").toString()) ? "60" : "40";
           text = text.replaceAll("\\[AC_CROSS_SELL_PRICE\\]", crossSellPrice);
       } 
	      
	      //Not needed 
	     /*  final DiffInfo diffInfo = resource.adaptTo(DiffInfo.class);
	       if ( diffInfo != null ) {
	           final DiffService diffService = sling.getService(DiffService.class);
	           final ValueMap map = ResourceUtil.getValueMap(diffInfo.getContent());
	           final String diffOutput = DiffInfo.getDiffOutput(diffService, diffInfo, text, isRichText, map.get("text", ""));
	           if ( diffOutput != null ) {
	               text = diffOutput;
	               isRichText = true;
	           }
	       } */
	    //Get User Reviews
	    text = getUserReviews(sku,currentPage,metternichModule,text, request);
	       
	     if (isProduction) { 
	        text = text.replaceAll("bvstaging%2F", "");
	        text = text.replaceAll("bvstaging/", "");
	     } 
		
	   return text;
	}
	
	
	public String getUserReviews(String sku,Page currentPage,Node metternichModule,String text, SlingHttpServletRequest request) throws  Exception{
		String globalAppName = this.globalSettings.getGlobalAppName();
		if (globalAppName.equals("cqecomcom")){                              
	           String userreview = "";
	           javax.jcr.Node reviewsNode = getNodeContent("content/BV Content/Reviews");                                
	           ArrayList reviewsNodeNames = new ArrayList();
	                
	           if (reviewsNode.hasNode(sku)) {
	               NodeIterator reviewsNodeIterator = getNodeContent("content/BV Content/Reviews/" + sku).getNodes();                   
	               while (reviewsNodeIterator.hasNext()){                   
	                  Node reviewsItemNode = reviewsNodeIterator.nextNode();
	                  if (reviewsItemNode.hasProperty("rating")) {
	                     String retingString = reviewsItemNode.getProperty("rating").getString();                     
	                     int itemRating = Integer.parseInt(retingString);	                     
	                     if (itemRating >= 4){                         
	                        reviewsNodeNames.add(reviewsItemNode.getPath());                                                                           
	                     }
	                  }                       
	               }

	               int reviewsNodeSize = reviewsNodeNames.size();
	               Random generator = new Random();
	               if (reviewsNodeSize >= 1){
	                   int randomIndex1 = 0;

	                   userreview = "<blockquote id='user-reviews' style='display:block'><h3><a href='#' onclick='readReviews();'>";
	                   userreview = userreview + "<span class='mn'>User Reviews <span class='cta'></span></span></a></h3>";
	                   userreview = userreview + "<div class='review-content clearfix'><div id='reviews'>" ;                                                                     
	                   String reviewNameText =   reviewsNodeNames.get(randomIndex1).toString();    
	                   Node reviewNodeFirst = session.getNode(reviewNameText);                      
	                   String html = "" ; 
	                  
	                   html = "<div";                                                                   
	                   String location1 =  reviewNodeFirst.getProperty("location").getString();                  
	                   String title1 = reviewNodeFirst.getProperty("title").getString();
	                   String reviewer1 = reviewNodeFirst.getProperty("name").getString(); 
	                   String reviewid1 = reviewNodeFirst.getName();       
	                   String rating1 = reviewNodeFirst.getProperty("rating").getString(); 
	                   String text1 = reviewNodeFirst.getProperty("text").getString();
	                   String  reviewRating1 = rating1.toString().replaceAll("\\.", "_" );
	                   String date1 = reviewNodeFirst.getProperty("date").getString();                      
	                     
	                   if (reviewsNodeSize >= 2){ 
	                      html= html + " class='left'" ;
	                   }
	                          
	                   html = html + "><img width='65' height='12' src='/content/dam/cqecomcom/images/personal/stars/rating-" + reviewRating1 + "_0.gif' ";
	                   html = html + "alt='BV Rating' class='bv-img'/><br/><span class='star-value'>" ;
	                   html = html + reviewRating1.toString().replaceAll("_", "." ) + " out of 5" ;
	                   html = html + "</span><br/><b>" + title1 + "</b> <br/>" ; 
	                        
	                   int reviewlength = text1.length();
	                      
	                   if (reviewlength > 200) 
	                       text1 = text1.substring(0, 200) + " ...";                           
	                        
	                   html = html + text1 + " <br/>" ;
	                   if (!(reviewer1.equals(""))) 
	                       html = html + "- " + reviewer1 ;                        
	                   if (!(location1.equals("")))
	                       html = html + "<br/>(" + location1 + ")," ; 
	                   if (date1.length() > 10) 
	                       html = html + date1.substring(0, 10);
	                   if(reviewlength > 200) 
	                       html = html + " | <a href='" + currentPage.getPath()  + ".html?reviews=true&amp;featurereview="+ reviewid1 +"'>more</a>" ;                         
	                   html = html + "</div>";
	                      
	                   if (reviewsNodeSize >= 2) {
	                       int randomIndex2 = generator.nextInt(reviewsNodeSize-1);

	                       if (randomIndex2 == 0){
	                          randomIndex2 = 1;
	                       }
	                                                       
	                       reviewNameText =   reviewsNodeNames.get(randomIndex2).toString();    
	                       reviewNodeFirst = session.getNode(reviewNameText);                      
	                       location1 =  reviewNodeFirst.getProperty("location").getString() ;                  
	                       title1 = reviewNodeFirst.getProperty("title").getString() ;
	                       reviewer1 = reviewNodeFirst.getProperty("name").getString() ; 
	                       reviewid1 = reviewNodeFirst.getName() ;       
	                       rating1 = reviewNodeFirst.getProperty("rating").getString() ; 
	                       text1 = reviewNodeFirst.getProperty("text").getString() ;
	                       reviewRating1 = rating1.toString().replaceAll("\\.", "_" );
	                       date1 = reviewNodeFirst.getProperty("date").getString();     
	                          
	                       html = html + "<div class='right'><img width='65' height='12' src='/content/dam/cqecomcom/images/personal/stars/rating-" + reviewRating1 + "_0.gif' ";
	                       html = html + "alt='BV Rating' class='bv-img'/><br/><span class='star-value'>" ;
	                       html = html + reviewRating1.toString().replaceAll("_", "." ) + " out of 5" ;
	                       html = html + "</span><br/><b>" + title1 + "</b> <br/>" ; 
	                        
	                       reviewlength = text1.length();
	                      
	                       if (reviewlength > 200) 
	                          text1 = text1.substring(0,197) + "  ..." ;    
	                                                      
	                       html = html + text1 + " <br/>" ;
	                       if (!(reviewer1.equals(""))) 
	                          html = html + "- " + reviewer1 ;                        
	                       if (!(location1.equals("")))
	                          html = html + "<br/>(" + location1 + ")," ;  
	                       if (date1.length() > 10) 
	                          html = html + date1.substring(0, 10);
	                       if(reviewlength > 200) 
	                          html = html + " | <a href='" + currentPage.getPath()  + ".html?reviews=true&amp;featurereview="+ reviewid1 +"'>more</a>" ;                         
	                       html = html + "</div>"; 
	                   }
					   
	                   userreview = userreview + html ;
	                   userreview = userreview + "</div><div class='review-footer clearfix'>" ;
					   
					   if (productsSampleCommandObj.getEdition().toString().equals("PE")) {
						    String currentPath=request.getResource().getPath();		
						    String langCode = productsSampleCommandObj.getLanguage();
						    LanguageSettings languageSettings=languageSettingsReader.findLanguageSettings(session, currentPath,langCode );
							userreview = userreview + "<a href='" + "/reviews/" + languageSettings.getName().toLowerCase() + "/level-1" + "' onclick='readReviews();' class='see'>See all reviews</a>" ;
					   }
					   else {
							userreview = userreview + "<a href='javascript:void(0)' onclick='readReviews();' class='see'>See all reviews</a>" ;
					   }

	                   userreview = userreview + "<a href='/"+this.globalSettings.getSiteContentPath()+"/personal/reviews/submit.html?bvdisplaycode=5703&bvappcode=rr&bvproductid=[SKU]&bvpage=http%3A%2F%2Fcqecom.ugc.bazaarvoice.com%2F5703%2F[SKU]%2Fsubmitreview.htm%3Fformat%3Dembedded%26campaignid%3DBV_RATING_SUMMARY%26return%3Dhttp://www.cqecom.com";
	                   userreview = userreview + currentPage.getPath() + ".html%26innerreturn%3Dhttp%253A%252F%252Fcqecom.ugc.bazaarvoice.com%252F5703%252F[SKU]%252Freviews.djs%253Fformat%253Dembeddedhtml%26user%3D__USERID__%26sessionparams%3D__BVSESSIONPARAMS__&bvcontenttype=REVIEW_SUBMISSION&bvauthenticateuser=false' class='write'>Write a review</a></div></div></blockquote>" ;  
	                   
	                  
	                   
	                   //User Reviews for metternich.
	                   if (metternichModule != null && currentPage.getPath().matches(".*(test_design_d|test_design_b|test_design_c|test_design_g|test_design_a|hola_design_f).*")){                  
	                      
	                     int randomIndex2;
	                     userreview = "<div id='user_reviews' class='u_reviews' style='display: block;'><div id='review-sub' style='float: left; display: block;'><h2>User Reviews &nbsp;&nbsp;<a onclick='readReviews();' class='see-all' href='javascript:void(0)'>See All</a></h2>";
	                     ArrayList randList = new ArrayList();
	                     for(int in = 0; in < 3; in++) {              
	                        randomIndex2 = generator.nextInt(reviewsNodeSize-1);
	                        boolean presentRand = false;
	                        if(in > 0){
	                          presentRand = false;
	                          for(int cnt = 0; cnt < randList.size(); cnt++){
	                             if(randomIndex2 == (Integer) randList.get(cnt)){
	                               presentRand = true;
	                             }                                   
	                          }                        
	                        }
	                        
	                        if(!presentRand){
	                           randList.add(randomIndex2);
	                        }else{
	                          in = in - 1;
	                          continue;
	                        }  
	                                                       
	                        reviewNameText =   reviewsNodeNames.get(randomIndex2).toString();    
	                        reviewNodeFirst = session.getNode(reviewNameText);                      
	                        location1 =  reviewNodeFirst.getProperty("location").getString() ;                  
	                        title1 = reviewNodeFirst.getProperty("title").getString() ;
	                        reviewer1 = reviewNodeFirst.getProperty("name").getString() ; 
	                        reviewid1 = reviewNodeFirst.getName();       
	                        rating1 = reviewNodeFirst.getProperty("rating").getString() ; 
	                        text1 = reviewNodeFirst.getProperty("text").getString() ;
	                        reviewRating1 = rating1.toString().replaceAll("\\.", "_" );
	                        date1 = reviewNodeFirst.getProperty("date").getString(); 
	                          
	                        HashMap dateMap = new HashMap(); 
	                        String[] months = new DateFormatSymbols().getMonths();	                        
	                        for(int i = 0; i < months.length-1 ;i++){
	                       	 if(i < 9){
	                       		 dateMap.put("0"+(i+1), months[i]);
	                       	 }else{
	                       		 dateMap.put(i+1, months[i]); 
	                       	 }
	                        }               
	                        
	  //                     Date date2 = new Date(date1); 
	                       
	                        userreview = userreview + "<div class='left'><img width='65' height='12' class='bv-img' alt='BV Rating' src='/content/dam/cqecomcom/images/personal/stars/rating-";
	                        userreview = userreview + reviewRating1 + "_0.gif'><span class='star-value'>  (";
	                        userreview = userreview + reviewRating1.toString().replaceAll("_", "." ) + " out of 5)</span> <br><b>" + title1 + "</b> <br>";
	                        
	                        if(text1.length() > 225)
	                           text1 = text1.substring(0,222) + "  ..." ;   
	                        userreview = userreview  + text1 + "<br>-" + reviewer1 + "<br>" ;
	                        if (!(location1.equals("")))
	                            userreview = userreview  + "(" + location1 + "), " ;                                                              
	                        if (date1.length() > 10){                    
	                          date1 = dateMap.get(date1.substring(5,7)) + " " + date1.substring(8,10) + ", " + date1.substring(0,4) ;
	                          userreview = userreview +  date1 ; 
	                        }
	                        if(text1.length() > 225) 
	                          userreview = userreview + " | <a href='" + currentPage.getPath()  + ".html?reviews=true&amp;featurereview="+ reviewid1 +"'>more</a>" ;
	                        userreview = userreview + "</div>";                        
	                      }                    
	                      userreview = userreview + "</div></div>";
	                   }                                  
	               }   
	               else{
	                   if (currentPage.getName().contains("totale")) {                        
	                      userreview = "<blockquote id='default_user-reviews' style='display:block;'><h3>";
	                      userreview = userreview + "<span class='mn pad'>What Customers are Saying About TOTALe&trade;</span></h3>";
	                      userreview = userreview + "<div class='review-content clearfix'><div id='defaul_reviews'>";
	                      userreview = userreview + "<div class='left'><b>My best decision</b> <br/>";
	                      userreview = userreview + "I learned quicker and even better with the online games and the live studio sessions... The online coaches are friendly and very encouraging. They make learning easy. I gained confidence from interacting with my coaches. They have taught me new words that have helped me express myself better... Their excitement as they watch me progress me in their language encourages me and instills a desire to learn more.";
	                      userreview = userreview + "<br/>- Sharon <span style='color:#666666'>(Ocean Springs, MS)</span></div>";
	                      userreview = userreview + "<div class='right'><b>WOW!</b> <br/>";
	                      userreview = userreview + "I love it! I have learned quickly and it really doesn't \"feel\" like  learning because it is so much fun... The live coaching sessions are very informative and interacting with the language coaches and the other learners is so much fun. They really help aid in the learning process... Thank you CqEcom for making learning so easy and fun!";
	                      userreview = userreview + "<br/>- Melissa <span style='color:#666666'>(Lexington, KY)</span></div></div>";
	                      userreview = userreview + "<div class='review-footer clearfix'>";
	                      userreview = userreview + "<a class='see' rel='facebox' href='/"+this.globalSettings.getSiteContentPath()+"/personal/languages/totale-pop.html' oncontextmenu='return false;'>";
	                      userreview = userreview + "See all TOTALe reviews</a><a href='/"+this.globalSettings.getSiteContentPath()+"/personal/reviews/submit.html?bvdisplaycode=5703&amp;bvproductid=[SKU]&amp;bvpage=http%3A%2F%2Freviews.cqecom.com%2Fbvstaging%2F5703%2F[SKU]%2Faction.htm%3Fformat%3Dembedded%26action%3DAddReview%26user%3D__USERID__%26userdisplayname%3D__USERDISPLAYNAME__%26return%3Dhttp://www.cqecom.com";
	                      userreview = userreview + currentPage.getPath() + ".html%26campaignid%3DBV_RATING_SUMMARY_ZERO_REVIEWS&amp;bvcontenttype=REVIEW_SUBMISSION&amp;bvauthenticateuser=false' class='write'>Write a review</a> ";
	                      userreview = userreview + "</div></div></blockquote>" ;                       
	                   }
	                   else{
	                      userreview = "<blockquote id='user-reviews' style='display:block'><h3>";
	                      userreview = userreview + "<span class='mn pad'>User Reviews<span class='cta'></span></span></h3>";
	                      userreview = userreview + "<div class='review-content clearfix'><p id='noreviews'>";
	                      userreview = userreview + "There are no reviews yet. Be the first to write a <a href='/"+this.globalSettings.getSiteContentPath()+"/personal/reviews/submit.html?bvdisplaycode=5703&amp;bvproductid=[SKU]&amp;bvpage=http%3A%2F%2Freviews.cqecom.com%2Fbvstaging%2F5703%2F[SKU]%2Faction.htm%3Fformat%3Dembedded%26action%3DAddReview%26user%3D__USERID__%26userdisplayname%3D__USERDISPLAYNAME__%26return%3Dhttp://www.cqecom.com";
	                      userreview = userreview + currentPage.getPath() + ".html%26campaignid%3DBV_RATING_SUMMARY_ZERO_REVIEWS&amp;bvcontenttype=REVIEW_SUBMISSION&amp;bvauthenticateuser=false'>";
	                      userreview = userreview + "review</a>.</p></div></blockquote>";  
	                  }  
	                  //User Reviews for metternich.
	                  if (metternichModule != null && currentPage.getPath().matches(".*(test_design_d|test_design_b|test_design_c|test_design_g|test_design_a|hola_design_f).*")){
	                     userreview = "";
	                  }
	                }                                        
	           }    
	           else {
	                if (currentPage.getName().contains("totale")) {                        
	                      userreview = "<blockquote id='default_user-reviews' style='display:block;'><h3>";
	                      userreview = userreview + "<span class='mn pad'>What Customers are Saying About TOTALe&trade;</span></h3>";
	                      userreview = userreview + "<div class='review-content clearfix'><div id='defaul_reviews'>";
	                      userreview = userreview + "<div class='left'><b>My best decision</b> <br/>";
	                      userreview = userreview + "I learned quicker and even better with the online games and the live studio sessions... The online coaches are friendly and very encouraging. They make learning easy. I gained confidence from interacting with my coaches. They have taught me new words that have helped me express myself better... Their excitement as they watch me progress me in their language encourages me and instills a desire to learn more.";
	                      userreview = userreview + "<br/>- Sharon <span style='color:#666666'>(Ocean Springs, MS)</span></div>";
	                      userreview = userreview + "<div class='right'><b>WOW!</b> <br/>";
	                      userreview = userreview + "I love it! I have learned quickly and it really doesn't \"feel\" like  learning because it is so much fun... The live coaching sessions are very informative and interacting with the language coaches and the other learners is so much fun. They really help aid in the learning process... Thank you CqEcom for making learning so easy and fun!";
	                      userreview = userreview + "<br/>- Melissa <span style='color:#666666'>(Lexington, KY)</span></div></div>";
	                      userreview = userreview + "<div class='review-footer clearfix'>";
	                      userreview = userreview + "<a class='see' rel='facebox' href='/"+this.globalSettings.getSiteContentPath()+"/personal/languages/totale-pop.html' oncontextmenu='return false;'>";
	                      userreview = userreview + "See all TOTALe reviews</a><a href='/"+this.globalSettings.getSiteContentPath()+"/personal/reviews/submit.html?bvdisplaycode=5703&amp;bvproductid=[SKU]&amp;bvpage=http%3A%2F%2Freviews.cqecom.com%2Fbvstaging%2F5703%2F[SKU]%2Faction.htm%3Fformat%3Dembedded%26action%3DAddReview%26user%3D__USERID__%26userdisplayname%3D__USERDISPLAYNAME__%26return%3Dhttp://www.cqecom.com";
	                      userreview = userreview + currentPage.getPath() + ".html%26campaignid%3DBV_RATING_SUMMARY_ZERO_REVIEWS&amp;bvcontenttype=REVIEW_SUBMISSION&amp;bvauthenticateuser=false' class='write'>Write a review</a> ";
	                      userreview = userreview + "</div></div></blockquote>" ;                       
	                }
	                else{
	                      userreview = "<blockquote id='user-reviews' style='display:block'><h3>";
	                      userreview = userreview + "<span class='mn pad'>User Reviews<span class='cta'></span></span></h3>";
	                      userreview = userreview + "<div class='review-content clearfix'><p id='noreviews'>";
	                      userreview = userreview + "There are no reviews yet. Be the first to write a <a href='/"+this.globalSettings.getSiteContentPath()+"/personal/reviews/submit.html?bvdisplaycode=5703&amp;bvproductid=[SKU]&amp;bvpage=http%3A%2F%2Freviews.cqecom.com%2Fbvstaging%2F5703%2F[SKU]%2Faction.htm%3Fformat%3Dembedded%26action%3DAddReview%26user%3D__USERID__%26userdisplayname%3D__USERDISPLAYNAME__%26return%3Dhttp://www.cqecom.com";
	                      userreview = userreview + currentPage.getPath() + ".html%26campaignid%3DBV_RATING_SUMMARY_ZERO_REVIEWS&amp;bvcontenttype=REVIEW_SUBMISSION&amp;bvauthenticateuser=false'>";
	                      userreview = userreview + "review</a>.</p></div></blockquote>";
	                }
	                //User Reviews for metternich.
	                if (metternichModule != null && currentPage.getPath().matches(".*(test_design_d|test_design_b|test_design_c|test_design_g|test_design_a|hola_design_f).*")){
	                     userreview = "";
	                }
	           }           
	           userreview  = userreview.replaceAll("\\[SKU\\]", sku);    
	           text = text.replaceAll("\\[USER_REVIEWS\\]", java.util.regex.Matcher.quoteReplacement(userreview));   
	       }
		 return text;
	}
	
	private Node getNodeContent(String nodePath) throws PathNotFoundException, RepositoryException{		
		return session.getRootNode().getNode(nodePath);
	}
	
	private String replaceLanguageValue(String nodePath,String text,String lang) throws PathNotFoundException, RepositoryException{		
		NodeIterator iterator = getNodeContent(nodePath).getNodes();        
		while (iterator.hasNext()) {
			Node itemNode = iterator.nextNode();
			if(itemNode.hasProperty("sling:resourceType") && 
					"cqecom/components/content/htmlText".equals(itemNode.getProperty("sling:resourceType").getString())) {
				String itemEdition = "PE";
				if (itemNode.hasProperty("edition")){
					itemEdition = itemNode.getProperty("edition").getString();
				}							  	                   
				if (itemEdition.equals( product.get("edition").toString())) {
					text =  itemNode.getProperty("text").getString().replaceAll("\\[LANGUAGE\\]", lang);
					break;  
				}                                   
			} 
		}
		return text;
	}
	
	private String prepareInternalNav(String text,boolean subNav,String rootNav,SlingHttpServletRequest request,String navStart,Page currentPage){
		String globalAppName = this.globalSettings.getGlobalAppName();
		String globalSite = this.globalSettings.getGlobalSite();
		String internalNav = "";
	    if(subNav){
	       if (rootNav.length() > 0) {
	           if(rootNav.matches("parentIsRoot")){
	              if (globalAppName.equals("cqecomcom")) {
	            	  navStart = "/" + this.globalSettings.getSiteContentPath() + "/" + currentPage.getParent().getName();
	              }else{
	            	  navStart = currentPage.getParent().getPath();
	              }
	           }else{
	               if (globalAppName.equals("cqecomcom")) {
	            	   navStart = "/" + this.globalSettings.getSiteContentPath() + "/" + currentPage.getName();
	              }else{
	            	  navStart = currentPage.getPath(); 
	              } 
	           }
	       }
	       
		   Resource rootRes = request.getResourceResolver().getResource(navStart);
           Page rootPage = rootRes == null ? null : rootRes.adaptTo(Page.class);
           String active = "";
           
           if (rootPage != null) {
               Iterator<Page> children = rootPage.listChildren(new PageFilter(request)); 
               internalNav = internalNav + "<div class=\"internal-nav-condensed\" id=\"nav\"><div class=\"internal-nav\" style='position:relative;'><ul>"; 
               while (children.hasNext()) {
                  Page child = children.next();
                  if(child.getName().equals(currentPage.getName()))
                     active="class=\"active\"";
                  else
                     active="";
                  internalNav = internalNav + "<li " + active + "><a href=\"" + child.getPath() + ".html\"" + " title=\"" + child.getTitle() + "\" " + active + " rel=\"nofollow\">" + child.getTitle() + "</a></li>"; 
               }
               internalNav = internalNav + "</ul>";
           
               if (globalAppName.equals("cqecomcom")){
                 if (rootPage.getName().equals(currentPage.getName())){                  
                   internalNav = internalNav + "<div class='viewallproducts'><a href='" + rootPage.getParent().getPath() + ".html" + "'>View All Products</a></div>"; 
                  }else{
                   internalNav = internalNav + "<div class='viewallproducts'><a href='" + rootPage.getPath() + ".html" + "'>View All Products</a></div>";  
                  } 
               }   
               internalNav = internalNav + "</div></div>"; 
               text = text.replaceAll("\\[INTERNAL_NAV\\]", internalNav);
           }
	       }else{
		           text = text.replaceAll("\\[INTERNAL_NAV\\]", "");
		   }
		return text;
	}
}
