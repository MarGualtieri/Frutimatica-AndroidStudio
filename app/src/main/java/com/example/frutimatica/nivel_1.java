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

        public class nivel_1 extends AppCompatActivity {

            private TextView tv_nombre, tv_score;
            private ImageView iv_auno, iv_ados, iv_vidas;
            private EditText et_respuesta;
            private static MediaPlayer mp, mp_great, mp_bad;

            int score, numAleatorio_uno, numAleatorio_dos, resultado, vidas = 3;
            String nombre_jugador, string_score, string_vidas, nombre_jugadorNew;

            String numero[] = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
            private int String_score;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_nivel_1);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);



        tv_nombre = (TextView) findViewById(R.id.textView_nombre);
        tv_score = (TextView) findViewById(R.id.textView_score);
        iv_vidas = (ImageView) findViewById(R.id.imageView_vidas);
        iv_auno = (ImageView) findViewById(R.id.image1);
        iv_ados = (ImageView) findViewById(R.id.image2);
        et_respuesta = (EditText) findViewById(R.id.editText_resultado);





        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("jugador: " + nombre_jugador);


        string_score = getIntent().getStringExtra("score");
        if(string_score==null){
            string_score="0";
        }
        tv_score.setText("score: "+ string_score);

        mp = MediaPlayer.create(this, R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mp_great = MediaPlayer.create(this, R.raw.wonderful);
        mp_bad = MediaPlayer.create(this, R.raw.bad);






        NumAleatorio();


    }

    public void Comparar(View view) {
        String respuesta = et_respuesta.getText().toString();
        if (!respuesta.equals("")) {

            int respuesta_jugador = Integer.parseInt(respuesta);
            if (resultado == respuesta_jugador) {
                mp_great.start();
                score++;
                tv_score.setText("score: " + score);
                et_respuesta.setText("");
                BaseDeDatos();


            } else {

                mp_bad.start();
                vidas--;
                BaseDeDatos();

                switch (vidas) {

                    case 3:
                        iv_vidas.setImageResource(R.drawable.tresvidas);
                        break;
                    case 2:
                        iv_vidas.setImageResource(R.drawable.dosvidas);

                        break;
                    case 1:
                        iv_vidas.setImageResource(R.drawable.unavida);

                        break;
                    case 0:

                        Intent intent = new Intent(this, SplashScreenFinal.class);
                        string_score = String.valueOf(score);
                        intent.putExtra("score", string_score);
                        intent.putExtra("jugador", nombre_jugador);
                        mp.stop();
                        mp.release();
                        startActivity(intent);
                        finish();
                        break;
                }

                et_respuesta.setText("");
            }

            NumAleatorio();

        } else {
            Toast.makeText(this, "Escribe tu respuesta", Toast.LENGTH_SHORT).show();
        }
    }

    public void NumAleatorio() {
        if (score <= 9) {

            numAleatorio_uno = (int) (Math.random() * 10);
            numAleatorio_dos = (int) (Math.random() * 10);

            resultado = numAleatorio_uno + numAleatorio_dos;

            if (resultado <= 10) {
                for (int i = 0; i < numero.length; i++) {
                    int id = getResources().getIdentifier(numero[i], "drawable", getOpPackageName());
                    if (numAleatorio_uno == i) {
                        iv_auno.setImageResource(id);

                    }
                    if (numAleatorio_dos == i) {
                        iv_ados.setImageResource(id);

                    }
                }
            } else {
                NumAleatorio();
            }

        } else {
            Intent intent = new Intent(this, nivel_2.class);
            string_score = String.valueOf(score);
            string_vidas = String.valueOf(vidas);
            intent.putExtra("jugador", nombre_jugador);
            intent.putExtra("score", string_score);
            intent.putExtra("vidas", string_vidas);

            startActivity(intent);
            finish();
            mp.stop();
            mp.release();


        }

    }

    public void BaseDeDatos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery("select * from puntaje where score = (select max(score) from puntaje)", null);
        if (consulta.moveToFirst()) {
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);

            int bestScore = Integer.parseInt(temp_score);

            if (score > bestScore) {
                ContentValues modificacion = new ContentValues();
                modificacion.put("nombre", nombre_jugador);
                modificacion.put("score", score);
                BD.update("puntaje", modificacion, "score=" + bestScore, null);

            }

            BD.close();


        } else {
            ContentValues insertar = new ContentValues();
            insertar.put("nombre", nombre_jugador);
            insertar.put("score", score);
            BD.insert("puntaje", null, insertar);
            BD.close();
        }

    }

    public void salir(View view) {  // BOTON PARA SALIR

        finish();

    }
   @Override
    public void onBackPressed() {

    }
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

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("media", mp.getCurrentPosition());
        mp.stop();
        mp.release();
        outState.putString("score",tv_score.getText().toString());
        outState.putInt("IMAGE_RESOURCE",iv_vidas );

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mp.seekTo(savedInstanceState.getInt("media"));

        String scoreText = savedInstanceState.getString("score");
        tv_score.setText(scoreText);

    }*/
}

