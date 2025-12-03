import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class gestionale_spesa {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Map<String, Articolo> listaArticoli = new LinkedHashMap<>();
        
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
                listaArticoli.put(nome, articolo);

                System.out.println("\nArticolo aggiunto con successo!");

            } else if (scelta == 2) {

                System.out.println("\n═══════════════════════════════════════");
                System.out.println("          LISTA DELLA SPESA");
                System.out.println("═══════════════════════════════════════\n");

                if (!listaArticoli.isEmpty()) {

                    int comprati = 0;
                    double totale = 0;
                    int numero = 1;

                    for (String nome : listaArticoli.keySet()) {
                        
                        Articolo a = listaArticoli.get(nome);
                        
                        String check;
                        if (a.acquistato) {
                            check = "[X]";
                        } else {
                            check = "[ ]";
                        }

                        double subtotale = a.prezzo * a.quantita;

                        System.out.printf("%s %d. %s - %s\n", check, numero, a.nome, a.categoria);
                        System.out.printf("       €%.2f x %d = €%.2f\n\n", a.prezzo, a.quantita, subtotale);

                        if (a.acquistato) comprati++;

                        totale += subtotale;
                        numero++;
                    }

                    int nonComprati = listaArticoli.size() - comprati;

                    System.out.println("═══════════════════════════════════════");
                    System.out.println("Totale: " + listaArticoli.size() + " articoli");
                    System.out.println("Non acquistati: " + nonComprati);
                    System.out.println("Acquistati: " + comprati);
                    System.out.printf("Spesa totale: €%.2f\n", totale);
                    System.out.println("═══════════════════════════════════════");

                } else {
                    System.out.println("La lista è vuota!");
                    System.out.println("═══════════════════════════════════════");
                }

            } else if (scelta == 3) {

                System.out.print("Quale articolo rimuovere? (numero)");
                int opzione = input.nextInt();
                input.nextLine();
                int numero = 1;
                boolean trovato = false;
                String nomeArticolo = null;
                
                for (String nome : listaArticoli.keySet()) {
                    if (opzione == numero) {
                        listaArticoli.remove(nome);
                        nomeArticolo = nome;
                        trovato = true;
                        break;
                    }
                    numero ++;
                 }

                 if (trovato) {
                    System.out.println(nomeArticolo + " rimosso dalla lista");
                 } else {
                    System.out.println("L'elemento indicato non è presente nella lista");
                 }

            } else if (scelta == 4) {
                
                System.out.print("Cosa cerchi? ");
                String nomeCercato = input.nextLine();
                boolean trovato = false;
                
                for (String nome : listaArticoli.keySet()) {
                    
                    if (nome.equals(nomeCercato)) {
                        Articolo a = listaArticoli.get(nome);
                        double subtotale = a.prezzo * a.quantita;
                        System.out.println("Trovato!");
                        if (a.acquistato == false) {
                            System.out.println(a.nome + " - " + a.categoria + " - €" + a.prezzo + " * " + a.quantita + " = " + subtotale + " [Non acquistato]");
                        } else {
                            System.out.println(a.nome + " - " + a.categoria + " - €" + a.prezzo + " * " + a.quantita + " = " + subtotale + " [Acquistato]") ;
                        }
                        trovato = true;
                        break;
                    }
                }

                if (!trovato) {
                    System.out.println("L'articolo cercato non è presente nella lista");
                }

            } else if (scelta == 5) {
                
                System.out.print("Quale articolo hai acquistato? (numero): ");
                int opzione = input.nextInt();
                input.nextLine();
                int numero = 1;
                boolean trovato = false;
                String nomeArticolo = null;
                
                for (String nome : listaArticoli.keySet()) {
                    if (opzione == numero) {
                        Articolo a = listaArticoli.get(nome);
                        a.acquistato = true;
                        nomeArticolo = nome;
                        trovato = true;
                        break;
                    }
                    numero ++;
                 }

                if (trovato) {
                    System.out.println(nomeArticolo + " segnato come acquistato");
                } else {
                    System.out.println("L'elemento indicato non è presente nella lista");
                }

            } else if (scelta == 6) {
                
                System.out.println("CALCOLO SPESA TOTALE");
                
                if (!listaArticoli.isEmpty()) {

                    int comprati = 0;
                    double totale = 0;
                    int numero = 1;
                    double spesaAcquisiti = 0;
                    double spesaRimanente = 0;

                    for (String nome : listaArticoli.keySet()) {
                        
                        Articolo a = listaArticoli.get(nome);

                        double subtotale = a.prezzo * a.quantita;

                        if (a.acquistato) {
                            comprati++;
                            spesaAcquisiti += subtotale; 
                        } else {
                            spesaRimanente += subtotale;
                        }

                        totale += subtotale;
                        numero++;
                    }

                    int nonComprati = listaArticoli.size() - comprati;

                    System.out.println("═══════════════════════════════════════");
                    System.out.println("Articoli totali: " + listaArticoli.size());
                    System.out.println("Non acquistati: " + nonComprati);
                    System.out.println("Acquistati: " + comprati);
                    System.out.println("═══════════════════════════════════════");
                    System.out.printf("Spesa totale: €%.2f\n", totale);
                    System.out.printf("Spesa acquisiti: €%.2f\n", spesaAcquisiti);
                    System.out.printf("Spesa rimanente: €%.2f\n", spesaRimanente);
                    System.out.println("═══════════════════════════════════════");

                } else {
                    System.out.println("La lista è vuota!");
                    System.out.println("═══════════════════════════════════════");
                }

            } else if (scelta == 7) {

                try {
                    StringBuilder csv = new StringBuilder();
                    
                    if (!Files.exists(Paths.get("spesa.csv")) || Files.size(Paths.get("spesa.csv")) == 0) {
                        csv.append("Nome,Prezzo,Quantita,Categoria,Acquistato\n");
                    }

                    for (Articolo a : listaArticoli.values()) {
                        csv.append(a.nome).append(",");
                        csv.append(a.prezzo).append(",");
                        csv.append(a.quantita).append(",");
                        csv.append(a.categoria).append(",");
                        csv.append(a.acquistato).append("\n");
                    }

                    Files.writeString(Paths.get("spesa.csv"), csv.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    System.out.println("Lista salvata in spesa.csv");
                    System.out.println("(" + listaArticoli.size() + " articoli salvati)");
                } catch (Exception e) {
                    System.out.println("Errore durante il salvataggio: " + e.getMessage());
                } 

            } else if (scelta == 8) {
                
                try {
                    List<String> linee = Files.readAllLines(Paths.get("carica.csv"));

                    for (int i = 1; i < linee.size(); i++) {  // Salta header
                        String linea = linee.get(i);
                        String[] campi = linea.split(",");

                        String nome = campi[0];
                        double prezzo = Double.parseDouble(campi[1]);
                        int quantita = Integer.parseInt(campi[2]);
                        String categoria = campi[3];
                        boolean acquistato = Boolean.parseBoolean(campi[4]);

                        Articolo a = new Articolo(nome, prezzo, quantita, categoria, acquistato);
                        listaArticoli.put(nome, a);
                    }
                } catch (Exception e) {
                    System.out.println("Errore durante il caricamento: " + e.getMessage());
                }

            } else if (scelta == 9) {
                break;
            } else {
                System.out.println("\nScelta non valida!");
            }
        }
        
        input.close();
    }
}
