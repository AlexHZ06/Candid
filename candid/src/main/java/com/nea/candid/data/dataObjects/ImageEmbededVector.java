package com.nea.candid.data.dataObjects;

public class ImageEmbededVector {

    private float[] brightness;
    private float[] saturation;
    private float[] hue;
    private float edgeDensity;
    private float aspectRatio;

    public ImageEmbededVector(float[] brightness, float[] saturation, float[] hue, float edgeDensity, float aspectRatio) {
        this.brightness = brightness;
        this.saturation = saturation;
        this.hue = hue;
        this.edgeDensity = edgeDensity;
        this.aspectRatio = aspectRatio;
    }

    public float[] getBrightness() {
        return brightness;
    }

    public float[] getSaturation() {
        return saturation;
    }

    public float[] getHue() {
        return hue;
    }

    public float getEdgeDensity() {
        return edgeDensity;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }
}