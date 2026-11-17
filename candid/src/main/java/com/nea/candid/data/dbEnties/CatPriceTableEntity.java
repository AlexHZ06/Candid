package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CatPriceTableEntity {

    @Id
    private long catpriceid;
    private long userid;
    private String category;
    private float avprice;

    public CatPriceTableEntity(long catpriceid, long userid, String category, float avprice) {
        this.catpriceid = catpriceid;
        this.userid = userid;
        this.category = category;
        this.avprice = avprice;
    }

    public long getCatpriceid() {
        return catpriceid;
    }

    public long getUserid() {
        return userid;
    }

    public String getCategory() {
        return category;
    }

    public float getAvprice() {
        return avprice;
    }
}
