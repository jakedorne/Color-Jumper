package com.jake.colorjumper;

import android.app.Application;

/**
 * Created by jaek on 15/09/16.
 */
public class MyApplication extends Application {
    // 1 = easy, 2 = hard.
    public static int difficulty = 1;
    public static float value = 1f;
    public static float saturation = 0.3f;

    public static void toggleDifficulty() {
        if(difficulty==1){
            difficulty = 2;
        } else {
            difficulty = 1;
        }
    }

    public static void setValue(float f){
        value = f;
    }

    public static void setSaturation(float f){
        saturation = f;
    }
}
