package com.example.frutimatica;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenTerminal extends AppCompatActivity {


    private static MediaPlayer mp;
Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreenterminal);

        mp = MediaPlayer.create(this, R.raw.terminalsound);
        mp.start();


        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreenTerminal.this, MainActivity.class);
                startActivity(intent);
                mp.stop();
                mp.release();
                finish();
            }
        },3000);




    }
}