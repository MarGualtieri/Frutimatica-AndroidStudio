package com.example.frutimatica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.RSAPrivateKeySpec;

public class MainActivity extends AppCompatActivity {


    private ImageView iv_personaje;
    private Button btn_jugar;
    private TextView tv_bestScore;
    private EditText et_nombre;
    private MediaPlayer mp;


    int num_aleatorio = (int) (Math.random() * 5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayShowHomeEnabled(true); // iconos de la barra
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        iv_personaje = (ImageView) findViewById(R.id.imageView_personaje);
        btn_jugar = (Button) findViewById(R.id.Boton_jugar);
        tv_bestScore = (TextView) findViewById(R.id.textView_BestScore);
        et_nombre = (EditText) findViewById(R.id.Ingresar_nombre);

        int id;
        if (num_aleatorio == 1) {
            id = getResources().getIdentifier("mango", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if (num_aleatorio == 2) {
            id = getResources().getIdentifier("fresa", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if (num_aleatorio == 3) {
            id = getResources().getIdentifier("manzana", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if (num_aleatorio == 4) {
            id = getResources().getIdentifier("sandia", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (num_aleatorio == 0) {
            id = getResources().getIdentifier("sandia", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1); // BASE DE DATOS PARA UNIR EL SCORE O PUNTAJE
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery(   // LINKEAR BASE DE DATOS

                "select * from puntaje where score = (select max(score) from puntaje)", null);
        if (consulta.moveToFirst()) {

            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);

            tv_bestScore.setText("Record : " + temp_score + " de " + temp_nombre);

            BD.close();
        } else {
            BD.close();
        }

        mp = MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);


    }


    public void jugar(View view) {

        String nombre = et_nombre.getText().toString();

        if (!nombre.equals("")) {
          /*  mp.stop();
            mp.release();*/
            Intent intent = new Intent(this, nivel_1.class);
            intent.putExtra("jugador", et_nombre.getText().toString());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Debes escribir tu nombre antes", Toast.LENGTH_SHORT).show();

            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_nombre, InputMethodManager.SHOW_IMPLICIT);
        }

    }

    @Override
    public void onBackPressed() {  // DESHABILITA LA FELCHA DE SALIDA DEL CELULAR

    }

    public void salir(View view) {  // BOTON PARA SALIR

        finish();
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //if you are using mediaplayer than u need to mp.getCurrentPosition();
            int currentPosition = mp.getCurrentPosition();
            mp.seekTo(currentPosition);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int currentPosition = mp.getCurrentPosition();
            mp.seekTo(currentPosition);
        }
    }

 @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
     super.onSaveInstanceState(outState);

     outState.putInt("media", mp.getCurrentPosition());
    mp.stop();
    mp.release();

 }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int position=savedInstanceState.getInt("media");
        mp.seekTo(position);



    }

*/
    @Override
    public void onPause() {
        super.onPause();
        mp.pause();

    }

    @Override
    public void onResume() {
        super.onResume();
        mp.start();
    }
}
