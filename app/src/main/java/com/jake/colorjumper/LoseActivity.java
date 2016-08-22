package com.jake.colorjumper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jake on 19/05/16.
 */
public class LoseActivity extends Activity {

    public void restart(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose);
        setScore();
    }

    public void setScore(){
        Bundle b = getIntent().getExtras();
        int score = b.getInt("Score");
        TextView text = (TextView) findViewById(R.id.score);
        text.setText("Score: "+score);
    }
}
