package com.example.frutimatica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreenFinal extends AppCompatActivity {

    String string_score,nombre_jugador;
    int score;
    private static MediaPlayer mp;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreenfinal);

        string_score = getIntent().getStringExtra("score");
        score= Integer.parseInt(string_score);
        nombre_jugador = getIntent().getStringExtra("jugador");


        mp = MediaPlayer.create(this, R.raw.finalsound);
        mp.start();

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                    if(score>0 && score<10){
                        Intent intent = new Intent(SplashScreenFinal.this, nivel_1.class);
                        string_score = String.valueOf(score);
                        intent.putExtra("score", "0");
                        intent.putExtra("jugador", nombre_jugador);
                        startActivity(intent);
                        mp.stop();
                        mp.release();
                        finish();
                    }
                if(score>=10 && score<20){
                    Intent intent = new Intent(SplashScreenFinal.this, nivel_2.class);
                    string_score = String.valueOf(score);
                    intent.putExtra("score", "10");
                    intent.putExtra("jugador", nombre_jugador);
                    startActivity(intent);
                    mp.stop();
                    mp.release();
                    finish();
                }
                if(score>=20 && score<=29){
                    Intent intent = new Intent(SplashScreenFinal.this, nivel_3.class);
                    string_score = String.valueOf(score);
                    intent.putExtra("score", "20");
                    intent.putExtra("jugador", nombre_jugador);
                    startActivity(intent);
                    mp.stop();
                    mp.release();
                    finish();
                }
                if(score>=30 && score<=39){
                    Intent intent = new Intent(SplashScreenFinal.this, nivel_4.class);
                    string_score = String.valueOf(score);
                    intent.putExtra("score", "30");
                    intent.putExtra("jugador", nombre_jugador);
                    startActivity(intent);
                    mp.stop();
                    mp.release();
                    finish();
                }
                if(score>=40 && score<=49){
                    Intent intent = new Intent(SplashScreenFinal.this, nivel_5.class);
                    string_score = String.valueOf(score);
                    intent.putExtra("score", "40");
                    intent.putExtra("jugador", nombre_jugador);
                    startActivity(intent);
                    mp.stop();
                    mp.release();
                    finish();
                }
                if(score>=50 && score<=59){
                    Intent intent = new Intent(SplashScreenFinal.this, nivel_6.class);
                    string_score = String.valueOf(score);
                    intent.putExtra("score", "50");
                    intent.putExtra("jugador", nombre_jugador);
                    startActivity(intent);
                    mp.stop();
                    mp.release();
                    finish();
                }
            }
        },2500);




    }
}