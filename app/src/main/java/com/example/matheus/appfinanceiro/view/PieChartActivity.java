package com.example.matheus.appfinanceiro.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.matheus.appfinanceiro.R;
import com.example.matheus.appfinanceiro.VO.TransacaoVO;
import com.example.matheus.appfinanceiro.dao.TransacaoDAO;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class PieChartActivity  extends AppCompatActivity {

    private PieChart pieChart;
    private TransacaoDAO transacaoDAO = new TransacaoDAO(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_custos);

        buscarTransacoesDebitos();

        pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        Description description = new Description();
        description.setText("Gráfico sobre o centro de gastos entre as transações bancárias");
        description.setTextSize(10f);
        pieChart.setDescription(description);



        PieDataSet dataSet = new PieDataSet(incluirValoresChart(), "Custos");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
    }

    private ArrayList<PieEntry> incluirValoresChart(){
        ArrayList<PieEntry> valores = new ArrayList<>();

        List<TransacaoVO> transacoesDebitos = buscarTransacoesDebitos();

        Double totalValorTransacoesDebitos = transacaoDAO.buscarValorTransacoesDebito();

        for(TransacaoVO transacao : transacoesDebitos){
            Double porcentagem = (Double.valueOf(transacao.getValor()) / totalValorTransacoesDebitos) * 100;
            valores.add(new PieEntry(porcentagem.floatValue(), transacao.getCentroCusto()));
        }

        return valores;
    }

    private List<TransacaoVO> buscarTransacoesDebitos() {
        return transacaoDAO.buscarTransacoesDebito();
    }
}
