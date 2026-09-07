package com.nea.candid.data.dto;

import java.sql.Date;

public class ProfileRequest {

    private final  String profilename;
    private final  String profiledescription;
    private final  float mincost;
    private final  float maxcost;
    private final  String projectcatagory;
    private final double latitude;
    private final double longitude;
    private final long[] likes;
    private final long[] dislikes;

    public ProfileRequest(String profilename, String profiledescription, float mincost, float maxcost, String projectcatagory, double latitude, double longitude, long[] likes, long[] dislikes) {

        this.profilename = profilename;
        this.profiledescription = profiledescription;
        this.mincost = mincost;
        this.maxcost = maxcost;
        this.projectcatagory = projectcatagory;
        this.latitude = latitude;
        this.longitude = longitude;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public String getProfilename() {
        return profilename;
    }

    public String getProfiledescription() {
        return profiledescription;
    }

    public float getMincost() {
        return mincost;
    }

    public float getMaxcost() {
        return maxcost;
    }

    public String getProjectcatagory() {
        return projectcatagory;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long[] getLikes() {
        return likes;
    }

    public long[] getDislikes() {
        return dislikes;
    }
}
