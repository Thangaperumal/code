package com.cqecom.cms.components.banner;


public class ProductLanguage implements Comparable<ProductLanguage>{

    private String code;
    private String name;
    private String longName;
    private Integer version;
    private String productLink;
    private Boolean visible;

    /*
    The full url to product page, for representation only
     */
    private String productUrl;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLongName() {
        return longName;
    }
    public void setLongName(String longName) {
        this.longName = longName;
    }
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCodeUpperCase() {
        return code == null ? null : code.toUpperCase();
    }
    
    public int compareTo(ProductLanguage lang) {
    	return this.longName.compareTo(lang.longName);
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
