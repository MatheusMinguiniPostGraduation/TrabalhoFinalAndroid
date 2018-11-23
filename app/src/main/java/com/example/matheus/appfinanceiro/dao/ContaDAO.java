package com.example.matheus.appfinanceiro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.appfinanceiro.helper.SQLiteHelper;
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.util.DBQueries;

import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContaDAO(Context context) {
        this.dbHelper = SQLiteHelper.getInstance(context);
    }

    public Double buscarSaldoContas(){
        try {
            database = dbHelper.getReadableDatabase();

            StringBuilder sql = new StringBuilder();
            sql.append(DBQueries.BUSCAR_SALDO_CONTAS_QUERY);
            Cursor cursor = database.rawQuery(sql.toString(), null);

            cursor.moveToFirst(); // Precisa ir para o primeiro item do retorno da query, sem esse método, ocorre Exception;

            return cursor.getDouble(0);
        }catch(Exception e){
            Log.i("ERROR", e.getMessage());
        }

        return null;
    }

    public Double buscarSaldoContaPorId(Integer id){
        try{
            database = dbHelper.getReadableDatabase();

            Cursor cursor = database.rawQuery(DBQueries.BUSCAR_SALDO_CONTA_POR_ID_QUERY, new String[] {String.valueOf(id)});

            cursor.moveToFirst();

            return cursor.getDouble(0);
        }catch(Exception e){
            Log.i("ERROR", e.getMessage());
        }

       return null;
    }

    public Conta buscarContaPorId(Integer id){

        try{
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(DBQueries.BUSCAR_CONTA_POR_ID, new String[] {String.valueOf(id)});

            cursor.moveToFirst();

            Conta contaDB = new Conta();
            contaDB.setId(id);
            contaDB.setDescricao(cursor.getString(0));
            contaDB.setSaldo(cursor.getDouble(1));

            cursor.close();

            database.close();

            return contaDB;
        }catch (Exception e){
            Log.i("ERROR", e.getMessage());
        }

       return null;
    }

    public Boolean salvaConta(Conta conta) {
        try{
            database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("descricao", conta.getDescricao());
            values.put("saldo", conta.getSaldo());

            database.insert(DBQueries.TABELA_CONTA, null, values);


            database.close();
        }catch(Exception e){
            Log.i("ERROR", e.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public List<Conta> buscarContas() {
        try{
            database = dbHelper.getReadableDatabase();

            List<Conta> contasDB = new ArrayList<>();

            String[] cols = new String[]{ "id", "descricao", "saldo"};
            Cursor cursor = database.query(DBQueries.TABELA_CONTA,
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
        }catch(Exception e){
            Log.i("ERROR", e.getMessage());
        }
        return null;
    }
}