package com.dehboxturtle.miningprototype.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.dehboxturtle.miningprototype.util.Constants;
import com.dehboxturtle.miningprototype.util.MyPerlin;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiningGridView extends View {
    public static double max = 0;
    public static double min = 0;

    private int mThreshold = 30;
    private int gridWidth = 15;
    private int gridHeight = 35;
    private MyPerlin mMyPerlin;
    private Paint[] paints;
    private boolean mThresholdEnabled;
    private NormalDistribution mDistribution;
    private int mStartingLocation;
    private Random random;
    private List<boolean[]> mRules = new ArrayList<>();
    private int mMineralCount = 0;

    public MiningGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MiningGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MiningGridView(Context context) {
        super(context);
        init();
    }

    public void setThreshold(int mThreshold) {
        this.mThreshold = mThreshold;
    }

    public void setThresholdEnabled(boolean enabled) {
        mThresholdEnabled = enabled;
    }

    public boolean getThresholdEnabled() {
        return mThresholdEnabled;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public void setRules(List<boolean[]> rules) {
        mRules = rules;
    }

    public MyPerlin getPerlin() {
        return mMyPerlin;
    }

    public void setStartingLocation(int starting_location) {
        mStartingLocation = starting_location;
        invalidate();
    }

    public int getMineralCount() {
        return mMineralCount;
    }

    private void init() {
        mMyPerlin = new MyPerlin();

        paints = new Paint[51];
        for (int i = 0; i < 51; i ++) {
            paints[i] = new Paint();
            paints[i].setARGB(255, 5 * i, 5 * i, 5 * i);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final double boxsize = Math.min(getMeasuredWidth() / (double)gridWidth,
                getMeasuredHeight() / (double)gridHeight);
        setMeasuredDimension((int)boxsize * gridWidth + 1, (int)boxsize * gridHeight + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int dx = Math.min(height / gridHeight, width / gridWidth);
        int dy = dx;
        double perlin;
        Paint paint;

        boolean[] previous = new boolean[gridWidth];
        boolean[] current;
        int currentRule = 0;
        random = new Random(Constants.RANDOM_SEED);
        for (int j = 0; j < gridHeight; j++) {
            current = new boolean[gridWidth];
            for (int i = 0; i < gridWidth; i++) {
                perlin = mMyPerlin.octavePerlin(i + random.nextDouble(), j + random.nextDouble());
                if (mRules.size() != 0) {
                    if (j != 0) {
                        current[i] = isDug(i, mRules.get(currentRule), previous);
                    } else if (i == mStartingLocation) {
                        current[i] = true;
                    }
                }
                if(j == 0 && isStartingLocation(i)) {
                    paint = new Paint();
                    paint.setARGB(255, 0, 255, 0);
                } else {
                    paint = getPaint(perlin * getDepthMultiplier(j), current[i]);
                }
                canvas.drawRect(i*dx, j*dy, dx*(i + 1), dy*(j + 1), paint);
            }
            if (mRules.size() != 0) {
                currentRule = (currentRule + 1) % mRules.size();
            }
            previous = current;
        }
        Log.d("MiningGridView", String.valueOf(max));
        Log.d("MiningGridView", String.valueOf(min));
    }

    private boolean isDug(int x, boolean[] rule, boolean[] previous) {
        final int buffer = (rule.length - 1) / 2;
        boolean active = true;
        if (x - buffer >= 0 && x + buffer < previous.length) {
            int start = x - buffer;
            for (int i = 0; i < rule.length; i++) {
                if (rule[i] != previous[start + i]) {
                    active = false;
                }
            }
        } else {
            active = false;
        }
        return active;
    }

    private boolean isStartingLocation(int x) {
        return x == mStartingLocation;
    }

    private double getDepthMultiplier(int j) {
        if (mDistribution == null) {
            mDistribution = new NormalDistribution(gridHeight / 2, gridHeight / 4);
        }
        final double cumulative = mDistribution.cumulativeProbability(j);
        return Math.max(1.2 - cumulative, .2 + cumulative);
    }

    private Paint getPaint(double value, boolean isDug) {
        max = Math.max(max, value);
        min = Math.min(min, value);
        Paint paint = new Paint();
        int ivalue = (int) (value * 255);
        if (mThresholdEnabled) {
            if (ivalue < mThreshold) {
                if (isDug) {
                    mMineralCount++;
                }
                paint.setARGB(255, 204, 102, 0);
            } else {
                ivalue = Math.min(255, ivalue + 50);
                paint.setARGB(255, ivalue, ivalue, ivalue);
            }
            if (isDug) {
                paint.setARGB(255, 0, 0, 255);
            }
        } else {
            paint.setARGB(255, ivalue, ivalue, ivalue);
        }
        return paint;
    }
}
