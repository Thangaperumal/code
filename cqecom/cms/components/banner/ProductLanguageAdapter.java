package com.cqecom.cms.components.banner;

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
    @Property(name = "adapters", value = {"com.cqecom.cms.components.banner.ProductLanguage" })
})
@Service(value = AdapterFactory.class)
public class ProductLanguageAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);
        ProductLanguage language = new ProductLanguage();
        language.setCode(values.get("code", String.class));
        language.setLongName(values.get("longName", String.class));
        language.setName(values.get("name", String.class));
        language.setProductLink(values.get("productLink", String.class));
        language.setVisible(values.get("visible", true));

        //set correct value at the servlet
        language.setProductUrl("#");

        return (AdapterType) language;
    }

}
