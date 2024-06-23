package Objetos;

public class Romance extends Livro {
    @Override
    public void mostraInfo() {
        System.out.println("Informacoes do livro:");
        System.out.println("ID Livro: " + idLivro);
        System.out.println("Nome do Livro: " + titulo);

        if (genero != null){
            System.out.println("Genero do Livro: " + genero.getNome_genero());
        }

        System.out.println("\nInformacoes dos autores:");
        for (Autor autor : autores) {
            autor.mostraInfo();
        }
    }
}
