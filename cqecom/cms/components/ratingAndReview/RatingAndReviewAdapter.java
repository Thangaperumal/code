package com.cqecom.cms.components.ratingAndReview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;

@Component
@Properties({
    @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource" }),
    @Property(name = "adapters", value = {"com.cqecom.cms.components.ratingAndReview.RatingAndReview" })
})
@Service(value = AdapterFactory.class)
public class RatingAndReviewAdapter implements AdapterFactory {

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        Resource resource = (Resource) adaptable;
        ValueMap values = resource.adaptTo(ValueMap.class);
        RatingAndReview rating = new RatingAndReview();
        rating.setRating(values.get("rating", String.class));
        rating.setRatingPanelWidth(values.get("ratingPanelWidth", 456));
        rating.setRatingPanelHeight(values.get("ratingPanelHeight", 197));

        List<Review> reviews = new ArrayList<Review>();
        Iterator<Resource> iterator = ResourceUtil.listChildren(resource);
        while (iterator.hasNext()) {
            Resource childResource = iterator.next();
            ValueMap childValues = childResource.adaptTo(ValueMap.class);
            Review review = new Review();
            review.setHeader(childValues.get("header", String.class));
            review.setRating(childValues.get("rating", Integer.class));
            review.setText(childValues.get("text", String.class));
            review.setReviewer(childValues.get("reviewer", String.class));
            reviews.add(review);
        }
        rating.setReviews(reviews);
        return (AdapterType) rating;
    }
}
