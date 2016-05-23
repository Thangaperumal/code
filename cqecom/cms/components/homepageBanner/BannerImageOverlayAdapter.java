package com.cqecom.cms.components.homepageBanner;

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
    @Property(name = "adapters", value = {"com.cqecom.cms.components.homepageBanner.BannerImageOverlay" })
})
@Service(value = AdapterFactory.class)
public class BannerImageOverlayAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);
        BannerImageOverlay bannerImageOverlay = new BannerImageOverlay();
        
        bannerImageOverlay.setOverlayImageReference(values.get("overlayImageReference", String.class));
        bannerImageOverlay.setOverlayImagePosX(values.get("overlayImagePosX", new Integer(0)));
        bannerImageOverlay.setOverlayImagePosY(values.get("overlayImagePosY", new Integer(0)));
        bannerImageOverlay.setPromoHoverImageReference(values.get("promoHoverImageReference", String.class));
        bannerImageOverlay.setPromoImageReference(values.get("promoImageReference", String.class));
        bannerImageOverlay.setPromoImagePosX(values.get("promoImagePosX", new Integer(0)));
        bannerImageOverlay.setPromoImagePosY(values.get("promoImagePosY", new Integer(0)));
        bannerImageOverlay.setContentBlockSourcePage(values.get("contentBlockSourcePage", String.class));
        bannerImageOverlay.setContentBlockId(values.get("contentBlockId", String.class));
        bannerImageOverlay.setContentBlockPosX(values.get("contentBlockPosX", new Integer(0)));
        bannerImageOverlay.setContentBlockPosY(values.get("contentBlockPosY", new Integer(0)));

        return (AdapterType)bannerImageOverlay;
    }
}
