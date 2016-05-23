package com.cqecom.cms.components.intnavigation;

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
    @Property(name = "adapters", value = {"com.cqecom.cms.components.intnavigation.Navigation" })
})
@Service(value = AdapterFactory.class)
public class NavigationAdapter implements AdapterFactory {

    private static final String DEFAULT_STRIP_STYLE = "sectionStrip1";

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        Resource resource = (Resource) adaptable;
        Navigation navigation = new Navigation();
        ValueMap valueMap = resource.adaptTo(ValueMap.class);
        if (valueMap == null) {
            navigation.setLevel(Navigation.Level.NONE);
            return (AdapterType) navigation;
        } else {
            String levelStr = valueMap.get("level", "none");
            Navigation.Level level;
            if (levelStr.equalsIgnoreCase("current_level")) {
                level = Navigation.Level.CURRENT;
            } else {
                level = levelStr.equalsIgnoreCase("descendants") ? Navigation.Level.DESCENDANTS : Navigation.Level.NONE;
            }
            navigation.setLevel(level);
        }
        navigation.setStripStyle(valueMap.get("stripStyle", DEFAULT_STRIP_STYLE));
        navigation.setHeaderText(valueMap.get("headerText", String.class));
        navigation.setHideParent(Boolean.parseBoolean(valueMap.get("hideParent", "false")));
        navigation.setParentTabName(valueMap.get("parentTabName", String.class));
        navigation.setHideCurrentPage(Boolean.parseBoolean(valueMap.get("hideCurrentPage", "false")));
        navigation.setTabName(valueMap.get("tabName", String.class));
        return (AdapterType) navigation;
    }

}
