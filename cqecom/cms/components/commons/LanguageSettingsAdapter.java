package com.cqecom.cms.components.commons;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

@Component
@Properties({
    @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource" }),
    @Property(name = "adapters", value = {"com.cqecom.cms.components.commons.LanguageSettings" })
})
@Service(value = AdapterFactory.class)
public class LanguageSettingsAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);

        LanguageSettings languageSettings = new LanguageSettings();

        languageSettings.setCode(values.get("code", String.class));
        languageSettings.setLongName(values.get("longName", String.class));
        languageSettings.setName(values.get("name", String.class));
        languageSettings.setVersion(values.get("version", Long.class));
        languageSettings.setLevels(values.get("levels", String.class));
        languageSettings.setOTBLevels(values.get("otbLevels", String.class));
        languageSettings.setOSUBLevels(values.get("osubLevels", String.class));
        languageSettings.setAXRLevels(values.get("axrLevels", String.class));
        languageSettings.setIlLanguageName(values.get("ilLanguageName", ""));
        languageSettings.setNoIlLanguageName(values.get("noIlLanguageName", ""));

        return (AdapterType) languageSettings;
    }

}