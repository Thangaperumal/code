package com.cqecom.cms.components.sitewidemodule;

public class Link {
    private String url;
    private Boolean isLightbox;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isLightbox() {
        return isLightbox;
    }

    public void setLightbox(Boolean lightbox) {
        isLightbox = lightbox;
    }
}
