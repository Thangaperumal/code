package com.cqecom.cms.components.sitewidemodule;

import com.cqecom.cms.components.commons.image.Image;

public class ModuleImage extends Image {

    public Boolean getIsSet() {
    	return !getReference().endsWith("default.png");
    }
}
