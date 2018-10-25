package com.example.idtyp.egg_app;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timeSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;

        String secondString = Integer.toString(seconds);
        if (seconds < 10) {
            secondString = "0" + seconds;
        }

        timerTextView = findViewById(R.id.timerTextView);
        timerTextView.setText(minutes + ":" + secondString);
    }

    public void controlTimer(View view) {

        if (!counterIsActive) {
            counterIsActive = true;
            timeSeekBar.setEnabled(false);
            controllerButton.setText("Stop");
            countDownTimer = new CountDownTimer(timeSeekBar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    System.out.println(millisUntilFinished);
                    updateTimer((int) (millisUntilFinished + 100) / 1000);
                    timeSeekBar.setProgress((int) ((millisUntilFinished + 100) / 1000));
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    counterIsActive = false;
                    controllerButton.setText("Go!");
                    timerTextView.setText("0:30");
                    timeSeekBar.setProgress(30);
                    mediaPlayer.start();
                    timeSeekBar.setEnabled(true);

                }
            }.start();
        } else {
            counterIsActive = false;
            timeSeekBar.setEnabled(true);
            controllerButton.setText("Go!");
            countDownTimer.cancel();
//            timerTextView.setText("0:30");
//            timeSeekBar.setProgress(30);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerButton = findViewById(R.id.controllerButton);

        timeSeekBar = findViewById(R.id.timerSeekBar);

        timeSeekBar.setMax(600);
        timeSeekBar.setProgress(30);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
