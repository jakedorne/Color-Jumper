package com.jake.colorjumper.models;

/**
 * Created by jaek on 17/09/16.
 */
public class Score {
    private String name;
    private int score;

    public Score(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }
}
