import DAO.AutorDAO;
import DAO.LivroDAO;
import DAO.PremioDAO;
import DAO.RelacaoAutorLivroDAO;
import Interface.EmAlta;
import Objetos.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LivroDAO livroDAO = new LivroDAO();
        AutorDAO autorDAO = new AutorDAO();
        PremioDAO premioDAO = new PremioDAO();
        RelacaoAutorLivroDAO relacaoDAO = new RelacaoAutorLivroDAO();
        Scanner input = new Scanner(System.in);
        Livro livro;

        boolean flag = true;

        System.out.println("");

        while(flag) {
            System.out.println("\nEscolha sua opcao com letras minusculas");
            System.out.println("1 - Cadastrar um Autor");
            System.out.println("2 - Listar dados dos autores");
            System.out.println("3 - Cadastrar um Livro");
            System.out.println("4 - Mostrar dados dos livros");
            System.out.println("5 - Atualizar dados de um livro");
            System.out.println("6 - Deletar dados de um livro");

            String opStr = input.nextLine();
            int op;
            try {
                op = Integer.parseInt(opStr);
            } catch (NumberFormatException e) {
                System.out.println("Insira um numero valido!");
                continue;
            }

            switch(op){
                case 1:
                    System.out.println("Digite o nome do autor: ");
                    String nome = input.nextLine();
                    System.out.println("Digite a idade do autor: ");
                    String nasc = input.nextLine();
                    System.out.println("Digite o titulo do premio do autor: ");
                    String nomePremio = input.nextLine();

                    Autor autor = new Autor(nome, nasc);
                    Premio premio = new Premio(nomePremio);
                    int id = premioDAO.insertPremio(premio);
                    premio.setIdPremio(id);
                    autor.premio = premio;
                    autorDAO.insertAutor(autor);

                    break;

                case 2:
                    for (Autor autor1 : autorDAO.selectAutor()) {
                        System.out.println("---------------");
                        autor1.mostraInfo();
                    }
                    System.out.println("---------------");

                    break;

                case 3:
                    livro = generateLivro(input);
                    if (livro == null) {
                        break;
                    }

                    id = livroDAO.insertLivro(livro);

                    for (Autor autor1 : livro.autores) {
                        livro.idLivro = id;
                        RelacaoAutorLivro relacaoAutorLivro = new RelacaoAutorLivro(autor1, livro);
                        relacaoDAO.insertRelacao(relacaoAutorLivro);
                    }

                    break;

                case 4:
                    ArrayList<Livro> livros = livroDAO.selectLivro();
                    ArrayList<Autor> autores = autorDAO.selectAutor();
                    ArrayList<RelacaoAutorLivro> relacoes = relacaoDAO.selectRelacao();

                    // Relaciona os livros com o respectivo autor com base nas relacoes
                    for (Livro livro1 : livros) {
                        ArrayList<Autor> autores1 = new ArrayList<>();
                        for (RelacaoAutorLivro relacao : relacoes) {
                            if (livro1.idLivro == relacao.getLivro().idLivro) {
                                for (Autor autor1 : autores) {
                                    if (autor1.idAutor == relacao.getAutor().idAutor) {
                                        autores1.add((autor1));
                                        break;
                                    }
                                }
                            }
                        }
                        livro1.autores = autores1;
                    }

                    for (Livro livro1 : livros) {
                        System.out.println("---------------");

                        if (livro1 != null) {
                            livro1.mostraInfo();
                        } else {
                            System.out.println("Nenhum livro foi adicionado ainda.");
                            break;
                        }

                        System.out.println("");
                        if (livro1 instanceof EmAlta) {
                            ((EmAlta) livro1).EmAlta();
                        } else {
                            System.out.println("Nao esta em alta");
                        }
                    }

                    System.out.println("---------------");

                    break;

                case 5:
                    System.out.println("Digite o id do livro a ser atualizado: ");
                    String idStr = input.nextLine();
                    try {
                        id = Integer.parseInt(idStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Insira um numero valido!");
                        break;
                    }

                    livro = generateLivro(input);
                    if (livro == null) {
                        break;
                    }

                    // Refaz as relações
                    relacaoDAO.deleteRelacao(id);
                    for (Autor autor1 : livro.autores) {
                        livro.idLivro = id;
                        RelacaoAutorLivro relacaoAutorLivro = new RelacaoAutorLivro(autor1, livro);
                        relacaoDAO.insertRelacao(relacaoAutorLivro);
                    }
                    livroDAO.updateLivro(id, livro);

                    break;

                case 6:
                    System.out.println("Digite o id do livro a ser deletado: ");
                    idStr = input.nextLine();
                    try {
                        id = Integer.parseInt(idStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Insira um numero valido!");
                        break;
                    }

                    relacaoDAO.deleteRelacao(id);
                    livroDAO.deleteLivro(id);
                    break;

                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    private static Livro generateLivro(Scanner input) {
        System.out.println("Digite o titulo do livro: ");
        String titulo = input.nextLine();
        System.out.println("Digite a genero do livro (romance ou terror)");
        String nomeGeneroGenero = input.nextLine();
        System.out.println("Digite os ids dos autores ja cadastrados separados por virgula: ");
        String idAutores = input.nextLine().replace(" ", "");

        Livro livro;
        if (Objects.equals(nomeGeneroGenero, "terror")) {
            livro = new Terror();
        } else if (Objects.equals(nomeGeneroGenero, "romance")) {
            livro = new Romance();
        } else {
            System.out.println("Genero invalido! Livro nao cadastrado!");
            return null;
        }

        ArrayList<Autor> autores = new ArrayList<>();
        for (String idAutor : idAutores.split(",")) {
            int id;
            try {
                id = Integer.parseInt(idAutor);
            } catch (NumberFormatException e) {
                System.out.println("Insira um id valido!");
                return null;
            }

            Autor autor = new Autor();
            autor.idAutor = id;

            autores.add(autor);
        }

        Genero genero = new Genero(nomeGeneroGenero);

        livro.titulo = titulo;
        livro.autores = autores;
        livro.genero = genero;

        return livro;
    }
}
