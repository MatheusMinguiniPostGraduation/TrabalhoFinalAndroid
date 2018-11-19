package com.example.matheus.appfinanceiro.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.appfinanceiro.R;
import com.example.matheus.appfinanceiro.dao.ContaDAO;
import com.example.matheus.appfinanceiro.dao.TransacaoDAO;
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.model.Transacao;
import com.example.matheus.appfinanceiro.util.ConstantesUtil;

import java.util.List;

public class DetalheContaActivity extends AppCompatActivity {

    private static final int NOVA_TRANSACAO_REQUEST_CODE = 0;
    TextView descricaoView;
    TextView saldoView;

    Integer contaId;

    List<Transacao> historicoTransacao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_conta);

        contaId = (Integer) getIntent().getSerializableExtra(ConstantesUtil.CONTA_DETALHE);

        Conta contaDetalhe = new ContaDAO(this).buscarContaPorId(contaId);

        saldoView = findViewById(R.id.saldo_conta_detalhe);
        descricaoView = findViewById(R.id.descricao_conta_detalhe);

        saldoView.setText("R$ " + contaDetalhe.getSaldo());
        descricaoView.setText(contaDetalhe.getDescricao());

        buscarHistoricoTransacoes();

        getSupportActionBar().setSubtitle("Detalhes da conta bancária");

    }

    public void buscarHistoricoTransacoes(){
        historicoTransacao = new TransacaoDAO(this).buscarHistoricoTransacoesPorConta(contaId);
        Log.i("info", "AAAA");

    }

    public void  novaTransacao(View view){
        Intent novaTransacaoIntent = new Intent(this, NovaTransacaoActivity.class);

        novaTransacaoIntent.putExtra(ConstantesUtil.CONTA_ID, contaId);
        startActivityForResult(novaTransacaoIntent, NOVA_TRANSACAO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case NOVA_TRANSACAO_REQUEST_CODE:
                if(resultCode == RESULT_OK) {

                    //VER SE ISSO AQUI VAI FICAR ASSIM MESMO
                    /*this.listaConta.removeAll(listaConta);
                    this.listaConta.addAll(contaDAO.buscarContas());

                    saldoContasView.setText("R$ ".concat(String.valueOf(contaDAO.buscarSaldoContas()))); //Atualiza o saldo na tela
                    this.contaAdapter.notifyDataSetChanged();*/
                    Toast.makeText(this, R.string.msg_sucesso, Toast.LENGTH_LONG).show();
                }

                if(resultCode == RESULT_CANCELED){
                    Toast.makeText(this, R.string.msg_cancelamento, Toast.LENGTH_LONG).show();
                }

                if(resultCode == ConstantesUtil.RESULT_ERROR){
                    Toast.makeText(this, R.string.msg_cancelamento, Toast.LENGTH_LONG).show();
                }
             break;
        }
    }
}
