package com.example.matheus.appfinanceiro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.appfinanceiro.VO.TransacaoVO;
import com.example.matheus.appfinanceiro.helper.SQLiteHelper;
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.model.Transacao;
import com.example.matheus.appfinanceiro.util.ConstantesUtil;
import com.example.matheus.appfinanceiro.util.DBQueries;

import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public TransacaoDAO(Context context) {
        this.dbHelper = SQLiteHelper.getInstance(context);
    }

    public Boolean salvarTransacao(Transacao transacao) {
        try{
            database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("descricao", transacao.getDescricao());
            values.put("valor", transacao.getValor());
            values.put("conta", transacao.getConta().getId());
            values.put("centro_custo", transacao.getCentro_custo().getId());
            values.put("debito", transacao.getNatureza_operacao());

            database.insert("transacao", null, values);

            database.close();
        }catch(Exception e){
            Log.i("info", e.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public List<TransacaoVO> buscarHistoricoTransacoesPorConta(Integer contaId){
        database = dbHelper.getReadableDatabase();

        List<TransacaoVO> transacoesDB = new ArrayList<>();

        String[] cols = new String[]{"t.descricao", "t.valor", "t.natureza_operacao", "c.descricao"};

        Cursor cursor = database.rawQuery(DBQueries.BUSCAR_HISTORICO_TRANSACAO_POR_CONTA_QUERY + contaId, null);


        while (cursor.moveToNext()) {
            TransacaoVO transacao = new TransacaoVO();
            transacao.setDescricao(cursor.getString(0));
            transacao.setValor(cursor.getString(1));

            String natureza_operacao = (String.valueOf(cursor.getInt(2))
                    .equalsIgnoreCase(String.valueOf(ConstantesUtil.DEBITO)) ? "Débito" : "Crédito");
            transacao.setNaturezaOperacao(natureza_operacao);

            transacao.setCentroCusto(cursor.getString(3));

            transacoesDB.add(transacao);
        }

        cursor.close();

        database.close();

        return transacoesDB;
    }
}
