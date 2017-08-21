package com.dehboxturtle.miningprototype.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class AutomataRuleView extends View {
    private boolean[] mRule;

    public AutomataRuleView(Context context) {
        super(context);
    }

    public AutomataRuleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutomataRuleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final Paint WHITE = new Paint();
    private static final Paint BLACK = new Paint();
    private static final Paint OUTLINE = new Paint();
    static {
        WHITE.setARGB(255, 255, 255, 255);
        BLACK.setARGB(255, 0, 0, 0);
        OUTLINE.setARGB(255, 0, 0, 0);
        OUTLINE.setStyle(Paint.Style.STROKE);
    }

    public void setRule(boolean[] rule) {
        mRule = rule;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final float boxsize = getMeasuredWidth() / (float)mRule.length;
        setMeasuredDimension(getMeasuredWidth(), (int)boxsize + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final float boxsize = getMeasuredWidth() / (float)mRule.length;
        for(int i = 0; i < mRule.length; i++) {
            boolean active = mRule[i];
            canvas.drawRect(i * boxsize + 1, 0, boxsize * (i + 1), boxsize, active ? BLACK : WHITE);
            canvas.drawRect(i * boxsize + 1, 0, boxsize * (i + 1), boxsize, OUTLINE);
            if (i != 0 && mRule[i-1] == active) {
                canvas.drawLine(i * boxsize, 0, i * boxsize, boxsize, active ? WHITE : BLACK);
            }
        }
    }

    public boolean[] getRule() {
        return mRule;
    }
}
