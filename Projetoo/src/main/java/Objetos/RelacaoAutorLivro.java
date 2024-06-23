package Objetos;

public class RelacaoAutorLivro {
    private Autor autor;
    private Livro livro;

    public RelacaoAutorLivro(Autor autor, Livro livro) {
        this.autor = autor;
        this.livro = livro;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
