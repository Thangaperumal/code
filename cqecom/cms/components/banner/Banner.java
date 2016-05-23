package com.cqecom.cms.components.banner;

import java.util.Set;

public class Banner {

    private BannerType bannerType;
    private String languagePath;
    private String bannersPath;
    private String bannerImageRef;
    private String languageImageRef;
    private String languageDropdownReference;
    private String lowerBarReference;
    private String languagePopupReference;
    private String bannerText;
    private String guaranteeText;
    private Integer topImageHeight;
    private Integer bottomImageHeight;
    private Integer dropdownX;
    private Integer dropdownY;
    private Integer dropdownWidth;
    private String dropdownAlignment;
    private Boolean useProductLink;
    private Set<String> languageCodes;

    public BannerType getBannerType() {
        return bannerType;
    }

    public void setBannerType(BannerType bannerType) {
        this.bannerType = bannerType;
    }

    public String getLanguagePath() {
        return languagePath;
    }

    public void setLanguagePath(String languagePath) {
        this.languagePath = languagePath;
    }

    public String getBannersPath() {
        return bannersPath;
    }

    public void setBannersPath(String bannersPath) {
        this.bannersPath = bannersPath;
    }

    public String getBannerImageRef() {
        return bannerImageRef;
    }

    public void setBannerImageRef(String bannerImageRef) {
        this.bannerImageRef = bannerImageRef;
    }

    public String getLanguageImageRef() {
        return languageImageRef;
    }

    public void setLanguageImageRef(String languageImageRef) {
        this.languageImageRef = languageImageRef;
    }

    public String getLanguageDropdownReference() {
        return languageDropdownReference;
    }

    public void setLanguageDropdownReference(String languageDropdownReference) {
        this.languageDropdownReference = languageDropdownReference;
    }

    public String getLowerBarReference() {
        return lowerBarReference;
    }

    public void setLowerBarReference(String lowerBarReference) {
        this.lowerBarReference = lowerBarReference;
    }

    public String getLanguagePopupReference() {
        return languagePopupReference;
    }

    public void setLanguagePopupReference(String languagePopupReference) {
        this.languagePopupReference = languagePopupReference;
    }

    public String getBannerText() {
        return bannerText;
    }

    public void setBannerText(String bannerText) {
        this.bannerText = bannerText;
    }

    public String getGuaranteeText() {
        return guaranteeText;
    }

    public void setGuaranteeText(String guaranteeText) {
        this.guaranteeText = guaranteeText;
    }

    public Number getTopImageHeight() {
        return topImageHeight;
    }

    public void setTopImageHeight(Integer topImageHeight) {
        this.topImageHeight = topImageHeight;
    }

    public Number getBottomImageHeight() {
        return bottomImageHeight;
    }

    public void setBottomImageHeight(Integer bottomImageHeight) {
        this.bottomImageHeight = bottomImageHeight;
    }

    public Number getDropdownX() {
        return dropdownX;
    }

    public void setDropdownX(Integer dropdownX) {
        this.dropdownX = dropdownX;
    }

    public Number getDropdownY() {
        return dropdownY;
    }

    public void setDropdownY(Integer dropdownY) {
        this.dropdownY = dropdownY;
    }

	public Integer getDropdownWidth() {
		return dropdownWidth;
	}

	public void setDropdownWidth(Integer dropdownWidth) {
		this.dropdownWidth = dropdownWidth;
	}

	public String getDropdownAlignment() {
		return dropdownAlignment;
	}

	public void setDropdownAlignment(String dropdownAlignment) {
		this.dropdownAlignment = dropdownAlignment;
	}
	
	public String getDropdownLeft() {
		Integer offset = dropdownWidth - 544; // 544 is the width of the popup
		if (this.dropdownAlignment.equalsIgnoreCase("right")) return "-" + offset.toString() + "px";
		if (this.dropdownAlignment.equalsIgnoreCase("left")) return "0px";
		
		return "";
	}

    public Boolean getUseProductLink() {
        return useProductLink;
    }

    public void setUseProductLink(Boolean useProductLink) {
        this.useProductLink = useProductLink;
    }

     public Set<String> getLanguageCodes() {
        return languageCodes;
    }

    public void setLanguageCodes(Set<String> languageCodes) {
        this.languageCodes = languageCodes;
    }
}
