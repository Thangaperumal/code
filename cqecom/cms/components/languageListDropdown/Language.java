package com.cqecom.cms.components.languageListDropdown;


public class Language implements Comparable<Language>{
    String code;
    String name;
    String fullName;
    String link;
    Integer version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int compareTo(Language lang) {
    	return this.fullName.compareTo(lang.fullName);
    }
}
