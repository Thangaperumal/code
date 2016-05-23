package com.cqecom.cms.components.htmlcode;

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
        @Property(name = "adapters", value = {"com.cqecom.cms.components.htmlcode.HtmlCode" })
})
@Service(value = AdapterFactory.class)
public class HtmlCodeAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);
        HtmlCode text = new HtmlCode();
        if (values != null) {
            text.setCode(values.get("code", String.class));
        }
        return (AdapterType) text;
    }
}
