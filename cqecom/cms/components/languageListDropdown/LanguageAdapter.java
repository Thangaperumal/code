package com.cqecom.cms.components.languageListDropdown;

import com.cqecom.cms.components.banner.ProductLanguage;

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
    @Property(name = "adapters", value = {"com.cqecom.cms.components.languageListDropdown.Language" })
})
@Service(value = AdapterFactory.class)
public class LanguageAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);

        Language language = new Language();
        language.setCode(values.get("code", String.class));
        language.setFullName(values.get("longName", String.class));
        language.setName(values.get("name", String.class));
        language.setVersion(Integer.decode(values.get("version", "4")));
        language.setLink(values.get("link", String.class));

        return (AdapterType) language;
    }
}

