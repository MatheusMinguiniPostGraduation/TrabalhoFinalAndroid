package com.example.matheus.appfinanceiro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.appfinanceiro.R;
import com.example.matheus.appfinanceiro.VO.TransacaoVO;
import com.example.matheus.appfinanceiro.adapter.TransacaoAdapter;
import com.example.matheus.appfinanceiro.dao.ContaDAO;
import com.example.matheus.appfinanceiro.dao.TransacaoDAO;
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.util.ConstantesUtil;

import java.util.List;

public class DetalheContaActivity extends AppCompatActivity {

    private static final int NOVA_TRANSACAO_REQUEST_CODE = 0;
    TextView descricaoView;
    TextView saldoView;
    ListView historicoTransacoesListview;

    Integer contaId;

    List<TransacaoVO> historicoTransacao;

    TransacaoAdapter historicoTransacaoAdapter;

    private ContaDAO contaDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_conta);

        contaId = (Integer) getIntent().getSerializableExtra(ConstantesUtil.CONTA_DETALHE);

        Conta contaDetalhe = new ContaDAO(this).buscarContaPorId(contaId);

        contaDAO = new ContaDAO(this);

        historicoTransacao = new TransacaoDAO(this).buscarHistoricoTransacoesPorConta(contaId);

        historicoTransacaoAdapter = new TransacaoAdapter(this, historicoTransacao);
        historicoTransacoesListview = findViewById(R.id.historico_transacao_list_view);
        historicoTransacoesListview.setAdapter(historicoTransacaoAdapter);


        saldoView = findViewById(R.id.saldo_conta_detalhe);
        descricaoView = findViewById(R.id.descricao_conta_detalhe);

        saldoView.setText("R$ " + contaDAO.buscarSaldoContaPorId(contaId));
        descricaoView.setText(contaDetalhe.getDescricao());

        getSupportActionBar().setSubtitle("Detalhes da conta bancária");
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
                    this.historicoTransacao.removeAll(historicoTransacao);
                    this.historicoTransacao.addAll(new TransacaoDAO(this).buscarHistoricoTransacoesPorConta(contaId));

                    saldoView.setText("R$ " + contaDAO.buscarSaldoContaPorId(contaId)); //Atualiza o saldo na tela
                    this.historicoTransacaoAdapter.notifyDataSetChanged();
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