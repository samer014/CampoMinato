public class Casella {
    private boolean Hide; // Indica se la casella è coperta o scoperta
    private boolean Bomb; // Indica se la casella contiene una bomba
    private boolean Flag; // Indica se la casella ha una bandiera
    private int bombeVicine; // Numero di bombe vicine

    public Casella() {
        this.Hide = true; // Inizialmente la casella è coperta
        this.Bomb = false; // Inizialmente la casella non è una bomba
        this.Flag = false; // Inizialmente la casella non ha una bandiera
        this.bombeVicine = 0; // Inizialmente la casella non ha bombe vicine
    }
    
    // Rende visibile la casella se non ha una bandiera
    public void scopri(){
        if(!Flag){Hide = false;}
    }

    // Restituisce true se la casella è coperta, altrimenti false
    public boolean isHide() {
        return Hide;
    }
    
    // Restituisce true se la casella contiene una bomba, altrimenti false
    public boolean isBomb() {
        return Bomb;
    }
    
    public void setBomb(boolean Bomb){
        this.Bomb = Bomb;
    }
    // Restituisce true se la casella contiene una bandiera, altrimenti false
    public boolean isFlag() {
        return Flag;
    }

    public void setFlag(boolean isFlag) {
        if(Hide){this.Flag = isFlag;} // Imposta la bandiera solo se la casella è coperta
    }

    public void setBombeVicine(int bombeVicine){
        this.bombeVicine = bombeVicine;
    }
    
    public int getBombeVicine() {
        return bombeVicine;
    }

    @Override
    public String toString() {
        if (Flag) {
            return "F"; // Casella con bandiera
        } else if (Hide){
            return "@"; // Casella coperta
        } else if (Bomb) {
            return "X"; // Casella con bomba
        } else if(bombeVicine == 0){
            return " "; // Casella vuota senza bombe vicine
        } else{
            return Integer.toString(bombeVicine); // Casella con il numero di bombe vicine
        }
    }
}
