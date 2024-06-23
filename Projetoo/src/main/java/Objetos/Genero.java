package Objetos;

public class Genero {
    private int idGenero;
    private String nomeGenero;

    public Genero(String nome_genero) {
        this.nomeGenero = nome_genero;
    }

    public void mostraInfo(){
        System.out.println("ID Genero: " + getIdGenero());
        System.out.println("Nome da Genero do Filme: " + getNome_genero());
        System.out.println("\n------------------");
    }

    public int getIdGenero() {
        return idGenero;
    }

    public String getNome_genero() {
        return nomeGenero;
    }

}
