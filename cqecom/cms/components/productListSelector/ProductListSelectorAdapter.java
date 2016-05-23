package com.cqecom.cms.components.productListSelector;

import com.cqecom.cms.components.banner.BannerType;

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
    @Property(name = "adapters", value = {"com.cqecom.cms.components.productListSelector.ProductListSelector" })
})
@Service(value = AdapterFactory.class)

public class ProductListSelectorAdapter implements AdapterFactory {

    /**
     * @return ProductsList object or null if resource doesn't exist
     */
    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ValueMap values = ((Resource) adaptable).adaptTo(ValueMap.class);
        ProductListSelector product = null;
        if (values != null) {
            product = new ProductListSelector();

            product.setDisplayStyle(values.get("displayStyle", "slide"));

            product.setProductImageRef(values.get("productImageReference", ""));

            product.setLeftArrowImageRef(values.get("leftArrowImageReference", ""));

            product.setLeftArrowHoverImageRef(values.get("leftArrowHoverImageReference", ""));

            product.setRightArrowImageRef(values.get("rightArrowImageReference", ""));

            product.setRightArrowHoverImageRef(values.get("rightArrowHoverImageReference", ""));

            product.setComponentHeight(values.get("componentHeight", 256));

            product.setAddToCartImageRef(values.get("atcimageReference",""));

            product.setAddToCartHoverImageRef(values.get("atchoverimageReference",""));

            product.setShowOnlineAccess(values.get("showOnlineAccess",false));

            String productEdition = values.get("productEdition", String.class);
            if (productEdition != null) {
                product.setProductEdition(ProductListSelector.ProductEditionType.valueOf(productEdition));
            }

            String productsOrder = values.get("productsOrder", "direct");
            if (productsOrder != null) {
                product.setProductsOrder(ProductListSelector.ProductsOrder.valueOf(productsOrder));
            }
        }

        return (AdapterType) product;
    }
}