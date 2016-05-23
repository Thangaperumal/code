package com.cqecom.cms.components.catalogproduct;

public class CatalogProductCommand {
	private String edition;
	private String level;
	private String language;
	private String localization;
	private String productType;
	private String prodVersion;
	private String acLevel;
	private boolean isRichText;
	private boolean isFeatured;
	private boolean isBest;
	private boolean isGreat;
	private boolean isLightBox;
	
	public boolean isLightBox() {
		return isLightBox;
	}
	
	public void setLightBox(boolean isLightBox) {
		this.isLightBox = isLightBox;
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
	public String getAcLevel() {
		return acLevel;
	}
	public void setAcLevel(String acLevel) {
		this.acLevel = acLevel;
	}
	public boolean isRichText() {
		return isRichText;
	}
	public void setRichText(boolean isRichText) {
		this.isRichText = isRichText;
	}
	public boolean isFeatured() {
		return isFeatured;
	}
	public void setFeatured(boolean isFeatured) {
		this.isFeatured = isFeatured;
	}
	public boolean isBest() {
		return isBest;
	}
	public void setBest(boolean isBest) {
		this.isBest = isBest;
	}
	public boolean isGreat() {
		return isGreat;
	}
	public void setGreat(boolean isGreat) {
		this.isGreat = isGreat;
	}


}
