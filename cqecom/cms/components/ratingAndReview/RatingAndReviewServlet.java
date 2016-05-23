package com.cqecom.cms.components.ratingAndReview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;

import com.cqecom.cms.components.AbstractMvcServlet;

@Component
@Service(Servlet.class)
@Properties(value = {
        @Property(name = "sling.servlet.resourceTypes", value = "cqecom/components/content/rsRatingAndReview"),
        @Property(name = "sling.servlet.extensions", value = "html")
})
public class RatingAndReviewServlet extends AbstractMvcServlet<RatingAndReview> {

    private final String imagePath = "/content/dam/cqecomcom/images/personal/stars";

    @Override
    protected RatingAndReview loadValueObject(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        Resource resource = request.getResource();
        return resource.adaptTo(RatingAndReview.class);
    }

    @Override
    protected String getViewName(SlingHttpServletRequest request, SlingHttpServletResponse response,
            RatingAndReview valueObject, Map<String, Object> model) {
        return "RatingAndReview";
    }

    @Override
    protected String getValueObjectName() {
        return "rating";
    }

    @Override
    protected String getGetJspPath() {
        return "cqecom/components/content/rsRatingAndReview/views";
    }

    @Override
    protected void initModel(SlingHttpServletRequest request, SlingHttpServletResponse response,
            RatingAndReview valueObject, Map<String, Object> model) {
        List<String> images = new ArrayList<String>();
        images.add(imagePath + "/rating-1_0.gif");
        images.add(imagePath + "/rating-2_0.gif");
        images.add(imagePath + "/rating-3_0.gif");
        images.add(imagePath + "/rating-4_0.gif");
        images.add(imagePath + "/rating-5_0.gif");
        model.put("images", images);
    }
}
