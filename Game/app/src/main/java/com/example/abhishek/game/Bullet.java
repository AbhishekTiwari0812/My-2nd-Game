package com.example.abhishek.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * Created by Abhishek on 18-04-2016.
 */
public class Bullet extends GameObject {

    Drawable drawable;
    int redrawn;

    Bullet(Player p) {
        this.r = 5f;
        this.x = p.x;
        this.y = p.y - p.r;
        this.redrawn = 0;
        this.dy = 1;
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(Color.parseColor("#af2233"));
    }

    public void draw(Canvas canvas) {
        this.redrawn++;
        if (this.redrawn > 3) {        //this decides the speed of the bullets
            this.y -= this.dy;
        }
        canvas.drawCircle(x, y, r, paint);
    }
}
