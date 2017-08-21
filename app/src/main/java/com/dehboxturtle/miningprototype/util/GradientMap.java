package com.dehboxturtle.miningprototype.util;

import android.util.Pair;

import java.util.HashMap;
import java.util.Random;

public class GradientMap {
    private Random mRandom;
    private HashMap<Pair<Integer, Integer>, Double> mMap;

    public GradientMap(Random random) {
        mRandom = random;
        mMap = new HashMap<>();
    }

    public double getVectorAngle(int x, int y) {
        final Pair<Integer, Integer> pair = new Pair<>(x, y);
        double angle;
        if (mMap.containsKey(pair)) {
            angle = mMap.get(pair);
        } else {
            angle = getRandomAngle();
            mMap.put(pair, angle);
        }
        return angle;
    }

    private double getRandomAngle() {
        return mRandom.nextDouble() * 2.0 * Math.PI;
    }
}
