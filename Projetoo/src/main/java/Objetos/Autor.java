package Objetos;

public class Autor {
    public int idAutor;
    public String nome;
    public String idadeAutor;
    public Premio premio;

    public Autor(String nome, String idadeAutor) {
        this.nome = nome;
        this.idadeAutor = idadeAutor;
    }

    public Autor() {

    }

    public void mostraInfo(){
        System.out.println("ID Autor: " + idAutor);
        System.out.println("Nome do Autor: " + nome);
        System.out.println("Idade do Autor: " + idadeAutor);
        System.out.println("Premio do Autor: " + premio.getTipo());
    }
}
