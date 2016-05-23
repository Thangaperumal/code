package com.cqecom.cms.components.tabbedText;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

@Component
@Properties({
        @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource"}),
        @Property(name = "adapters", value = {"com.cqecom.cms.components.tabbedText.Tab"})
})
@Service(value = AdapterFactory.class)
public class TabAdapter implements AdapterFactory {

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> adapterTypeClass) {
        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);
        Tab tab = new Tab();
        String whatToShow = values.get("whatToShow", "none");
        Tab.AdditionalContent content = Tab.AdditionalContent.valueOf(whatToShow.toUpperCase());
        tab.setAdditionalContent(content);
        if (content.equals(Tab.AdditionalContent.IMAGE)) {
            tab.setImagePath(values.get("imageReference", String.class));
            tab.setPositionX(values.get("imagePosX", "0"));
            tab.setPositionY(values.get("imagePosY", "0"));
            tab.setShowNoBackground(values.get("showNoBackground", Boolean.FALSE));
            tab.setContainerWidth(values.get("containerWidth", 50L));
        } else if (content.equals(Tab.AdditionalContent.VIDEO)) {
            tab.setPreviewImagePath(values.get("previewImageReference", String.class));
            tab.setPositionX(values.get("videoPosX", "0"));
            tab.setPositionY(values.get("videoPosY", "0"));
            tab.setVideoUrl(values.get("videoUrl", String.class));
            tab.setDataSourcePath(values.get("dataSourceReference", String.class));
        }
        tab.setShowTab(values.get("showTab", Boolean.FALSE));
        tab.setTitle(values.get("title", String.class));
        tab.setText(values.get("text", String.class));
        return (AdapterType) tab;
    }
}
