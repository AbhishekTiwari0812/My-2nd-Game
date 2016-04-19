package com.example.abhishek.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by Abhishek on 18-04-2016.
 */
abstract class GameObject {
    Bitmap object;
    float x;
    float y;
    float dx;
    float dy;
    float r;
    Paint paint;


    public void update(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        this.x += dx;
        this.y += dy;
    }

    public void setSize(int newHeight, int newWidth) {
        int width = object.getWidth();
        int height = object.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        object = Bitmap.createBitmap(
                object, 0, 0, width, height, matrix, false);
    }

    public void p(String s) {
        System.out.println("" + s);
    }
}
