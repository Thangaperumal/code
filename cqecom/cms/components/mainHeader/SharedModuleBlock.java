package com.cqecom.cms.components.mainHeader;

import com.cqecom.cms.components.htmltext.HtmlText;

public class SharedModuleBlock {

    private Boolean use;

    private String posX;
    private String posY;

    private String sourcePage;
    private String sourceModuleId;

    private HtmlText block;

    public Boolean getUse() {
        return use;
    }

    public void setUse(Boolean use) {
        this.use = use;
    }

    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        this.posX = posX;
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        this.posY = posY;
    }

    public String getSourcePage() {
        return sourcePage;
    }

    public void setSourcePage(String sourcePage) {
        this.sourcePage = sourcePage;
    }

    public String getSourceModuleId() {
        return sourceModuleId;
    }

    public void setSourceModuleId(String sourceModuleId) {
        this.sourceModuleId = sourceModuleId;
    }

    public HtmlText getBlock() {
        return block;
    }

    public void setBlock(HtmlText block) {
        this.block = block;
    }
}
