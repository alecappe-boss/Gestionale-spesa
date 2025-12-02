public class Articolo {
    String nome;
    double prezzo;
    int quantita;
    String categoria;
    boolean acquistato;

    public Articolo(String nome, double prezzo, int quantita, String categoria, boolean acquistato) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.categoria = categoria;
        this.acquistato = acquistato;
    }
}
