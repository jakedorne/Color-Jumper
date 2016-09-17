package com.jake.colorjumper.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.jake.colorjumper.R;
import com.jake.colorjumper.models.Score;
import com.jake.colorjumper.controllers.ScoreAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaek on 17/09/16.
 */
public class HighScoresActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);
        listView = (ListView) findViewById(R.id.listView);

        getHighscores();
    }

    private void populateListView(List<Score> scores) {
        ScoreAdapter arrayAdapter = new ScoreAdapter(this, scores);
        listView.setAdapter(arrayAdapter);
        listView.invalidateViews();
    }

    public void getHighscores() {
        new GetScoresTask().execute("https://heroku-postgres-4d67b95d.herokuapp.com/easy");
    }

    private class GetScoresTask extends AsyncTask<String, Void, List<Score>> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(getApplicationContext());
//            progressDialog.setMessage("Retrieving highscores...");
//            progressDialog.show();
        }

        @Override
        protected List<Score> doInBackground(String... params) {
            HttpURLConnection connection = null;
            StringBuilder stringBuilder = new StringBuilder();
            List<Score> scores = new ArrayList<Score>();

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = bufferedReader.readLine()) !=null){
                    // parse into scores...
                    stringBuilder.append(line);
                }

                try {
                    JSONArray array = new JSONArray(stringBuilder.toString());
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        System.out.println(object.toString());
                        Score score = new Score(object.getString("name"), object.getInt("score"));
                        scores.add(score);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return scores;
        }

        @Override
        protected void onPostExecute(List<Score> scores) {
            super.onPostExecute(scores);
            System.out.println(scores);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            populateListView(scores);
        }
    }

}
