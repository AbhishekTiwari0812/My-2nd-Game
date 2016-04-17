package com.example.abhishek.game;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Abhishek on 18-04-2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread game_thread;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        game_thread=new GameThread(getHolder(),this);
        //don't know what this does!
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        game_thread.setRunning(true);
        game_thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //this may cause an error.
        //check here.
        boolean retry = true;
        while (retry) {
            try {
                game_thread.setRunning(false);
                game_thread.join();
                retry = false;
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //TODO: fire bullets on screen tap event.

        return super.onTouchEvent(event);
    }

    public void update() {
    }
}
