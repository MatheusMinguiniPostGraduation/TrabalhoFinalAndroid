package com.example.matheus.appfinanceiro.util;

import android.content.Context;

import com.example.matheus.appfinanceiro.VO.TransacaoVO;
import com.example.matheus.appfinanceiro.dao.TransacaoDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransacaoUtil {

    public static Double calcularSaldoConta(List<TransacaoVO> historicoTransacao) {
        List<TransacaoVO> listaDebito = retornarTransacoesDebito(historicoTransacao);
        List<TransacaoVO> listaCredito = retornarTransacoesCredito(historicoTransacao);

        Double saldoPositivo = 0d;
        Double saldoNegativo = 0d;

        for(TransacaoVO transacao : listaDebito){
            saldoNegativo += Double.valueOf(transacao.getValor());
        }

        for(TransacaoVO transacao : listaCredito){
            saldoPositivo += Double.valueOf(transacao.getValor());
        }

        return saldoPositivo - saldoNegativo;
    }

    public static List<TransacaoVO> buscarHistoricoTransacoes(Context context, Integer contaId){
        return new TransacaoDAO(context).buscarHistoricoTransacoesPorConta(contaId);
    }

    private static List<TransacaoVO> retornarTransacoesDebito(List<TransacaoVO> listaOriginal) {
        List<TransacaoVO> listaFiltrada = new ArrayList<TransacaoVO>();
        Iterator<TransacaoVO> transacaoIterator = listaOriginal.iterator();
        while (transacaoIterator.hasNext()) {
            TransacaoVO transacao = transacaoIterator.next();
            if (transacao.getNaturezaOperacao().equalsIgnoreCase(ConstantesUtil.SAIDA_TRANSACAO)) {
                listaFiltrada.add(transacao);
            }
        }
        return listaFiltrada;
    }

    private static List<TransacaoVO> retornarTransacoesCredito(List<TransacaoVO> listaOriginal) {
        List<TransacaoVO> listaFiltrada = new ArrayList<TransacaoVO>();
        Iterator<TransacaoVO> transacaoIterator = listaOriginal.iterator();
        while (transacaoIterator.hasNext()) {
            TransacaoVO transacao = transacaoIterator.next();
            if (transacao.getNaturezaOperacao().equalsIgnoreCase(ConstantesUtil.ENTRADA_TRANSACAO)) {
                listaFiltrada.add(transacao);
            }
        }
        return listaFiltrada;
    }
}
