package com.example.abhishek.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * Created by Abhishek on 18-04-2016.
 */
public class Enemy extends GameObject {
    static final String ENEMY_COLOR1 = "#332299";
    static final String ENEMY_COLOR2 = "#223399";
    boolean is_first_color;

    Drawable drawable;
    int redrawn;
    int direction;
    Enemy obj;
    Enemy() {
        this.is_first_color = true;
        obj = this;
        this.r = 50f;
        this.x = GameActivity.ScreenWidth / 2;
        this.y = this.r;
        this.direction = 1;
        this.redrawn = 0;
        this.dx = 3;
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(Color.parseColor(this.ENEMY_COLOR1));
    }

    public void draw(Canvas canvas) {
        this.redrawn++;
        //  if (redrawn > 2) {        //this decides the speed of the bullets
            this.x += (this.direction) * this.dx;
        // }
        if ((this.x + this.r >= GameActivity.ScreenWidth) || (this.x - this.r < 0))
            this.direction *= -1;

        canvas.drawCircle(x, y, r, paint);
    }

    //TODO: remove the concurrency problem
    //changes the color of the enemy temporarily.

    void changeColorTemp(Canvas canvas) {
        if (this.is_first_color) {
            this.paint.setColor(Color.parseColor(this.ENEMY_COLOR2));
            this.is_first_color = false;
        } else {
            this.is_first_color = true;
            this.paint.setColor(Color.parseColor(this.ENEMY_COLOR1));
        }

    }
}
