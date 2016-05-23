package com.cqecom.cms.components.intnavigation.corporate;

import java.util.List;

import com.cqecom.cms.components.intnavigation.NavigationLink;

public class NavigationCorporate {

    public enum Level {
        CURRENT, DESCENDANTS, NONE
    }

    private Level level;
    private boolean hideParent = false;
    private String parentTabName;
    private boolean hideCurrentPage = false;
    private String tabName;

    private List<NavigationLink> links;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
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
