package com.cqecom.cms.components.homepageBanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;

@Component
@Properties({ @Property(name = "adaptables", value = { "org.apache.sling.api.resource.Resource" }),
        @Property(name = "adapters", value = { "com.cqecom.cms.components.homepageBanner.HomepageBanner" }) })
@Service(value = AdapterFactory.class)
public class HomepageBannerAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);
        HomepageBanner banner = new HomepageBanner();

        banner.setShowBorder(values.get("showBorder", new Boolean(false)));
        banner.setBackgroundImageReference(values.get("backgroundImageReference", String.class));
        banner.setMargin(values.get("margin", "0 0 0 -5px"));
        banner.setPadding(values.get("padding", "5px"));
        banner.setWidth(values.get("width", "950"));
        banner.setHeight(values.get("height", "490"));
        banner.setUseRotation(values.get("useRotation", new Boolean(false)));
        banner.setRotationInterval(Integer.valueOf(values.get("rotation_int", "4000")));
        banner.setUseSitewidePromo(values.get("useSitewidePromo", Boolean.FALSE));

        List<BannerImage> images = new ArrayList<BannerImage>();
        Iterator<Resource> iterator = ResourceUtil.listChildren(resource);
        int position = 0;
        while (iterator.hasNext()) {
            Resource childResource = iterator.next();
            BannerImage image = childResource.adaptTo(BannerImage.class);
            if (image != null && image.getUseThisBanner() || position == 0) {
                images.add(image);
            }
            position++;
        }
        banner.setImages(images);
        return (AdapterType) banner;
    }
}
