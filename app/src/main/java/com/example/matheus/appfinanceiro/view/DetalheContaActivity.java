package com.example.matheus.appfinanceiro.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.matheus.appfinanceiro.R;
import com.example.matheus.appfinanceiro.dao.ContaDAO;
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.util.ConstantesUtil;

public class DetalheContaActivity extends AppCompatActivity {

    TextView descricaoView;
    TextView saldoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_conta);

        Integer contaId = (Integer) getIntent().getSerializableExtra(ConstantesUtil.CONTA_DETALHE);

        Conta contaDetalhe = new ContaDAO(this).buscarContaPorId(contaId);

        saldoView = findViewById(R.id.saldo_conta_detalhe);
        descricaoView = findViewById(R.id.descricao_conta_detalhe);

        saldoView.setText("R$ " + contaDetalhe.getSaldo());
        descricaoView.setText(contaDetalhe.getDescricao());

        getSupportActionBar().setSubtitle("Detalhes da conta bancária");

    }
}
