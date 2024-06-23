package DAO;

import java.sql.*;

public class ConnectionDAO {
    public Connection con; //conexão
    public PreparedStatement pst; // declaração(query) preparada - código em sql
    public Statement st; //declaração(query) - código em sql
    public ResultSet rs; //resposta do banco

    private final String database = "mydb";//nome do BD
    private final String user = "root";
    private final String password = "Vitinho12345@";
    private final String url = "jdbc:mysql://localhost:3306/" + database + "?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    public void connectToDB() {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch(SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
        }
    }
}
