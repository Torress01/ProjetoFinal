package Objetos;

import java.util.ArrayList;

public abstract class Livro {
    public int idLivro;
    public String titulo;
    public ArrayList<Autor> autores;
    public Genero genero;

    public abstract void mostraInfo();
}
