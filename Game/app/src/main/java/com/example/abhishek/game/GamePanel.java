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
    private Player player;
    long last_draw_time;
    long current_time;
    long update_delay = 125;        //screen gets refreshed every 125 millisecond
    ArrayList<Bullet> bullets_on_screen;
    private Enemy enemy;


    public GamePanel(Context context) {
        super(context);
        bullets_on_screen = new ArrayList<Bullet>();
        getHolder().addCallback(this);
        game_thread=new GameThread(getHolder(),this);
        setFocusable(true);
    }
    /*
    * initializes the objects in the game.
    * Uses the LOCKED view to eliminate concurrent thread excetions
    *  @param holder: holder for the game view
    */

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
                //stopping the game when the surface is destroyed
                //called when onBackPressed() is called implicitly.
                game_thread.setRunning(false);
                game_thread.join();
                retry = false;
            } catch (Exception e) {

            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //if user taps on the screen , check whether extra bullets are allowed.
        //if allowed, create new bullet.
        int bullet_count = 0;
        for (int i = 0; i < bullets_on_screen.size(); i++) {
            if (bullets_on_screen.get(i).y >= 0) {
                bullet_count++;
            }
        }
        this.invalidate();
        if (bullet_count < bulletsAllowed()) {
            Bullet b = new Bullet(this.player);
            bullets_on_screen.add(b);
        }
        return super.onTouchEvent(event);

    }


    /*
     *uses the static var BulletsAllowed being computed by the broadcast receiver in MainActivity.
     * @param void
     * @return limit on the number of bullets on the screen
    */
    int bulletsAllowed() {
        int allowed = (int) (MainActivity.BulletsAllowed);
        if (allowed < 2)
            allowed = 2;
        p("Bullets allowed are:" + MainActivity.BulletsAllowed);
        return allowed;
    }


    //TODO: remove the warning
    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            final int saved_state = canvas.save();
            player.draw(canvas);
            enemy.draw(canvas);
            Bullet temp_bullet;
            for (int i = 0; i < bullets_on_screen.size(); i++) {
                temp_bullet = bullets_on_screen.get(i);
                double distance_be = Math.sqrt(Math.pow((double) (temp_bullet.x - enemy.x), 2) + Math.pow((double) (temp_bullet.y - enemy.y), 2));
                if (distance_be < temp_bullet.r + enemy.r) {
                    p("Enemy hit");
                    //TODO: fix this
                    if (!temp_bullet.delete_self)
                        player.score++;
                    p("Player Score" + player.score);
                    enemy.changeColorTemp(canvas);
                    temp_bullet.delete_self = true;
                } else if (temp_bullet.y < 0) {
                    temp_bullet.delete_self = true;
                }
            }
            Iterator<Bullet> iterator = bullets_on_screen.iterator();
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
