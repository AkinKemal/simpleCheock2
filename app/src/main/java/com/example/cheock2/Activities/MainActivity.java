package com.example.cheock2.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cheock2.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button player_one_timer_button;
    private Button player_two_timer_button;
    private Button start_button;
    private Button stop_button;
    private Button reset_button;
    private Button change_time_button;

    private CountDownTimer player_one_countdown_timer;
    private CountDownTimer player_two_countdown_timer;

    protected int fixedTime = 10;
    private long timeSecond1 = fixedTime * 60 * 1000;
    private long timeSecond2 = fixedTime * 60 * 1000;
    private int time = fixedTime;

    private boolean bool1 = false;
    private boolean bool2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player_one_timer_button = findViewById(R.id.player_one_timer_button);
        player_two_timer_button = findViewById(R.id.player_two_timer_button);
        start_button = findViewById(R.id.start_button);
        stop_button = findViewById(R.id.stop_button);
        reset_button = findViewById(R.id.reset_button);
        change_time_button = findViewById(R.id.change_time_button);

        player_one_timer_button.setEnabled(false);
        player_two_timer_button.setEnabled(false);
        stop_button.setEnabled(false);
        reset_button.setEnabled(false);
        change_time_button.setEnabled(false);

        bool1 = false;
        bool2 = false;

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });

        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        player_one_timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCountDownTimer2();
                startCountDownTimer1();
            }
        });

        player_two_timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCountDownTimer1();
                startCountDownTimer2();
            }
        });

        change_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    private void start() {

        if (bool1) {
            startCountDownTimer2();
        } else if (bool2) {
            startCountDownTimer1();
        } else {
            startCountDownTimer1();
        }

        start_button.setEnabled(false);
        stop_button.setEnabled(true);
        reset_button.setEnabled(false);
        change_time_button.setEnabled(true);
    }

    private void stop() {

        if (bool2) {
            cancelCountDownTimer1();
        } else if (bool1) {
            cancelCountDownTimer2();
        }

        stop_button.setEnabled(false);
        reset_button.setEnabled(true);
        change_time_button.setEnabled(false);
        start_button.setEnabled(true);
    }

    private void finishControl() {
        stop_button.setEnabled(false);
        reset_button.setEnabled(false);
        change_time_button.setEnabled(false);
        start_button.setEnabled(true);

        timeSecond1 = (long) fixedTime * 60 * 1000;
        timeSecond2 = (long) fixedTime * 60 * 1000;
        time = fixedTime;

        player_one_timer_button.setBackgroundColor(Color.parseColor("#0E171E"));
        player_one_timer_button.setAlpha(1);
        player_two_timer_button.setBackgroundColor(Color.parseColor("#0E171E"));
        player_two_timer_button.setAlpha(1);
    }

    private void reset() {

        if (bool1) {
            cancelCountDownTimer2();
            bool1 = false;
        } else if (bool2) {
            cancelCountDownTimer1();
            bool2 = false;
        }

        finishControl();
        player_one_timer_button.setText(getResources().getString(R.string.player_1));
        player_two_timer_button.setText(getResources().getString(R.string.player_2));
    }

    private void update() {
        time += 0.5;
        timeSecond1 += 30 * 1000;
        timeSecond2 += 30 * 1000;

        // Eğer bir countdown timer çalışıyorsa, kalan süresini güncelle
        if (bool1) {
            cancelCountDownTimer2();
            startCountDownTimer2();

            int minutes2 = (int) (timeSecond2 / 60000);
            int second2 = (int) ((timeSecond2 / 1000) % 60);
            String text2 = String.format(Locale.getDefault(), "%02d:%02d", minutes2, second2);
            player_two_timer_button.setText(text2);

        } else if (bool2) {
            cancelCountDownTimer1();
            startCountDownTimer1();
            int minutes = (int) (timeSecond1 / 60000);
            int second = (int) ((timeSecond1 / 1000) % 60);
            String text = String.format(Locale.getDefault(), "%02d:%02d", minutes, second);
            player_one_timer_button.setText(text);
        }
    }

    private void startCountDownTimer1() {

        bool1 = false;
        bool2 = true;

        player_two_timer_button.setEnabled(true);
        player_one_timer_button.setAlpha(0.5F);
        player_two_timer_button.setAlpha(1);

        player_two_countdown_timer = new CountDownTimer(timeSecond2, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeSecond2 = millisUntilFinished;
                int minutes = (int) (timeSecond2 / 60000);
                int second = (int) ((timeSecond2 / 1000) % 60);
                String text = String.format(Locale.getDefault(), "%02d:%02d", minutes, second);
                player_two_timer_button.setText(text);

                if ((((long) time * 60 * 1000) * 10) / 100 >= timeSecond2) {
                    player_two_timer_button.setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }

            @Override
            public void onFinish() {
                player_two_timer_button.setText(getResources().getString(R.string.lose));
                player_two_timer_button.setEnabled(false);

                finishControl();
            }
        };

        player_two_countdown_timer.start();
    }

    private void cancelCountDownTimer1() {
        if (bool2) {
            player_two_timer_button.setEnabled(false);
            player_two_countdown_timer.cancel();
        }
    }

    private void startCountDownTimer2() {

        bool2 = false;
        bool1 = true;

        player_one_timer_button.setEnabled(true);
        player_two_timer_button.setAlpha(0.5F);
        player_one_timer_button.setAlpha(1);

        player_one_countdown_timer = new CountDownTimer(timeSecond1, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeSecond1 = millisUntilFinished;
                int minutes = (int) (timeSecond1 / 60000);
                int second = (int) ((timeSecond1 / 1000) % 60);
                String text = String.format(Locale.getDefault(), "%02d:%02d", minutes, second);
                player_one_timer_button.setText(text);

                if ((((long) time * 60 * 1000) * 10) / 100 >= timeSecond1) {
                    player_one_timer_button.setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }

            @Override
            public void onFinish() {
                player_one_timer_button.setText(getResources().getString(R.string.lose));
                player_one_timer_button.setEnabled(false);

                finishControl();
            }
        };

        player_one_countdown_timer.start();
    }

    private void cancelCountDownTimer2() {
        if (bool1) {
            player_one_timer_button.setEnabled(false);
            player_one_countdown_timer.cancel();
        }
    }
}