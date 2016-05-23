package com.cqecom.cms.components.banner;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Properties({
    @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource" }),
    @Property(name = "adapters", value = {"com.cqecom.cms.components.banner.Banner" })
})
@Service(value = AdapterFactory.class)
public class BannerAdapter implements AdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);
        Banner banner = new Banner();

        String bannerType = values.get("bannerType", String.class);
        if (bannerType != null) {
            banner.setBannerType(BannerType.valueOf(bannerType));
        }
        banner.setBannersPath(values.get("bannersPath", String.class));
        banner.setBannerImageRef(values.get("bannerImageRef", String.class));
        banner.setLanguageImageRef(values.get("languageImageRef", String.class));
        banner.setLanguageDropdownReference(
                values.get("languageDropdownReference",
                        "/content/dam/cqecomcom/images/mt/immersion/lang_drop_down.png"));
        banner.setLowerBarReference(
                values.get("lowerBarReference",
                        "/content/dam/cqecomcom/images/lpbanners/blue_offer_bar.png"));
        banner.setLanguagePopupReference(
                values.get("languagePopupReference",
                        "/etc/designs/cqecom/images/rsBanner/langselect-bg-noheader.png"));
        String bannerText = values.get("bannerText",
                "Our award winning immersion method, combined with our "
                + "state-of-the-art software and speech-recognition technology, is the best way to learn");
        banner.setBannerText(bannerText);
        banner.setGuaranteeText(values.get("guaranteeText", "We guarantee it"));
        banner.setTopImageHeight(Integer.decode(values.get("topImageHeight", "240")));
        banner.setBottomImageHeight(Integer.decode(values.get("bottomImageHeight", "110")));
        banner.setDropdownX(Integer.decode(values.get("dropdownX", "630")));
        banner.setDropdownY(Integer.decode(values.get("dropdownY", "50")));
        banner.setDropdownWidth(Integer.decode(values.get("dropdownWidth", "250")));
        banner.setDropdownAlignment(values.get("dropdownAlignment", "right"));
        banner.setUseProductLink(values.get("useProductLink", false));

        String[] languageCodesArray = values.get("lang", new String[]{});
        Set<String> languageCodes = new LinkedHashSet<String>(Arrays.asList(languageCodesArray));

        banner.setLanguageCodes(languageCodes);

        return (AdapterType) banner;
    }
}