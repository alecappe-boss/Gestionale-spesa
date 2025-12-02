import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class gestionale_spesa {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Articolo> listaArticoli = new ArrayList<>();
        
        while (true) {
            
            System.out.println("\nGESTIONE LISTA DELLA SPESA");
            System.out.println("\n1. Aggiungi articolo");
            System.out.println("2. Visualizza lista");
            System.out.println("3. Rimuovi articolo");
            System.out.println("4. Cerca articolo");
            System.out.println("5. Marca come acquistato");
            System.out.println("6. Calcola totale spesa");
            System.out.println("7. Salva su file");
            System.out.println("8. Carica da file");
            System.out.println("9. Esci");

            System.out.print("\nDigita un comando: ");
            int scelta = input.nextInt();
            input.nextLine();

            if (scelta == 1) {

                System.out.print("Nome: ");
                String nome = input.nextLine();

                System.out.print("Prezzo: ");
                double prezzo = input.nextDouble();
                input.nextLine();
                while (prezzo <= 0) {
                    System.out.println("Il prezzo deve essere positivo!");
                    System.out.print("Prezzo: ");
                    prezzo = input.nextDouble();
                    input.nextLine();
                }

                System.out.print("Quantità: ");
                int quantita = input.nextInt();
                input.nextLine();
                
                System.out.print("Categoria: ");
                String categoria = input.nextLine();
                
                boolean acquistato = false;

                Articolo articolo = new Articolo(nome, prezzo, quantita, categoria, acquistato);
                listaArticoli.add(articolo);

                System.out.println("\nArticolo aggiunto con successo!");

            } else if (scelta == 2) {

                System.out.println("\nLISTA DELLA SPESA");

                if (listaArticoli.size()>0) {
                    int nonComprato = 0;
                    int comprato = 0;
                    double prezzoTotale = 0;
                    int numero = 1;

                    for (Articolo a : listaArticoli) {
                        System.out.println("\n" + numero + ". Nome: " + a.nome + ", prezzo: " + a.prezzo + ", quantita: " + a.quantita + ", categoria: " + a.categoria + ", acquistato: " + a.acquistato);
                        if (a.acquistato==true) {
                            comprato++;
                        }

                        numero++;

                        prezzoTotale += a.prezzo * a.quantita;
                    }

                    nonComprato = listaArticoli.size() - comprato;

                    System.out.println("\nTotale: " + listaArticoli.size());
                    System.out.println("Non acquistati: " + nonComprato);
                    System.out.println("Acquistati: " + comprato);
                    System.out.printf("Spesa totale: €%.2f\n", prezzoTotale);

                } else {
                    System.out.println("\nLa lista è vuota!");
                }

            } else if (scelta == 3) {
                System.out.println();
            } else if (scelta == 4) {
                System.out.println();
            } else if (scelta == 5) {
                System.out.println();
            } else if (scelta == 6) {
                System.out.println();
            } else if (scelta == 7) {
                System.out.println();
            } else if (scelta == 8) {
                System.out.println();
            } else if (scelta == 9) {
                break;
            } else {
                System.out.println("\nScelta non valida!");
            }
        }
        
        input.close();
    }
}
