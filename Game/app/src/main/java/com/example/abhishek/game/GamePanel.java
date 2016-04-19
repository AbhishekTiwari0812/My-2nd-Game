package com.example.abhishek.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread game_thread;
    Context context;
    private Player player;
    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        game_thread=new GameThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player = new Player();
        game_thread.setRunning(true);
        game_thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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

    @Override
    public void draw(Canvas canvas) {
        player.draw(canvas);
    }

    public void update() {
    }
    public void p(String s) {
        System.out.println("" + s);
    }
}
