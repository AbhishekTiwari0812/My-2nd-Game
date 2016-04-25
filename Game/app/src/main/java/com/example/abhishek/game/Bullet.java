package com.example.abhishek.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Abhishek on 18-04-2016.
 */
public class Bullet extends GameObject {

    int redrawn;
    boolean delete_self;
    Bullet(Player p) {
        this.r = 5f;
        this.delete_self = false;
        this.x = p.x;
        this.y = p.y - p.r;
        this.redrawn = 0;
        this.dy = 2;
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(Color.parseColor("#af2233"));
    }

    public void draw(Canvas canvas) {
        this.redrawn++;
        //if (this.redrawn > 3) {        //this decides the speed of the bullets
            this.y -= this.dy;
        //}
        if (!this.delete_self)
            canvas.drawCircle(x, y, r, paint);

    }
}
