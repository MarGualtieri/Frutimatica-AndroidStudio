package com.example.frutimatica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) { //ESTE ES EL CONSTRUCTOR
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase base) {
    base.execSQL("create table puntaje(nombre text, score int)");    // SE CREA LA TABLA Y SE LE INDICA 2 PARAMETROS EN ESTE CASO
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}


