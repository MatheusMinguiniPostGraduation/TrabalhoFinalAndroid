package com.example.matheus.appfinanceiro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.appfinanceiro.helper.SQLiteHelper;
import com.example.matheus.appfinanceiro.model.CentroCusto;
import com.example.matheus.appfinanceiro.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class CentroCustoDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public CentroCustoDAO(Context context) {

        this.dbHelper = SQLiteHelper.getInstance(context);

        this.verificaNecessidadePopularTabela();
    }

    private void verificaNecessidadePopularTabela() {
        List<CentroCusto> lista = buscarTodosCentroCusto();

        if(lista.isEmpty()){
            popularTabela();
        }
    }

    public List<CentroCusto> buscarTodosCentroCusto() {
        List<CentroCusto> listaCentroCustoDB = new ArrayList<>();

        try {
            database = dbHelper.getReadableDatabase();


            String[] cols = new String[]{"id", "descricao"};
            Cursor cursor = database.query("centro_custo",
                    cols,
                    null, null, null, null, "descricao");

            while (cursor.moveToNext()) {
                CentroCusto centroCusto = new CentroCusto();
                centroCusto.setId(cursor.getInt(0));
                centroCusto.setDescricao(cursor.getString(1));
                listaCentroCustoDB.add(centroCusto);
            }
        } catch (Exception e) {
            Log.i("info", e.getMessage());
        }

        return listaCentroCustoDB;
    }

    private void popularTabela(){
        database = dbHelper.getWritableDatabase();

        List<ContentValues> listaContentValues = new ArrayList<ContentValues>();

        List<String> descricaoCentroCusto = new ArrayList<>();
        descricaoCentroCusto.add("Combustível");
        descricaoCentroCusto.add("Lazer");
        descricaoCentroCusto.add("Educação");
        descricaoCentroCusto.add("Alimentação");
        descricaoCentroCusto.add("Transporte");


        for(int i = 0; i < 5; i++){
            ContentValues values = new ContentValues();
            values.put("descricao", descricaoCentroCusto.get(i));
            listaContentValues.add(values);
        }

        for(ContentValues values : listaContentValues){
            database.insert("centro_custo", null, values);
        }
    }
}
