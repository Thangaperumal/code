package com.cqecom.cms.components.languageListDropdown;

import com.cqecom.cms.components.commons.image.Image;
import com.cqecom.cms.model.Configuration;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;


@Component
@Properties({
        @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource"}),
        @Property(name = "adapters", value = {"com.cqecom.cms.components.languageListDropdown.LanguageListDropdown"})
})

@Service(value = AdapterFactory.class)

public class LanguageListDropdownAdapter implements AdapterFactory {
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> adapterTypeClass) {
        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);

        LanguageListDropdown languageListDropdown = new LanguageListDropdown();
        languageListDropdown.setDropdownX(values.get("LanguageListDropdownX", 0));
        languageListDropdown.setDropdownY(values.get("LanguageListDropdownY", 26));
        languageListDropdown.setColumnsCount(values.get("LanguageListDropdownColumnsCount", 4));
        languageListDropdown.setLocalizationsPath(values.get("localizationsPath", Configuration.GLOBAL_PATH+"languages/en_US"));
        languageListDropdown.setDropdownTitleLeft(values.get("languageListDropdownTitleLeft", 15));
        languageListDropdown.setDropdownTitleTop(values.get("languageListDropdownTitleTop", 10));
        languageListDropdown.setDropdownTitle(values.get("languageListDropdownTitle", ""));
        languageListDropdown.setUseThreeImage(values.get("useThreeImage",false));

        Image btnImage = createButtonImage(resource.getResourceResolver(), values);
        languageListDropdown.setButtonImage(btnImage);

        languageListDropdown.setButtonMouseOverImageRef(values.get("btnMouseOverImageRef",""));

        languageListDropdown.setButtonClickImageRef(values.get("btnClickImageRef",""));

        Image dropdownImage = createDropdownImage(resource.getResourceResolver(), values);
        languageListDropdown.setDropdownImage(dropdownImage);

        languageListDropdown.setMouseOverImageRef(values.get("moImageRef",
                "/etc/designs/cqecom/images/components/content/rsLanguageListDropdown/bg_blue.png"));
        return (AdapterType) languageListDropdown;
    }

    private Image createDropdownImage(ResourceResolver resourceResolver, ValueMap values) {
        Image dropdownImage = null;
        String dropdownImageRef = values.get("dropdownImageRef", String.class);
        if (dropdownImageRef != null) {
            Resource dropdownImageResource = resourceResolver.getResource(dropdownImageRef);
            if (dropdownImageResource != null) {
                dropdownImage = dropdownImageResource.adaptTo(Image.class);
            }
        } else {
            dropdownImage = new Image();
            dropdownImage.setReference("/etc/designs/cqecom/images/components/content/rsLanguageListDropdown/langselectbg_blue.gif");
            dropdownImage.setWidth(550);
            dropdownImage.setHeight(233);
        }
        return dropdownImage;
    }

    private Image createButtonImage(ResourceResolver resourceResolver, ValueMap values) {
        Image btnImage = null;
        String btnImageRef = values.get("btnImageRef", String.class);
        if (btnImageRef != null) {
            Resource btnImageResource = resourceResolver.getResource(btnImageRef);
            if (btnImageResource != null) {
                btnImage = btnImageResource.adaptTo(Image.class);
            }
        } else {
            btnImage = new Image();
            btnImage.setReference("/etc/designs/cqecom/images/components/content/rsLanguageListDropdown/btn_select-a-language-top.png");
            btnImage.setWidth(130);
            btnImage.setHeight(26);
        }
        return btnImage;
    }


}
