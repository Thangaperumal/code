package com.cqecom.cms.components.formValidator;


import org.apache.sling.api.SlingHttpServletRequest;

public class ValidatorUtils {

    public static boolean isBrazil(SlingHttpServletRequest formsRequest) {
        return formsRequest.getRequestPathInfo().getResourcePath().contains("cqecombr");
    }
}
