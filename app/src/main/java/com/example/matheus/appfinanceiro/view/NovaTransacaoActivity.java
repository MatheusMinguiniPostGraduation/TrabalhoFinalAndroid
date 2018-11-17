package com.example.matheus.appfinanceiro.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.matheus.appfinanceiro.R;
import com.example.matheus.appfinanceiro.dao.CentroCustoDAO;
import com.example.matheus.appfinanceiro.model.CentroCusto;

import java.util.List;

public class NovaTransacaoActivity extends AppCompatActivity {

    Spinner spinnerCentroCusto;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_transacao);

        List<CentroCusto> centroCustoList = new CentroCustoDAO(this).buscarTodosCentroCusto();


        ArrayAdapter<CentroCusto> centroCustoArrayAdapter = new ArrayAdapter<CentroCusto>(this,
                android.R.layout.simple_expandable_list_item_1, centroCustoList);


        spinnerCentroCusto = findViewById(R.id.centro_custo_spinner);
        spinnerCentroCusto.setAdapter(centroCustoArrayAdapter);


        getSupportActionBar().setSubtitle("Informe detalhes da transação");
    }

    public void salvarTransacao(View view){

    }

}
