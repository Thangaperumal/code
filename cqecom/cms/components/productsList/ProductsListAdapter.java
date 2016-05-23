package com.cqecom.cms.components.productsList;

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
    @Property(name = "adapters", value = {"com.cqecom.cms.components.productsList.ProductsList" })
})
@Service(value = AdapterFactory.class)

public class ProductsListAdapter implements AdapterFactory {

    /**
     * @return ProductsList object or null if resource doesn't exist
     */
    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);
        ProductsList list = null;
        if (values != null) {
            list = new ProductsList();
            list.setTemplate(values.get("template", "1"));
            list.setOrder(values.get("order", "1"));
        }
        return (AdapterType) list;
    }
}