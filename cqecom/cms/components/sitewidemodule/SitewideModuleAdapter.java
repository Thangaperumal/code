package com.cqecom.cms.components.sitewidemodule;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.cqecom.cms.components.termsConditions.TermsConditions;

@Component
@Properties({
    @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource" }),
    @Property(name = "adapters", value = {"com.cqecom.cms.components.sitewidemodule.SitewideModule" })
})
@Service(value = AdapterFactory.class)
public class SitewideModuleAdapter implements AdapterFactory {

    private static final String DEFAULT_IMAGE_REF
        = "/etc/designs/cqecom/images/components/content/rsSidewideModule/default.png";
    private static final String DEFAULT_COLOR = "FFFFFF";

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {

        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);

        // when page was created and opened at the first time values == null, because resource does not exist. 
        if (values == null) {
            return (AdapterType) (new CatalogStyle());
        }

        String componentType = values.get("type", String.class);
        SitewideModule module;
        if ((componentType != null) && componentType.equals("standard")) {
            module = adaptToStandardModule(values);
        } else {
            module = adaptToCatalogStyle(values, resource);
        }

        loadImages(resource, values, module);

        loadTermAndConditions(resource, module);

        return (AdapterType) module;
    }


    private void loadTermAndConditions(Resource resource, SitewideModule module) {
        Resource termsRes = resource.getResourceResolver().getResource(resource.getPath() + "/../termsandconditions");
        if (termsRes != null) {
            TermsConditions terms = termsRes.adaptTo(TermsConditions.class);
            module.setTermsAndConditions(terms.getText());
        }
    }


    private void loadImages(Resource resource, ValueMap values, SitewideModule module) {
        String imageRef = values.get("backgroundImageReference", DEFAULT_IMAGE_REF);
        Resource imageResource = resource.getResourceResolver().getResource(imageRef);
        if (imageResource != null) {
            ModuleImage image = imageResource.adaptTo(ModuleImage.class);
            module.setBackgroundImage(image);
        }

        imageRef = values.get("buttonImageReference", DEFAULT_IMAGE_REF);
        imageResource = resource.getResourceResolver().getResource(imageRef);
        if (imageResource != null) {
            ModuleImage image = imageResource.adaptTo(ModuleImage.class);
            module.setButtonImage(image);
        }
    }

    
    private CatalogStyle adaptToCatalogStyle(ValueMap values, Resource resource) {
        CatalogStyle catStyle = new CatalogStyle();

        return catStyle;
    }


    private StandardModule adaptToStandardModule(ValueMap values) {
        StandardModule stdModule = new StandardModule();
        stdModule.setBottomLinkText(values.get("standardBottomLinkText", String.class));
        stdModule.setTitleLink(values.get("standardTitleLink", String.class));
        stdModule.setTitleLinkText(values.get("standardTitleLinkText", String.class));
        stdModule.setBackgroundLinkType(values.get("standardBackgroundLinkType", String.class));
        stdModule.setBackgroundLink(values.get("standardBackgroundLink", String.class));
        stdModule.setStripStyle(values.get("stripStyle", "strip1"));
        stdModule.setBottomLinkLeft(values.get("bottomLinkLeft", 20));
        stdModule.setBottomLinkBottom(values.get("bottomLinkBottom", 0));
        stdModule.setBottomLinkColor(values.get("standardBottomLinkColor", DEFAULT_COLOR));

        stdModule.setButtonLink(values.get("buttonLink", String.class));
        stdModule.setButtonLeft(values.get("buttonLeft", 20));
        stdModule.setButtonTop(values.get("buttonTop", 20));
        
        return stdModule;
    }
}
