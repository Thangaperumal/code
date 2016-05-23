package com.cqecom.cms.components.termsConditions;

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
    @Property(name = "adapters", value = {"com.cqecom.cms.components.termsConditions.TermsConditions" })
})
@Service(value = AdapterFactory.class)
public class TermsConditionsAdapter implements AdapterFactory {

    /**
     * @return TermsConditions object or null if resource doesn't exist
     */
    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);
        TermsConditions terms = null;
        if (values != null) {
            terms = new TermsConditions();
            terms.setText(values.get("text", "Please enter text for Terms & Conditions"));
            terms.setLinkText(values.get("linkText", "Terms & Conditions"));
            terms.setLinkClass(values.get("linkClass", String.class));
        }
        return (AdapterType) terms;
    }
}
