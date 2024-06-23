package DAO;

import Objetos.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class AutorDAO extends ConnectionDAO {

    //DAO - Data Access Object
    private boolean sucesso = false; //Para saber se funcionou

    //INSERT
    public void insertAutor(Autor autor) {
        connectToDB();

        String sql = "INSERT INTO autor (nome_autor, idade_autor, idPremio) values(?, ?, ?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, autor.nome);
            pst.setString(2, autor.idadeAutor);
            pst.setInt(3, autor.premio.getIdPremio());
            pst.execute();
            sucesso = true;
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
    }

    //UPDATE
    public boolean updateAutor(int id, Autor autor) {
        connectToDB();
        String sql = "UPDATE autor SET nome_autor=?, idade_autor=?, idPremio=? where idAutor=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, autor.nome);
            pst.setString(2, autor.idadeAutor);
            pst.setInt(3, autor.premio.getIdPremio());
            pst.setInt(4, id);
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
    public boolean deleteAutor(int id) {
        connectToDB();
        String sql = "DELETE FROM autor where idAutor=?";
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
    public ArrayList<Autor> selectAutor() {
        ArrayList<Autor> autores = new ArrayList<>();
        connectToDB();
        String sql = "SELECT * FROM autor";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Autor autor = new Autor(
                        rs.getString("nome_autor"),
                        rs.getString("idade_autor")
                );

                PremioDAO premioDAO = new PremioDAO();
                Premio premio = premioDAO.selectPremioPorId(rs.getInt("idPremio"));
                autor.premio = premio;
                autor.idAutor = rs.getInt("idAutor");

                autores.add(autor);
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

        return autores;
    }

    public Autor selectAutorPorId(int id) {
        connectToDB();
        String sql = "Select * FROM autor where idAutor=?";

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            rs.next();
            Autor autor = new Autor(
                    rs.getString("nome_autor"),
                    rs.getString("idade_autor")
            );
            autor.idAutor = rs.getInt("idAutor");

            PremioDAO premioDAO = new PremioDAO();
            autor.premio = premioDAO.selectPremioPorId(rs.getInt("idPremio"));

            return autor;
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
