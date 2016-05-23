package com.erp.lead.helper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liferay.portal.kernel.util.PrefsPropsUtil;

public class LDAPHelper {
	public static final String LDAP_ENDPOINT_URL = "ldap.endPointUrl";
	public static final String LDAP_USERNAME = "ldap.username";
	public static final String LDAP_PASSWORD = "ldap.password";
	
	public static String validateLogin(String userName, String userPassword) {
	    Hashtable<String, String> env = new Hashtable<String, String>();
	    Map<String, String> conf;
    	conf = getLDAPConfiguration();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    //env.put(Context.PROVIDER_URL, "ldap://10.1.2.40:389/OU=Lister,OU=Consultants,OU=Corporate,DC=rosettastone,DC=local");
	    env.put(Context.PROVIDER_URL, conf.get("endPointUrl"));
	    //dc=rosettastone,dc=local

	    // To get rid of the PartialResultException when using Active Directory
	    env.put(Context.REFERRAL, "follow");

	    // Needed for the Bind (User Authorized to Query the LDAP server) 
	    env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL, conf.get("username"));
	    env.put(Context.SECURITY_CREDENTIALS, conf.get("password"));
	    DirContext ctx;
	    try {
	       ctx = new InitialDirContext(env);
	    } catch (NamingException e) {
	       throw new RuntimeException(e);
	    }

	    NamingEnumeration<SearchResult> results = null;

	    try {
	       SearchControls controls = new SearchControls();
	       controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search Entire Subtree
	       controls.setCountLimit(1);   //Sets the maximum number of entries to be returned as a result of the search
	       controls.setTimeLimit(5000); // Sets the time limit of these SearchControls in milliseconds

	       String searchString = "(&(objectCategory=user)(samaccountname=" + userName + "))";
	       results = ctx.search("", searchString, controls);
	       if (results.hasMore()) {

	           SearchResult result = (SearchResult) results.next();
	           Attributes attrs = result.getAttributes();
	           Attribute dnAttr = attrs.get("distinguishedName");
	           Attribute groups = attrs.get("memberof");
	           String dn = (String) dnAttr.get();
	           
	           // User Exists, Validate the Password
	           
	           env.put(Context.SECURITY_PRINCIPAL, dn);
	           env.put(Context.SECURITY_CREDENTIALS, userPassword);

	           new InitialDirContext(env); // Exception will be thrown on Invalid case

	           List<String> authorizedUsers = new ArrayList<String>();
	           authorizedUsers.add(conf.get("ldap.administrators"));
	           authorizedUsers.add(conf.get("ldap.endangered_language_program"));
	           authorizedUsers.add(conf.get("ldap.news_program"));
	           
	           for(String group:authorizedUsers){
	        	   if(groups.toString().toLowerCase().contains(group.toLowerCase())){
	        		   String name="";
	        		   	Pattern pattern = Pattern.compile("CN=[(a-zA-Z\\\\ ,)]*,");
	       				Matcher matcher = pattern.matcher(dn);
	       				while (matcher.find())
	       				{
	       					//System.out.println(matcher.group());
	       					name=matcher.group();
	       					name=name.substring(3,name.length()-1);
	       					name=name.replace("\\","");
	       				}
	        		   return "true;"+name;
	        	   }
	           }
	           return "unauthorized;";
	       } 
	       else 
	           return "false;";

	    } catch (AuthenticationException e) { // Invalid Login

	        return "false;";
	    } catch (NameNotFoundException e) { // The base context was not found.

	        return "false;";
	    } catch (SizeLimitExceededException e) {
	        throw new RuntimeException("LDAP Query Limit Exceeded, adjust the query to bring back less records", e);
	    } catch (NamingException e) {
	       throw new RuntimeException(e);
	    } finally {

	       if (results != null) {
	          try { results.close(); } catch (Exception e) { /* Do Nothing */ }
	       }

	       if (ctx != null) {
	          try { ctx.close(); } catch (Exception e) { /* Do Nothing */ }
	       }
	    }
	}
	
	private static Map<String, String> getLDAPConfiguration() {
		Map<String, String> conf = new HashMap<String, String>();
		try {
			
			conf.put("endPointUrl", PrefsPropsUtil.getString(LDAP_ENDPOINT_URL));
			conf.put("username", PrefsPropsUtil.getString(LDAP_USERNAME));
			conf.put("password", PrefsPropsUtil.getString(LDAP_PASSWORD));
			conf.put("ldap.administrators", PrefsPropsUtil.getString("ldap.administrators"));
			conf.put("ldap.endangered_language_program", PrefsPropsUtil.getString("ldap.endangered_language_program"));
			conf.put("ldap.news_program", PrefsPropsUtil.getString("ldap.news_program"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error in fetching configuration from properties"+ e);
		}
		return conf;
	}
	
}