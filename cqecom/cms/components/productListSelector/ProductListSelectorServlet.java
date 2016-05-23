package com.cqecom.cms.components.productListSelector;

import com.cqecom.cms.components.AbstractMvcServlet;
import com.cqecom.cms.components.commons.CommonUtils;
import com.cqecom.cms.components.commons.GlobalSettings;
import com.cqecom.cms.components.commons.GlobalSettingsReader;
import com.cqecom.cms.components.commons.LocalizationReader;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.servlet.Servlet;

import java.util.Map;
import java.util.regex.Pattern;

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsProductListSelector"),
        @Property(name = "sling.servlet.extensions", value = "html")
})

public class ProductListSelectorServlet extends AbstractMvcServlet<ProductListSelector> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductListSelectorServlet.class);

    private static final String DESCRIPTION_KEY_PREFIX = "content_";
    private static final String TOOLTIP_KEY_PREFIX = "tooltip_";
    private static final String TITLE_KEY_PREFIX = "title_";
    private static final String LINK_KEY_PREFIX = "link_";
    private static final String TODAY_ONLY_KEY = "todayOnlyText";
    private static final String LEARN_MORE_KEY = "learnMoreText";

    @Override
    protected void initModel(SlingHttpServletRequest request, SlingHttpServletResponse response, ProductListSelector valueObject, Map<String, Object> model) {
        super.initModel(request, response, valueObject, model);

        model.put("l10n", LocalizationReader.findLocalizationByRequest(request));

        String globalSite = GlobalSettingsReader.getInstance().findSettings(request.getResource().adaptTo(Node.class)).getGlobalSite();
        model.put("globalSite", globalSite);

        ValueMap resourceMap = request.getResource().adaptTo(ValueMap.class);
        JSONObject productContent = new JSONObject();

        for (CommonUtils.ProductLevel productLevel : CommonUtils.ProductLevel.values()) {
            populateJSONObjectFromValueMap(productContent, resourceMap, DESCRIPTION_KEY_PREFIX + productLevel.name());
            populateJSONObjectFromValueMap(productContent, resourceMap, TOOLTIP_KEY_PREFIX + productLevel.name());
            populateJSONObjectFromValueMap(productContent, resourceMap, TITLE_KEY_PREFIX + productLevel.name());
            populateJSONObjectFromValueMap(productContent, resourceMap, LINK_KEY_PREFIX + productLevel.name());
        }

        populateJSONObjectFromValueMap(productContent, resourceMap, TODAY_ONLY_KEY);
        populateJSONObjectFromValueMap(productContent, resourceMap, LEARN_MORE_KEY);

        model.put("productContent", productContent.toString());
    }

    private void populateJSONObjectFromValueMap(JSONObject jsonObject, ValueMap valueMap, String key) {
        try {
            if (valueMap.containsKey(key)) {
                jsonObject.put(key, valueMap.get(key));
            }
        } catch (JSONException e) {
            LOG.error("ProductListSelectorServlet error", e);
        }
    }

    @Override
    protected ProductListSelector loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Resource resource = request.getResource();
        return resource.adaptTo(ProductListSelector.class);
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
            ProductListSelector valueObject, Map<String, Object> model) {
				LOG.info("inside get view name");
        return "productListSelector";
    }

    @Override
    protected String getGetJspPath() {
 				LOG.info("inside get jsp Path name ++++++++++++++++++++");
        return "cqecom/components/content/rsProductListSelector/views";
    }

    @Override
    protected String getValueObjectName() {
        return "product";
    }
}
