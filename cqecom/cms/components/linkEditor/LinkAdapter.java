package com.cqecom.cms.components.linkEditor;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

@Component
@Properties({
        @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource"}),
        @Property(name = "adapters", value = {"com.cqecom.cms.components.linkEditor.Link"})
})
@Service(value = AdapterFactory.class)
public class LinkAdapter implements AdapterFactory {

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> adapterTypeClass) {
        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);
        Link link = new Link();
        if (values != null) {
            link.setName(values.get("name", String.class));
            link.setTitle(values.get("title", String.class));
            link.setUrl(values.get("url", String.class));
            link.setLinkClass(values.get("linkClass", String.class));
            link.setRel(values.get("rel", String.class));
            link.setTarget(values.get("target", false));
        }
        return (AdapterType) link;
    }
}
