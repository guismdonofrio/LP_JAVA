package com.example.projetinho.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    // String de conexão JDBC: protocolo://host:porta/banco
    private static final String URL      = "jdbc:mysql://localhost:3306/projetinho";
    private static final String USER     = "root";
    private static final String PASSWORD = "guis";

    // Instância única da conexão
    private static Connection instancia;

    // Retorna a conexão, abrindo uma nova se necessário.
    public static Connection getConexao() {
        try {
            if (instancia == null || instancia.isClosed()) {
                //cria a conexão com as credenciais fornecidas
                instancia = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage(), e);
        }
        return instancia;
    }

    // Fecha a conexão.
    public static void fechar() {
        try {
            if (instancia != null && !instancia.isClosed()) {
                instancia.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }

    // Impede instanciação
    private ConexaoDB() {}
}
