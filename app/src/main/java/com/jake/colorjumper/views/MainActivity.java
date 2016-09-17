package com.jake.colorjumper.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jake.colorjumper.R;

public class MainActivity extends Activity {

    public void startGame(View view){
        // switches from main menu to game activity
        Intent game = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(game);

    }

    public void highScores(View view){
        Intent highscore = new Intent(getApplicationContext(), HighScoresActivity.class);
        startActivity(highscore);
    }


    public void settings(View view){
        // switches from main menu to settings activity
        Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(settings);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
