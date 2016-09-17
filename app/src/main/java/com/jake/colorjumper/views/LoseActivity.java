package com.jake.colorjumper.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jake.colorjumper.R;

/**
 * Created by jake on 19/05/16.
 */
public class LoseActivity extends Activity {

    private int score;

    public void restart(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void submitScore(View view){
        Intent submitScore = new Intent(getApplicationContext(), SubmitScoreActivity.class);
        submitScore.putExtra("Score", score);
        startActivity(submitScore);
    }

    public void highscores(View view){
        Intent highscore = new Intent(getApplicationContext(), HighScoresActivity.class);
        startActivity(highscore);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose);

        Bundle b = getIntent().getExtras();
        score = b.getInt("Score");

        setScore();
    }

    public void setScore(){
        TextView text = (TextView) findViewById(R.id.score_list_score);
        text.setText("Score: "+score);
    }
}
