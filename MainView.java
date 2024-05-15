import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainView {
    static boolean clickIniziale = false;
    private class CasellaMainView extends JButton { 
        int r; // Indice di riga della casella
        int c; // Indice di colonna della casella
        boolean bomba; // Flag che indica se la casella contiene una bomba o meno

        public CasellaMainView(int r, int c) { // Costruttore
            this.r = r; // Inizializzazione dell'indice di riga
            this.c = c; // Inizializzazione dell'indice di colonna
            this.bomba = false; // Inizializzazione del flag bomba a false
        }
    }
    private CampoMinato campo = new CampoMinato(12); // Campo di dimensione 12
    
    // Dichiarazioni di variabili per la gestione grafica del campo di gioco
    int dimensioneCasella = 80; // dimensione in pixel
    int numRighe = campo.getDimensione(); // Numero di righe del campo di gioco
    int numColonne = numRighe; // Numero di colonne del campo di gioco (uguale al numero di righe)
    int larghezzaTabellone = numColonne * dimensioneCasella; // Larghezza totale del tabellone
    int altezzaTabellone = numRighe * dimensioneCasella; // Altezza totale del tabellone
    
    // Dichiarazioni degli elementi grafici per la finestra di gioco
    JFrame frame = new JFrame("Campo Minato"); // Creazione di una finestra con titolo "Campo Minato"
    JPanel pannelloTabellone = new JPanel(); // Pannello per contenere il tabellone di gioco

    int conteggioMine = campo.getBombe(); // Numero totale di mine nel campo.
    CasellaMainView[][] tabellone = new CasellaMainView[numRighe][numColonne]; // Matrice per memorizzare le caselle del campo di gioco
    boolean partitaFinita = false; // Flag che indica se la partita è finita o meno
    
    MainView() {
        frame.setSize(larghezzaTabellone, altezzaTabellone);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Configurazione del pannello per il tabellone di gioco
        pannelloTabellone.setLayout(new GridLayout(numRighe, numColonne));
        frame.add(pannelloTabellone);

        // Creazione e configurazione delle caselle del tabellone di gioco
        for (int r = 0; r < numRighe; r++) {
            for (int c = 0; c < numColonne; c++) {
                CasellaMainView casella = new CasellaMainView(r, c);
                tabellone[r][c] = casella;

                casella.setFocusable(false);
                casella.setMargin(new Insets(0, 0, 0, 0));
                casella.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (partitaFinita) {
                            return;
                        }
                        CasellaMainView casella = (CasellaMainView) e.getSource();

                        // clic sinistro
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            inizioClic(casella.r, casella.c); // Esegue la funzione inizioClic al clic del mouse sinistro su una casella.
                        }
                        // clic destro
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            cambioBandiera(casella.r, casella.c); // Esegue la funzione cambioBandiera al clic del mouse destro su una casella.
                        }
                    } 
                });

                pannelloTabellone.add(casella);
            }
        }

        frame.setVisible(true); 
        impostaMine(); // Posiziona le mine nel campo di gioco
    }

    void inizioClic(int x, int y) {
        if(clickIniziale){
            scopriCasella(x, y); // Se è già stato effettuato un clic iniziale, esegue la funzione scopriCasella
        } else {
            campo.inizioClick(x, y); 
            clickIniziale = true; // Imposta il flag del primo clic a true dopo il primo clic iniziale
        }

        aggiornaTabellone(); // Aggiorna il tabellone dopo ogni azione
    }

    void scopriCasella(int x, int y) {
        campo.svelaCasella(x, y); // Rende visibile la casella indicata dalle coordinate (x, y)
        if(campo.getCasella(x, y).isBomb() && !campo.getCasella(x, y).isFlag()){
            rivelareCampo(); // Se la casella cliccata contiene una bomba, rivela tutto il campo di gioco
        }else{
            aggiornaTabellone(); // Altrimenti, aggiorna il tabellone
        }

    }

    void cambioBandiera(int x, int y) {
        campo.changeBandiera(x, y); // Cambia lo stato della bandiera sulla casella indicata dalle coordinate (x, y)
        aggiornaTabellone(); // Aggiorna il tabellone dopo il cambio di bandiera
    }

    void rivelareCampo() {
        campo.svelaCampo(); // Rivela tutte le caselle del campo di gioco
        aggiornaTabellone(); // Aggiorna il tabellone dopo aver rivelato il campo
    }
    
    void aggiornaTabellone() {
        for (int r = 0; r < numRighe; r++) {
            for (int c = 0; c < numColonne; c++) {
                CasellaMainView casella = tabellone[r][c];
                casella.setText(campo.getCasella(r, c)+""); // Aggiorna il testo delle caselle sulla base dello stato attuale del campo, chiamando il toString().
            }
        }
        if (campo.campoFinito()) {
            rivelareCampo(); // Se la partita è finita, rivela tutte le mine
            partitaFinita = true;
        }
    }

    void impostaMine() {
        int mineRimanenti = conteggioMine;
        while (mineRimanenti > 0) {
            int r = (int) (Math.random() * numRighe);
            int c = (int) (Math.random() * numColonne);
            CasellaMainView casella = tabellone[r][c]; 
            if (!casella.bomba) {
                casella.bomba = true; // Imposta la casella come contenente una bomba
                mineRimanenti--; // Decrementa il numero di mine rimanenti
            }
        }
    }
}