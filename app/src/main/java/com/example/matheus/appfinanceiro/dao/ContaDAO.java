package com.example.matheus.appfinanceiro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.appfinanceiro.helper.SQLiteHelper;
import com.example.matheus.appfinanceiro.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContaDAO(Context context) {
        this.dbHelper = SQLiteHelper.getInstance(context);
    }

    public Boolean salvaConta(Conta conta) {
        try{
            database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("descricao", conta.getDescricao());
            values.put("saldo", conta.getSaldo());

            /*if (conta.getId() != null || conta.getId() > 0)
                database.update("conta", values, "id" + "=" + conta.getId(), null);
            else {*/
                database.insert("conta", null, values);
            //}

            database.close();
        }catch(Exception e){
            Log.i("info", e.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public Double buscarSaldoContas(){
        Double saldo = new Double(0);
        database = dbHelper.getReadableDatabase();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUM(saldo) as total FROM conta");
        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst(); // Precisa ir para o primeiro item do retorno da query, sem esse método, ocorre Exception;

        saldo = cursor.getDouble(0);

        return saldo;
    }

    public Conta buscarContaPorId(Integer id){

        database = dbHelper.getReadableDatabase();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT descricao, saldo FROM conta WHERE id = " + id);
        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst(); // Precisa ir para o primeiro item do retorno da query, sem esse método, ocorre Exception;

        Conta contaDB = new Conta();
        contaDB.setId(id);
        contaDB.setDescricao(cursor.getString(0));
        contaDB.setSaldo(cursor.getDouble(1));

        cursor.close();

        database.close();

        return contaDB;
    }

    public List<Conta> buscarContas() {
        database = dbHelper.getReadableDatabase();

        List<Conta> contasDB = new ArrayList<>();

        String[] cols = new String[]{ "id", "descricao", "saldo"};
        Cursor cursor = database.query("conta",
                cols,
                null, null, null, null, "saldo");

        while (cursor.moveToNext()) {
            Conta conta = new Conta();
            conta.setId(cursor.getInt(0));
            conta.setDescricao(cursor.getString(1));
            conta.setSaldo(Double.valueOf(cursor.getString(2)));

            contasDB.add(conta);
        }

        cursor.close();

        database.close();

        return contasDB;
    }

    public void deletaPorraToda(){
        database = dbHelper.getWritableDatabase();
        database.execSQL(new StringBuilder("DELETE FROM conta").toString());
    }
}