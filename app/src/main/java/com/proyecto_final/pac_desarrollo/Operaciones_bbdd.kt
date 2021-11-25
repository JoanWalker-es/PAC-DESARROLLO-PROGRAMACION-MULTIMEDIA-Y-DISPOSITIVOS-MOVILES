package com.proyecto_final.pac_desarrollo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class Operaciones_bbdd(context: Context, DATABASE_NAME: String?, DATABASE_VERSION: Int) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Alumnos(codigo integer primary key, nombre text, apellidos text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}