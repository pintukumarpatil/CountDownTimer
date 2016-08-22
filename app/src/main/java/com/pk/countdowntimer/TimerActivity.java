package com.pk.countdowntimer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_timer;
   CountDownTimer countDownTimer;
    boolean isRunning=true;
    long counterTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //initialize all views
        tv_timer=(TextView)findViewById(R.id.tv_timer);
        findViewById(R.id.rl_out).setOnClickListener(this);


        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts/book.ttf");
       // tv_timer.setTypeface(tf);

        isRunning=true;
        counterTime= TimeUnit.SECONDS.toMillis(60);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start(counterTime);
            }
        }, 200);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_out:
                if (isRunning){
                    isRunning=false;
                    stop();
                }else {
                    isRunning=true;
                    start(counterTime);
                }
                break;
        }
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    private String getTimeString2(long millis) {
        int seconds = (int) ((millis / 1000) % 60);
        int seconds100 = (int) ((millis / 10) % 100);
        return  String.format("%02d:%02d", seconds, seconds100);
    }
    public void stop(){
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
    public void start(long time){
        countDownTimer = new CountDownTimer(time, 1) {
            public void onTick(long millisUntilFinished) {
                counterTime=millisUntilFinished;
                // tv_timer.setText((new SimpleDateFormat("ss:SSS")).format(new Date(millisUntilFinished)));
                tv_timer.setText(getTimeString2(millisUntilFinished));
            }
            public void onFinish() {
                tv_timer.setText("00:00");
                startActivity(new Intent(TimerActivity.this,MainActivity.class));
                finish();
            }
        };
        if (countDownTimer!=null){
            countDownTimer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}
