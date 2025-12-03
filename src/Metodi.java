import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Metodi {

    // Scanner per leggere input da console
    private static Scanner input = new Scanner(System.in);

    // ===================== MENU =====================
    // Mostra il menu principale dell'applicazione
    public static void mostraMenu() {
        System.out.println("\nGESTIONE LISTA DELLA SPESA");
        System.out.println("1. Aggiungi articolo");
        System.out.println("2. Visualizza lista");
        System.out.println("3. Rimuovi articolo");
        System.out.println("4. Cerca articolo");
        System.out.println("5. Marca come acquistato");
        System.out.println("6. Calcola totale spesa");
        System.out.println("7. Salva su file");
        System.out.println("8. Carica da file");
        System.out.println("9. Esci");
        System.out.print("Digita un comando: ");
    }

    // ===================== GESTIONE ARTICOLI =====================
    // Aggiunge un nuovo articolo alla lista
    public static void aggiungiArticolo(Map<String, Articolo> listaArticoli, String nome, double prezzo, int quantita, String categoria) {
        Articolo articolo = new Articolo(nome, prezzo, quantita, categoria, false);
        listaArticoli.put(nome, articolo);
    }

    // Visualizza tutti gli articoli con dettagli e subtotale
    public static String visualizzaLista(Map<String, Articolo> listaArticoli) {
        if (checkListaVuota(listaArticoli)) return "La lista è vuota.";

        StringBuilder sb = new StringBuilder();
        int comprati = 0;
        double totale = 0;
        int numero = 1;

        for (Articolo a : listaArticoli.values()) {
            double subtotale = a.prezzo * a.quantita;
            sb.append(formattaArticolo(a, numero, subtotale));
            if (a.acquistato) comprati++;
            totale += subtotale;
            numero++;
        }

        int nonComprati = listaArticoli.size() - comprati;
        sb.append("═══════════════════════════════════════\n");
        sb.append("Totale articoli: ").append(listaArticoli.size()).append("\n");
        sb.append("Acquistati: ").append(comprati).append("\n");
        sb.append("Non acquistati: ").append(nonComprati).append("\n");
        sb.append(String.format("Spesa totale: €%.2f\n", totale));
        sb.append("═══════════════════════════════════════\n");

        return sb.toString();
    }

    // Rimuove un articolo selezionato dall'utente tramite numero
    public static boolean rimuoviArticolo(Map<String, Articolo> listaArticoli, Articolo a) {
        if (a != null) {
            listaArticoli.remove(a.nome);
            return true;
        } else {
            return false;
        }
    }

    // Cerca un articolo per nome e lo stampa se trovato
    public static boolean cercaArticolo(Map<String, Articolo> listaArticoli, String nomeCercato) {
        Articolo a = listaArticoli.get(nomeCercato);

        if (a != null) {
            double subtotale = a.prezzo * a.quantita;
            stampaArticolo(a, 0, subtotale); // numero = 0 significa nessun numero davanti
            return true;
        } else {
            return false;
        }
    }

    // Marca un articolo come acquistato selezionandolo tramite numero
    public static boolean marcaAcquistato(Articolo a) {
        if (a != null) {
            a.acquistato = true;
            return a.acquistato;
        } else {
            return false;
        }
    }

    // Calcola e mostra il totale della spesa, spesa degli articoli acquistati e rimanenti
    public static String calcolaTotale(Map<String, Articolo> listaArticoli) {
        if (checkListaVuota(listaArticoli)) return "La lista è vuota.";

        int comprati = 0;
        double totale = 0;
        double spesaAcquistati = 0;
        double spesaRimanente = 0;

        for (Articolo a : listaArticoli.values()) {
            double subtotale = a.prezzo * a.quantita;
            totale += subtotale;
            if (a.acquistato) {
                comprati++;
                spesaAcquistati += subtotale;
            } else {
                spesaRimanente += subtotale;
            }
        }

        int nonComprati = listaArticoli.size() - comprati;

        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("Articoli totali: ").append(listaArticoli.size()).append("\n");
        sb.append("Acquistati: ").append(comprati).append("\n");
        sb.append("Non acquistati: ").append(nonComprati).append("\n");
        sb.append("═══════════════════════════════════════\n");
        sb.append(String.format("Spesa totale: €%.2f\n", totale));
        sb.append(String.format("Spesa acquisiti: €%.2f\n", spesaAcquistati));
        sb.append(String.format("Spesa rimanente: €%.2f\n", spesaRimanente));
        sb.append("═══════════════════════════════════════\n");

        return sb.toString();
    }

    // ===================== GESTIONE FILE =====================
    // Salva la lista di articoli su un file CSV
    public static String salvaSuFile(Map<String, Articolo> listaArticoli) {
        try {
            StringBuilder csv = new StringBuilder();
            csv.append("Nome,Prezzo,Quantita,Categoria,Acquistato\n"); // header CSV
            for (Articolo a : listaArticoli.values()) {
                csv.append(articoloToCsv(a));
            }

            Files.writeString(Paths.get("spesa.csv"), csv.toString(), StandardOpenOption.CREATE);
            return "(" + listaArticoli.size() + " articoli salvati)";
        } catch (Exception e) {
            return "Errore durante il salvataggio: " + e.getMessage();
        }
    }

    // Carica articoli da un file CSV nella lista
    public static String caricaDaFile(Map<String, Articolo> listaArticoli) {
        try {
            List<String> linee = Files.readAllLines(Paths.get("spesa.csv"));
            for (int i = 1; i < linee.size(); i++) { // salta header
                Articolo a = csvToArticolo(linee.get(i));
                listaArticoli.put(a.nome, a);
            }
            return "Lista caricata da spesa.csv (" + listaArticoli.size() + " articoli)";
        } catch (Exception e) {
            return "Errore durante il caricamento: " + e.getMessage();
        }
    }

    // ===================== METODI AUSILIARI =====================
    // Stampa un articolo con numero, nome, categoria e subtotale
    private static void stampaArticolo(Articolo a, int numero, double subtotale) {
        String check = a.acquistato ? "[X]" : "[ ]"; // segna se acquistato
        if (numero > 0) {
            System.out.printf("%s %d. %s - %s\n", check, numero, a.nome, a.categoria);
        } else {
            System.out.printf("%s %s - %s\n", check, a.nome, a.categoria);
        }
        System.out.printf("       €%.2f x %d = €%.2f\n\n", a.prezzo, a.quantita, subtotale);
    }

    // Restituisce un articolo selezionato tramite numero
    public static Articolo getArticoloByNumero(Map<String, Articolo> listaArticoli, String messaggio) {
        System.out.print(messaggio);
        int opzione = input.nextInt();
        input.nextLine();
        int numero = 1;

        for (Articolo a : listaArticoli.values()) {
            if (numero == opzione) return a;
            numero++;
        }
        return null;
    }

    // Controlla se la lista è vuota e stampa messaggio
    public static boolean checkListaVuota(Map<String, Articolo> listaArticoli) {
        if (listaArticoli.isEmpty()) {
            return true;
        }
        return false;
    }

    // Converte un articolo in formato CSV
    private static String articoloToCsv(Articolo a) {
        return a.nome + "," + a.prezzo + "," + a.quantita + "," + a.categoria + "," + a.acquistato + "\n";
    }

    // Converte una riga CSV in oggetto Articolo
    private static Articolo csvToArticolo(String linea) {
        String[] campi = linea.split(",");
        return new Articolo(
                campi[0],
                Double.parseDouble(campi[1]),
                Integer.parseInt(campi[2]),
                campi[3],
                Boolean.parseBoolean(campi[4])
        );
    }

    // Metodo ausiliario per restituire stringa dell'articolo
    private static String formattaArticolo(Articolo a, int numero, double subtotale) {
        String check = a.acquistato ? "[X]" : "[ ]";
        StringBuilder sb = new StringBuilder();
        sb.append(numero > 0 
                ? String.format("%s %d. %s - %s\n", check, numero, a.nome, a.categoria)
                : String.format("%s %s - %s\n", check, a.nome, a.categoria));
        sb.append(String.format("       €%.2f x %d = €%.2f\n\n", a.prezzo, a.quantita, subtotale));
        return sb.toString();
    }

    public static double leggiNumPositivo(Scanner input, String messaggio) {
        double valore = -1;
        do {
            try {
                System.out.print(messaggio);
                valore = input.nextDouble();
                input.nextLine();
                if (valore <= 0) System.out.println("Il numero deve essere > 0!");
            } catch (Exception e) {
                System.out.println("Devi inserire un numero!");
                input.nextLine();
            }
        } while (valore <= 0);
        return valore;
    }

}
