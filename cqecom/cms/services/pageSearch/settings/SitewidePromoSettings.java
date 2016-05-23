package com.cqecom.cms.services.pageSearch.settings;

/*
 * Sitewide promotions settings
 */
public class SitewidePromoSettings {

    //site name
    private String name;

    //timezone offset for the site
    private Integer timezoneOffset;    

    //path to site's pages
    private String sitePagesPath;

    //path to sitewide promo pages
    private String sitewidePromoPagesPath;


    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public String getSitePagesPath() {
        return sitePagesPath;
    }

    public void setSitePagesPath(String sitePagesPath) {
        this.sitePagesPath = sitePagesPath;
    }

    public String getSitewidePromoPagesPath() {
        return sitewidePromoPagesPath;
    }

    public void setSitewidePromoPagesPath(String sitewidePromoPagesPath) {
        this.sitewidePromoPagesPath = sitewidePromoPagesPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SitewidePromoSettings settings = (SitewidePromoSettings) o;        
        if (!name.equals(settings.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        return "SitewidePromoSettings{" +
                "name='" + name + '\'' +
                ", timezoneOffset=" + timezoneOffset +
                ", sitePagesPath='" + sitePagesPath + '\'' +
                ", sitewidePromoPagesPath='" + sitewidePromoPagesPath + '\'' +
                '}';
    }
}
