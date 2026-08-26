package com.nea.candid.data.dataObjects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageProfileObject {

    private final BufferedImage image;
    private final ArrayList<EmbeddedVector> embeddedVectors =  new ArrayList<>();
    private EmbeddedVector globalEmbeddedVector;
    private float[] globalVector;
    private final ArrayList<float[]> sectionVectors =  new ArrayList<>();

    public ImageProfileObject(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public ArrayList<EmbeddedVector> getEmbededVectors() {
        return embeddedVectors;
    }



    public EmbeddedVector getGlobalEmbeddedVector() {
        return globalEmbeddedVector;
    }

    public void setGlobalEmbeddedVector(EmbeddedVector globalEmbeddedVector) {
        this.globalEmbeddedVector = globalEmbeddedVector;
    }

    public ArrayList<EmbeddedVector> getEmbeddedVectors() {
        return embeddedVectors;
    }

    public float[] getGlobalVector() {
        return globalVector;
    }

    public void setGlobalVector(float[] globalVector) {
        this.globalVector = globalVector;
    }

    public ArrayList<float[]> getSectionVectors() {
        return sectionVectors;
    }
}
