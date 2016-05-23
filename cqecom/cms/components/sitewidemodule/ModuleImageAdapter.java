package com.cqecom.cms.components.sitewidemodule;

import com.cqecom.cms.components.commons.image.ImageUtil;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;

@Component
@Properties({
    @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource" }),
    @Property(name = "adapters", value = {"com.cqecom.cms.components.sitewidemodule.ModuleImage" })
})
@Service(value = AdapterFactory.class)
public class ModuleImageAdapter implements AdapterFactory {

    private static final Integer DEFAULT_WIDTH = 64;
    private static final Integer DEFAULT_HEIGHT = 64;

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        Resource resource = (Resource) adaptable;

        ModuleImage image = new ModuleImage();
        image.setReference(resource.getPath());

        ImageUtil.setImageDimensions(image, resource.getResourceResolver(), DEFAULT_HEIGHT, DEFAULT_WIDTH);

        return (AdapterType) image;
    }

}
