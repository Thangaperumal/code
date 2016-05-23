package com.cqecom.cms.components.ratingAndReview;

import org.apache.commons.lang.StringUtils;


public class Review {

    private String header;

    private Integer rating;

    private String text;

    private String reviewer;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public boolean isFilled() {
        return StringUtils.isNotBlank(header) || StringUtils.isNotBlank(reviewer) || StringUtils.isNotBlank(text);
    }
}
