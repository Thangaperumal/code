package com.cqecom.cms.components.htmltext;

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
        @Property(name = "adapters", value = {"com.cqecom.cms.components.htmltext.HtmlText" })
})
@Service(value = AdapterFactory.class)
public class HtmlTextAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);
        HtmlText text = new HtmlText();
        if (values != null) {
            text.setText(values.get("htmlText", String.class));
            text.setScripts(values.get("scripts", String.class));
            text.setStyles(values.get("styles", String.class));
        }
        return (AdapterType) text;
    }
}
