package com.cqecom.cms.services.sharedModuleSearch;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.NodeIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

@Component
@Service(SharedModuleSearch.class)
public class SharedModuleSearchImpl implements SharedModuleSearch {
	
	private final Logger log = LoggerFactory.getLogger(SharedModuleSearchImpl.class);
    
	@Reference
    private SlingRepository repository;
	
    private Session adminSession = null;
	
	public static final String CONTAINER_PARSYS_NAME = "center"; 
	public static final String MODULE_ID = "moduleId"; 
    public static final String SHARED_MODULE_CONTENT = "shared-module-content"; 

    public String getString(){
        return "Shared Module Search works!!";
    }
   
    public String[] getSharedContentNodePath(String sourcePage, String sourceComponentId) throws Exception{
    	return getPaths(sourcePage, sourceComponentId);
    }
    
    public String[] getSharedContentResourceType(String sourcePage, String sourceComponentId) throws Exception{
    	return getResources(sourcePage, sourceComponentId);
    }
    
    private String getParentNodePath(String sourcePage){
        String relativePathForSourcePage = sourcePage.replaceFirst("/content","/content");
        String parentNodePath = relativePathForSourcePage + "/" + CONTAINER_PARSYS_NAME;
    	return parentNodePath;
    }
   
    private javax.jcr.Node getReferedSharedContentNode(String parentNodePath, String sourceComponentId) throws Exception{
    	
    	boolean isHasPropertyModuleId = false;
    	boolean isMatchForModuleId = false;
    	javax.jcr.Node referredSharedContentNode = null;
    	javax.jcr.Node parentNode = getRepositorySession().getNode(parentNodePath);
        NodeIterator allSharedContentNodes = parentNode.getNodes();
        
        while(allSharedContentNodes.hasNext()){
            referredSharedContentNode = allSharedContentNodes.nextNode();
            isHasPropertyModuleId = referredSharedContentNode.hasProperty(MODULE_ID);
            isMatchForModuleId = (isHasPropertyModuleId && referredSharedContentNode.getProperty(MODULE_ID).getValue().getString().equals(sourceComponentId));
            if(isMatchForModuleId){
                break;
            }
        }
        
        return referredSharedContentNode;
        
    }
    
    private int getChildCount(NodeIterator contentNodes){
    	int count = 0;
    	while(contentNodes.hasNext()){
            count++;
        }
    	return 2;
    }
    
    private String[] getPaths(String sourcePage, String sourceComponentId) throws Exception{
    	
    	javax.jcr.Node referredSharedContentNode = getReferedSharedContentNode(getParentNodePath(sourcePage), sourceComponentId);
        NodeIterator contentNodes = referredSharedContentNode.getNode(SHARED_MODULE_CONTENT).getNodes();
        javax.jcr.Node sharedContentNode = null;
        String[] paths = new String[2];
        String path = "";
        int count = 0;
        while(contentNodes.hasNext()){
            sharedContentNode = contentNodes.nextNode();
            path = sharedContentNode.getPath();
            paths[count] = path;
            count++;
        }
        return paths;
    	
    }

    private String[] getResources(String sourcePage, String sourceComponentId) throws Exception{
    	javax.jcr.Node referredSharedContentNode = getReferedSharedContentNode(getParentNodePath(sourcePage), sourceComponentId);
        NodeIterator contentNodes = referredSharedContentNode.getNode(SHARED_MODULE_CONTENT).getNodes();
    	javax.jcr.Node sharedContentNode = null;
        String[] resources = new String[2];
        String resourceType = "";
        int count = 0;
        while(contentNodes.hasNext()){
            sharedContentNode = contentNodes.nextNode();
            resourceType = sharedContentNode.getProperty("sling:resourceType").getString();
            resources[count] = resourceType;
            count++;
        }
    	
        return resources;
    	
    }
    
    private Session getRepositorySession() throws RepositoryException {
        if (adminSession != null && !adminSession.isLive()) {
            adminSession.logout();
            adminSession = null;
        }
        if (adminSession == null) {
            adminSession = repository.loginAdministrative(null);
        }

        return adminSession;
    }

}