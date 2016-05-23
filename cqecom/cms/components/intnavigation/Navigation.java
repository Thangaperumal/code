package com.cqecom.cms.components.intnavigation;

import java.util.List;

public class Navigation {

    public enum Level {
        CURRENT, DESCENDANTS, NONE
    }

    private Level level;

    private String stripStyle;
    private String headerText;
    private String menuStyle;
    private boolean hideParent;
    private String parentTabName;
    private boolean hideCurrentPage;
    private String tabName;

    private List<NavigationLink> links;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getStripStyle() {
        return stripStyle;
    }

    public void setStripStyle(String stripStyle) {
        this.stripStyle = stripStyle;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getMenuStyle() {
        return menuStyle;
    }

    public void setMenuStyle(String menuStyle) {
        this.menuStyle = menuStyle;
    }

    public List<NavigationLink> getLinks() {
        return links;
    }

    public void setLinks(List<NavigationLink> links) {
        this.links = links;
    }

    public boolean isHideParent() {
        return hideParent;
    }

    public void setHideParent(boolean hideParent) {
        this.hideParent = hideParent;
    }

    public boolean isHideCurrentPage() {
        return hideCurrentPage;
    }

    public void setHideCurrentPage(boolean hideCurrentPage) {
        this.hideCurrentPage = hideCurrentPage;
    }

    public String getParentTabName() {
        return parentTabName;
    }

    public void setParentTabName(String parentTabName) {
        this.parentTabName = parentTabName;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}
