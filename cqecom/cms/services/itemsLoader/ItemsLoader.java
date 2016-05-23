package com.cqecom.cms.services.itemsLoader;

/*
 * Service Interface for setting the creating/setting the priceApprover status.
 * When the price_approver task is run in EC, a curl script gets executed.
 * Example : curl -u <admin>:<Password> http://lumpy.lan.flt:4603/content/cqecomcom/en/itemsLoader.html?timestamp=Fri%20Feb%2004%2002:04:55%20-0500%202011
 * The status property of the priceApprover node is updated as per the timestamp value received as a parameter.
 */
public interface ItemsLoader {
	
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
								 String parentPath) throws Exception;
	
	/*
	 * Function to set the status property of priceApprover node. 
	 */
	public void setPriceApproverStatus(javax.jcr.Node rootNode, 
									   String globalSite, 
									   String approverTimeStamp,
									   String parentNodePath) throws Exception;
	
}
