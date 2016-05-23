package com.cqecom.cms.components.homepageBanner;

import com.cqecom.cms.components.htmltext.HtmlText;

public class BannerImageOverlay {
    
    private String overlayImageReference;
    
    private int overlayImagePosX;
    
    private int overlayImagePosY;
    
    private String promoImageReference;
    
    private String promoHoverImageReference;
    
    private int promoImagePosX;
    
    private int promoImagePosY;
    
    private String contentBlockSourcePage;
    
    private String contentBlockId;
    
    private int contentBlockPosX;
    
    private int contentBlockPosY;
    
    private HtmlText text;

    public String getOverlayImageReference() {
        return overlayImageReference;
    }

    public void setOverlayImageReference(String overlayImageReference) {
        this.overlayImageReference = overlayImageReference;
    }

    public int getOverlayImagePosX() {
        return overlayImagePosX;
    }

    public void setOverlayImagePosX(int overlayImagePosX) {
        this.overlayImagePosX = overlayImagePosX;
    }

    public int getOverlayImagePosY() {
        return overlayImagePosY;
    }

    public void setOverlayImagePosY(int overlayImagePosY) {
        this.overlayImagePosY = overlayImagePosY;
    }

    public String getPromoImageReference() {
        return promoImageReference;
    }

    public void setPromoImageReference(String promoImageReference) {
        this.promoImageReference = promoImageReference;
    }

    public int getPromoImagePosX() {
        return promoImagePosX;
    }

    public void setPromoImagePosX(int promoImagePosX) {
        this.promoImagePosX = promoImagePosX;
    }

    public int getPromoImagePosY() {
        return promoImagePosY;
    }

    public void setPromoImagePosY(int promoImagePosY) {
        this.promoImagePosY = promoImagePosY;
    }

    public String getContentBlockSourcePage() {
        return contentBlockSourcePage;
    }

    public void setContentBlockSourcePage(String contentBlockSourcePage) {
        this.contentBlockSourcePage = contentBlockSourcePage;
    }

    public String getContentBlockId() {
        return contentBlockId;
    }

    public void setContentBlockId(String contentBlockId) {
        this.contentBlockId = contentBlockId;
    }

    public String getPromoHoverImageReference() {
        return promoHoverImageReference;
    }

    public void setPromoHoverImageReference(String promoHoverImageReference) {
        this.promoHoverImageReference = promoHoverImageReference;
    }

    public int getContentBlockPosX() {
        return contentBlockPosX;
    }

    public void setContentBlockPosX(int contentBlockPosX) {
        this.contentBlockPosX = contentBlockPosX;
    }

    public int getContentBlockPosY() {
        return contentBlockPosY;
    }

    public void setContentBlockPosY(int contentBlockPosY) {
        this.contentBlockPosY = contentBlockPosY;
    }

    public HtmlText getText() {
        return text;
    }

    public void setText(HtmlText text) {
        this.text = text;
    }
}
