package com.example.abhishek.game;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Abhishek on 18-04-2016.
 */
public class GameActivity extends AppCompatActivity {
    static float ScreenWidth;
    static float ScreenHeight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GamePanel(this));
        ActionBar a = getSupportActionBar();
        if (a != null)
            a.hide();

        Point size = new Point();
        WindowManager w = getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            w.getDefaultDisplay().getSize(size);
            ScreenWidth = size.x;
            ScreenHeight = size.y;
        } else {
            Display d = w.getDefaultDisplay();
            ScreenWidth = d.getWidth();
            ScreenHeight = d.getHeight();
        }

    }

    void p(String str) {
        System.out.println("" + str);
    }
}
