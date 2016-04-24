package com.example.abhishek.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * Created by Abhishek on 18-04-2016.
 */
public class Player extends GameObject {

    Drawable drawable;
    long score;
    Player() {
        this.r = 40f;
        this.x = GameActivity.ScreenWidth / 2;
        this.y = GameActivity.ScreenHeight - this.r;
        this.paint = new Paint();
        this.score = 0;
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(Color.parseColor("#CD5C5C"));
    }

    public void draw(Canvas canvas) {
        //p("x=" + x + " y=" + y + " r=" + r);
        canvas.drawCircle(x, y, r, paint);
    }
}
