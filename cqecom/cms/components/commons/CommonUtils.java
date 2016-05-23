package com.cqecom.cms.components.commons;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;

import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {
	
	private static final Logger log = LoggerFactory
			.getLogger(CommonUtils.class);

	public static Session getCurrentSession(SlingHttpServletRequest request) {
		return request.getResourceResolver().adaptTo(Session.class);
	}

	// Format price based on the currency
	public static String formatPrice(String site, String currency,
			Integer listPrice, boolean escape) {
		String formattedPrice = "";
		String num_after_format = "";
		if (site.equals("en_US") || site.equals("uk"))
			formattedPrice = currency + listPrice.toString() + ".00";
		else if (site.equals("de_DE") || site.equals("it") || site.equals("en_GB") || site.equals("fr_FR") || site.equals("es_ES"))
			formattedPrice = currency + " " + listPrice.toString() + ",00";
		else if (site.equals("ko_KR")) {
			NumberFormat ncf = NumberFormat.getInstance(Locale.KOREAN);
			try {
				num_after_format = ncf.format(listPrice.doubleValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			formattedPrice = currency + " " + num_after_format;
		}
		return (escape ? Matcher.quoteReplacement(formattedPrice)
				: formattedPrice);
	}

	// Format price and return non-precisioned values
	public static String formatACPrice(String site, String currency,
			Integer listPrice, boolean escape) {
		String formattedPrice = "";
		String num_after_format = "";
		if (site.equals("en_US") || site.equals("uk"))
			formattedPrice = currency + listPrice.toString();
		else if (site.equals("de") || site.equals("it") || site.equals("en_GB") || site.equals("fr_FR") || site.equals("es_ES"))
			formattedPrice = currency + " " + listPrice.toString();
		else if (site.equals("kr")) {
			NumberFormat ncf = NumberFormat.getInstance(Locale.KOREAN);
			try {
				num_after_format = ncf.format(listPrice.doubleValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			formattedPrice = currency + " " + num_after_format;
		}
		return (escape ? Matcher.quoteReplacement(formattedPrice)
				: formattedPrice);
	}

	public static void logException(Exception exception, Logger logger) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.print(" [ ");
		pw.print(exception.getClass().getName());
		pw.print(" ] ");
		pw.print(exception.getMessage());
		exception.printStackTrace(pw);
		logger.error(sw.toString());

	}

	public static String getGlobalEdition(String currentPage) {
		String globalEdition = "";
		if (currentPage.matches(".*(learn|lerne)-.*|(.*)-(lernen)")) {
			globalEdition = "PE";
		} else if (currentPage.matches(".*(homeschool)-.*")) {
			globalEdition = "HS";
		}
		return globalEdition;
	}

	public static boolean isProduction() {
		boolean isProduction = false;
		String tier = System.getProperty("com.cqecom.webdev.tier");
		if (tier != null && tier.equals("production"))
			isProduction = true;
		else
			isProduction = false;
		return isProduction;
	}

	public static GlobalSettings getGlobalSettings(SlingHttpServletRequest request,String resourcePath){
        GlobalSettingsReader globalSettingsReader =  GlobalSettingsReader.getInstance();
        try {
			HashMap<String,GlobalSettings> settings = globalSettingsReader.findAllSettings(getCurrentSession(request));
			GlobalSettings globalSettings = CommonUtils.getGlobalSettings(resourcePath, settings);
			return globalSettings;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error", e);
		}
		return null;
	}
	
	
	public static GlobalSettings getGlobalSettings(
			String requestPath,
			HashMap<String, GlobalSettings> globalSettingsMap) {		

		// iterate on the sites and get the corresponding settings
        for (GlobalSettings gs : globalSettingsMap.values()) {
            if (gs != null && StringUtils.isNotBlank(gs.getGlobalContentUrl()) && requestPath.contains(gs.getGlobalContentUrl())) {
                return gs;
            }
        }
		return null;
	}
	
        public static enum ProductLevel {
                L1, L2, L3, L4, L5, S2, S3, S5, U1, TOT 
        }


}
