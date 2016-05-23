package com.cqecom.cms.model;

public class Configuration {

    /**
    * The path constants are absolute paths. 
    * Use Node.getSession().nodeExists(path) to check whether the node exists or
    * Node.getSession().getNode() to get the node via absolute path
    */

    // Path to node that stores global reference lists (like languages)
    public static final String GLOBAL_PATH = "/content/global";
    // OBSOLETE - Path to local languages
    public static final String LANGUAGE_PATH = "/content/cqecomcom/languages";
    // Path to folder with components
    public static final String COMPONENTS_PATH = "/apps/cqecom/components/";
    // Path to javascript files for components
    public static final String JS_COMPONENTS_PATH = "/etc/designs/cqecom/javascript/components/";
    // Path to css files for components
    public static final String CSS_COMPONENTS_PATH = "/etc/designs/cqecom/styles/components/";
    // Path to javascript files for pages
    public static final String JS_PAGES_PATH = "/etc/designs/cqecom/javascript/pages/";
    // Path to css files for pages
    public static final String CSS_PAGES_PATH = "/etc/designs/cqecom/styles/pages/";
    // Path to javascript files for utils
    public static final String JS_UTIL_PATH = "/etc/designs/cqecom/javascript/util/";
}
