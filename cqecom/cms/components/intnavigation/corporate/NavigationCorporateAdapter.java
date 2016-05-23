package com.cqecom.cms.components.intnavigation.corporate;

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
    @Property(name = "adapters", value = {"com.cqecom.cms.components.intnavigation.corporate.NavigationCorporate" })
})
@Service(value = AdapterFactory.class)
public class NavigationCorporateAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        Resource resource = (Resource) adaptable;
        NavigationCorporate navigation = new NavigationCorporate();
        ValueMap valueMap = resource.adaptTo(ValueMap.class);
        if (valueMap == null) {
            navigation.setLevel(NavigationCorporate.Level.NONE);
            return (AdapterType) navigation;
        } else {
            String levelStr = valueMap.get("level", "none");
            NavigationCorporate.Level level;
            if (levelStr.equalsIgnoreCase("current_level")) {
                level = NavigationCorporate.Level.CURRENT;
            } else {
                level = levelStr.equalsIgnoreCase("descendants") ? NavigationCorporate.Level.DESCENDANTS : NavigationCorporate.Level.NONE;
            }
            navigation.setLevel(level);
        }
        navigation.setHideParent(Boolean.parseBoolean(valueMap.get("hideParent", "false")));
        navigation.setParentTabName(valueMap.get("parentTabName", String.class));
        navigation.setHideCurrentPage(Boolean.parseBoolean(valueMap.get("hideCurrentPage", "false")));
        navigation.setTabName(valueMap.get("tabName", String.class));
        return (AdapterType) navigation;
    }

}
