package com.cqecom.cms.components.tabbedText;

import java.util.List;

public class TabbedText {

    private String headerText;

    private String headerColor;

    private String description;

    private String activeTabBackgroundColor;

    private String activeTabTextColor;

    private String inactiveTabBackgroundColor;

    private String inactiveTabTextColor;

    private String tabBorderColor;

    private Long tabContentHeight;

    private String activeArrowImage;

    private String inactiveArrowImage;

    private List<Tab> tabs;

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActiveTabBackgroundColor() {
        return activeTabBackgroundColor;
    }

    public void setActiveTabBackgroundColor(String activeTabBackgroundColor) {
        this.activeTabBackgroundColor = activeTabBackgroundColor;
    }

    public String getActiveTabTextColor() {
        return activeTabTextColor;
    }

    public void setActiveTabTextColor(String activeTabTextColor) {
        this.activeTabTextColor = activeTabTextColor;
    }

    public String getInactiveTabBackgroundColor() {
        return inactiveTabBackgroundColor;
    }

    public void setInactiveTabBackgroundColor(String inactiveTabBackgroundColor) {
        this.inactiveTabBackgroundColor = inactiveTabBackgroundColor;
    }

    public String getInactiveTabTextColor() {
        return inactiveTabTextColor;
    }

    public void setInactiveTabTextColor(String inactiveTabTextColor) {
        this.inactiveTabTextColor = inactiveTabTextColor;
    }

    public String getTabBorderColor() {
        return tabBorderColor;
    }

    public void setTabBorderColor(String tabBorderColor) {
        this.tabBorderColor = tabBorderColor;
    }

    public Long getTabContentHeight() {
        return tabContentHeight;
    }

    public void setTabContentHeight(Long tabContentHeight) {
        this.tabContentHeight = tabContentHeight;
    }

    public String getActiveArrowImage() {
        return activeArrowImage;
    }

    public void setActiveArrowImage(String activeArrowImage) {
        this.activeArrowImage = activeArrowImage;
    }

    public String getInactiveArrowImage() {
        return inactiveArrowImage;
    }

    public void setInactiveArrowImage(String inactiveArrowImage) {
        this.inactiveArrowImage = inactiveArrowImage;
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }
}
