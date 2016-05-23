package com.cqecom.cms.components.homepageBanner;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;

@Component
@Properties({
    @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource" }),
    @Property(name = "adapters", value = {"com.cqecom.cms.components.homepageBanner.BannerImage" })
})
@Service(value = AdapterFactory.class)
public class BannerImageAdapter implements AdapterFactory {

    private static final Integer DEFAULT_WIDTH = 100;
    private static final Integer DEFAULT_HEIGHT = 100;

    @SuppressWarnings("unchecked")
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);

        BannerImage bannerImage = new BannerImage();

        bannerImage.setImageReference(values.get("imageReference", String.class));
        bannerImage.setUseThisBanner(values.get("useBanner", Boolean.FALSE));
        bannerImage.setUseFirstImageOverlay(values.get("useFirstImageOverlay", Boolean.FALSE));
        bannerImage.setUseFirstBannerImage(values.get("useFirstBannerImage", Boolean.FALSE));
        bannerImage.setImageOverlay(getBannerImageOverlay((Resource)adaptable));
        setImageDim(resource, bannerImage);

        return (AdapterType)bannerImage;
    }

    private BannerImageOverlay getBannerImageOverlay(Resource resource) {
        BannerImageOverlay overlay = resource.adaptTo(BannerImageOverlay.class);
        return overlay;
    }

    private void setImageDim(Resource resource, BannerImage image) {

        String path = image.getImageReference() + "/jcr:content/metadata";
        Resource metadataResouce = resource.getResourceResolver().getResource(path);

        if (metadataResouce == null) {
            image.setHeight(DEFAULT_HEIGHT);
            image.setWidth(DEFAULT_WIDTH);
        } else {
            ValueMap metadata = metadataResouce.adaptTo(ValueMap.class);
            image.setHeight(metadata.get("tiff:ImageLength", DEFAULT_HEIGHT));
            image.setWidth(metadata.get("tiff:ImageWidth", DEFAULT_WIDTH));
        }
    }
}
