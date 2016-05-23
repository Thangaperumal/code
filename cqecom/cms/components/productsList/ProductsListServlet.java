package com.cqecom.cms.components.productsList;

import java.util.Map;

import javax.servlet.Servlet;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.components.AbstractMvcServlet;

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/qbProductsList"),
        @Property(name = "sling.servlet.extensions", value = "html")
})

public class ProductsListServlet extends AbstractMvcServlet<ProductsList> {

		private static final Logger LOG = LoggerFactory.getLogger(ProductsListServlet.class);

    @Override
    protected ProductsList loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Resource resource = request.getResource();
        ProductsList list = resource.adaptTo(ProductsList.class);
        return list;
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
            ProductsList valueObject, Map<String, Object> model) {
				LOG.info("inside get view name");
        return "qbProductsList";
    }

    @Override
    protected String getGetJspPath() {
 				LOG.info("inside get jsp Path name ++++++++++++++++++++");
        return "cqecom/components/content/qbProductsList/views";
    }

    @Override
    protected String getValueObjectName() {
        return "list";
    }
}