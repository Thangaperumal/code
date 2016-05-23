package com.cqecom.cms.components.mainHeader;

import org.apache.commons.lang.StringUtils;


public class MainHeader {

    private String headerWidth;
    private String headerHeight;

    private String topStripImageReference;

    private String logoImageReference;

    private String logoLink;

    private SharedModuleBlock utilNav;

    private SharedModuleBlock topNavigation;

    private SharedModuleBlock tfn;

    private SharedModuleBlock button;

    private SharedModuleBlock subNav;

    public String getHeaderWidth() {
        return headerWidth;
    }

    public void setHeaderWidth(String headerWidth) {
        this.headerWidth = headerWidth;
    }

    public String getHeaderHeight() {
        return headerHeight;
    }

    public void setHeaderHeight(String headerHeight) {
        this.headerHeight = headerHeight;
    }

    public String getTopStripImageReference() {
        return topStripImageReference;
    }

    public void setTopStripImageReference(String topStripImageReference) {
        this.topStripImageReference = topStripImageReference;
    }

    public String getLogoImageReference() {
        return logoImageReference;
    }

    public void setLogoImageReference(String logoImageReference) {
        this.logoImageReference = logoImageReference;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public SharedModuleBlock getUtilNav() {
        return utilNav;
    }

    public void setUtilNav(SharedModuleBlock utilNav) {
        this.utilNav = utilNav;
    }

    public SharedModuleBlock getTopNavigation() {
        return topNavigation;
    }

    public void setTopNavigation(SharedModuleBlock topNavigation) {
        this.topNavigation = topNavigation;
    }

    public SharedModuleBlock getTfn() {
        return tfn;
    }

    public void setTfn(SharedModuleBlock tfn) {
        this.tfn = tfn;
    }


    public SharedModuleBlock getButton() {
        return button;
    }

    public void setButton(SharedModuleBlock button) {
        this.button = button;
    }

    public SharedModuleBlock getSubNav() {
        return subNav;
    }

    public void setSubNav(SharedModuleBlock subNav) {
        this.subNav = subNav;
    }

    public Boolean isEmpty() {
        return StringUtils.isBlank(headerHeight) || StringUtils.isBlank(headerWidth);
    }
}
