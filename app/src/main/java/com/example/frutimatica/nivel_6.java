package com.example.frutimatica;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class nivel_6 extends AppCompatActivity {

    private TextView tv_nombre,tv_score;
    private ImageView iv_auno, iv_ados, iv_vidas,iv_signo;
    private EditText et_respuesta;
    private MediaPlayer mp,mp_great,mp_bad;

    int score, numAleatorio_uno, numAleatorio_dos, resultado, vidas=3;
    String nombre_jugador, string_score, string_vidas;

    String numero [] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel_6);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);



        tv_nombre = (TextView)findViewById(R.id.textView_nombre);
        tv_score = (TextView)findViewById(R.id.textView_score);
        iv_vidas = (ImageView) findViewById(R.id.imageView_vidas);
        iv_auno = (ImageView) findViewById(R.id.image1);
        iv_ados = (ImageView) findViewById(R.id.image2);
        iv_signo = (ImageView) findViewById(R.id.mas);
        et_respuesta = (EditText) findViewById(R.id.editText_resultado);


        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("jugador: "+ nombre_jugador);



        string_score = getIntent().getStringExtra("score");
        if(string_score==null){
            string_score="5";
        }
        score = Integer.parseInt(string_score);
        tv_score.setText("score: "+ score);


        string_vidas = getIntent().getStringExtra("vidas");
        if(string_vidas==null){
            string_vidas="3";
        }

        vidas = Integer.parseInt(string_vidas);
        if(vidas==3){
            iv_vidas.setImageResource(R.drawable.tresvidas);
        }
        if(vidas==2){
            iv_vidas.setImageResource(R.drawable.dosvidas);
        }
        if(vidas==1){
            iv_vidas.setImageResource(R.drawable.unavida);
        }

        mp = MediaPlayer.create(this,R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mp_great = MediaPlayer.create(this, R.raw.wonderful);
        mp_bad = MediaPlayer.create(this, R.raw.bad);

        NumAleatorio();



    }

    public void Comparar(View view){
        String respuesta= et_respuesta.getText().toString();
        if(!respuesta.equals("")){

            int respuesta_jugador = Integer.parseInt(respuesta);
            if(resultado==respuesta_jugador){
                mp_great.start();
                score++;
                tv_score.setText("score: "+ score);
                et_respuesta.setText("");
                BaseDeDatos();


            }else{

                mp_bad.start();
                vidas--;
                BaseDeDatos();

                switch (vidas){

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

        }else{
            Toast.makeText(this, "Escribe tu respuesta", Toast.LENGTH_SHORT).show();
        }
    }

    public void NumAleatorio(){
        if(score<60){

            numAleatorio_uno= (int) (Math.random()*10);
            numAleatorio_dos= (int) (Math.random()*10);
            resultado= numAleatorio_uno + numAleatorio_dos;
            iv_signo.setImageResource(R.drawable.adicion);

            if(numAleatorio_uno >=0 && numAleatorio_dos <=3) {
                resultado = numAleatorio_uno + numAleatorio_dos;
                iv_signo.setImageResource(R.drawable.adicion);

            }else if(numAleatorio_uno >=4 && numAleatorio_dos <=7){
                    resultado = numAleatorio_uno - numAleatorio_dos;
                    iv_signo.setImageResource(R.drawable.resta);

            }else{
                resultado = numAleatorio_uno * numAleatorio_dos;
                iv_signo.setImageResource(R.drawable.multiplicacion);
            }

            if(resultado>=0){

                for(int i = 0; i<numero.length; i++) {
                    int id = getResources().getIdentifier(numero[i], "drawable", getOpPackageName());
                    if(numAleatorio_uno==i){
                        iv_auno.setImageResource(id);

                    }if(numAleatorio_dos==i){
                        iv_ados.setImageResource(id);

                    }
                }
            }else{
                NumAleatorio();
            }




        }else{
            Intent intent= new Intent(this, SplashScreenTerminal.class);

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
                ContentValues modification = new ContentValues();
                modification.put("nombre", nombre_jugador);
                modification.put("score", score);

                BD.update("puntaje", modification, "score", null);
                BD.close();


            } else {
                ContentValues insertar = new ContentValues();
                insertar.put("nombre", nombre_jugador);
                insertar.put("score", score);

                BD.insert("puntaje", null, insertar);
                BD.close();
            }

        }
    }
    public void salir(View view) {  // BOTON PARA SALIR

        finish();
    }
    @Override
    public void onBackPressed(){

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
}