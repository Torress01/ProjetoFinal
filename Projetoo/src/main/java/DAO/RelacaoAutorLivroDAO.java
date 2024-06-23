package DAO;

import Objetos.Autor;
import Objetos.Livro;
import Objetos.RelacaoAutorLivro;

import java.sql.SQLException;
import java.util.ArrayList;

public class RelacaoAutorLivroDAO extends ConnectionDAO {

    //DAO - Data Access Object
    private boolean sucesso = false; //Para saber se funcionou

    //INSERT
    public void insertRelacao(RelacaoAutorLivro relacaoAutorLivro) {
        connectToDB();

        String sql = "INSERT INTO relacaoautorlivro (idAutor, idLivro) values(?, ?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, relacaoAutorLivro.getAutor().idAutor);
            pst.setInt(2, relacaoAutorLivro.getLivro().idLivro);
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

    //SELECT
    public ArrayList<RelacaoAutorLivro> selectRelacao() {
        ArrayList<RelacaoAutorLivro> relacoes = new ArrayList<>();
        connectToDB();
        String sql = "SELECT * FROM relacaoautorlivro";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                LivroDAO livroDAO = new LivroDAO();
                AutorDAO autorDAO = new AutorDAO();
                Autor autor = autorDAO.selectAutorPorId(rs.getInt("idAutor"));
                Livro livro = livroDAO.selectLivroPorId(rs.getInt("idLivro"));
                RelacaoAutorLivro relacaoAutorLivro = new RelacaoAutorLivro(autor, livro);

                relacoes.add(relacaoAutorLivro);
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

        return relacoes;
    }

    //DELETE
    public void deleteRelacao(int idLivro) {
        connectToDB();
        String sql = "DELETE FROM relacaoautorlivro where idLivro=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, idLivro);
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
    }
}
