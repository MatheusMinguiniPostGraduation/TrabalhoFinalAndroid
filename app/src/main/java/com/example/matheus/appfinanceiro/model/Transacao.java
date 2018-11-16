package com.example.matheus.appfinanceiro.model;

public class Transacao {

    private Conta conta;
    private String descricao;
    private Double valor;
    private Integer centro_custo;
    private String natureza_operacao; //Se a transação é débito ou crédito

    //Caso tenha periodicidade
    private Integer dias_periodicidade;

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getCentro_custo() {
        return centro_custo;
    }

    public void setCentro_custo(Integer centro_custo) {
        this.centro_custo = centro_custo;
    }

    public String getNatureza_operacao() {
        return natureza_operacao;
    }

    public void setNatureza_operacao(String natureza_operacao) {
        this.natureza_operacao = natureza_operacao;
    }

    public Integer getDias_periodicidade() {
        return dias_periodicidade;
    }

    public void setDias_periodicidade(Integer dias_periodicidade) {
        this.dias_periodicidade = dias_periodicidade;
    }
}
