package com.jake.colorjumper.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jake.colorjumper.GameView;

/**
 * Created by jake on 13/05/16.
 */
public class GameActivity extends Activity {
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    protected void onDestroy(){
        System.out.println("~~~~~~~~~` onDestroy was called!!! ~~~~~~~~~~");
        gameView.destroy();
        super.onDestroy();
    }


    @Override
    protected void onStop(){
        System.out.println("~~~~~~~~ onStop was called!!! ~~~~~~");
        gameView.destroy();
        super.onStop();
    }
}
