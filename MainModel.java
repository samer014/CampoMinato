import java.util.Scanner;

public class MainModel {

    // Dichiarazione delle variabili di classe
    public static int contatore = 0;
    public static boolean giocofinito;
    private static CampoMinato campo;
    private static boolean clickIniziale = false;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        try {
            // Richiesta all'utente di inserire la dimensione del campo minato
            System.out.println("INSERISCI DIMENSIONE");
            int dimensioneS = Integer.parseInt(s.nextLine());
            campo = new CampoMinato(dimensioneS);
            giocofinito = gioco(campo);
        } catch(NumberFormatException e) {
            // Gestione dell'eccezione nel caso in cui l'utente inserisca un valore non valido per la dimensione
            System.out.println("Inserisci la dimensione corretta");
            campo.setDimensione(12); // Impostazione di una dimensione predefinita
        }
        
        // Se il gioco non è finito, si richiama il metodo per giocare ancora
        if (!(giocofinito)) {
            clickIniziale = false;
            giocaAncora(true);
        } else {
            giocaAncora(false);
        }
    }

    // Metodo per gestire il gioco
    private static boolean gioco(CampoMinato campo) {
        Scanner s = new Scanner(System.in);
        // Condizione di vittoria: tutte le caselle tranne quelle con le bombe sono state scoperte
        if (contatore == (campo.getDimensione() * campo.getDimensione()) - campo.getBombe()) {
            contatore = 0;
            return false;
        }
        // Visualizzazione del campo minato e istruzioni per l'utente
        System.out.println(campo);
        System.out.println("F = mettere Flag , X = liberare casella nascosta ,DEVI SCRIVERE COSI -F x y-");
        String risposta = s.nextLine();
        int x, y;
        try {
            String[] r2 = risposta.split(" ");
            x = Integer.parseInt(r2[1]);
            y = Integer.parseInt(r2[2]);
            switch(r2[0].charAt(0)) {
                // Gestione delle azioni dell'utente in base all'input
                case 'F', 'f' -> campo.changeBandiera(x, y);
                case 'X', 'x' -> {
                    // Se è il primo clic, viene inizializzata la posizione cliccata
                    if(clickIniziale == false) {
                        clickIniziale = true;
                        campo.inizioClick(x,y);
                    } else {
                        // Altrimenti viene svelata la casella e si verifica se contiene una bomba
                        campo.svelaCasella(x, y);
                        if(campo.getCasella(x,y).isBomb()) {
                            return true;
                        }
                    }
                    contatore++;
                }
            }
        } catch (NumberFormatException e) {
            // Gestione dell'eccezione nel caso in cui l'utente inserisca una posizione non valida
            System.out.println("Scrivi bene la posizione");
            gioco(campo);
        } catch (IndexOutOfBoundsException e) {
            // Gestione dell'eccezione nel caso in cui l'utente inserisca una posizione non valida
            System.out.println("Scrivi bene la posizione in modo corretto");
        }
        // Ricorsione per continuare il gioco
        gioco(campo);
        return false;
    }

    // Metodo per chiedere all'utente se vuole giocare ancora
    private static void giocaAncora(boolean vittoria) {
        Scanner s = new Scanner(System.in);
        if (vittoria) {
            // Se ha perso, viene svelato il campo minato e si comunica la sconfitta
            campo.svelaCampo();
            System.out.println(campo);
            System.out.println("Hai perso!");
        } else {
            // Altrimenti, si comunica la vittoria
            System.out.println("Hai vinto!");
        }
        // Chiede all'utente se vuole giocare ancora
        System.out.println("Vuoi giocare ancora? S / N");
        String risposta = s.nextLine();
        switch (risposta) {
            case "S", "s" -> {
                // Se vuole giocare ancora, chiede di inserire una nuova dimensione per il campo minato e ricomincia il gioco
                System.out.println("INSERISCI DIMENSIONE");
                int dimensioneS = Integer.parseInt(s.nextLine());
                CampoMinato campo = new CampoMinato(dimensioneS);
                gioco(campo);
            }
            case "N", "n" -> {
                // Se non vuole giocare più, esce dal gioco
                break;
            }
        }
    }
}
