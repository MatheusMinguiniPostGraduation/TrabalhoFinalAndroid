package com.example.matheus.appfinanceiro.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matheus.appfinanceiro.R;
import com.example.matheus.appfinanceiro.VO.TransacaoVO;
import com.example.matheus.appfinanceiro.util.ConstantesUtil;


import java.util.List;

public class TransacaoAdapter extends ArrayAdapter<TransacaoVO> {

    private TextView descricaoTransacaoTextView;
    private TextView valorTransacaoTextView;
    private TextView naturezaOperacaoTransacaoTextView;
    private TextView centroCustoTransacaoTextView;

    //Objeto responsável por transformar um xml em uma instância de um objeto
    private LayoutInflater layoutInflater;

    public TransacaoAdapter(@NonNull Context context, List<TransacaoVO> transacaoList) {
        super(context, R.layout.layout_view_transacao_adapter, transacaoList);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Verificar se célula ainda não existe
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_view_transacao_adapter, null);
        }

        this.descricaoTransacaoTextView =  convertView.findViewById(R.id.descricao_transacao_view);
        this.valorTransacaoTextView =  convertView.findViewById(R.id.valor_transacao_view);
        this.naturezaOperacaoTransacaoTextView =  convertView.findViewById(R.id.natureza_transacao_view);
        this.centroCustoTransacaoTextView =  convertView.findViewById(R.id.centro_custo_transacao_view);


        pintarTela(getItem(position));

        TransacaoVO transacao = getItem(position);
        descricaoTransacaoTextView.setText(transacao.getDescricao());
        valorTransacaoTextView.setText("R$ ".concat(String.valueOf(transacao.getValor())));
        naturezaOperacaoTransacaoTextView.setText(transacao.getNaturezaOperacao());
        centroCustoTransacaoTextView.setText("(".concat(transacao.getCentroCusto()).concat(")"));

        return convertView;
    }

    private void pintarTela(TransacaoVO transacaoVO){
        Integer cor;
        if(transacaoVO.getNaturezaOperacao().equals("Entrada")){
            cor = Color.parseColor("#FF008577");
        }else{
            cor = Color.parseColor("#B22222");
        }

        descricaoTransacaoTextView.setTextColor(cor);
        valorTransacaoTextView.setTextColor(cor);
        naturezaOperacaoTransacaoTextView.setTextColor(cor);
        centroCustoTransacaoTextView.setTextColor(cor);
    }
}
