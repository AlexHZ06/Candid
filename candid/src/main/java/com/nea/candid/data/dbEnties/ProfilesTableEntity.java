package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "profilestable")
public class ProfilesTableEntity {

    @Id
    private long profileid;
    private long userid;
    private String profilename;
    private String profiledescription;
    private Date creationdate;
    private boolean active;
    private float mincost;
    private float maxcost;
    private String projectcatagory;

    public ProfilesTableEntity(long profileid, long userid, String profilename, String profiledescription, Date creationdate, boolean active, float mincost, float maxcost, String projectcatagory) {
        this.profileid = profileid;
        this.userid = userid;
        this.profilename = profilename;
        this.profiledescription = profiledescription;
        this.creationdate = creationdate;
        this.active = active;
        this.mincost = mincost;
        this.maxcost = maxcost;
        this.projectcatagory = projectcatagory;
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
}
