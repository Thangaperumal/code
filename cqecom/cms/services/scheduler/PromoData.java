package com.cqecom.cms.services.scheduler;

import java.util.Date;

public class PromoData {

    private String promoPagePath;

    private Date lastModified;

    public String getPromoPagePath() {
        return promoPagePath;
    }

    public void setPromoPagePath(String promoPagePath) {
        this.promoPagePath = promoPagePath;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
