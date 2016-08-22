package com.jake.colorjumper;

import android.graphics.Rect;

import java.util.Random;

/**
 * Created by jake on 13/05/16.
 */
public class Platform {

    private int x;
    private int y;
    private int speed;
    private int width;
    private boolean goingLeft = true;


    public Platform(int x, int y){
        this.x = x;
        this.y = y;
        Random random = new Random();
        this.speed = random.nextInt(15) + 5;
        this.width = random.nextInt(200) + 100;
        this.goingLeft = random.nextBoolean();
    }

    public void move(){
        if(goingLeft){
            x -= speed;
            if(x <= 0){
                goingLeft = false;
            }
        } else {
            x += speed;
            if(x + width >= GameView.width){
                goingLeft = true;
            }
        }
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth(){
        return this.width;
    }

    public Rect getBounds() {
        return new Rect(x, y, x + width, y + GameView.platformHeight);
    }
}
