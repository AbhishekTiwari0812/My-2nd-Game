package com.example.abhishek.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * Created by Abhishek on 18-04-2016.
 */
public class Enemy extends GameObject {
    static final String ENEMY_COLOR = "#332299";
    static final String ENEMY_HIT_COLOR = "#223399";
    Drawable drawable;
    int redrawn;
    int direction;

    Enemy() {
        this.r = 60f;
        this.x = GameActivity.ScreenWidth / 2;
        this.y = this.r;
        this.direction = 1;
        this.redrawn = 0;
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(Color.parseColor(this.ENEMY_COLOR));
    }

    public void draw(Canvas canvas) {

        this.redrawn++;
        if (redrawn > 2) {        //this decides the speed of the bullets
            this.x += (this.direction) * this.dx;
            //this.redrawn = 0;
        }
        if ((this.x + this.r >= GameActivity.ScreenWidth) || (this.x - this.r < 0))
            this.direction *= -1;

        canvas.drawCircle(x, y, r, paint);
    }

    void changeColorTemp() {
        this.paint.setColor(Color.parseColor(this.ENEMY_HIT_COLOR));
      /*  try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        this.paint.setColor(Color.parseColor(this.ENEMY_COLOR));
    }
}
