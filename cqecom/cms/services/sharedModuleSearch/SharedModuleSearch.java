package com.cqecom.cms.services.sharedModuleSearch;

/**
 * Finds the shared module details for the Shared Module Reference component
 */

public interface SharedModuleSearch {

    public String getString();

    public String[] getSharedContentNodePath(String sourcePage, String moduleId) throws Exception;
    
    public String[] getSharedContentResourceType(String sourcePage, String moduleId) throws Exception;
    
}