package com.cqecom.cms.components.linkEditor;

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
        @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource"}),
        @Property(name = "adapters", value = {"com.cqecom.cms.components.linkEditor.LinkEditor"})
})
@Service(value = AdapterFactory.class)
public class LinkEditorAdapter implements AdapterFactory {

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> adapterTypeClass) {
        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);
        LinkEditor linkEditor = new LinkEditor();
        linkEditor.setGroupName(values.get("groupName", String.class));
        linkEditor.setListClass(values.get("listClass", String.class));
        linkEditor.setStyles(values.get("styles", String.class));
        List<Link> links = new ArrayList<Link>();

        //find 'links' node among child nodes, this node contains nodes for all links
        Iterator<Resource> iterator = ResourceUtil.listChildren(resource);
        Resource linksResource = null;
        while (iterator.hasNext()) {
            Resource childResource = iterator.next();
            String name = ResourceUtil.getName(childResource);
            if (name.equals("links")) {
                linksResource = childResource;
                break;
            }
        }
        if (linksResource != null) {
            iterator = ResourceUtil.listChildren(linksResource);
            while (iterator.hasNext()) {
                Resource linkResource = iterator.next();
                Link link = linkResource.adaptTo(Link.class);
                links.add(link);
            }
        }
        linkEditor.setLinks(links);
        return (AdapterType) linkEditor;
    }
}
