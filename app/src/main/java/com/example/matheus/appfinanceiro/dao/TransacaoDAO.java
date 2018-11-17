package com.example.matheus.appfinanceiro.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.matheus.appfinanceiro.helper.SQLiteHelper;
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.model.Transacao;
import com.example.matheus.appfinanceiro.util.DBQueries;

import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public TransacaoDAO(Context context) {
        this.dbHelper = SQLiteHelper.getInstance(context);
    }

    public List<Transacao> buscarHistoricoTransacoesPorConta(){
        database = dbHelper.getReadableDatabase();

        List<Transacao> transacoesDB = new ArrayList<>();

        String[] cols = new String[]{"descricao", "valor", };

        Cursor cursor = database.rawQuery(DBQueries.BUSCAR_HISTORICO_TRANSACAO_POR_CONTA_QUERY, null);


        while (cursor.moveToNext()) {
            Transacao transacao = new Transacao();
            transacao.setDescricao(cursor.getString(0));
            transacao.setValor(Double.valueOf(cursor.getString(1)));

            String natureza_operacao = (String.valueOf(cursor.getInt(2)) == "1" ? "Débito" : "Crédito");
            transacao.setNatureza_operacao(natureza_operacao);

            transacao.setValor(Double.valueOf(cursor.getString(1)));

            transacoesDB.add(transacao);
        }

        cursor.close();

        database.close();

        return transacoesDB;
    }
}
