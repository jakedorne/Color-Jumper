package com.jake.colorjumper.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jake.colorjumper.R;
import com.jake.colorjumper.models.Score;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SubmitScoreActivity extends Activity {

    protected int score;
    protected String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_score);
        Bundle b = getIntent().getExtras();
        score = b.getInt("Score");

    }

    public void submitScore(View view){
        EditText nameField = (EditText) findViewById(R.id.scoreSubmissionName);
        name = nameField.getText().toString();
        new SubmitScoreTask().execute("https://heroku-postgres-4d67b95d.herokuapp.com/easy");
    }

    private class SubmitScoreTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(getApplicationContext());
//            progressDialog.setMessage("Retrieving highscores...");
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return submitData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected String submitData(String urlString) throws IOException{
            HttpURLConnection connection = null;
            JSONObject object = new JSONObject();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            BufferedWriter bufferedWriter = null;

            try {
                object.put("name", name);
                object.put("score", score);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();

                OutputStream outputStream = connection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(object.toString());
                bufferedWriter.flush();

                InputStream inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(bufferedReader!=null){
                    bufferedReader.close();
                }
                if(bufferedWriter!=null){
                    bufferedWriter.close();
                }
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }

            Intent intent = new Intent(getApplicationContext(), HighScoresActivity.class);
            startActivity(intent);
        }
    }
}
