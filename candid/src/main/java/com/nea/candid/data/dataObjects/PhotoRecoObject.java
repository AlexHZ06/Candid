package com.nea.candid.data.dataObjects;

import com.nea.candid.data.dbEnties.PhotosTableEntity;

public class PhotoRecoObject {

    private PhotosTableEntity photos;
    private float score;

    public PhotoRecoObject(PhotosTableEntity photos, float score) {
        this.photos = photos;
        this.score = score;
    }

    public PhotosTableEntity getPhotos() {
        return photos;
    }

    public float getScore() {
        return score;
    }
}
