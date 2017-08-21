package com.dehboxturtle.miningprototype.util;

import java.util.Random;

public class MyPerlin {
    private Random mRandom;
    private GradientMap mGradients;
    public static double max = 0;
    public static double min = 0;

    private int octaves = 1;
    private double persistence = 2.0;
    private double exp = 1;

    public MyPerlin() {
        mRandom = new Random();
        mGradients = new GradientMap(mRandom);
    }

    public MyPerlin(long seed) {
        mRandom = new Random(seed);
        mGradients = new GradientMap(mRandom);
    }

    public double dotGridGradient(int ix, int iy, double x, double y) {
        double dx = x - ix;
        double dy = y - iy;

        double angle = mGradients.getVectorAngle(ix, iy);
        return dx * Math.cos(angle)
                + dy * Math.sin(angle);
    }

    public double perlin(double x, double y) {
        final int x0 = (int)x;
        final int x1 = x0 + 1;
        final int y0 = (int)y;
        final int y1 = y0 + 1;

//        double sx = fade(x - x0);
//        double sy = fade(y - y0);

        double sx = x - (double)x0;
        double sy = y - (double)y0;

        double n0, n1, n2, n3, ix0, ix1, value;
        n0 = dotGridGradient(x0, y0, x, y);
        n1 = dotGridGradient(x1, y0, x, y);
        ix0 = lerp(n0, n1, sx);

        n2 = dotGridGradient(x0 ,y1, x, y);
        n3 = dotGridGradient(x1 ,y1, x, y);
        ix1 = lerp(n2, n3, sx);

        value = lerp(ix0, ix1, sy);

        return (value + .7) / 1.4;

    }

    public double octavePerlin(double x, double y) {
        double total = 0;
        double frequency = 1;
        double amplitude = 1;
        double maxValue = 0;  // Used for normalizing result to 0.0 - 1.0
        for(int i=0;i<octaves;i++) {
            total += perlin(x * frequency, y * frequency) * amplitude;

            maxValue += amplitude;

            amplitude *= persistence;
            frequency *= 2;
        }

        return Math.pow(total/maxValue, exp);
    }

    public void setOctaves(int octaves) {
        this.octaves = octaves > 0 ? octaves : 1;
    }

    public void setPersistence(double persistence) {
        this.persistence = persistence > 0 ? persistence : 1;
    }

    public void setExp(double exp) {
        this.exp = exp > 0 ? exp : 1;
    }

    private double lerp(double a0, double a1, double w) {
//        return a0 + w * (a0 - a1);
        return (1.0 - w) * a0 + w * a1;
    }
}
