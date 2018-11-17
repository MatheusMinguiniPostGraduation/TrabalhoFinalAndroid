package com.example.matheus.appfinanceiro.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.matheus.appfinanceiro.model.Conta;
import com.example.matheus.appfinanceiro.util.ConstantesUtil;

import java.util.List;

public class ListaContaActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener{

    private final int NOVA_CONTA_REQUEST_CODE = 0;

    private ListView listaContaView;
    private TextView saldoContasView;

    private List<Conta> listaConta;

    ContaAdapter contaAdapter;

    ContaDAO contaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_conta);

        contaDAO = new ContaDAO(this);

        listaConta = contaDAO.buscarContas();


        listaContaView = findViewById(R.id.lista_conta_view);
        listaContaView.setOnItemClickListener(this);

        saldoContasView = findViewById(R.id.totalSaldoContasView);

        saldoContasView.setText("R$ ".concat(String.valueOf(contaDAO.buscarSaldoContas())));
        contaAdapter = new ContaAdapter(this, listaConta);

        listaContaView.setAdapter(contaAdapter);
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

        intent = new Intent(getApplicationContext(), NovaContaActivity.class);

        startActivityForResult(intent, NOVA_CONTA_REQUEST_CODE);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case NOVA_CONTA_REQUEST_CODE:
                if(resultCode == RESULT_OK) {

                    //VER SE ISSO AQUI VAI FICAR ASSIM MESMO
                    this.listaConta.removeAll(listaConta);
                    this.listaConta.addAll(contaDAO.buscarContas());

                    saldoContasView.setText("R$ ".concat(String.valueOf(contaDAO.buscarSaldoContas()))); //Atualiza o saldo na tela
                    this.contaAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Conta conta = listaConta.get(position);

        Intent detalhesContaIntent = new Intent(this, DetalheContaActivity.class);
        detalhesContaIntent.putExtra(ConstantesUtil.CONTA_DETALHE, conta.getId());

        startActivity(detalhesContaIntent);


    }
}
