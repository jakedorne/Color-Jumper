package com.jake.colorjumper.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.jake.colorjumper.MyApplication;
import com.jake.colorjumper.R;

/**
 * Created by jake on 13/05/16.
 */
public class SettingsActivity extends Activity {

    SeekBar valueBar;
    SeekBar saturationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton);
        valueBar = (SeekBar) findViewById(R.id.valueBar);
        valueBar.setProgress((int)(MyApplication.value*100));
        saturationBar = (SeekBar) findViewById(R.id.saturationBar);
        saturationBar.setProgress((int)(MyApplication.saturation*100));
        setValueBarListener();

        if(MyApplication.difficulty==1){
            button.setChecked(true);
        } else {
            button.setChecked(false);
        }
    }

    private void setValueBarListener() {
        valueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println("seek bar moved: "+ progress/100);
                MyApplication.setValue((float)progress/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saturationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println("seek bar moved: "+ progress/100);
                MyApplication.setSaturation((float)progress/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void toggleDifficulty(View view){
        MyApplication.toggleDifficulty();
    }
}
