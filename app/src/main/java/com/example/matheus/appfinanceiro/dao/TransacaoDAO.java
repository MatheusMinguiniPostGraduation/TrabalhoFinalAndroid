package com.example.matheus.appfinanceiro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.appfinanceiro.VO.TransacaoVO;
import com.example.matheus.appfinanceiro.helper.SQLiteHelper;
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

    public Double buscarValorTransacoesDebito(){
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(DBQueries.BUSCAR_VALOR_TRANSACOES_DEBITO_QUERY, new String[] {String.valueOf(ConstantesUtil.DEBITO)});

        cursor.moveToFirst();

        return cursor.getDouble(0);
    }

    public Double buscarValorTransacoesCredito(){
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(DBQueries.BUSCAR_VALOR_TRANSACOES_CREDITO_QUERY, new String[] {String.valueOf(ConstantesUtil.CREDITO)});

        cursor.moveToFirst();

        return cursor.getDouble(0);
    }

    public List<TransacaoVO> buscarTransacoesDebito(){
        List<TransacaoVO> transacoesDebito = new ArrayList<TransacaoVO>();
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(DBQueries.BUSCAR_TRANSACOES_DEBITO_AGRUPADAS_POR_TIPO_CUSTO_QUERY, new String[] {String.valueOf(ConstantesUtil.DEBITO)});

        while (cursor.moveToNext()) {
            TransacaoVO transacao = new TransacaoVO();
            transacao.setValor(String.valueOf(cursor.getDouble(0)));
            transacao.setCentroCusto(cursor.getString(1));

            transacoesDebito.add(transacao);
        }

        return transacoesDebito;
    }

    public List<TransacaoVO> buscarHistoricoTransacoesPorConta(Integer contaId){
        database = dbHelper.getReadableDatabase();

        List<TransacaoVO> transacoesDB = new ArrayList<>();

        String[] cols = new String[]{"t.descricao", "t.valor", "t.natureza_operacao", "c.descricao"};

        Cursor cursor = database.rawQuery(DBQueries.BUSCAR_HISTORICO_TRANSACAO_POR_CONTA_QUERY, new String[] {String.valueOf(contaId)});


        while (cursor.moveToNext()) {
            TransacaoVO transacao = new TransacaoVO();
            transacao.setDescricao(cursor.getString(0));
            transacao.setValor(cursor.getString(1));

            String natureza_operacao = (String.valueOf(cursor.getInt(2))
                    .equalsIgnoreCase(String.valueOf(ConstantesUtil.DEBITO)) ? ConstantesUtil.SAIDA_TRANSACAO : ConstantesUtil.ENTRADA_TRANSACAO);
            transacao.setNaturezaOperacao(natureza_operacao);

            transacao.setCentroCusto(cursor.getString(3));

            transacoesDB.add(transacao);
        }

        cursor.close();

        database.close();

        return transacoesDB;
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

    public Boolean salvarTransacao(Transacao transacao) {
        try{
            database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("descricao", transacao.getDescricao());
            values.put("valor", transacao.getValor());
            values.put("conta", transacao.getConta().getId());
            values.put("centro_custo", transacao.getCentro_custo().getId());
            values.put("debito", transacao.getNatureza_operacao());

            database.insert(DBQueries.TABELA_TRANSACAO, null, values);

            Double saldo = buscarSaldoContaPorId(transacao.getConta().getId());

            //Subtrai ou adiciona do saldo atual da conta, baseando-se na natureza da operação (crédito ou débito)
            if(transacao.getNatureza_operacao() == ConstantesUtil.DEBITO){
                saldo = saldo - transacao.getValor();
            }else{
                saldo = saldo + transacao.getValor();
            }

            //Atualizar saldo da conta
            ContentValues valor = new ContentValues();
            valor.put("saldo", saldo);
            database.update(DBQueries.TABELA_CONTA, valor, "id="+transacao.getConta().getId(), null);

            database.close();
        }catch(Exception e){
            Log.i("ERROR", e.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

}
