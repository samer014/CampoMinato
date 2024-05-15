public class CampoMinato {
    protected Casella[][] campo; // Matrice di caselle che rappresenta il campo di gioco
    protected int dimensione; // Numero di righe e colonne, dimensione del campo
    protected int numBombe; // Numero totale di bombe nel campo

    // Costruttore della classe CampoMinato
    public CampoMinato(int dimensione) {
        this.dimensione = dimensione;
        // Se la dimensione del campo è inferiore a 8 o superiore a 30, viene impostata a 12
        if(this.dimensione < 8 || this.dimensione > 30) this.dimensione = 12;
        
        campo = new Casella[dimensione][dimensione]; // Inizializzazione del campo di gioco
        // Ciclo per inizializzare ogni casella del campo
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                // Ogni casella ha una probabilità del 15.87% di contenere una bomba
                if(j < dimensione || i < dimensione){
                    campo[i][j] = new Casella();
                    double s = Math.random() * 100;
                    if(s <= 15.87){
                        campo[i][j].setBomb(true);
                        numBombe++;
                    }
                }                
            }
        }
    }

    // Metodo per gestire il primo click dell'utente, inizializza la posizione cliccata
    public void inizioClick(int x, int y){
        for (int i = x -1; i <= x + 1; i++) {
            for (int j = y -1; j <= y + 1; j++) {
                if(i >= 0 && i < dimensione && j >= 0 && j < dimensione){
                    campo[i][j].setBomb(false);
                }
            }
        }
        svelaCasella(x, y);
    }

    // Metodo per svelare una casella del campo
    public void svelaCasella(int x, int y) {
        // Se la casella è nascosta, la svela e conta il numero di bombe vicine
        if (campo[x][y].isHide() && !campo[x][y].isFlag()) {
            campo[x][y].scopri();
            contaBombeVicino(x, y);
            // Se la casella è vuota e non contiene una bomba, svela le caselle vicine
            if (campo[x][y].getBombeVicine() == 0 && !campo[x][y].isBomb()) {
                svelaBombeVicino(x-1, y-1);
                svelaBombeVicino(x-1, y);
                svelaBombeVicino(x-1, y+1);

                svelaBombeVicino(x, y-1);
                svelaBombeVicino(x, y+1);

                svelaBombeVicino(x+1, y-1);
                svelaBombeVicino(x+1, y);
                svelaBombeVicino(x+1, y+1);
            }
        }
    }

    // Metodo ricorsivo per svelare le caselle vicine
    public void svelaBombeVicino(int x, int y){
        if(x < 0 || x >= dimensione || y < 0 || y >= dimensione || campo[x][y].isBomb() || campo[x][y].isFlag() || !campo[x][y].isHide()){
            return;
        }
        svelaCasella(x,y);
    }

    // Metodo per cambiare lo stato della bandiera su una casella
    public void changeBandiera(int x, int y){
        if(campo[x][y].isHide() && campo[x][y].isFlag()) campo[x][y].setFlag(false);
        else if(campo[x][y].isHide()) campo[x][y].setFlag(true);
    }

    // Metodo per ottenere una casella specifica del campo
    public Casella getCasella(int x , int y){
        return this.campo[x][y];
    }    
    
    // Metodi getter per ottenere la dimensione del campo e il numero totale di bombe
    public int getDimensione(){
        return this.dimensione;
    }
    
    public int getBombe(){
        return this.numBombe;
    }

    // Metodo per contare il numero di bombe vicino a una casella
    public void contaBombeVicino(int x, int y) {
        if(x < 0 || x >= dimensione || y < 0 || y >= dimensione){
            return;
        }
        int mine = 0;

        mine += contaMina(x-1, y-1);
        mine += contaMina(x-1, y);
        mine += contaMina(x-1, y+1);

        mine += contaMina(x, y-1);
        mine += contaMina(x, y+1);

        mine += contaMina(x+1, y-1);
        mine += contaMina(x+1, y);
        mine += contaMina(x+1, y+1);

        campo[x][y].setBombeVicine(mine);
    }

    // Metodo ausiliario per contare il numero di bombe in una posizione specifica
    public int contaMina(int x, int y){
        if (x >= 0 && x < dimensione && y >= 0 && y < dimensione) {
            if(campo[x][y].isBomb()) return 1;
            else return 0;
        }
        return 0;
    }

    // Metodo per verificare se il campo è stato completamente scoperto
    public boolean campoFinito(){
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                if(!(campo[i][j].isHide() && campo[i][j].isBomb() && campo[i][j].isFlag()) || !(campo[i][j].isHide() && campo[i][j].isBomb())){
                    return false;
                }
            }
        }
        return true;
    }

    // Metodo per impostare una nuova dimensione per il campo
    public void setDimensione(int d){
        this.dimensione = d;
    }

    // Metodo per svelare completamente il campo (usato alla fine del gioco)
    public void svelaCampo(){
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                if(campo[i][j].isHide()){
                    campo[i][j].scopri();
                }
            }
        }
    }

    // Metodo toString per visualizzare il campo di gioco
    @Override
    public String toString() {
        for (int i = 0; i < dimensione; i++){
            for (int j = 0; j < dimensione; j++) {
                System.out.print(campo[i][j].toString() + " ");
            }
            System.out.println();
        }
        return "";
    }
}
