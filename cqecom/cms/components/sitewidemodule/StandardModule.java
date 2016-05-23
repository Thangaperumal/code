package com.cqecom.cms.components.sitewidemodule;

public class StandardModule extends SitewideModule {

    private static final int STRIP_WIDTH = 312;

    private String titleLink;
    private String titleLinkText;
    private String backgroundLink;
    private String bottomLinkText;
    private String bottomLinkColor;
    private String stripStyle;

    private String backgroundLinkType;
    private Integer bottomLinkBottom;
    private Integer bottomLinkLeft;
    
    private String buttonLink;
    private Integer buttonLeft;
    private Integer buttonTop;

    public String getTitleLink() {
        return titleLink;
    }
    public void setTitleLink(String titleLink) {
        this.titleLink = titleLink;
    }
    public String getTitleLinkText() {
        return titleLinkText;
    }
    public void setTitleLinkText(String titleLinkText) {
        this.titleLinkText = titleLinkText;
    }
    public String getBottomLinkText() {
        return bottomLinkText;
    }
    public void setBottomLinkText(String bottomLinkText) {
        this.bottomLinkText = bottomLinkText;
    }

    public String getBottomLinkColor() {
        return bottomLinkColor;
    }

    public void setBottomLinkColor(String bottomLinkColor) {
        this.bottomLinkColor = bottomLinkColor;
    }

    public String getBackgroundLink() {
        return backgroundLink;
    }
    public void setBackgroundLink(String backgroundLink) {
        this.backgroundLink = backgroundLink;
    }
    public String getStripStyle() {
        return stripStyle;
    }
    public void setStripStyle(String stripStyle) {
        this.stripStyle = stripStyle;
    }

    public boolean isImageWidthValid() {
        return getBackgroundImage() != null
            && getBackgroundImage().getWidth() != null
            && getBackgroundImage().getWidth().intValue() == STRIP_WIDTH;
    }

    public Integer getBottomLinkBottom() {
        return bottomLinkBottom;
    }

    public void setBottomLinkBottom(Integer bottomLinkBottom) {
        this.bottomLinkBottom = bottomLinkBottom;
    }

    public Integer getBottomLinkLeft() {
        return bottomLinkLeft;
    }

    public void setBottomLinkLeft(Integer bottomLinkLeft) {
        this.bottomLinkLeft = bottomLinkLeft;
    }

    public String getBackgroundLinkType() {
        return backgroundLinkType;
    }

    public void setBackgroundLinkType(String backgroundLinkType) {
        this.backgroundLinkType = backgroundLinkType;
    }
    
	public String getButtonLink() {
		return buttonLink;
	}
	
	public void setButtonLink(String buttonLink) {
		this.buttonLink = buttonLink;
	}
	
	public Integer getButtonLeft() {
		return buttonLeft;
	}
	
	public void setButtonLeft(Integer buttonLeft) {
		this.buttonLeft = buttonLeft;
	}
	
	public Integer getButtonTop() {
		return buttonTop;
	}
	
	public void setButtonTop(Integer buttonTop) {
		this.buttonTop = buttonTop;
	}
	
}
