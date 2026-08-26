package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "profilestable")
public class ProfilesTableEntity {

    @Id
    private final  long profileid;
    private final  long userid;
    private final  String profilename;
    private final  String profiledescription;
    private final  Date creationdate;
    private final  boolean active;
    private final  float mincost;
    private final  float maxcost;
    private final  String projectcatagory;
    private final float[][] preferencevector;
    private final int photointeractions;

    public ProfilesTableEntity(long profileid, long userid, String profilename, String profiledescription, Date creationdate, boolean active, float mincost, float maxcost, String projectcatagory, float[][] preferencevector, int photointeractions) {
        this.profileid = profileid;
        this.userid = userid;
        this.profilename = profilename;
        this.profiledescription = profiledescription;
        this.creationdate = creationdate;
        this.active = active;
        this.mincost = mincost;
        this.maxcost = maxcost;
        this.projectcatagory = projectcatagory;
        this.preferencevector = preferencevector;
        this.photointeractions = photointeractions;
    }

    public int getPhotointeractions() {
        return photointeractions;
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

    public Date getCreationdate() {
        return creationdate;
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

    public String getProjectcatagory() {
        return projectcatagory;
    }

    public float[][] getPreferencevector() {
        return preferencevector;
    }
}
