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
    float x;            //x position of the object (centered)
    float y;            //y position of the obejct )centered)
    float dx;           // speed while moving in the x direction
    float dy;           //speed while moving in y direction
    float r;            //radius of the object (since every object is a circle)
    Paint paint;        //used for setting the color of the object

    //not being used
    //TODO: refactor the code.
    public void update(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        this.x += dx;
        this.y += dy;
    }

    //used to change the size of the Game objects
    //TOOD: handle concurrency
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
