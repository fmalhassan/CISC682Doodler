package com.example.cisc682doodler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static com.example.cisc682doodler.MainActivity.drawPath;
import static com.example.cisc682doodler.MainActivity.drawPaint;
import static com.example.cisc682doodler.MainActivity.canvas;
import static com.example.cisc682doodler.MainActivity.brushSize;
import static com.example.cisc682doodler.MainActivity.paintColor;


import androidx.annotation.Nullable;

public class DoodleView extends View {

    //public Path drawPath = new Path();
    //public Paint drawPaint = new Paint();
    public Paint canvasPaint = new Paint();
    public Bitmap bitmap;
    private int paintAlpha = 255;


    //public Canvas canvas;
    //boolean erase = false;


    public DoodleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        doodle();
    }

    public void doodle() {

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5f);
        drawPaint.setColor(paintColor);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);

       /* brushSize = getResources().getInteger(R.integer.MediumSize);
        currentBrushSize = brushSize;
        drawPaint.setStrokeWidth(brushSize)*/

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        } invalidate();
        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0,0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    public void brushSize(int selectedSize){

        brushSize = selectedSize;
        drawPaint.setStrokeWidth(brushSize);
    }

    public void brushColor(int color){
        invalidate();
        paintColor = color;
        drawPaint.setColor(paintColor);
    }

    public int getPaintAlpha(){
        return Math.round((float)paintAlpha/255*100);
    }

    public void setPaintAlpha(int newAlpha){
        paintAlpha=Math.round((float)newAlpha/100*255);
        drawPaint.setColor(paintColor);
        drawPaint.setAlpha(paintAlpha);
    }

    /*public void eraseCanvas(boolean isErase)
    {
        erase = isErase;
        if(erase)
        {
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }else
        {
            drawPaint.setXfermode(null);
        }
    }*/



}

