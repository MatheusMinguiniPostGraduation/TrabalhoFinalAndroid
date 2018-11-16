package com.example.matheus.appfinanceiro.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteHelper sInstance;

    private static final String DATABASE_NAME = "app_financeiro";
    private static final int DATABASE_VERSION = 1;

    public static synchronized SQLiteHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new SQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       final String DATABASE_CREATE= "CREATE TABLE conta" +
                " (" + " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " descricao TEXT NOT NULL, " +
                " saldo DECIMAL (10,2));";

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}