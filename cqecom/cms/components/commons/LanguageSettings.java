package com.cqecom.cms.components.commons;

public class LanguageSettings {
	private String code;
	private String longName;
	private String ilLanguageName;
    private String noIlLanguageName;
	private String longNameEn;
	private String name;
	private String levels;
	private String axrLevels;
	private String osubLevels;
	private String otbLevels;
	public String getLongNameEn() {
		return  longNameEn+"en";
	}
	public String getNameEn() {
		return nameEn+"en";
	}
	private String nameEn;
	private long version;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "LanguageSettings [code=" + code + ", longName=" + longName
		+ ", name=" + name + ", version=" + version + "]";
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public String getLevels() {
                return levels;
        }
        public void setLevels(String levels) {
                this.levels = levels;
        }

	public String getAXRLevels() {
                return axrLevels;
        }
        public void setAXRLevels(String axrLevels) {
                this.axrLevels = axrLevels;
        }
	public String getOSUBLevels() {
                return osubLevels;
        }
        public void setOSUBLevels(String osubLevels) {
                this.osubLevels = osubLevels;
        }
	public String getOTBLevels() {
                return otbLevels;
        }
        public void setOTBLevels(String otbLevels) {
                this.otbLevels = otbLevels;
        }
	
	public String getIlLanguageName() {
        return ilLanguageName;
    }

    public void setIlLanguageName(String ilLanguageName) {
        this.ilLanguageName = ilLanguageName;
    }

    public String getNoIlLanguageName() {
        return noIlLanguageName;
    }

    public void setNoIlLanguageName(String noIlLanguageName) {
        this.noIlLanguageName = noIlLanguageName;
    }

}
