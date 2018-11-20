package com.example.matheus.appfinanceiro.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.appfinanceiro.R;
import com.example.matheus.appfinanceiro.dao.CentroCustoDAO;
import com.example.matheus.appfinanceiro.dao.TransacaoDAO;
import com.example.matheus.appfinanceiro.model.CentroCusto;
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.model.Transacao;
import com.example.matheus.appfinanceiro.util.ConstantesUtil;

import java.util.ArrayList;
import java.util.List;

public class NovaTransacaoActivity extends AppCompatActivity {

    Spinner spinnerCentroCusto;

    private TextView descricaoView;
    private EditText valorView;
    private RadioButton debitoView;
    private RadioButton creditoView;
    private Integer contaId;

    //Lista auxilar que ficará em memória para nao precisar acessar o DB novamente e pegar o centro de custo clicado
    private List<CentroCusto> centroCustoList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_transacao);

        /*** Centro de custo ***/
        centroCustoList = new CentroCustoDAO(this).buscarTodosCentroCusto();
        List<String> centroCustosDescricoes = new ArrayList<>();

        for(CentroCusto centroCusto : centroCustoList){
            centroCustosDescricoes.add(centroCusto.getDescricao());
        }

        ArrayAdapter<String> centroCustoArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, centroCustosDescricoes);


        spinnerCentroCusto = findViewById(R.id.centro_custo_spinner);
        spinnerCentroCusto.setAdapter(centroCustoArrayAdapter);
        /*** centro de custo fim **/


        getSupportActionBar().setSubtitle("Informe detalhes da transação");
    }

    public void salvarTransacao(View view){
          pegarElementosTela();

          if(verificarCamposPreenchidos()){
              if(persistirDados()){
                  setResult(RESULT_OK);
              }else{
                  setResult(ConstantesUtil.RESULT_ERROR);
              }
              finish();
          }else{
              Toast.makeText(this, R.string.campos_obrigatorios, Toast.LENGTH_LONG).show();
          }
    }

    private void pegarElementosTela(){
        descricaoView = findViewById(R.id.natureza_transacao_view);
        valorView = findViewById(R.id.valor_transacao_view);
        debitoView = findViewById(R.id.debito_botao_view);
        creditoView = findViewById(R.id.credito_botao_view);
    }

    private Boolean persistirDados() {
        Transacao transacaoNova = new Transacao();
        transacaoNova.setDescricao(descricaoView.getText().toString());
        transacaoNova.setValor(Double.valueOf(valorView.getText().toString()));
        transacaoNova.setConta(new Conta((Integer) getIntent().getSerializableExtra(ConstantesUtil.CONTA_ID)));
        //transacaoNova.setCentro_custo();

        Integer indexItemClicado = spinnerCentroCusto.getSelectedItemPosition();
        transacaoNova.setCentro_custo(centroCustoList.get(indexItemClicado));

        if(debitoView.isChecked()){
            transacaoNova.setNatureza_operacao(ConstantesUtil.DEBITO);
        }else{
            transacaoNova.setNatureza_operacao(ConstantesUtil.CREDITO);
        }

        TransacaoDAO transacaoDAO = new TransacaoDAO(this);
        return transacaoDAO.salvarTransacao(transacaoNova);
    }

    private Boolean verificarCamposPreenchidos(){

        if(descricaoView.getText().toString() == "" || valorView.getText().toString() == "" || (!debitoView.isChecked() && !creditoView.isChecked())){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
