package com.example.matheus.appfinanceiro.util;

public final class DBQueries {

    private DBQueries(){

    }

    //NOME DAS TABELAS
    public static final String TABELA_CONTA = "conta";
    public static final String TABELA_TRANSACAO = "transacao";
    public static final String TABELA_CENTRO_CUSTO = "centro_custo";


    //COMANDOS DDL
    public static final String CRIAR_TABELA_CONTA = "CREATE TABLE IF NOT EXISTS conta( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descricao TEXT NOT NULL, " +
            "saldo DECIMAL (10,2));";

    public static final String CRIAR_TABELA_TRANSACAO = "CREATE TABLE IF NOT EXISTS transacao( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT NOT NULL, " +
                "valor DECIMAL (10,2), " +
                "conta INTEGER, " +
                "centro_custo INTEGER, " +
                "debito INTEGER, " +
                "dias_periodicidade INTEGER, " +
                "FOREIGN KEY(conta) REFERENCES conta(id), " +
                "FOREIGN KEY(centro_custo) REFERENCES centro_cuto(id) " +
            ");";

    public static final String CRIAR_TABELA_CENTRO_CUSTO = "CREATE TABLE IF NOT EXISTS centro_custo( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descricao TEXT NOT NULL); ";



    // COMANDOS DML DA TRANSACAO
    public static final String BUSCAR_HISTORICO_TRANSACAO_POR_CONTA_QUERY = "SELECT t.descricao, t.valor, t.debito, c.descricao FROM transacao t " +
            "INNER JOIN centro_custo c ON c.id = t.centro_custo WHERE t.conta = ?";

    public static final String BUSCAR_TRANSACOES_DEBITO_AGRUPADAS_POR_TIPO_CUSTO_QUERY = "SELECT SUM(t.valor) as total, c.descricao, c.id " +
            "FROM transacao t INNER JOIN centro_custo c ON t.centro_custo = c.id WHERE " +
            "t.debito = ? GROUP BY c.id";

    public static final String BUSCAR_VALOR_TRANSACOES_DEBITO_QUERY = "SELECT SUM(valor) as total FROM transacao WHERE debito = ?";
    public static final String BUSCAR_VALOR_TRANSACOES_CREDITO_QUERY = "SELECT SUM(valor) as total FROM transacao WHERE debito = ?";



    //COMANDOS DML DA CONTA
    public static final String BUSCAR_SALDO_CONTAS_QUERY = "SELECT SUM(saldo) as total FROM conta";
    public static final String BUSCAR_SALDO_CONTA_POR_ID_QUERY = "SELECT SUM(saldo) as total FROM conta WHERE id = ?";
    public static final String BUSCAR_CONTA_POR_ID = "SELECT descricao, saldo FROM conta WHERE id = ? ";
}
