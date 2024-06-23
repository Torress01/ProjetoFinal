package DAO;

import Objetos.Genero;
import Objetos.Livro;
import Objetos.Romance;
import Objetos.Terror;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class LivroDAO extends ConnectionDAO {

    //DAO - Data Access Object
    private boolean sucesso = false; //Para saber se funcionou

    //INSERT
    public int insertLivro(Livro livro) {
        connectToDB();

        String sql = "INSERT INTO livro (titulo, idGenero) values(?, ?)";
        try {
            pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, livro.titulo);
            if (Objects.equals(livro.genero.getNome_genero(), "romance")) {
                pst.setString(2, "1");
            } else {
                pst.setString(2, "2");
            }

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
    public boolean updateLivro(int id, Livro livro) {
        connectToDB();
        String sql = "UPDATE livro SET titulo=?, idGenero=? where idLivro=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, livro.titulo);
            if (Objects.equals(livro.genero.getNome_genero(), "romance")) {
                pst.setString(2, "1");
            } else {
                pst.setString(2, "2");
            }
            pst.setInt(3,id);
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
    public boolean deleteLivro(int id) {
        connectToDB();
        String sql = "DELETE FROM livro where idLivro=?";
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
    public ArrayList<Livro> selectLivro() {
        ArrayList<Livro> livros = new ArrayList<>();
        connectToDB();
        String sql = "SELECT * FROM livro";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Livro livro;
                Genero genero;

                if (Objects.equals(rs.getString("idGenero"), "1")) {
                    livro = new Romance();
                    genero = new Genero("romance");
                } else {
                    livro = new Terror();
                    genero = new Genero("terror");
                }

                livro.idLivro = rs.getInt("idLivro");
                livro.titulo = rs.getString("titulo");

                livro.genero = genero;

                livros.add(livro);
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
        return livros;
    }

    public Livro selectLivroPorId(int id) {
        connectToDB();
        String sql = "Select * FROM livro where idLivro=?";

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            rs.next();
            Livro livro;
            Genero genero;

            if (Objects.equals(rs.getString("idGenero"), "1")) {
                livro = new Romance();
                genero = new Genero("romance");
            } else {
                livro = new Terror();
                genero = new Genero("terror");
            }

            livro.idLivro = rs.getInt("idLivro");
            livro.titulo = rs.getString("titulo");

            livro.genero = genero;

            return livro;
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
