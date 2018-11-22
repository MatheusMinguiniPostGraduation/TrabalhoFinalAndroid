package com.example.matheus.appfinanceiro.util;

public final class DBQueries {

    private DBQueries(){

    }

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


    public static final String BUSCAR_HISTORICO_TRANSACAO_POR_CONTA_QUERY = "SELECT t.descricao, t.valor, t.debito, c.descricao FROM transacao t " +
            "INNER JOIN centro_custo c ON c.id = t.centro_custo WHERE t.conta = ";

    public static final String  BUSCAR_TRANSACOES_DEBITO_AGRUPADAS_POR_TIPO_CUSTO = "SELECT SUM(t.valor) as total, c.descricao, c.id " +
            "FROM transacao t INNER JOIN centro_custo c ON t.centro_custo = c.id WHERE " +
            "t.debito =? GROUP BY c.id";
}
