package com.example.matheus.appfinanceiro.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.appfinanceiro.R;
import com.example.matheus.appfinanceiro.dao.ContaDAO;
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.util.ConstantesUtil;

import java.util.ArrayList;
import java.util.List;

public class NovaContaActivity extends AppCompatActivity {

    private EditText saldo;
    private EditText descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_conta);

        pegarElementosTela();

        getSupportActionBar().setSubtitle("Preencha os dados da conta nova");
    }

    public void salvarConta(View view) {
        List<String> erros = validarPreenchimento();
        if(erros.isEmpty()){
            TextView avisoErro = findViewById(R.id.avisoErroTextView);
            avisoErro.setVisibility(View.GONE);
            if (R.id.botao_salvar_nova_conta == view.getId()) {
                if (this.saldo.getText().toString() == "" || this.descricao.getText().toString() == "") {
                    Toast.makeText(this, R.string.campos_obrigatorios, Toast.LENGTH_LONG).show();
                    finish();
                }

                if(persistirConta()){
                    setResult(RESULT_OK);
                }else{
                    setResult(ConstantesUtil.RESULT_ERROR);
                }

                finish();
            }
        }else{
            exibirMensagemErro(erros);
        }

    }

    private void exibirMensagemErro(List<String> erros) {

        TextView avisoErro = findViewById(R.id.avisoErroTextView);
        String mensagem = "Erros no preenchimento da conta: \n\n";

        Integer contador = 1;
        for(String erro : erros){
            mensagem += contador.toString().concat(" - ").concat(erro + "\n");
            contador++;
        }

        avisoErro.setText(mensagem);
        avisoErro.setVisibility(View.VISIBLE);
    }

    public void cancelarConta(View view ){
        if (R.id.bota_cancelar_nova_conta == view.getId()) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private Boolean persistirConta(){
        Conta conta = new Conta();
        conta.setDescricao(String.valueOf(this.descricao.getText()));
        conta.setSaldo(Double.valueOf(this.saldo.getText().toString()));

        ContaDAO contaDAO = new ContaDAO(this);
        return contaDAO.salvaConta(conta);
    }

    private void pegarElementosTela() {
        this.saldo = findViewById(R.id.saldo_nova_conta);
        this.descricao = findViewById(R.id.descricao_conta_nova);
    }

    private List<String> validarPreenchimento(){
        List<String> erros = new ArrayList<String>();

        if(this.descricao.getText().toString().equals("")){
            erros.add("Informe uma descrição.");
        }

        if(this.saldo.getText().toString().equals("")){
            erros.add("Informe um saldo.");
        }

        return erros;
    }

}
