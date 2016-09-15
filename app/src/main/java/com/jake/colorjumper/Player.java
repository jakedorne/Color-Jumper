package com.jake.colorjumper;

import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by jake on 13/05/16.
 */
public class Player {

    private int x;
    private int y;
    private int size = 30;
    private float xVelocity;
    private float yVelocity;
    private float xDrag = 0.2f;
    private float terminalYVelocity = -20;
    private float jumpHeight = 25f;
    private float jumpWidth = 10f;

    public Player(int x, int y){
        this.x = x;
        this.y = y;
    }


    public void update(){
        fall();
        move();
    }

    public void fall(){
        if(yVelocity > terminalYVelocity){
            yVelocity -= GameView.gravity;
        }
        y -= yVelocity;
    }

    public void move(){
        if(xVelocity > 0){
            xVelocity -= xDrag;
            if(x < GameView.width){
                x += xVelocity;
            }
        } else if (xVelocity < 0){
            xVelocity += xDrag;
            if(x > 0){
                x += xVelocity;
            }
        }

    }

    public void jump(float x) {
        if(x > this.x){
            xVelocity = jumpWidth;
        } else {
            xVelocity = -jumpWidth;
        }
        yVelocity = jumpHeight;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public int getSize(){
        return this.size;
    }

    public Rect getBounds(){
        return new Rect(x, y, x + size, y + size);
    }


}