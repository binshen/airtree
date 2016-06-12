package com.moral.airtree.widget;

/**
 * Created by bin.shen on 5/24/16.
 */
import android.view.View;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.graphics.Canvas;

import com.moral.airtree.R;

public class MyCircleView extends View {
    private final Context mContext;
    private int mMaxProgress;
    private final Paint mPaint;
    private int mProgress;
    private final RectF mRectF;
    private final int mOutCircleLineStrokeWidth = 0x23;
    private final int mMiddleCircleLineStrokeWidth = 0x14;

    public MyCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if(width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }
        mPaint.setAntiAlias(true);
        canvas.drawColor(0);
        mPaint.setStrokeWidth(35.0f);
        mPaint.setStyle(Paint.Style.STROKE);
        mRectF.left = 17;
        mRectF.top = 17;
        mRectF.right = (float)(width - 17);
        mRectF.bottom = (float)(height - 17);
        mPaint.setARGB(0xff, 0xff, 0xf5, 0x99);
        canvas.drawArc(mRectF, 0.0f, 0.0f, false, mPaint);
        mPaint.setARGB(0xff, 0x99, 0xd9, 0xff);
        canvas.drawArc(mRectF, 180.0f, 0.0f, false, mPaint);
        mPaint.setARGB(0xff, 0xff, 0xe1, 0xcd);
        canvas.drawArc(mRectF, 0.0f, -360.0f, false, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawColor(0);

//        mRectF.left = 36;
//        mRectF.top = 36;
//        mRectF.right = (float)(width - 36);
//        mRectF.bottom = (float)(height - 36);
//        mPaint.setARGB(0xff, 0x1c, 0xa9, 0xff);
//        canvas.drawArc(mRectF, 0.0f, 0.0f, false, mPaint);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawColor(0);
//
//        mRectF.left = 56;
//        mRectF.top = 56;
//        mRectF.right = (float)(width - 56);
//        mRectF.bottom = (float)(height - 56);
//        mPaint.setARGB(0xff, 0x99, 0xd9, 0xff);
//        canvas.drawArc(mRectF, 0.0f, 0.0f, false, mPaint);
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }
}