package com.jake.colorjumper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by jake on 13/05/16.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private GameThread thread;
    private Context context;
    private int cameraY;

    // drawing stuff
    private Canvas canvas;
    private Paint paint = new Paint(Color.RED);

    // possible world variables
    private boolean isPlaying = true;
    private int gamestate = 1;
    public static float gravity = 0.8f;

    // player stuff
    //private int playerCount = 1; -- no longer used. application variable now.
    private float score = 0;
    private Player[] players = new Player[MyApplication.difficulty];

    // color stuff
    private float hue;
    //private float value = 1f; -- no longer used. application variable now.
    //private float saturation = 0.3f; -- no longer used. application variable now.
    private float colorChangeSpeed = 2f;

    // platform stuff
    private int platformCount = 3;
    public static int platformHeight = 30;
    private int platformInterval;
    private Platform[] platforms = new Platform[platformCount];

    // canvas width + height
    public static int width;
    public static int height;


    public GameView(Context context){
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        setFocusable(true);
    }

    private void setPlayerStartingPositions() {
        for (int i = 0; i < players.length; i++){
            players[i] = new Player(getWidth() / (MyApplication.difficulty + 1) * (i + 1), getHeight()/2);

        }
    }

    private void spawnPlatforms() {
        Random random = new Random();
        for(int i = 0; i < platforms.length; i++){
            platforms[i] = new Platform(random.nextInt(getWidth()), (int) (players[0].getY() - (i + 1) * platformInterval));
        }
    }

    public void update(){
        // game logic goes here
        updateCamera();
        updateScore();
        checkCollision();
        for(Player p: players){
            p.update();
        }
        if(Math.abs(heighestY() - lowestY()) > height){
            lose();
        }
        for(int i = 0; i < platforms.length; i++){
            if(platforms[i].getY() > players[0].getY() + height / 2){
                // Player has passed platform (needs to be changed)...
                Random random = new Random();
                int newY = platforms[i].getY() - platformCount * platformInterval;
                platforms[i] = new Platform(random.nextInt(getWidth()), newY);
            } else {
                platforms[i].move();
            }
        }
    }

    private void updateScore() {
        float bestHeight = Float.MAX_VALUE;
        for(Player p: players){
            if(p.getY() < bestHeight){
                bestHeight = p.getY();
            }
        }

        score = - bestHeight + getHeight()/2;
    }

    private void checkCollision() {
        for(Player player: players){
            for(Platform platform: platforms){
                if(player.getBounds().intersect(platform.getBounds())){
                    lose();
                }
            }
        }

    }

    public void draw(){
        if(getHolder().getSurface().isValid()){
            canvas = getHolder().lockCanvas();

            if(canvas == null){
                return;
            }
            // draw things here

            float[] backgroundColor = {hue, MyApplication.saturation, MyApplication.value};
            float[] otherColor = {oppositeHue(), MyApplication.saturation, MyApplication.value};

            paint.setColor(Color.HSVToColor(backgroundColor));
            // Draw the background color
            canvas.drawColor(paint.getColor());

            // Choose the brush color for drawing
            paint.setColor(Color.HSVToColor(otherColor));

            // Make the text a bit bigger
            paint.setTextSize(50);

            // Display the current fps on the screen
            canvas.drawText("Score: " + (int) score, 20, 70, paint);

            // draw players
            for(Player p: players){
                canvas.drawRect(p.getX(), p.getY()- cameraY, p.getX() + p.getSize(), p.getY() + p.getSize()- cameraY, paint);
            }
            for(Platform p: platforms){
                canvas.drawRect(p.getX(), p.getY()- cameraY, p.getX() + p.getWidth(), p.getY() + platformHeight- cameraY, paint);
            }

            incrementHue();
            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            if(!thread.isRunning()){
                thread.setRunning(true);
                thread.start();
            } else {

                Player closestPlayer = players[0];

                for (Player p : players) {
                    if (Math.abs(p.getX() - motionEvent.getX()) < Math.abs(closestPlayer.getX() - motionEvent.getX())) {
                        closestPlayer = p;
                    }
                }

                closestPlayer.jump(motionEvent.getX());
            }
        }
        return true;
    }

    // returns highest in terms of game (actually lowest y value)
    private float heighestY(){
        float heighest = Float.POSITIVE_INFINITY;
        for(Player p: players){
            if(p.getY() < heighest){
                heighest = p.getY();
            }
        }
        return heighest;
    }

    // returns lowest in terms of game (actually highest y value)
    private float lowestY(){
        float lowest = Float.NEGATIVE_INFINITY;
        for(Player p: players){
            if(p.getY() > lowest){
                lowest = p.getY();
            }
        }
        return lowest;
    }

    private float oppositeHue(){
        return (hue < 180f) ? hue + 180 : hue - 180;
    }

    private void incrementHue(){
        if (hue < 360f) {
            hue += colorChangeSpeed;
        } else {
            hue = colorChangeSpeed;
        }
    }

    private void updateCamera() {
        int avgY = 0;
        for(Player p: players){
            avgY += p.getY();
        }

        avgY /= MyApplication.difficulty;
        cameraY = avgY - getHeight() / 2;
    }

    public void lose(){
        thread.setRunning(false);

        Intent loseMenu = new Intent(context, LoseActivity.class);
        Bundle b = new Bundle();
        b.putInt("Score", (int) score);
        loseMenu.putExtras(b);
        context.startActivity(loseMenu);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        width = getWidth();
        height = getHeight();
        platformInterval = height / platformCount;
        setPlayerStartingPositions();
        spawnPlatforms();
        thread = new GameThread(this);
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // trying to end thread until it does
        while(retry==true){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void destroy() {
        thread.setRunning(false);
    }
}
