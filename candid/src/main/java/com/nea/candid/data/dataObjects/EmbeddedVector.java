package com.nea.candid.data.dataObjects;

public class EmbeddedVector {

    private final float brightnessMean;
    private final float brightnessDev;
    private final float dynamicRange;
    private float shadows;
    private float highlights;
    private final float saturationMean;
    private final float saturationDev;
    private final float colourCoverage;
    private final float[] colours;
    private final float sharpness;
    private final float edgeDensity;
    private final  float entropy;

    public EmbeddedVector(float brightnessMean, float brightnessDev, float dynamicRange, float shadows, float highlights, float saturationMean, float saturationDev, float colourCoverage, float[] colours, float sharpness, float edgeDensity, float entropy) {
        this.brightnessMean = brightnessMean;
        this.brightnessDev = brightnessDev;
        this.dynamicRange = dynamicRange;
        this.shadows = shadows;
        this.highlights = highlights;
        this.saturationMean = saturationMean;
        this.saturationDev = saturationDev;
        this.colourCoverage = colourCoverage;
        this.colours = colours;
        this.sharpness = sharpness;
        this.edgeDensity = edgeDensity;
        this.entropy = entropy;
    }

    public float getBrightnessMean() {
        return brightnessMean;
    }

    public float getBrightnessDev() {
        return brightnessDev;
    }

    public float getDynamicRange() {
        return dynamicRange;
    }

    public float getShadows() {
        return shadows;
    }

    public float getHighlights() {
        return highlights;
    }

    public float getSaturationMean() {
        return saturationMean;
    }

    public float getSaturationDev() {
        return saturationDev;
    }

    public float getColourCoverage() {
        return colourCoverage;
    }

    public float[] getColours() {
        return colours;
    }

    public float getSharpness() {
        return sharpness;
    }

    public float getEdgeDensity() {
        return edgeDensity;
    }

    public float getEntropy() {
        return entropy;
    }

    public void setShadows(float shadows) {
        this.shadows = shadows;
    }

    public void setHighlights(float highlights) {
        this.highlights = highlights;
    }
}
