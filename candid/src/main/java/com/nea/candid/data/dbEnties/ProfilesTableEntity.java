package com.nea.candid.data.dbEnties;

import java.sql.Date;

public class ProfilesTableEntity {

    private final  long profileid;
    private final  long userid;
    private final  String profilename;
    private final  String profiledescription;
    private final  boolean active;
    private final  float mincost;
    private final  float maxcost;
    private final  String projectcatagory;
    private final float[][] preferencevector;
    private final float[][] dislikesvector;
    private final double latitude;
    private final double longitude;

    public ProfilesTableEntity(long profileid, long userid, String profilename, String profiledescription, boolean active, float mincost, float maxcost, String projectcatagory, float[][] preferencevector, float[][] dislikesvector, double latitude, double longitude) {
        this.profileid = profileid;
        this.userid = userid;
        this.profilename = profilename;
        this.profiledescription = profiledescription;
        this.active = active;
        this.mincost = mincost;
        this.maxcost = maxcost;
        this.projectcatagory = projectcatagory;
        this.preferencevector = preferencevector;
        this.dislikesvector = dislikesvector;

        this.latitude = latitude;
        this.longitude = longitude;
    }


    public long getProfileid() {
        return profileid;
    }

    public long getUserid() {
        return userid;
    }

    public String getProfilename() {
        return profilename;
    }

    public String getProfiledescription() {
        return profiledescription;
    }

    public boolean isActive() {
        return active;
    }

    public float getMincost() {
        return mincost;
    }

    public float getMaxcost() {
        return maxcost;
    }

    public float[][] getDislikesvector() {
        return dislikesvector;
    }

    public String getProjectcatagory() {
        return projectcatagory;
    }

    public float[][] getPreferencevector() {
        return preferencevector;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
