package com.nea.candid.data.dataObjects;

public class FeedRequestObject {

    private String search;
    private String category;
    private long userId;
    private float minPrice;
    private float maxPrice;
    private float radius;

    public FeedRequestObject(String search, String category, long userId, float minPrice, float maxPrice, float radius) {
        this.search = search;
        this.category = category;
        this.userId = userId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public String getSearch() {
        return search;
    }

    public String getCategory() {
        return category;
    }

    public long getUserId() {
        return userId;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }
}
