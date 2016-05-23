package com.cqecom.cms.services.priceCacher;

import org.slf4j.Logger;


/**
 * Cache prices for the products
 */
public interface PriceCacher {

    public String getString();
  
    public javax.jcr.Node cacheItemPrices(javax.jcr.Node promoCode, javax.jcr.Node vertical, javax.jcr.Node langCode, javax.jcr.Node level, String lang, String[] levelList, String globalEdition, String siteCode, String globalCurrency, String paStatus, Logger log ) throws Exception;
    public javax.jcr.Node cacheQBItemPrices(javax.jcr.Node languageNode, javax.jcr.Node productTypeNode, javax.jcr.Node verticalNode, javax.jcr.Node versionNode, javax.jcr.Node levelNode, String lang, String productType, String[] levelList, String globalEdition, String siteCode, String paStatus, long version, String promo) throws Exception;
}
