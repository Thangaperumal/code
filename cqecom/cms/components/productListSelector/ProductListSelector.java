package com.cqecom.cms.components.productListSelector;

public class ProductListSelector {

    public enum ProductEditionType {
        /**
         * Personal edition
         */
        PE,
        /**
         * Homeschool edition
         */
        HS,
         /**
         * ReFlex edition
         */
        RF
    }

    public enum ProductsOrder {
        direct, reverse
    }

    private ProductsOrder productsOrder;

    private String displayStyle;

    private ProductEditionType productEdition;

    private String productImageRef;

    private String leftArrowImageRef;

    private String leftArrowHoverImageRef;

    private String rightArrowImageRef;

    private String rightArrowHoverImageRef;

    private Integer componentHeight;

    private String addToCartImageRef;

    private String addToCartHoverImageRef;

    private boolean showOnlineAccess;

    public boolean isShowOnlineAccess() {
        return showOnlineAccess;
    }

    public void setShowOnlineAccess(boolean showOnlineAccess) {
        this.showOnlineAccess = showOnlineAccess;
    }

    public String getAddToCartImageRef() {
            return addToCartImageRef;
    }

    public void setAddToCartImageRef(String addToCartImageRef) {
        this.addToCartImageRef = addToCartImageRef;
    }

    public String getAddToCartHoverImageRef() {
        return addToCartHoverImageRef;
    }

    public void setAddToCartHoverImageRef(String addToCartHoverImageRef) {
        this.addToCartHoverImageRef = addToCartHoverImageRef;
    }


    public String getDisplayStyle() {
            return displayStyle;
    }

    public void setDisplayStyle(String displayStyle) {
            this.displayStyle = displayStyle;
    }

    public ProductEditionType getProductEdition() {
            return productEdition;
    }

    public void setProductEdition(ProductEditionType productEdition) {
            this.productEdition = productEdition;
    }

    public String getProductImageRef() {
        return productImageRef;
    }

    public void setProductImageRef(String productImageRef) {
        this.productImageRef = productImageRef;
    }

    public ProductsOrder getProductsOrder() {
        return productsOrder;
    }

    public void setProductsOrder(ProductsOrder productsOrder) {
        this.productsOrder = productsOrder;
    }

    public String getLeftArrowImageRef() {
        return leftArrowImageRef;
    }

    public void setLeftArrowImageRef(String leftArrowImageRef) {
        this.leftArrowImageRef = leftArrowImageRef;
    }

    public String getLeftArrowHoverImageRef() {
        return leftArrowHoverImageRef;
    }

    public void setLeftArrowHoverImageRef(String leftArrowHoverImageRef) {
        this.leftArrowHoverImageRef = leftArrowHoverImageRef;
    }

    public String getRightArrowImageRef() {
        return rightArrowImageRef;
    }

    public void setRightArrowImageRef(String rightArrowImageRef) {
        this.rightArrowImageRef = rightArrowImageRef;
    }

    public String getRightArrowHoverImageRef() {
        return rightArrowHoverImageRef;
    }

    public void setRightArrowHoverImageRef(String rightArrowHoverImageRef) {
        this.rightArrowHoverImageRef = rightArrowHoverImageRef;
    }

    public Integer getComponentHeight() {
        return componentHeight;
    }

    public void setComponentHeight(Integer componentHeight) {
        this.componentHeight = componentHeight;
    }
}