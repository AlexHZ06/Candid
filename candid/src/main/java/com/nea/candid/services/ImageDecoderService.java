package com.nea.candid.services;

import com.nea.candid.data.dataObjects.ImageProfileObject;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class ImageDecoderService {

    //This is my favourite class as it has all the interesting maths and embedded vectorisation

    private final EmbeddedVectorService imageProfileService;

    private final float hueThreshold = 0.25F;

    public ImageDecoderService(EmbeddedVectorService imageProfileService) {
        this.imageProfileService = imageProfileService;
    }

    public void calculateValues(ImageProfileObject imageProfileObject) {

        ArrayList<int[][]> sampleBounds = extractSampleBounds(imageProfileObject.getImage(), 4);
        ArrayList<ArrayList<Float>> globalBrightnessList = new ArrayList<>();

        final ArrayList<Float> globalBrightness = new ArrayList<>();
        final ArrayList<int[]> globalPixelsArgb = new ArrayList<>();
        final ArrayList<int[]> globalPixels = new ArrayList<>();

        int sampleSize = sampleBounds.size();

        for(int i = 0; i < sampleSize; i++) {

            ArrayList<int[]> samplePixels = extractSamplePixels(sampleBounds.get(i)[0][0], sampleBounds.get(i)[0][1], sampleBounds.get(i)[1][0], sampleBounds.get(i)[1][1], imageProfileObject.getImage());
            ArrayList<int[]> samplePixelsArgb = extractSamplePixelsArgb(samplePixels, imageProfileObject.getImage());
            ArrayList<Float> sampleBrightness = extractPixelsBrightness(samplePixelsArgb);
            globalBrightness.addAll(sampleBrightness);
            globalPixelsArgb.addAll(samplePixelsArgb);
            globalPixels.addAll(samplePixels);
            globalBrightnessList.add(sampleBrightness);

            float[] brightness = calculateSectionBrightness(sampleBrightness);
            float[] saturation = calculateSectionSaturation(samplePixelsArgb);
            float[] hue = calculateSectionHue(samplePixelsArgb);
            float[] shadowHighlights = {0,0};
            float[] texture = new float[3];
            texture[0] = calculateSharpness(sampleBrightness, samplePixels, imageProfileObject.getImage());
            texture[1] = calculateEdgeDensity(imageProfileObject.getImage(), brightness[2], sampleBrightness, samplePixels);
            texture[2] = calculateSectionEntropy(sampleBrightness);

            imageProfileService.addVector(imageProfileObject, brightness, saturation, hue, shadowHighlights, texture);

        }

        for(int i = 0; i < imageProfileObject.getEmbededVectors().size(); i++) {

            float[] shadowHighlights = calculateShadowHighlights(globalBrightnessList.get(i), globalBrightness);
            imageProfileObject.getEmbededVectors().get(i).setHighlights(shadowHighlights[1]);
            imageProfileObject.getEmbededVectors().get(i).setShadows(shadowHighlights[0]);

        }

        float[] brightness = calculateSectionBrightness(globalBrightness);
        float[] saturation = calculateSectionSaturation(globalPixelsArgb);
        float[] hue = calculateSectionHue(globalPixelsArgb);
        float[] shadowHighlights = calculateShadowHighlights(globalBrightness, globalBrightness);
        float[] texture = new float[3];
        texture[0] = calculateSharpness(globalBrightness, globalPixels, imageProfileObject.getImage());
        texture[1] = calculateEdgeDensity(imageProfileObject.getImage(), brightness[2], globalBrightness, globalPixels);
        texture[2] = calculateSectionEntropy(globalBrightness);

        imageProfileService.setGlobalVector(imageProfileObject, brightness, saturation, hue, shadowHighlights, texture);
        imageProfileService.generateSectionVectors(imageProfileObject);
        imageProfileService.generateGlobalVector(imageProfileObject) ;

    }

    public void printImageValues(ImageProfileObject imageProfileObject) {

        System.out.println("-----Global-----");
        System.out.println("Brightness Mean " + imageProfileObject.getGlobalEmbeddedVector().getBrightnessMean());
        System.out.println("Brightness Dev " + imageProfileObject.getGlobalEmbeddedVector().getBrightnessDev());
        System.out.println("Saturation Mean " + imageProfileObject.getGlobalEmbeddedVector().getSaturationMean());
        System.out.println("Saturation Dev " + imageProfileObject.getGlobalEmbeddedVector().getSaturationDev());
        System.out.println("highlights" + imageProfileObject.getGlobalEmbeddedVector().getHighlights());
        System.out.println("shadows" + imageProfileObject.getGlobalEmbeddedVector().getShadows());
        System.out.println("colour coverage " + imageProfileObject.getGlobalEmbeddedVector().getColourCoverage());
        System.out.println("sharpness " + imageProfileObject.getGlobalEmbeddedVector().getSharpness());
        System.out.println("Red " + imageProfileObject.getGlobalEmbeddedVector().getColours()[0]);
        System.out.println("orange " + imageProfileObject.getGlobalEmbeddedVector().getColours()[1]);
        System.out.println("yellow " + imageProfileObject.getGlobalEmbeddedVector().getColours()[2]);
        System.out.println("green " + imageProfileObject.getGlobalEmbeddedVector().getColours()[3]);
        System.out.println("cyan " + imageProfileObject.getGlobalEmbeddedVector().getColours()[4]);
        System.out.println("blue " + imageProfileObject.getGlobalEmbeddedVector().getColours()[5]);
        System.out.println("purple " + imageProfileObject.getGlobalEmbeddedVector().getColours()[6]);
        System.out.println("magenta " + imageProfileObject.getGlobalEmbeddedVector().getColours()[7]);
        System.out.println("sharpness" + imageProfileObject.getGlobalEmbeddedVector().getSharpness());
        System.out.println("edgeDensity" + imageProfileObject.getGlobalEmbeddedVector().getEdgeDensity());
        System.out.println("entropy" + imageProfileObject.getGlobalEmbeddedVector().getShadows());

        for(int i = 0; i < imageProfileObject.getEmbededVectors().size(); i++) {

            System.out.println("-----section " + i + "-----");
            System.out.println("Brightness Mean " + imageProfileObject.getEmbededVectors().get(i).getBrightnessMean());
            System.out.println("Brightness Dev " + imageProfileObject.getEmbededVectors().get(i).getBrightnessDev());
            System.out.println("Saturation Mean " + imageProfileObject.getEmbededVectors().get(i).getSaturationMean());
            System.out.println("Saturation Dev " + imageProfileObject.getEmbededVectors().get(i).getSaturationDev());
            System.out.println("highlights" + imageProfileObject.getEmbededVectors().get(i).getHighlights());
            System.out.println("shadows" + imageProfileObject.getEmbededVectors().get(i).getShadows());
            System.out.println("colour coverage " + imageProfileObject.getEmbededVectors().get(i).getColourCoverage());
            System.out.println("sharpness " + imageProfileObject.getEmbededVectors().get(i).getSharpness());
            System.out.println("Red " + imageProfileObject.getEmbededVectors().get(i).getColours()[0]);
            System.out.println("orange " + imageProfileObject.getEmbededVectors().get(i).getColours()[1]);
            System.out.println("yellow " + imageProfileObject.getEmbededVectors().get(i).getColours()[2]);
            System.out.println("green " + imageProfileObject.getEmbededVectors().get(i).getColours()[3]);
            System.out.println("cyan " + imageProfileObject.getEmbededVectors().get(i).getColours()[4]);
            System.out.println("blue " + imageProfileObject.getEmbededVectors().get(i).getColours()[5]);
            System.out.println("purple " + imageProfileObject.getEmbededVectors().get(i).getColours()[6]);
            System.out.println("magenta " + imageProfileObject.getEmbededVectors().get(i).getColours()[7]);
            System.out.println("sharpness" + imageProfileObject.getEmbededVectors().get(i).getSharpness());
            System.out.println("edgeDensity" + imageProfileObject.getEmbededVectors().get(i).getEdgeDensity());
            System.out.println("entropy" + imageProfileObject.getEmbededVectors().get(i).getEntropy());

        }

    }

    private ArrayList<int[][]> extractSampleBounds(BufferedImage image, int divisions) {

        ArrayList<int[][]> sectionDivision = new ArrayList<>();
        float divisionPercentage = (float)(100 / divisions) / 100;

        ArrayList<int[]> xSectionDivision = new ArrayList<>();
        ArrayList<int[]> ySectionDivision = new ArrayList<>();

        float placeHolder = -1 * divisionPercentage;

        for(int i =0; i < divisions -1; i++){

            int startPixel = Math.round((placeHolder + divisionPercentage) * image.getWidth());
            placeHolder = placeHolder + divisionPercentage;
            int endPixel = Math.round((placeHolder + divisionPercentage) * image.getWidth());

            for(int j = 0; j < divisions; j++) {
                xSectionDivision.add(new int[]{startPixel, endPixel});
            }

        }

        for(int i = 0; i < divisions; i++){
            xSectionDivision.add(new int[]{Math.round((placeHolder + divisionPercentage) * image.getWidth()), image.getWidth()});
        }

        placeHolder = -1 * divisionPercentage;

        for(int i = 0; i < divisions - 1; i++){

            int startPixel = Math.round((placeHolder + divisionPercentage) * image.getHeight());
            placeHolder = placeHolder + divisionPercentage;
            int endPixel = Math.round((placeHolder + divisionPercentage) * image.getHeight());

            ySectionDivision.add(new int[]{startPixel, endPixel});

        }

        ySectionDivision.add(new int[]{Math.round((placeHolder + divisionPercentage) * image.getHeight()), image.getHeight()});

        int index = 0;

        for(int i = 0; i < xSectionDivision.size(); i++){

            int startXpix = xSectionDivision.get(i)[0];
            int endtXpix = xSectionDivision.get(i)[1];
            int startYpix = ySectionDivision.get(index)[0];
            int endYpix = ySectionDivision.get(index)[1];

            sectionDivision.add(new int[][]{{startXpix, startYpix},{endtXpix, endYpix}});

            if(index >= divisions - 1){

                index = 0;

            }
            else{

                index++;

            }


        }

        return sectionDivision;

    }

    private int[] convertArgb(int rgb) {

        String binary = String.format("%32s", Integer.toBinaryString(rgb)).replace(' ', '0');
        return new int[]{
                Integer.parseInt(binary.substring(0, 8), 2),
                Integer.parseInt(binary.substring(8, 16), 2),
                Integer.parseInt(binary.substring(16, 24), 2),
                Integer.parseInt(binary.substring(24, 32), 2),
        };

    }

    private ArrayList<int[]> extractSamplePixels(int startX, int startY, int endX, int endY, BufferedImage image) {

        if(startX >= endX || startY >= endY) {

            throw new IllegalArgumentException("startX or endX out of bounds");

        }

        double xDiff = endX - startX;
        double yDiff = endY - startY;

        int totalXpix = Math.max(1, (int) (xDiff * 0.3));
        int totalYpix = Math.max(1, (int) (yDiff * 0.3));

        double xStep = xDiff / (totalXpix);
        double yStep = yDiff / (totalYpix);


        ArrayList<int[]> pixels = new ArrayList<>();

        for (int i = 1; i < totalXpix + 1; i++) {

            for (int j = 1; j < totalYpix + 1; j++) {

                int x = Math.min(image.getWidth() - 1, (int) (startX + (i * xStep)));
                int y = Math.min(image.getHeight() - 1, (int) (startY + (j * yStep)));

                pixels.add(new  int[]{x, y});

            }
        }

        return pixels;

    }

    private ArrayList<int[]> extractSamplePixelsArgb(ArrayList<int[]> samplePixels, BufferedImage image) {

        ArrayList<int[]> aRgbs = new ArrayList<>();

        for(int i = 0; i < samplePixels.size(); i++){

            int x =  samplePixels.get(i)[0];
            int y =  samplePixels.get(i)[1];

            int[] argb = convertArgb(image.getRGB(x, y));

            aRgbs.add(argb);

        }

        return aRgbs;

    }

    private ArrayList<Float> extractPixelsBrightness(ArrayList<int[]> extractArgbs) {

        ArrayList<Float> sectionBrightness = new ArrayList<>();

        for(int i = 0; i < extractArgbs.size(); i++) {

            float brightness = 0.2126f * extractArgbs.get(i)[1] +  0.7152f * extractArgbs.get(i)[2] +   0.0722f * extractArgbs.get(i)[3];
            sectionBrightness.add(brightness);
        }

        return sectionBrightness;

    }

    private float[] calculateSectionBrightness(ArrayList<Float> sectionBrightness) {

        double total = 0;
        int upperPercentile = (int) ((sectionBrightness.size() - 1) * 0.85);
        int bottomPercentile = (int) ((sectionBrightness.size() - 1) * 0.15);

        for (int i = 0; i < sectionBrightness.size(); i++) {

            total += sectionBrightness.get(i);


        }

        double mean = total / (sectionBrightness.size());
        double squaredDifference = 0;

        for (int i = 0; i < sectionBrightness.size(); i++) {

            squaredDifference += (sectionBrightness.get(i) - mean) * (sectionBrightness.get(i) - mean);

        }

        float brightnessDeviation = (float) (Math.sqrt(squaredDifference / (sectionBrightness.size()))) / 255;

        Collections.sort(sectionBrightness);

        float dynamicRange = (sectionBrightness.get(upperPercentile) -  sectionBrightness.get(bottomPercentile)) / 255;
        mean = mean / 255;

        return new float[]{(float) mean, brightnessDeviation, dynamicRange};


    }

    private float[] calculateShadowHighlights(ArrayList<Float> sectionBrightness, ArrayList<Float> globalBrightness) {

        int upperPercentileIndex = (int) Math.round(globalBrightness.size() * 0.85);
        int lowerPercentileIndex = (int) Math.round(globalBrightness.size() * 0.15);

        float globalBotBrightPrec = globalBrightness.get(lowerPercentileIndex);
        float globalTopBrightPrec = globalBrightness.get(upperPercentileIndex);

        calculateSectionBrightness(sectionBrightness);

        float shadow = 0;
        float highlights = 0;

        for(int i = 0; i < sectionBrightness.size(); i++) {

            if(sectionBrightness.get(i) < globalBotBrightPrec){shadow ++;}
            else if(sectionBrightness.get(i) > globalTopBrightPrec){highlights ++;}

        }

        shadow = shadow / sectionBrightness.size();
        highlights = highlights / sectionBrightness.size();

        return new float[]{shadow, highlights};

    }

    private float[] calculateSectionSaturation(ArrayList<int[]> extractArgbs) {

        ArrayList<Float> saturations = new ArrayList<>();
        float total = 0;
        int totalHuePixels = 0;

        for(int i = 0; i < extractArgbs.size(); i++) {

            int[] argb = extractArgbs.get(i);
            int max = -1;
            int min = 256;

            for (int k = 1; k < argb.length; k++) {

                if (argb[k] > max) {

                    max = argb[k];

                }
                if (argb[k] < min) {

                    min = argb[k];

                }

            }

            float saturation = 0;

            if (max != 0) {

                saturation = (float) (max - min) / max;

            }

            saturations.add(saturation);
            total += saturation;
            if(saturation >= hueThreshold){totalHuePixels++;}


        }

        double mean = (total / (extractArgbs.size()));

        double squaredDifference = 0;

        for (int i = 0; i < saturations.size(); i++) {

            squaredDifference += (saturations.get(i) - mean) * (saturations.get(i) - mean);

        }

        double saturationStdDev = Math.sqrt(squaredDifference / (extractArgbs.size()));

        float colourCoverage = (float) totalHuePixels / extractArgbs.size();

        return new float[]{(float) mean, (float) saturationStdDev, colourCoverage};

    }

    private float[] calculateSectionHue(ArrayList<int[]> extractArgbs) {

        float red = 0;
        float orange = 0;
        float yellow = 0;
        float yellowGreen = 0;
        float green = 0;
        float greenCyan = 0;
        float cyan = 0;
        float cyanBlue = 0;
        float blue = 0;
        float bluePurple = 0;
        float purple = 0;
        float magenta = 0;

        int totalPixels = 0;

        for(int i =0; i < extractArgbs.size(); i++){

            int[] argb = extractArgbs.get(i);

            int maxRgb = -1;
            int minRgb = 256;

            int highestIndex = 0;

            for(int k = 1; k < argb.length; k++) {

                if(argb[k] > maxRgb) {

                    maxRgb = argb[k];
                    highestIndex = k;

                }
                if(argb[k] < minRgb) {

                    minRgb = argb[k];

                }

            }

            int dif = maxRgb - minRgb;

            if(maxRgb == 0) {

                continue;

            }

            float sat = (float) (dif) / maxRgb;

            if(sat >= hueThreshold) {

                float hue = 0;
                totalPixels++;

                if(highestIndex == 1) {

                    hue = (

                            (float) 60 * ((float) (argb[2] - argb[3]) / dif)

                    );

                    if(hue < 0){

                        hue = hue + 360;

                    }
                    if(hue > 360) {

                        hue = 360;

                    }

                }
                if(highestIndex == 2) {

                    hue = (

                            (float) 60 * (((float) (argb[3] - argb[1]) / dif) + 2)

                    );

                    if(hue < 0){

                        hue = hue + 360;

                    }
                    if(hue > 360) {

                        hue = 360;

                    }

                }
                if(highestIndex == 3) {

                    hue = (

                            (float) 60 * (((float) (argb[1] - argb[2]) / dif) + 4)

                    );

                    if(hue < 0){

                        hue = hue + 360;

                    }
                    if(hue > 360) {

                        hue = 360;

                    }

                }

                if (hue < 30) {

                    red++;

                } else if (hue < 60) {

                    orange++;

                } else if (hue < 90) {

                    yellow++;

                } else if (hue < 120) {

                    yellowGreen++;

                } else if (hue < 150) {

                    green++;

                } else if (hue < 180) {

                    greenCyan++;

                } else if (hue < 210) {

                    cyan++;

                } else if (hue < 240) {

                    cyanBlue++;

                } else if (hue < 270) {

                    blue++;

                } else if (hue < 300) {

                    bluePurple++;

                } else if (hue < 330) {

                    purple++;

                } else {

                    magenta++;

                }

            }

        }

        if(totalPixels == 0) {

            return new float[]{0,0,0,0,0,0,0,0,0,0,0,0};

        }

        red /= totalPixels;
        orange /= totalPixels;
        yellow /= totalPixels;
        yellowGreen /= totalPixels;
        green /= totalPixels;
        greenCyan /= totalPixels;
        cyan /= totalPixels;
        cyanBlue /= totalPixels;
        blue /= totalPixels;
        bluePurple /= totalPixels;
        purple /= totalPixels;
        magenta /= totalPixels;

        return new float[]{
                red,
                orange,
                yellow,
                yellowGreen,
                green,
                greenCyan,
                cyan,
                cyanBlue,
                blue,
                bluePurple,
                purple,
                magenta
        };

    }

    private float calculateSharpness(ArrayList<Float> sectionBrightness, ArrayList<int[]> samplePixels, BufferedImage image) {

        ArrayList<Float> sharpnessList = new ArrayList<>();
        double total = 0;

        for(int  i = 0; i < samplePixels.size(); i++) {

            if((samplePixels.get(i)[0] + 1 < image.getWidth()) && (samplePixels.get(i)[0] - 1 >= 0) && (samplePixels.get(i)[1] + 1 < image.getHeight()) && (samplePixels.get(i)[1] - 1 >= 0)) {

                int[] leftArgb = convertArgb(image.getRGB((samplePixels.get(i)[0] - 1), samplePixels.get(i)[1]));
                int[] rightArgb = convertArgb(image.getRGB((samplePixels.get(i)[0] + 1), samplePixels.get(i)[1]));
                int[] downArgb = convertArgb(image.getRGB(samplePixels.get(i)[0], (samplePixels.get(i)[1] + 1)));
                int[] upArgb = convertArgb(image.getRGB(samplePixels.get(i)[0], (samplePixels.get(i)[1] - 1)));

                float brightnessLeft = 0.2126f * leftArgb[1] + 0.7152f * leftArgb[2] + 0.0722f * leftArgb[3];
                float brightnessRight = 0.2126f * rightArgb[1] + 0.7152f * rightArgb[2] + 0.0722f * rightArgb[3];
                float brightnessUp = 0.2126f * upArgb[1] + 0.7152f * upArgb[2] + 0.0722f * upArgb[3];
                float brightnessDown = 0.2126f * downArgb[1] + 0.7152f * downArgb[2] + 0.0722f * downArgb[3];

                float sharpness = Math.abs(brightnessRight + brightnessLeft + brightnessUp + brightnessDown - 4 * sectionBrightness.get(i));
                sharpnessList.add(sharpness);
                total += sharpness;

            }

        }

        return (float) (total / sharpnessList.size()) / 1020;

    }

    private float calculateEdgeDensity(BufferedImage image, float dynamicRange, ArrayList<Float> extractedPixelsBrightness, ArrayList<int[]> samplePixels) {

        double edgeCount = 0;

        int pixelComp = 0;

        float threshold = Math.max(8f, 15f * (dynamicRange / 128f));

        for(int i = 0; i < samplePixels.size(); i++) {

            float pixelBrightness = extractedPixelsBrightness.get(i);
            int x = samplePixels.get(i)[0];
            int y = samplePixels.get(i)[1];

            if(x + 1 < image.getWidth()){

                pixelComp++;
                int[] rightArgb = convertArgb(image.getRGB(x + 1, y));
                float brightness = 0.2126f * rightArgb[1] + 0.7152f * rightArgb[2] + 0.0722f * rightArgb[3];

                float diff = Math.abs(pixelBrightness -  brightness);
                if(diff >= threshold){edgeCount++;}

            }
            if(y + 1 < image.getHeight()){

                pixelComp++;
                int[] downArgb =  convertArgb(image.getRGB(x, y + 1));
                float brightness = 0.2126f * downArgb[1] + 0.7152f * downArgb[2] + 0.0722f * downArgb[3];

                float diff = Math.abs(pixelBrightness -  brightness);
                if(diff >= threshold){edgeCount++;}

            }

        }

        if(pixelComp == 0){

            return 0;

        }

        return (float) edgeCount / pixelComp;

    }

    private float calculateSectionEntropy(ArrayList<Float> sampleBrightness){

        float[] brightnessHistogram = new float[256];

        for(int i = 0; i < sampleBrightness.size(); i++) {

            brightnessHistogram[(int)(sampleBrightness.get(i) + 0)] ++;

        }

        for(int i = 0; i < brightnessHistogram.length; i++) {

            brightnessHistogram[i] =  brightnessHistogram[i] / sampleBrightness.size();

        }

        float entropy = 0;

        for(int i = 0; i < brightnessHistogram.length; i++) {

            if(brightnessHistogram[i] > 0){
                float value = (float) ((-1 * brightnessHistogram[i]) * ((Math.log(brightnessHistogram[i]) / Math.log(2))));
                entropy += value;
            }
        }

        return entropy / 8;

    }

    public BufferedImage createThumbnail(ImageProfileObject imageProfileObject, int maxWidth){

        int originalWidth = imageProfileObject.getImage().getWidth();
        int originalHeight = imageProfileObject.getImage().getHeight();

        double scale = (double) maxWidth / originalWidth;
        int newWidth = maxWidth;
        int newHeight = (int) (originalHeight * scale);

        BufferedImage thumbnail = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = thumbnail.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        g2d.drawImage(imageProfileObject.getImage(), 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return thumbnail;

    }

    public BufferedImage createThumbnailSquare(ImageProfileObject imageProfileObject, int size){

        int width =  imageProfileObject.getImage().getWidth();
        int height = imageProfileObject.getImage().getHeight();

        int cropSize = Math.min(width, height);
        int x = (width - cropSize) / 2;
        int y = (height - cropSize) / 2;

        BufferedImage thumbnail = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = thumbnail.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        g2d.drawImage(imageProfileObject.getImage(), 0, 0, size, size, x, y, x + cropSize, y + cropSize, null);
        g2d.dispose();

        return thumbnail;

    }

}
