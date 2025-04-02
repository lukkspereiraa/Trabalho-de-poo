package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    public static Connection criarConexao() {
        Connection con = null;
        String usuario = "root";
        String senha = "@MonkeyDLukks2";  // Cuidado com caracteres especiais na senha
        String url = "jdbc:mysql://localhost:3306/academia?useSSL=false";
        
        try {
            // Carrega o driver (opcional em versões recentes, mas recomendado)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Tenta estabelecer a conexão
            con = DriverManager.getConnection(url, usuario, senha);
            
            if (con != null) {
                System.out.println("Conexão realizada com sucesso");
                return con;
            } else {
                System.out.println("Erro ao conectar no BD");
                return null;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
            return null;
        }
    }
}