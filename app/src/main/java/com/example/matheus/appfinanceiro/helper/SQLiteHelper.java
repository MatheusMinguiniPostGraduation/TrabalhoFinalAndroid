package com.example.matheus.appfinanceiro.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.matheus.appfinanceiro.util.DBQueries;
import com.example.matheus.appfinanceiro.view.ListaContaActivity;

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
        db.execSQL(DBQueries.CRIAR_TABELA_CONTA);
        db.execSQL(DBQueries.CRIAR_TABELA_CENTRO_CUSTO);
        db.execSQL(DBQueries.CRIAR_TABELA_TRANSACAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}