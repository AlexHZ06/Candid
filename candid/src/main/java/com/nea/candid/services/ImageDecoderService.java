package com.nea.candid.services;

import com.nea.candid.data.dataObjects.ImageProfileObject;
import com.nea.candid.data.dbEnties.EmbeddedVectorTableEntity;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ImageDecoderService {

    private float hueThreshold = 0.15F;
    private ArrayList<Float> globalBrightness = null;
    private float globalTopBrightprec = 0;
    private float gloablBotBrightprec = 0;

    public void calcAndDisplayInfo(ImageProfileObject imageProfileObject) {

        ArrayList<int[][]> sections = extractSampleBounds(imageProfileObject.getImage(), 3);
        float[] saturation = null;
        float aspectRatio = calculateAspectRatio(imageProfileObject);
        ArrayList<EmbeddedVectorTableEntity> vectors = new ArrayList<>();

        for(int i = 0; i < sections.size(); i++) {

            ArrayList<int[]> samplePixels = extractSamplePixels(sections.get(i)[0][0], sections.get(i)[0][1], sections.get(i)[1][0], sections.get(i)[1][1], imageProfileObject.getImage());
            ArrayList<int[]> samplePixelsArgb = extractSamplePixelsArgb(samplePixels, imageProfileObject.getImage());
            ArrayList<Float> samplePixelsBrightness = extractPixelsBrightness(samplePixelsArgb);
            float[] brightness = calculateSectionBrightness(samplePixelsBrightness);
            saturation = calculateSectionSaturation(samplePixelsArgb);
            float[] hue = calculateSectionHue(samplePixelsArgb, samplePixels.size());
            float edgeDensity = calculateEdgeDensity(imageProfileObject.getImage(), brightness[2], samplePixelsBrightness, samplePixels);
            float[] shadowHighlights = calculateShadowHighlights(samplePixelsBrightness);

            vectors.add(new EmbeddedVectorTableEntity(i, brightness[0], brightness[1], brightness[2], saturation[0], saturation[1], saturation[2], hue[0], hue[1], hue[2], hue[3], hue[4], hue[5], hue[6], edgeDensity, aspectRatio));

        }

        calculateBrightnessPercentiles();

        for(int i = 0; i < imageProfileObject.getEmbededVectors().size(); i++) {

            System.out.println("---------Section " + (i + 1) + " ------");
            System.out.println("Brightness");
            System.out.println("Mean: " + imageProfileObject.getEmbededVectors().get(i).getBrightnessmean());
            System.out.println("Varience: " + imageProfileObject.getEmbededVectors().get(i).getBrightnessdeviation());
            System.out.println("Dynamic Range: " + imageProfileObject.getEmbededVectors().get(i).getDynamicrange());
            System.out.println("Saturation");
            System.out.println("Mean: " + imageProfileObject.getEmbededVectors().get(i).getSaturationmean());
            System.out.println("Varience: " + imageProfileObject.getEmbededVectors().get(i).getSaturationdeviation());
            System.out.println("colour coverage" + saturation[2]);
            System.out.println("Hue");
            System.out.println("Red: " + imageProfileObject.getEmbededVectors().get(i).getRed());
            System.out.println("orange: " + imageProfileObject.getEmbededVectors().get(i).getOrange());
            System.out.println("yellow: " + imageProfileObject.getEmbededVectors().get(i).getYellow());
            System.out.println("green: " + imageProfileObject.getEmbededVectors().get(i).getGreen());
            System.out.println("cyan: " + imageProfileObject.getEmbededVectors().get(i).getCyan());
            System.out.println("blue: " + imageProfileObject.getEmbededVectors().get(i).getBlue());
            System.out.println("purple: " + imageProfileObject.getEmbededVectors().get(i).getPurple());
            System.out.println("Magenta: " + imageProfileObject.getEmbededVectors().get(i).getMagenta());
            System.out.println("Edge density: " + imageProfileObject.getEmbededVectors().get(i).getEdgedensity());
            System.out.println("AspectRatio: " + imageProfileObject.getEmbededVectors().get(i).getAspectratio());

        }

    }

    public void calculatePrintResults(ImageProfileObject imageProfileObject) {

        float aspectRatio = calculateAspectRatio(imageProfileObject);
        ArrayList<int[][]> sections = extractSampleBounds(imageProfileObject.getImage(), 3);

        for(int i =0; sections.size() > 0; i++) {

            ArrayList<int[]> samplePixels = extractSamplePixels(sections.get(i)[0][0], sections.get(i)[1][0], sections.get(i)[1][0], sections.get(i)[1][1], imageProfileObject.getImage());
            ArrayList<int[]> samplePixelsArgb = extractSamplePixelsArgb(samplePixels, imageProfileObject.getImage());
            ArrayList<Float> samplePixelsBrightness = extractPixelsBrightness(samplePixelsArgb);

            float[] brightness = calculateSectionBrightness(samplePixelsBrightness);
            float[] saturation = calculateSectionSaturation(samplePixelsArgb);
            float[] hues = calculateSectionHue(samplePixelsArgb, samplePixels.size());
            float edgeDensity = calculateEdgeDensity(imageProfileObject.getImage(), brightness[2], samplePixelsBrightness, samplePixels);
            float[] shadowHighlights = calculateShadowHighlights(samplePixelsBrightness);
            float sharpness = calculateSharpness(samplePixelsBrightness, samplePixels, imageProfileObject.getImage());

            System.out.println("----------Section " + i + "----------");
            System.out.println("MeanBrightness: " + brightness[0]);
            System.out.println("Brightness deviation: " + brightness[1]);
            System.out.println("Dynamic Range" + brightness[2]);
            

        }
    }

    private void calculateBrightnessPercentiles(){

        int upperPercentileIndex = (int) Math.round(globalBrightness.size() * 0.85);
        int lowerPercentileIndex = (int) Math.round(globalBrightness.size() * 0.15);

        gloablBotBrightprec = globalBrightness.get(lowerPercentileIndex);
        globalTopBrightprec = globalBrightness.get(upperPercentileIndex);

    }

    public ArrayList<int[][]> extractSampleBounds(BufferedImage image, int divisions) {

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
            globalBrightness.add(brightness);
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

        float brightnessDeviation = (float) Math.sqrt(squaredDifference / (sectionBrightness.size()));

        Collections.sort(sectionBrightness);

        float dynamicRange = sectionBrightness.get(upperPercentile) -  sectionBrightness.get(bottomPercentile);

        return new float[]{(float) mean, brightnessDeviation, dynamicRange};


    }

    private float[] calculateShadowHighlights(ArrayList<Float> sectionBrightness) {

        calculateSectionBrightness(sectionBrightness);

        float shadow = 0;
        float highlights = 0;

        for(int i = 0; i < sectionBrightness.size(); i++) {

            if(sectionBrightness.get(i) < gloablBotBrightprec){shadow ++;}
            else if(sectionBrightness.get(i) > globalTopBrightprec){highlights ++;}

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
            if(saturation < hueThreshold){totalHuePixels++;}


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

    private float[] calculateSectionHue(ArrayList<int[]> extractArgbs, int totalPixels) {

        float red = 0;
        float orange = 0;
        float yellow = 0;
        float green = 0;
        float cyan = 0;
        float blue = 0;
        float purple = 0;
        float magenta = 0;

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

            if(!(sat <= hueThreshold)) {

                float hue = 0;

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
                else if(highestIndex == 2) {

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
                else if(highestIndex == 3) {

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

                if (hue >= 0 && hue < 15) {
                    red += 1;
                }else if(hue >= 15 && hue < 45) {
                    orange += 1;
                }
                else if(hue >= 45 && hue < 75) {
                    yellow += 1;
                }
                else if(hue >= 75 && hue < 165) {
                    green += 1;
                }
                else if(hue >= 165 && hue < 195) {
                    cyan += 1;
                }
                else if(hue >= 195 && hue < 225) {
                    blue += 1;
                }
                else if(hue >= 225 && hue < 285) {
                    purple += 1;
                }
                else if(hue >= 285 && hue < 345) {
                    magenta += 1;
                }
                else if(hue >= 345 && hue < 360) {
                    red += 1;
                }

            }

        }

        red = red / totalPixels;
        orange = orange / totalPixels;
        yellow = yellow / totalPixels;
        green = green / totalPixels;
        cyan = cyan / totalPixels;
        blue = blue / totalPixels;
        purple = purple / totalPixels;
        magenta = magenta / totalPixels;

        return new float[]{red, orange, yellow, green, cyan, blue, purple, magenta};

    }

    public float calculateAspectRatio(ImageProfileObject imageProfileObject) {

        int width = imageProfileObject.getImage().getWidth();
        int height = imageProfileObject.getImage().getHeight();

        return (float) width /height;

    }

    private float calculateEdgeDensity(BufferedImage image, float dynamicRange, ArrayList<Float> extractedPixelsBrightness, ArrayList<int[]> samplePixels) {

        double edgeCount = 0;

        int pixelComp = 0;

        float threshold = (float) 15 * (dynamicRange / 128);

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

    private float calculateSharpness(ArrayList<Float> sectionBrightness, ArrayList<int[]> samplePixels, BufferedImage image) {

        ArrayList<Float> sharpnessList = new ArrayList<>();
        double total = 0;

        for(int  i = 0; i < samplePixels.size(); i++) {

            if((samplePixels.get(i)[0] + 1 < image.getWidth()) && (samplePixels.get(i)[0] - 1 > 0) && (samplePixels.get(i)[1] + 1 < image.getHeight()) && (samplePixels.get(i)[1] - 1 > 0)) {

                int[] leftArgb = convertArgb(image.getRGB((samplePixels.get(i)[0] - 1), samplePixels.get(i)[2]));
                int[] rightArgb = convertArgb(image.getRGB((samplePixels.get(i)[0] + 1), samplePixels.get(i)[2]));
                int[] upArgb = convertArgb(image.getRGB(samplePixels.get(i)[0], (samplePixels.get(i)[1] + 1)));
                int[] downArgb = convertArgb(image.getRGB(samplePixels.get(i)[0], (samplePixels.get(i)[1] - 1)));

                float brightnessLeft = 0.2126f * leftArgb[0] + 0.7152f * leftArgb[1] + 0.0722f * leftArgb[2];
                float brightnessRight = 0.2126f * rightArgb[0] + 0.7152f * rightArgb[1] + 0.0722f * rightArgb[2];
                float brightnessUp = 0.2126f * upArgb[0] + 0.7152f * upArgb[1] + 0.0722f * upArgb[2];
                float brightnessDown = 0.2126f * downArgb[0] + 0.7152f * downArgb[1] + 0.0722f * downArgb[2];

                float sharpness = Math.abs(brightnessRight + brightnessLeft + brightnessUp + brightnessDown - 4 * sectionBrightness.get(i));
                sharpnessList.add(sharpness);
                total += sharpness;

            }

        }

        return (float) total / sharpnessList.size();

    }

}
