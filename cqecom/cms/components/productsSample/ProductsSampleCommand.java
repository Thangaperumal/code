package com.cqecom.cms.components.productsSample;

public class ProductsSampleCommand {

	 private String edition;
	 private String level;
	 private String language;
	 private String localization;
	 private String productType;
	 private String prodVersion;	 
	 private String rootNav;
	 private Boolean subNav;
	 private Boolean richText;
	 private String text;
	 
	public Boolean getSubNav() {
		return subNav;
	}
	public void setSubNav(Boolean subNav) {
		this.subNav = subNav;
	}
	public Boolean getRichText() {
		return richText;
	}
	public void setRichText(Boolean richText) {
		this.richText = richText;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLocalization() {
		return localization;
	}
	public void setLocalization(String localization) {
		this.localization = localization;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProdVersion() {
		return prodVersion;
	}
	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
	}
	public boolean isSubNav() {
		return subNav;
	}
	public void setSubNav(boolean subNav) {
		this.subNav = subNav;
	}
	public String getRootNav() {
		return rootNav;
	}
	public void setRootNav(String rootNav) {
		this.rootNav = rootNav;
	}
}