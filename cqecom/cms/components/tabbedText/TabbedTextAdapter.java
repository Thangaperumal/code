package com.cqecom.cms.components.tabbedText;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Properties({
    @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource" }),
    @Property(name = "adapters", value = {"com.cqecom.cms.components.tabbedText.TabbedText" })
})
@Service(value = AdapterFactory.class)
public class TabbedTextAdapter implements AdapterFactory {

    private static final Long DEFAULT_TAB_CONTENT_HEIGHT = 160L;

    private static final String DEFAULT_ACTIVE_ARROW_IMAGE =
            "/content/dam/cqecomcom/images/corporate/superior-methods/hiw_arr_grn.png";

    private static final String DEFAULT_INACTIVE_ARROW_IMAGE =
            "/content/dam/cqecomcom/images/corporate/superior-methods/hiw_arr2.png";
    
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> adapterTypeClass) {
        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);

        TabbedText tabbedText = new TabbedText();
        tabbedText.setHeaderText(values.get("headerText", String.class));
        tabbedText.setHeaderColor(values.get("headerColor", String.class));
        tabbedText.setDescription(values.get("description", String.class));

        tabbedText.setActiveTabBackgroundColor(values.get("activeTabBackgroundColor", String.class));
        tabbedText.setActiveTabTextColor(values.get("activeTabTextColor", String.class));

        tabbedText.setTabBorderColor(values.get("tabBorderColor", String.class));

        tabbedText.setTabContentHeight(values.get("tabContentHeight", DEFAULT_TAB_CONTENT_HEIGHT));
        tabbedText.setInactiveTabBackgroundColor(values.get("inactiveTabBackgroundColor", String.class));
        tabbedText.setInactiveTabTextColor(values.get("inactiveTabTextColor", String.class));

        tabbedText.setActiveArrowImage(values.get("activeArrowImageReference", DEFAULT_ACTIVE_ARROW_IMAGE));
        tabbedText.setInactiveArrowImage(values.get("inactiveArrowImageReference", DEFAULT_INACTIVE_ARROW_IMAGE));

        List<Tab> tabs = new ArrayList<Tab>();
        Iterator<Resource> iterator = ResourceUtil.listChildren(resource);
        while (iterator.hasNext()) {
            Resource childResource = iterator.next();
            Tab tab = childResource.adaptTo(Tab.class);
            if (tab != null && tab.getShowTab()) {
                tabs.add(tab);
            }
        }
        tabbedText.setTabs(tabs);
        return (AdapterType) tabbedText;
    }
}
