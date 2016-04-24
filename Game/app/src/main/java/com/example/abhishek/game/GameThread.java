package com.example.abhishek.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Abhishek on 18-04-2016.
 */
public class GameThread extends Thread {
    static Canvas canvas;
    final int FPS = 30;           //frames per second.
    int AVG_FPS;
    GamePanel gamePanel;
    boolean running;
    private SurfaceHolder surfaceHolder;

    public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        long start_time;
        long time_millis;
        long wait_time;
        long total_time = 0;
        int frame_count = 0;
        long target_time = 1000 / FPS;
        while (running) {
            start_time = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }

                time_millis = (System.nanoTime() - start_time) / 1000000;
                wait_time = target_time - time_millis;
                try {
                    Thread.sleep(wait_time);
                } catch (InterruptedException e) {
                   // e.printStackTrace();
                }
                total_time += System.nanoTime() - start_time;
                frame_count++;
                if (frame_count == FPS) {
                    AVG_FPS = (int) (1000 / ((total_time / frame_count) / 1000000));
                    frame_count = 0;
                    total_time = 0;
                    p("Average FPS=" + AVG_FPS);
                }
            } catch (Exception e) {
                //    p("some error occurred while running the game thread");
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void p(String s) {
        System.out.println("" + s);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
