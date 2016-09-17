package com.jake.colorjumper.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.jake.colorjumper.R;
import com.jake.colorjumper.Score;
import com.jake.colorjumper.ScoreAdapter;

import java.util.ArrayList;

/**
 * Created by jaek on 17/09/16.
 */
public class HighScoresActivity extends Activity {

    private ArrayList<Score> scores = new ArrayList<Score>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);
        listView = (ListView) findViewById(R.id.listView);

        getHighscores();
        populateListView();
    }

    private void populateListView() {
        ScoreAdapter arrayAdapter = new ScoreAdapter(this, scores);
        listView.setAdapter(arrayAdapter);
    }

    public void getHighscores() {
        // populate list from database.
    }
}
