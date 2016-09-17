package com.jake.colorjumper.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jake.colorjumper.R;
import com.jake.colorjumper.models.Score;

import java.util.List;

/**
 * Created by jaek on 17/09/16.
 */
public class ScoreAdapter extends ArrayAdapter<Score> {

    public ScoreAdapter(Context context, List<Score> array) {
        super(context, R.layout.score_list_row, array);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.score_list_row, parent, false);

        Score score = getItem(position);

        TextView nameText = (TextView) view.findViewById(R.id.score_list_name);
        TextView scoreText = (TextView) view.findViewById(R.id.score_list_score);

        nameText.setText(score.getName());
        scoreText.setText(String.valueOf(score.getScore()));

        return view;
    }

}
