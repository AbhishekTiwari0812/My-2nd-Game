package com.example.abhishek.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Abhishek on 18-04-2016.
 */
public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: change to full screen
        //TODO: test this!
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        p("no title worked");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        p("full screen worked");
        setContentView(new GamePanel(this));
    }

    void p(String str) {
        System.out.println("" + str);
    }
}
