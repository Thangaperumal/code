package com.cqecom.cms.components.homepageBanner;

import java.util.List;

public class HomepageBanner {

    private boolean showBorder;

    private String backgroundImageReference;

    private String margin;

    private String padding;

    private String height;

    private String width;

    private boolean useRotation;

    private int rotationInterval;

    private boolean useSitewidePromo;

    private List<BannerImage> images;

    public boolean isShowBorder() {
        return showBorder;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    public String getBackgroundImageReference() {
        return backgroundImageReference;
    }

    public void setBackgroundImageReference(String backgroundImageReference) {
        this.backgroundImageReference = backgroundImageReference;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public List<BannerImage> getImages() {
        return images;
    }

    public void setImages(List<BannerImage> images) {
        this.images = images;
    }

    public int getRotationInterval() {
        return rotationInterval;
    }

    public void setRotationInterval(int rotationInterval) {
        this.rotationInterval = rotationInterval;
    }

    public boolean isUseRotation() {
        return useRotation;
    }

    public void setUseRotation(boolean useRotation) {
        this.useRotation = useRotation;
    }

    public boolean isUseSitewidePromo() {
        return useSitewidePromo;
    }

    public void setUseSitewidePromo(boolean useSitewidePromo) {
        this.useSitewidePromo = useSitewidePromo;
    }
}
