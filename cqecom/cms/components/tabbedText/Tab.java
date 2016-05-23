package com.cqecom.cms.components.tabbedText;

import org.apache.commons.lang.StringUtils;

public class Tab {

    enum AdditionalContent {
        VIDEO, IMAGE, NONE
    }

    private Boolean showTab;

    private String title;

    private String text;

    private String positionX;

    private String positionY;

    private AdditionalContent additionalContent;

    private String imagePath;

    private String videoUrl;

    private String dataSourcePath;

    private String previewImagePath;

    private Boolean showNoBackground;

    private Long containerWidth;

    public Boolean getShowTab() {
        return showTab;
    }

    public void setShowTab(Boolean showTab) {
        this.showTab = showTab;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public AdditionalContent getAdditionalContent() {
        return additionalContent;
    }

    public void setAdditionalContent(AdditionalContent additionalContent) {
        this.additionalContent = additionalContent;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDataSourcePath() {
        return dataSourcePath;
    }

    public void setDataSourcePath(String dataSourcePath) {
        this.dataSourcePath = dataSourcePath;
    }

    public String getPreviewImagePath() {
        return previewImagePath;
    }

    public void setPreviewImagePath(String previewImagePath) {
        this.previewImagePath = previewImagePath;
    }

    public Boolean getShowNoBackground() {
        return showNoBackground;
    }

    public void setShowNoBackground(Boolean showNoBackground) {
        this.showNoBackground = showNoBackground;
    }

    public Long getContainerWidth() {
        return containerWidth;
    }

    public void setContainerWidth(Long containerWidth) {
        this.containerWidth = containerWidth;
    }

    public boolean getCanShowVideo() {
        return StringUtils.isNotBlank(this.videoUrl) 
                && StringUtils.isNotBlank(this.previewImagePath)
                && StringUtils.isNotBlank(this.dataSourcePath);
    }
}
