package com.example.agendamento.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    static {
        try {
            // Nome da classe do Driver compatível com a versão 5.1.49
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado no projeto!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USUARIO, // Ajustado para os nomes da sua DatabaseConfig
                DatabaseConfig.SENHA
        );
    }
}