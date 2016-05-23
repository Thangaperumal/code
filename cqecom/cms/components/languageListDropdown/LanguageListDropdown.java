package com.cqecom.cms.components.languageListDropdown;

import com.cqecom.cms.components.commons.image.Image;

import java.util.List;

public class LanguageListDropdown {

    private List<Language> languages;

    private Integer dropdownX;

    private Integer dropdownY;

    private Integer columnsCount;

    private String localizationsPath;

    private Integer dropdownTitleLeft;

    private Integer dropdownTitleTop;

    private String dropdownTitle;

    private boolean useThreeImage;

    private Image buttonImage;

    private String buttonMouseOverImageRef;

    private String buttonClickImageRef;

    private Image dropdownImage;

    private String mouseOverImageRef;

    public boolean isUseThreeImage() {
        return useThreeImage;
    }

    public void setUseThreeImage(boolean useThreeImage) {
        this.useThreeImage = useThreeImage;
    }

    public String getButtonMouseOverImageRef() {
        return buttonMouseOverImageRef;
    }

    public void setButtonMouseOverImageRef(String buttonMouseOverImageRef) {
        this.buttonMouseOverImageRef = buttonMouseOverImageRef;
    }

    public String getButtonClickImageRef() {
        return buttonClickImageRef;
    }

    public void setButtonClickImageRef(String buttonClickImageRef) {
        this.buttonClickImageRef = buttonClickImageRef;
    }

    public Integer getDropdownTitleLeft() {
        return dropdownTitleLeft;
    }

    public void setDropdownTitleLeft(Integer dropdownTitleLeft) {
        this.dropdownTitleLeft = dropdownTitleLeft;
    }

    public Integer getDropdownTitleTop() {
        return dropdownTitleTop;
    }

    public void setDropdownTitleTop(Integer dropdownTitleTop) {
        this.dropdownTitleTop = dropdownTitleTop;
    }

    public String getDropdownTitle() {
        return dropdownTitle;
    }

    public void setDropdownTitle(String dropdownTitle) {
        this.dropdownTitle = dropdownTitle;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public Integer getDropdownX() {
        return dropdownX;
    }

    public void setDropdownX(Integer dropdownX) {
        this.dropdownX = dropdownX;
    }

    public Integer getDropdownY() {
        return dropdownY;
    }

    public void setDropdownY(Integer dropdownY) {
        this.dropdownY = dropdownY;
    }

    public Integer getColumnsCount() {
        return columnsCount;
    }

    public void setColumnsCount(Integer columnsCount) {
        this.columnsCount = columnsCount;
    }

    public String getLocalizationsPath() {
        return localizationsPath;
    }

    public void setLocalizationsPath(String localizationsPath) {
        this.localizationsPath = localizationsPath;
    }

    public Image getButtonImage() {
        return buttonImage;
    }

    public void setButtonImage(Image buttonImage) {
        this.buttonImage = buttonImage;
    }

    public Image getDropdownImage() {
        return dropdownImage;
    }

    public void setDropdownImage(Image dropdownImage) {
        this.dropdownImage = dropdownImage;
    }

    public String getMouseOverImageRef() {
        return mouseOverImageRef;
    }

    public void setMouseOverImageRef(String mouseOverImageRef) {
        this.mouseOverImageRef = mouseOverImageRef;
    }


}
