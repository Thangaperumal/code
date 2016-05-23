package com.cqecom.cms.components.homepageBanner;

public class BannerImage {

    private String imageReference;

    private int width;

    private int height;

    private Boolean useThisBanner;

    private Boolean useFirstImageOverlay;

    private Boolean useFirstBannerImage;

    private BannerImageOverlay imageOverlay;

    public String getImageReference() {
        return imageReference;
    }

    public void setImageReference(String imageReference) {
        this.imageReference = imageReference;
    }

    public BannerImageOverlay getImageOverlay() {
        return imageOverlay;
    }

    public void setImageOverlay(BannerImageOverlay imageOverlay) {
        this.imageOverlay = imageOverlay;
    }

    public Boolean getUseFirstImageOverlay() {
        return useFirstImageOverlay;
    }

    public void setUseFirstImageOverlay(Boolean useFirstImageOverlay) {
        this.useFirstImageOverlay = useFirstImageOverlay;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Boolean getUseFirstBannerImage() {
        return useFirstBannerImage;
    }

    public void setUseFirstBannerImage(Boolean useFirstBannerImage) {
        this.useFirstBannerImage = useFirstBannerImage;
    }

    public Boolean getUseThisBanner() {
        return useThisBanner;
    }

    public void setUseThisBanner(Boolean useThisBanner) {
        this.useThisBanner = useThisBanner;
    }
}
