package com.cqecom.cms.components.commons.image;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;

@Component
@Properties({
    @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource" }),
    @Property(name = "adapters", value = {"com.cqecom.cms.components.commons.image.Image" })
})
@Service(value = AdapterFactory.class)
public class ImageAdapter implements AdapterFactory{

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> adapterTypeClass) {
        Resource resource = (Resource) adaptable;
        Image image = new Image();
        image.setReference(resource.getPath());
        ImageUtil.setImageDimensions(image, resource.getResourceResolver(), 0, 0);
        return (AdapterType) image;
    }
}
