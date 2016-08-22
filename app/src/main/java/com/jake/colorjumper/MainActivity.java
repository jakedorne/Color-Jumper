package com.jake.colorjumper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void startGame(View view){
        // switches from main menu to game activity
        Intent game = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(game);

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
