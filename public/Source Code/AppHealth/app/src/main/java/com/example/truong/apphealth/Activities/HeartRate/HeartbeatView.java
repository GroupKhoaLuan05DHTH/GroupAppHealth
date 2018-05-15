package com.example.truong.apphealth.Activities.HeartRate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.truong.apphealth.R;

/**
 * Created by Truong on 5/10/2018.
 */

public class HeartbeatView extends View {

    private static final Matrix matrix = new Matrix();
    private static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static Bitmap greenBitmap = null;
    private static Bitmap redBitmap = null;

    private static int parentWidth = 0;
    private static int parentHeight = 0;

    public HeartbeatView(Context context, AttributeSet attr) {
        super(context, attr);

        greenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.like);
        redBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cardiogram);
    }
    public HeartbeatView(Context context) {
        super(context);

        greenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.like);
        redBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cardiogram);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(parentWidth, parentHeight);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null) throw new NullPointerException();

        Bitmap bitmap = null;
        if (HeartRateActivity.getCurrent() == HeartRateActivity.TYPE.GREEN) bitmap = greenBitmap;
        else bitmap = redBitmap;

        int bitmapX = bitmap.getWidth() / 2;
        int bitmapY = bitmap.getHeight() / 2;

        int parentX = parentWidth / 2;
        int parentY = parentHeight / 2;

        int centerX = parentX - bitmapX;
        int centerY = parentY - bitmapY;

        matrix.reset();
        matrix.postTranslate(centerX, centerY);
        canvas.drawBitmap(bitmap, matrix, paint);
    }
}
