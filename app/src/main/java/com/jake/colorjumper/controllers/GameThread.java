package com.jake.colorjumper.controllers;

import com.jake.colorjumper.GameView;

/**
 * Created by jake on 13/05/16.
 */
public class GameThread extends Thread{

    GameView game;
    private boolean running;

    // framerate stuff
    private long thisFrameTime;
    private long startFrameTime;
    public static long fps;

    public GameThread(GameView game){
        this.game = game;
    }

    public void run(){
        while(running){
            startFrameTime = System.currentTimeMillis();

            game.update();
            game.draw();

            // calculating framerate to use for movement, animation, etc...
            thisFrameTime = System.currentTimeMillis() - startFrameTime;
            if(thisFrameTime > 0){
                fps = 1000 / thisFrameTime;
            }

            game.update();
            game.draw();
        }
        System.out.println("thread should be stopped");
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public boolean isRunning(){
        return this.running;
    }
}
