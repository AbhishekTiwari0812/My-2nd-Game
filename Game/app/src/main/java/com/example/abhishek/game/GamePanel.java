package com.example.abhishek.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread game_thread;
    Context context;
    private Player player;
    long last_draw_time;
    long current_time;
    long update_delay = 125;        //screen gets refreshed every 125 millisecond
    ArrayList<Bullet> bullets_on_screen;
    int bullets_allowed;
    private Enemy enemy;
    public GamePanel(Context context) {
        super(context);
        bullets_on_screen = new ArrayList<Bullet>();
        getHolder().addCallback(this);
        game_thread=new GameThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player = new Player();
        enemy = new Enemy();
        this.setWillNotDraw(false);
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
        int bullet_count = bullets_on_screen.size();
        this.invalidate();
        if (bullet_count < bulletsAllowed()) {
            Bullet b = new Bullet(this.player);
            bullets_on_screen.add(b);
        }
        return super.onTouchEvent(event);

    }


    //TODO: edit this
    int bulletsAllowed() {
        return 10;
    }
    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            final int saved_state = canvas.save();
            player.draw(canvas);
            enemy.draw(canvas);
            Iterator<Bullet> iterator = bullets_on_screen.iterator();
            Bullet temp_bullet;
            double distance_between_bullet_and_enemy;
            while (iterator.hasNext()) {
                iterator.next().draw(canvas);
            }
            canvas.restoreToCount(saved_state);
            this.invalidate(0,0,100,100);
        }
    }


    public void update() {
        //TODO: check whether there's a collision
        //if yes, remove the bullet from the screen
        this.current_time = System.currentTimeMillis();
        if (current_time - last_draw_time > update_delay) {

            this.last_draw_time = System.currentTimeMillis();
        }

    }
    public void p(String s) {
        System.out.println("" + s);
    }
}
