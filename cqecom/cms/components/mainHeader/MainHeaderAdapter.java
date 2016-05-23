package com.cqecom.cms.components.mainHeader;

import java.util.Iterator;

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
    @Property(name = "adapters", value = {"com.cqecom.cms.components.mainHeader.MainHeader" })
})
@Service(value = AdapterFactory.class)
public class MainHeaderAdapter implements AdapterFactory{

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        Resource resource = (Resource) adaptable;
        MainHeader header = new MainHeader();
        ValueMap valueMap = resource.adaptTo(ValueMap.class);
        header.setHeaderWidth(valueMap.get("width", String.class));
        header.setHeaderHeight(valueMap.get("height", String.class));
        header.setTopStripImageReference(valueMap.get("topStripeImageReference", String.class));
        header.setLogoImageReference(valueMap.get("logoImageReference", String.class));
        header.setLogoLink(valueMap.get("logoLink", String.class));
        Iterator<Resource> iterator = ResourceUtil.listChildren(resource);
        while (iterator.hasNext()) {
            Resource childResource = iterator.next();
            if (childResource.getPath().contains("utilNav")) {
                SharedModuleBlock utilNav = childResource.adaptTo(SharedModuleBlock.class);
                header.setUtilNav(utilNav);
            } else if (childResource.getPath().contains("topNav")) {
                SharedModuleBlock topNavigation = childResource.adaptTo(SharedModuleBlock.class);
                header.setTopNavigation(topNavigation);
            } else if (childResource.getPath().contains("tfn")) {
                SharedModuleBlock tfn = childResource.adaptTo(SharedModuleBlock.class);
                header.setTfn(tfn);
            } else if (childResource.getPath().contains("button")) {
                SharedModuleBlock button = childResource.adaptTo(SharedModuleBlock.class);
                header.setButton(button);
            } else if (childResource.getPath().contains("subNav")) {
                SharedModuleBlock subNav = childResource.adaptTo(SharedModuleBlock.class);
                header.setSubNav(subNav);
            }
        }
        return (AdapterType) header;
    }
}
