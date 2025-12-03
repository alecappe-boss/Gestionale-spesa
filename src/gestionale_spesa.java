import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class gestionale_spesa {

    // Scanner per leggere input da console
    private static Scanner input = new Scanner(System.in);

    // Lista degli articoli memorizzata in una LinkedHashMap per mantenere l'ordine di inserimento
    private static Map<String, Articolo> listaArticoli = new LinkedHashMap<>();

    public static void main(String[] args) {
        // Ciclo principale del programma
        while (true) {
            // Mostra il menu all'utente
            Metodi.mostraMenu();

            // Legge la scelta dell'utente
            int scelta = input.nextInt();
            input.nextLine(); // Consuma il newline rimasto da nextInt()

            // Switch per eseguire il comando scelto dall'utente
            switch (scelta) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String nome = input.nextLine();

                    // Legge il prezzo, richiedendo un valore positivo
                    double prezzo = Metodi.leggiNumPositivo(input, "Prezzo: ");

                    // Legge quantità e categoria
                    int quantita = (int) Metodi.leggiNumPositivo(input, "Quantità: ");

                    System.out.print("Categoria: ");
                    String categoria = input.nextLine();
                    
                    Metodi.aggiungiArticolo(listaArticoli, nome, prezzo, quantita, categoria);   // Aggiunge un nuovo articolo
                    
                    System.out.println("\nArticolo aggiunto con successo!");
                }
                
                case 2 -> System.out.println(Metodi.visualizzaLista(listaArticoli));    // Visualizza tutti gli articoli
                
                case 3 -> {
                    if (Metodi.checkListaVuota(listaArticoli)) {
                        System.out.println("La lista è vuota!");
                    } else {
                        Articolo a = Metodi.getArticoloByNumero(listaArticoli, "Quale articolo rimuovere? (numero): ");
                        if (Metodi.rimuoviArticolo(listaArticoli, a)) {
                            System.out.println("Articolo rimosso con successo!");
                        } else {
                            System.out.println("Errore nella rimozione dell'articolo!");
                        }    // Rimuove un articolo selezionato
                    }
                }
                
                case 4 -> {
                    System.out.print("Cosa cerchi? ");
                    String nomeCercato = input.nextLine();
                    if (Metodi.cercaArticolo(listaArticoli, nomeCercato)) {
                        System.out.println("Trovato!");
                    } else {
                        System.out.println("L'articolo cercato non è presente nella lista");
                    }
                }      // Cerca un articolo per nome
                
                case 5 -> {
                    Articolo a = Metodi.getArticoloByNumero(listaArticoli, "Quale articolo hai acquistato? (numero): ");
                
                    if (Metodi.marcaAcquistato(a)) {
                        System.out.println(a.nome + " segnato come acquistato");
                    } else {
                        System.out.println("L'elemento indicato non è presente nella lista");
                    }
                }    // Marca un articolo come acquistato
                case 6 -> System.out.println(Metodi.calcolaTotale(listaArticoli));      // Calcola e mostra il totale spesa
                case 7 -> {
                    if(Metodi.checkListaVuota(listaArticoli)){
                        System.out.println("La lista è vuota!");
                    } else {
                        System.out.println(Metodi.salvaSuFile(listaArticoli));
                    }
                }        // Salva la lista degli articoli su file CSV
                case 8 -> System.out.println(Metodi.caricaDaFile(listaArticoli));       // Carica articoli da un file CSV
                case 9 -> { 
                    input.close(); // Chiude lo Scanner prima di uscire
                    return;       // Termina il programma
                }
                default -> System.out.println("Scelta non valida!"); // Gestione scelta errata
            }
        }
    }
}
