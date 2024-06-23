package DAO;

import Objetos.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PremioDAO extends ConnectionDAO {

    //DAO - Data Access Object
    private boolean sucesso = false; //Para saber se funcionou

    //INSERT
    public int insertPremio(Premio premio) {
        connectToDB();

        String sql = "INSERT INTO premio (tipo) values(?)";
        try {
            pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, premio.getTipo());
            pst.execute();
            rs = pst.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return generatedKey;
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            sucesso = false;
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }

        return 0;
    }

    //UPDATE
    public boolean updatePremio(int id, Premio premio) {
        connectToDB();
        String sql = "UPDATE premio SET tipo=? where idPremio=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, premio.getTipo());
            pst.setInt(2, id);
            pst.execute();
            sucesso = true;
        } catch (SQLException ex) {
            System.out.println("Erro = " + ex.getMessage());
            sucesso = false;
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }
        return sucesso;
    }

    //DELETE
    public boolean deletePremio(int id) {
        connectToDB();
        String sql = "DELETE FROM premio where idPremio=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.execute();
            sucesso = true;
        } catch (SQLException ex) {
            System.out.println("Erro = " + ex.getMessage());
            sucesso = false;
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }
        return sucesso;
    }

    //SELECT
    public ArrayList<Premio> selectPremio() {
        ArrayList<Premio> premios = new ArrayList<>();
        connectToDB();
        String sql = "SELECT * FROM premio";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Premio premio = new Premio(rs.getString("tipo"));

                premios.add(premio);
            }
            sucesso = true;
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            sucesso = false;
        } finally {
            try {
                con.close();
                st.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        return premios;
    }

    public Premio selectPremioPorId(int id) {
        connectToDB();
        String sql = "Select * FROM premio where idPremio=?";

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            rs.next();
            Premio premio = new Premio(rs.getString("tipo"));
            premio.setIdPremio(id);

            return premio;
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        return null;
    }

    public Premio selectPremioPorNome(String nome) {
        connectToDB();
        String sql = "Select * FROM premio where tipo=?";

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();

            rs.next();
            Premio premio = new Premio(rs.getString("nome_autor"));
            premio.setIdPremio(rs.getInt("idPremio"));

            return premio;
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        return null;
    }
}
