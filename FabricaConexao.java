/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jairo
 */
public class FabricaConexao {

    String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://localhost/metropole";
    private String usuario = "postgres";
    private String senha = "2344524246";

    public Connection getConnection() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } catch (ClassNotFoundException erro) {
            throw new RuntimeException(erro);
        }
    }
}
