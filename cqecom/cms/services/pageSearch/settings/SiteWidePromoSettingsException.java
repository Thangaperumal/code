package com.cqecom.cms.services.pageSearch.settings;

/*
 * Thrown if global sitewide promotion settings (set in repository) for sites are incorrect
 */
public class SiteWidePromoSettingsException extends Exception {

    public SiteWidePromoSettingsException() {
    }

    public SiteWidePromoSettingsException(String s) {
        super(s);
    }

    public SiteWidePromoSettingsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SiteWidePromoSettingsException(Throwable throwable) {
        super(throwable);
    }
}
