package com.example.matheus.appfinanceiro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.appfinanceiro.R;
import com.example.matheus.appfinanceiro.adapter.ContaAdapter;
import com.example.matheus.appfinanceiro.dao.ContaDAO;
import com.example.matheus.appfinanceiro.dao.TransacaoDAO;
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.util.ConstantesUtil;
import com.example.matheus.appfinanceiro.util.FormatNumberUtil;

import java.util.List;

public class ListaContaActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener{

    private final int NOVA_CONTA_REQUEST_CODE = 0;

    private ListView listaContaView;
    private TextView saldoContasView;
    private TextView entradaTextView;
    private TextView saidaTextView;

    private List<Conta> listaConta;

    private ContaAdapter contaAdapter;

    private ContaDAO contaDAO;
    private TransacaoDAO transacaoDAO;
    private ConstraintLayout mensagem_vazio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_conta);

        contaDAO = new ContaDAO(this);
        transacaoDAO = new TransacaoDAO(this);

        listaConta = contaDAO.buscarContas();

        //Lista na tela
        listaContaView = findViewById(R.id.lista_conta_view);
        mensagem_vazio = findViewById(R.id.mensagem_vazio_layout);

        if(listaConta.isEmpty()){
            mostrarMensagemVazio();
        }else{
            mostrarListaContas();
        }
    }

    private void mostrarListaContas() {

        listaContaView.setVisibility(View.VISIBLE);
        mensagem_vazio.setVisibility(View.GONE);

        listaContaView.setOnItemClickListener(this);

        saldoContasView = findViewById(R.id.totalSaldoContasView);

        saldoContasView.setText("R$".concat(FormatNumberUtil.formatDecimal(contaDAO.buscarSaldoContas())));
        contaAdapter = new ContaAdapter(this, listaConta);

        //Buscar total de entrada e saída
        entradaTextView = findViewById(R.id.entradaTextView);
        saidaTextView = findViewById(R.id.saidaTextView);
        entradaTextView.setText("R$ ".concat(String.valueOf(transacaoDAO.buscarValorTransacoesCredito())));
        saidaTextView.setText("R$ ".concat(String.valueOf(transacaoDAO.buscarValorTransacoesDebito())));


        listaContaView.setAdapter(contaAdapter);
    }



    public void mostrarMensagemVazio(){
        mensagem_vazio.setVisibility(View.VISIBLE);
        listaContaView.setVisibility(View.GONE);
    }



    @Override
    protected void onRestart() { super.onRestart();
        if(listaConta.isEmpty()){
            mostrarMensagemVazio();
        }else{
            mostrarListaContas();
            this.contaAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        if(item.getItemId() == R.id.novaContaMenuItem){
            intent = new Intent(getApplicationContext(), NovaContaActivity.class);
            startActivityForResult(intent, NOVA_CONTA_REQUEST_CODE);
        }

        if(item.getItemId() == R.id.graficoGastos){
            intent = new Intent(getApplicationContext(), PieChartActivity.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    public void novaConta(View view){
        Intent intent = new Intent(getApplicationContext(), NovaContaActivity.class);
        startActivityForResult(intent, NOVA_CONTA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case NOVA_CONTA_REQUEST_CODE:
                if(resultCode == RESULT_OK) {

                    this.listaConta.removeAll(listaConta);
                    this.listaConta.addAll(contaDAO.buscarContas());

                    if(listaConta.isEmpty()){
                        mostrarMensagemVazio();
                    }else{
                        mostrarListaContas();
                        this.contaAdapter.notifyDataSetChanged();
                    }

                    Toast.makeText(this, R.string.msg_sucesso, Toast.LENGTH_LONG).show();
                }

                if(resultCode == RESULT_CANCELED){
                    Toast.makeText(this, R.string.msg_cancelamento, Toast.LENGTH_LONG).show();
                }

                if(resultCode == ConstantesUtil.RESULT_ERROR){
                    Toast.makeText(this, R.string.msg_erro, Toast.LENGTH_LONG).show();
                }
            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Conta conta = listaConta.get(position);

        Intent detalhesContaIntent = new Intent(this, DetalheContaActivity.class);
        detalhesContaIntent.putExtra(ConstantesUtil.CONTA_DETALHE, conta.getId());

        startActivity(detalhesContaIntent);
    }
}
