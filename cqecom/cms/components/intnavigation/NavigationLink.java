package com.cqecom.cms.components.intnavigation;

public class NavigationLink {
    private String label;
    private String url;
    private Boolean isCurrent = Boolean.FALSE;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
    }
}
