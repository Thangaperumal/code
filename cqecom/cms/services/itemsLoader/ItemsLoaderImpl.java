package com.cqecom.cms.services.itemsLoader;

import javax.jcr.NodeIterator;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * ServiceImplementation class for setting the creating/setting the priceApprover status.
 * When the price_approver task is run in EC, a curl script gets executed.
 * Example : curl -u <admin>:<Password> http://lumpy.lan.flt:4603/content/cqecomcom/en/itemsLoader.html?timestamp=Fri%20Feb%2004%2002:04:55%20-0500%202011
 * The status property of the priceApprover node is updated as per the timestamp value received as a parameter.
 */
@Component
@Service(ItemsLoader.class)
public class ItemsLoaderImpl implements ItemsLoader {
	
	private final Logger log = LoggerFactory.getLogger(ItemsLoaderImpl.class);
	
	/*
	 * Returns the the priceApprover node inside content/Ecitems/(de|en)if exists, 
	 * Else a new priceApprover node is created.  
	 */
	private javax.jcr.Node getPriceApproverNode(javax.jcr.Node parentNode) throws Exception{
	    javax.jcr.Node priceApprover = null;
		if(parentNode.hasNode("priceApprover")){
			priceApprover = parentNode.getNode("priceApprover");
			log.info("ITEM_LOADER ==> The priceApprover node " + priceApprover.getPath() +"already exists.");
		}else{
			priceApprover = parentNode.addNode("priceApprover", "nt:unstructured");
			log.info("ITEM_LOADER ==> The priceApprover node does not exists. So, created a new priceAprrover node. " + priceApprover.getPath());
		}
		return priceApprover;
	}

	/*
	 * Sets the status property value for the priceApprover node as per the timestamp received.
	 */
	private void setStatusValue(javax.jcr.Node priceApprover, String approverTimeStamp) throws Exception{
		if(priceApprover != null){
			priceApprover.setProperty("status", approverTimeStamp); 
			priceApprover.getSession().save();
			log.info("ITEM_LOADER ==> The status for the priceapprover node " + priceApprover.getPath() + " has been updated to : "+ approverTimeStamp);
		}
	}

	/*
	 * Function to set the status property of priceApprover node. 
	 */
	public void setPriceApproverStatus( javax.jcr.Node rootNode, 
										String globalSite, 
										String approverTimeStamp,
										String parentNodePath) throws Exception{
		
	    javax.jcr.Node itemsNode = null;
	    javax.jcr.Node priceApprover = null;
	    itemsNode = rootNode.getNode(parentNodePath + globalSite);
		if (itemsNode != null) {
			priceApprover = getPriceApproverNode(itemsNode);
			setStatusValue(priceApprover, approverTimeStamp);
		}
	}

	/*
	 * This function is required for the quick-buy products list component.
	 * To avoid making EC calls every time we want to display the prices in the Qiuck-buy landing pages we store the price/sku values as properties of crx nodes.
	 * So when the price_approver task is run, we need to reload the price/sku values.
	 * For this to happen we need to delete the pages inside content/cqecomcom/en/cache/prices and content/cqecomde/de/cache/prices
	 * This will lead to a desirable 404 error for the pages inside content/cqecomcom/en/cache/prices or content/cqecomde/de/cache/prices.
	 * It will further lead to a new request to reload the price/sku values in side the nodes.
	 * This will also limit the content inside content/cqecomcom/en/cache/prices as only the content for the landing pages that are hit/new/latest will be generated.
	 */
	public void deletePricePages(javax.jcr.Node rootNode, 
								 String parentPath) throws Exception{
		
	    javax.jcr.Node pricesNode = null;
	    javax.jcr.Node test = null;
	    NodeIterator promoNodes = null;
	    
	    String pricesNodePath = parentPath + "/cache/prices";
		log.info("ITEM_LOADER ==> Contaniner node for the pages : " + pricesNodePath );
		pricesNode = rootNode.getNode(pricesNodePath);
	    if(pricesNode != null){
	    	promoNodes = pricesNode.getNodes();
	    	
	    	while(promoNodes.hasNext()){
	    		test = promoNodes.nextNode();
	    		if((test != null) && (!test.getName().equals("jcr:content"))){
	    			log.info("ITEM_LOADER ==> Node being deleted : " + test.getName());
	    			test.remove();
	    		}
            }
	    	pricesNode.getSession().save();
		}
	    log.info("ITEM_LOADER ==> The content nodes inside " + pricesNodePath + "have been deleted.");
	}
}
