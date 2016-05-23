package com.cqecom.cms.components.ratingAndReview;

import java.util.List;


public class RatingAndReview {

    private String rating;

    private List<Review> reviews;

    private Integer ratingPanelWidth;

    private Integer ratingPanelHeight;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Integer getRatingPanelWidth() {
        return ratingPanelWidth;
    }

    public void setRatingPanelWidth(Integer ratingPanelWidth) {
        this.ratingPanelWidth = ratingPanelWidth;
    }

    public Integer getRatingPanelHeight() {
        return ratingPanelHeight;
    }

    public void setRatingPanelHeight(Integer ratingPanelHeight) {
        this.ratingPanelHeight = ratingPanelHeight;
    }
}
