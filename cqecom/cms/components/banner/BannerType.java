package com.cqecom.cms.components.banner;

public enum BannerType {

    SimpleOfferOnBanner, SimpleOfferBelowBanner, LanguageSensitive;

    public boolean getLanguageSensitive() {
        return this.equals(LanguageSensitive);
    }
}
