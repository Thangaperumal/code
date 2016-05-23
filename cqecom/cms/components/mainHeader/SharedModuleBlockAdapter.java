package com.cqecom.cms.components.mainHeader;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

@Component
@Properties({
    @Property(name = "adaptables", value = { "org.apache.sling.api.resource.Resource" }),
    @Property(name = "adapters", value = { "com.cqecom.cms.components.mainHeader.SharedModuleBlock" })
})
@Service(value = AdapterFactory.class)
public class SharedModuleBlockAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        Resource resource = (Resource) adaptable;
        ValueMap valueMap = resource.adaptTo(ValueMap.class);
        SharedModuleBlock block = new SharedModuleBlock();
        block.setUse(valueMap.get("use", Boolean.FALSE));
        block.setPosX(valueMap.get("posX", String.class));
        block.setPosY(valueMap.get("posY", String.class));
        block.setSourcePage(valueMap.get("contentBlockSourcePage", String.class));
        block.setSourceModuleId(valueMap.get("contentBlockId", String.class));

        return (AdapterType) block;
    }

}
