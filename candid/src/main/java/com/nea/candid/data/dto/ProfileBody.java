package com.nea.candid.data.dto;

public class ProfileBody {

    private String profileName;
    private String getProfileDescription;
    private float minCost;
    private float maxCost;
    private String PhotoCategory;

    public ProfileBody(String profileName, String getProfileDescription, float minCost, float maxCost, String photoCategory) {
        this.profileName = profileName;
        this.getProfileDescription = getProfileDescription;
        this.minCost = minCost;
        this.maxCost = maxCost;
        PhotoCategory = photoCategory;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getGetProfileDescription() {
        return getProfileDescription;
    }

    public float getMinCost() {
        return minCost;
    }

    public float getMaxCost() {
        return maxCost;
    }

    public String getPhotoCategory() {
        return PhotoCategory;
    }
}
