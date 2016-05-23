package com.cqecom.cms.components.sitewidemodule;

public class SitewideModule {

    private ModuleImage backgroundImage;
    private ModuleImage buttonImage;
    private String termsAndConditions;

    public ModuleImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(ModuleImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public ModuleImage getButtonImage() {
        return buttonImage;
    }

    public void setButtonImage(ModuleImage buttonImage) {
        this.buttonImage = buttonImage;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }
}
