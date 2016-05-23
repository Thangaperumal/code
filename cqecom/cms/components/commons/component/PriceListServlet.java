package com.cqecom.cms.components.commons.component;

import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;
import com.cqecom.cms.components.commons.LanguageSettings;
import com.cqecom.cms.components.commons.LanguageSettingsReader;
import com.cqecom.webdev.ec.Clients;
import com.cqecom.webdev.ec.JawsClient;

import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.*;

/**
 * Returns list of prices in json format
 */
@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.paths", value = "/cqecom/prices.json")
})
public class PriceListServlet extends SlingSafeMethodsServlet {

  @Reference
  private SlingRepository repository;


    private static final String PAGE_PARAMETER = "globalContentUrl";
    private static final Logger logger = LoggerFactory.getLogger(PriceListServlet.class);

    private String getStackTrace(Exception ex) {
        String result = "";
        result += ex.getMessage() + "\n\t";

        for (StackTraceElement ste : ex.getStackTrace()) {
            result += ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + "\n\t";
        }

        return result;
    }

    @Override
    protected void service(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {

        String globalContentUrl = request.getParameter(PAGE_PARAMETER);

        Session session = null;
        GlobalSettings gs = null;
        try {
            session = repository.loginAdministrative(null);

            gs = GlobalSettingsReader.getInstance().findSettings(session, globalContentUrl.split("/"));

        } catch (Exception e) {
//            response.getWriter().append("ERROR: /cqecom/prices.json", e);
            response.getWriter().append("ERROR: /cqecom/prices.json: " + getStackTrace(e));
            if (session != null) {
                session.logout();
            }
            return;
        }

        NodeIterator nodeIter = null;
        String lang = (String) request.getParameter("langCode");
        String promo = (String) request.getParameter("promo");
        Iterator iterator = null;
        String[] mtVersion = {"v3", "v4"};
        javax.jcr.Node items = null;
        javax.jcr.Node languageNode = null;
        javax.jcr.Node promoCode = null;
        javax.jcr.Node vertical = null;
        javax.jcr.Node langCode = null;
        javax.jcr.Node level = null;
        javax.jcr.Node priceApprover = null;

        //creating super admin session to allow end users to create new nodes in repo for caching the dynamic item prices.
        String path = "";
        String base = "";
        try {
            if (promo != null) {

                if (gs.getGlobalAppName().equals("cqecomcom")) {
                    base = "en";
                } else if (gs.getGlobalAppName().equals("cqecomuk")) {
                    base = "uk";
                } else if (gs.getGlobalAppName().equals("cqecomde")) {
                    base = "de";
                }
                path = "content/EcItems/" + base;
                boolean hasItems = session.getRootNode().hasNode(path);
                boolean approverStatusFlag = true;

                if (hasItems) {
                    items = session.getRootNode().getNode(path);
                    if (items.hasNode("priceApprover"))
                        priceApprover = items.getNode("priceApprover");
                } else {
                    items = session.getNode("/content/EcItems").addNode(base, "sling:OrderedFolder");
                    priceApprover = items.addNode("priceApprover", "nt:unstructured");
                    priceApprover.setProperty("status", new Date().toString());
                }

                if (items != null && items.hasNode(promo))
                    promoCode = items.getNode(promo);
                if (promoCode != null && promoCode.hasNode(gs.getGlobalEdition()))
                    vertical = promoCode.getNode(gs.getGlobalEdition());
                if (vertical != null && vertical.hasNode(lang))
                    langCode = vertical.getNode(lang);

                if (priceApprover != null && langCode != null) {
                    if (priceApprover.hasProperty("status") && langCode.hasProperty("pas"))
                        if (priceApprover.getProperty("status").getString().equals(langCode.getProperty("pas").getString()))
                            approverStatusFlag = false;
                }

                if (promoCode == null || langCode == null || approverStatusFlag) {

                    if (priceApprover != null && priceApprover.hasProperty("status")) {
                        ArrayList<String> productsList = new ArrayList<String>();
                        LanguageSettingsReader languageReader = new LanguageSettingsReader();
                        LanguageSettings languageSettings = languageReader.findLanguageSettings(session, globalContentUrl, lang);
                        String[] levelList, langLevels, osubLevels, tsubLevels;
                        levelList = null;
                        long long_version = 0;
                        if (languageSettings != null) {
                            String langLevelsString = (languageSettings.getLevels() != null) ? languageSettings.getLevels().replace(" ", "") : "";
                            String osubLevelsString = (languageSettings.getOSUBLevels() != null) ? languageSettings.getOSUBLevels().replace(" ", "") : "";
                            String tsubLevelsString = (languageSettings.getOTBLevels() != null) ? languageSettings.getOTBLevels().replace(" ", "") : "";
                            langLevels = (langLevelsString.length() > 0) ? langLevelsString.split(",") : null;
                            osubLevels = (osubLevelsString.length() > 0) ? osubLevelsString.split(",") : null;
                            tsubLevels = (tsubLevelsString.length() > 0) ? tsubLevelsString.split(",") : null;
                            if (langLevels != null)
                                productsList.addAll(Arrays.asList(langLevels));
                            if (osubLevels != null)
                                productsList.addAll(Arrays.asList(osubLevels));
                            if (tsubLevels != null)
                                productsList.addAll(Arrays.asList(tsubLevels));
                            if (productsList.size() > 0)
                                levelList = (String[]) productsList.toArray(new String[productsList.size()]);
                            long_version = languageSettings.getVersion();
                        } else {
                            response.getWriter().append("Error : langusge Settings is null. Assigning it an empty String");
                        }
                        String paStatus = priceApprover.getProperty("status").getString();
                        //items = currentNode.getSession().getRootNode().getNode(path);

                        if (items.hasNode(promo)) {
                            promoCode = items.getNode(promo);
                            promoCode.setProperty("pas", paStatus);
                            promoCode.getSession().save();
                            if (langCode == null || approverStatusFlag) {
                                if (levelList != null)
                                    langCode = cacheItemPrices(promoCode, vertical, langCode, level, lang, levelList, gs.getGlobalEdition(), gs.getSiteCode(), gs.getGlobalCurrency(), paStatus, long_version);
                            }// end if PAS status
                        } else {
                            promoCode = items.addNode(promo, "nt:unstructured");
                            promoCode.setProperty("pas", paStatus);
                            promoCode.getSession().save();
                            if (levelList != null)
                                langCode = cacheItemPrices(promoCode, vertical, langCode, level, lang, levelList, gs.getGlobalEdition(), gs.getSiteCode(), gs.getGlobalCurrency(), paStatus, long_version);
                        }//end else promo has node

                    }//end price approver cond
                }
                if (langCode != null) {
                    ////Modified to overcome hard coding of populating the node iterator - starts Apr14

                    if (gs.getGlobalAppName().equals("cqecomcom") || gs.getGlobalAppName().equals("cqecomuk") || gs.getGlobalAppName().equals("cqecomde")) {
                        nodeIter = session.getRootNode().getNode(path + "/" + request.getParameter("promo") + "/" + gs.getGlobalEdition() + "/" + request.getParameter("langCode")).getNodes();
                    }
                    ////Modified to overcome hard coding of populating the node iterator - ends Apr14
                }
            }
        } catch (Exception e) {
            response.getWriter().append("ERROR: /cqecom/prices.json " + getStackTrace(e));
            if (session != null) {
                session.logout();
            }
            return;
        }

        String delim = "";
        response.setContentType("text/plain");

        String result = "";
        JSONArray resultJsonArray = new JSONArray();
        try {
            if (nodeIter != null) {
                while (nodeIter.hasNext()) {
                    Node itemNode = nodeIter.nextNode();

                    JSONObject option = new JSONObject();
                    option.put("level", itemNode.getName());

                    JSONArray priceJsonArray = new JSONArray();

                    JSONObject price1Options = new JSONObject();
                    price1Options.put("price", itemNode.getProperty("list_price_with_vat").getString());
                    price1Options.put("value", itemNode.getProperty("list_price_with_vat").getString());

                    JSONObject price2Options = new JSONObject();
                    price2Options.put("testPrice", itemNode.getProperty("your_price_with_vat").getString());
                    price2Options.put("value", itemNode.getProperty("your_price_with_vat").getString());

                    JSONObject price3Options = new JSONObject();
                    price2Options.put("upsellPrice", itemNode.getProperty("upsell_value_with_vat").getString());
                    price2Options.put("value", itemNode.getProperty("upsell_value_with_vat").getString());

                    priceJsonArray.put(price1Options);
                    priceJsonArray.put(price2Options);
                    priceJsonArray.put(price3Options);

                    option.put("prices", priceJsonArray);

                    resultJsonArray.put(option);
                }

                response.getWriter().append(resultJsonArray.toString());
            }
        } catch (Exception e) {
            response.getWriter().append("ERROR: /cqecom/prices.json " + getStackTrace(e));
        } finally {
            if (session != null) {
                session.logout();
            }
        }

    }


    public static javax.jcr.Node cacheItemPrices(javax.jcr.Node promoCode, javax.jcr.Node vertical, javax.jcr.Node langCode, javax.jcr.Node level, String lang, String[] levelList, String globalEdition, String siteCode, String globalCurrency, String paStatus, long version) throws Exception {
        String promo = promoCode.getName().toString();
        JawsClient jc = Clients.getJawsClient();
        for (int i = 0; i < levelList.length; i++) {
            HashMap categoryHash = new HashMap();
            HashMap categoryV4Hash = new HashMap();
            Float price = new Float(0.0);
            Float dynamicPrice = new Float(0.0);
            Float upsell_value = new Float(0.0);
            Integer productPrice = new Integer(0);
            ////included to seperate website specific code of building categoryHash - starts Apr14
            if (siteCode.equals("US_WEBSITE")) {
                categoryHash = buildCategoryHashForUS(lang, levelList[i], globalEdition, version, categoryHash);
            } else if (siteCode.equals("UK_WEBSITE")) {
                categoryHash = buildCategoryHashForUK(lang, levelList[i], globalEdition, version, categoryHash);
            } else if (siteCode.equals("DE_WEBSITE")) {
                categoryHash = buildCategoryHashForDE(lang, levelList[i], globalEdition, version, categoryHash);
            }
            ////included to seperate website specific code of building categoryHash - ends Apr14
            HashMap products = new HashMap();
            HashMap productsV4 = new HashMap();
            if (!promoCode.hasNode(globalEdition.toString())) {
                vertical = promoCode.addNode(globalEdition.toString(), "nt:unstructured");
                vertical.getSession().save();
            } else {
                vertical = promoCode.getNode(globalEdition.toString());
            }
            if (!vertical.hasNode(lang.toString())) {
                langCode = vertical.addNode(lang.toString(), "nt:unstructured");
                langCode.setProperty("pas", paStatus);
                langCode.getSession().save();
            } else {
                langCode = vertical.getNode(lang.toString());
                langCode.setProperty("pas", paStatus);
                langCode.getSession().save();
            }
            if (!langCode.hasNode(levelList[i].toString())) {
                level = langCode.addNode(levelList[i].toString(), "nt:unstructured");
                level.getSession().save();

            } else {
                level = langCode.getNode(levelList[i].toString());
            }
            products = jc.findItembyCategory(siteCode, categoryHash, promo);
            if (products != null) {
                if (products.get("list_price_with_vat") == null) {
                    level.setProperty("list_price_with_vat", "No Price");
                    products.put("list_price_with_vat", "NO Price");
                } else {
                    price = new Float((Integer) products.get("list_price_with_vat"));
                    price = (Float) price.floatValue() / 100;
                    price = new Float(Round(price.floatValue(), 2));
                    level.setProperty("list_price_with_vat", formatFloatPrice(globalCurrency, price, siteCode));
                    products.put("list_price_with_vat", formatFloatPrice(globalCurrency, price, siteCode));
                }
                if (products.get("your_price_with_vat") == null) {
                    level.setProperty("your_price_with_vat", "No Price");
                    products.put("your_price_with_vat", "NO Price");
                } else {
                    dynamicPrice = new Float((Integer) products.get("your_price_with_vat"));
                    dynamicPrice = (Float) dynamicPrice.floatValue() / 100;
                    dynamicPrice = new Float(Round(dynamicPrice.floatValue(), 2));
                    level.setProperty("your_price_with_vat", formatFloatPrice(globalCurrency, dynamicPrice, siteCode));
                    products.put("your_price_with_vat", formatFloatPrice(globalCurrency, dynamicPrice, siteCode));
                }
                if (products.get("upsell_value_with_vat") == null) {
                    level.setProperty("upsell_value_with_vat", "No Price");
                    products.put("upsell_value_with_vat", "NO Price");
                } else {
                    upsell_value = new Float((Integer) products.get("upsell_value_with_vat"));
                    upsell_value = (Float) upsell_value.floatValue() / 100;
                    upsell_value = new Float(Round(upsell_value.floatValue(), 2));
                    level.setProperty("upsell_value_with_vat", formatFloatPrice(globalCurrency, upsell_value, siteCode));
                    products.put("upsell_value_with_vat", formatFloatPrice(globalCurrency, upsell_value, siteCode));
                }
                level.getSession().save();
            }// end v3 if

        } //end for loop
        return langCode;
    }

    public static HashMap buildCategoryHashForUK(String lang, String levelListElement, String globalEdition, long version, HashMap categoryHash) {
        categoryHash.put("level", levelListElement);
        categoryHash.put("product_type", "ACB");
        categoryHash.put("edition", globalEdition);
        categoryHash.put("language", lang.toString());

        if (version == 4) {

            categoryHash.put("product_version", "4");
            categoryHash.put("product_type", "TO");
        } else if (version == 3) {
            if (levelListElement.toString().matches(".*(06).*") && lang.toString().matches(".*(DAR|URD|LAT|IND|KIS|PAS).*")) {
                categoryHash.put("product_version", "3");
                categoryHash.put("product_type", "OSUB");
                categoryHash.put("level", "NA");
                categoryHash.put("duration", levelListElement.toString());
            } else if (levelListElement.toString().matches(".*(03|06).*") && lang.toString().matches(".*(KOR|VIE|FAR|TGL).*")) {
                categoryHash.put("product_version", "3");
                categoryHash.put("product_type", "OTB");
                categoryHash.put("level", "TOT");
                categoryHash.put("duration", levelListElement.toString());
            } else {
                categoryHash.put("product_version", "3");
                categoryHash.put("product_type", "ACB");
            }
        } else if (version == 2) {
            if (levelListElement.toString().matches(".*(06).*") && lang.toString().matches(".*(CYM|DAN|THA).*")) {
                categoryHash.put("product_version", "2");
                categoryHash.put("product_type", "OSUB");
                categoryHash.put("level", "NA");
                categoryHash.put("duration", levelListElement.toString());
            } else {
                categoryHash.put("product_version", "2");
                categoryHash.put("product_type", "FG");
            }
        }
        return categoryHash;
    }

    public static HashMap buildCategoryHashForUS(String lang, String levelListElement, String globalEdition, long version, HashMap categoryHash) {
        categoryHash.put("level", levelListElement);
        categoryHash.put("product_type", "ACB");
        categoryHash.put("edition", globalEdition);
        categoryHash.put("language", lang.toString());

        if (lang.toString().matches(".*(THA|CYM|DAN).*")) {
            categoryHash.put("product_version", "2");
            categoryHash.put("product_type", "FG");
        } else if (lang.toString().matches(".*(PAS|IND|KIS|URD|DAR).*") && levelListElement.toString().matches(".*(03|06|12).*")) {
            categoryHash.put("product_version", "3");
            categoryHash.put("product_type", "OSUB");
            categoryHash.put("level", "NA");
            categoryHash.put("duration", levelListElement.toString());
        } else if (lang.toString().matches(".*(LAT).*")) {
            categoryHash.put("product_version", "3");
            categoryHash.put("product_type", "ACB");
        } else if (levelListElement.toString().matches(".*(03|06|12).*")) {
            categoryHash.put("product_version", "3");
            categoryHash.put("product_type", "OTB");
            categoryHash.put("level", "TOT");
            categoryHash.put("duration", levelListElement.toString());
        } else {
            categoryHash.put("product_version", "4");
            categoryHash.put("product_type", "TO");
        }
        return categoryHash;
    }

    public static HashMap buildCategoryHashForDE(String lang, String levelListElement, String globalEdition, long version, HashMap categoryHash) {
        categoryHash.put("level", levelListElement);
        categoryHash.put("product_type", "ACB");
        categoryHash.put("edition", globalEdition);
        categoryHash.put("language", lang.toString());

        if (version == 3) {
            if (levelListElement.toString().matches(".*(06|12).*")) {
                categoryHash.put("product_version", "3");
                categoryHash.put("product_type", "OSUB");
                categoryHash.put("level", "NA");
                categoryHash.put("duration", levelListElement.toString());
            } else {
                categoryHash.put("product_version", "3");
                categoryHash.put("product_type", "ACB");
            }
        }
        if (version == 2) {
            if (levelListElement.toString().matches(".*(06|12).*")) {
                categoryHash.put("product_version", "2");
                categoryHash.put("product_type", "OSUB");
                categoryHash.put("level", "NA");
                categoryHash.put("duration", levelListElement.toString());
            } else {
                categoryHash.put("product_version", "2");
                categoryHash.put("product_type", "FG");
            }
        }
        return categoryHash;
    }

    public static float Round(float Rval, int Rpl) {
        float p = (float) Math.pow(10, Rpl);
        Rval = Rval * p;
        float tmp = Math.round(Rval);
        return (float) tmp / p;
    }

    public static String formatFloatPrice(String currency, Float price, String siteCode) {
        String floatPrice = price.toString();
        String[] priceArray = floatPrice.split("\\.");
        if (priceArray[1].length() < 2)
            floatPrice += "0";
        if (siteCode.equals("DE_WEBSITE"))
            floatPrice = floatPrice.replace(".", ",");
        return currency + floatPrice;
    }

}
