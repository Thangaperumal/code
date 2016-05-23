package com.cqecom.cms.components.banner;

import org.apache.sling.api.resource.ValueMap;

public class Offer {

    private String sku;
    private String text;
    private ValueMap nodeProperties;
    private BannerType bannerType;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageRef() {
        if (bannerType != null) {
            return nodeProperties.get("imagePathFor" + bannerType.name(), String.class);
        }
        return null;
    }

    public void setNodeProperties(ValueMap nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    public void setBannerType(BannerType bannerType) {
        this.bannerType = bannerType;
    }
}
