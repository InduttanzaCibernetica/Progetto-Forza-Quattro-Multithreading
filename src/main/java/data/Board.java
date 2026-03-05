package data;

import enums.Token;

public class Board {

    public static final int ROWS = 6;
    public static final int COLS = 7;

    private final Token[][] grid = new Token[ROWS][COLS];

    public boolean isColumnAvailable(int col) {	// da true se colonna è valida e ha una cella libera
    	
        return col >= 0 && col < COLS && grid[0][col] == null;	//controllo cella in cima
    }
  
    
    

    public boolean applyMove(Token token, int col) {	//inserimento del token in basso alla colonna se libera, false se non si può
    	
        if (token == null || !isColumnAvailable(col)) return false;	//per colonna non disponibile
        for (int r = ROWS - 1; r >= 0; r--) {	//gravità dal basso verso alto
        	
            if (grid[r][col] == null) {	//prima cella libera trovata
            	
                grid[r][col] = token;	//mette disco
                
                return true;	//andato a buon fine
            }
        }
        return false;	//non andato a buon fine; colonna piena (a regola qua non dovrebbe arrivare, in questa riga si intente)
    }
    
    
    

    //restituisce true se token ha 4 dischi consecutivi in verticale, orizzontale o diagonale
    public boolean checkVictory(Token token) {
        if (token == null) return false;
        int[][] directions = {{0,1},{1,0},{1,1},{1,-1}};	//orizzontale, verticale, diagonale sinitra e destra
        
        for (int r = 0; r < ROWS; r++) {	//scorro le righe
        	
            for (int c = 0; c < COLS; c++) {	//scorro le colonne
            	
                for (int[] direzione : directions) {	//prova ogni direzione
                    if (line4(token, r, c, direzione[0], direzione[1])) return true;	//se trova una sequenza da quattro
                }
            }
        }
        return false;
    }

    
    //restituisce true se tutte le celle della prima riga sono occupate e quindi tabella piena deh
    public boolean isFull() {
        for (int c = 0; c < COLS; c++)
            if (grid[0][c] == null) return false;
        return true;
    }

    public String toLinearState() {
        StringBuilder sb = new StringBuilder(ROWS * COLS);
        for (Token[] row : grid)
            for (Token t : row)
                sb.append(t == null ? '.' : t.name());
        return sb.toString();
    }

    private boolean line4(Token t, int r, int c, int dr, int dc) {
        for (int k = 0; k < 4; k++) {
            int rr = r + dr * k;
            int cc = c + dc * k;
            if (rr < 0 || rr >= ROWS || cc < 0 || cc >= COLS) return false;
            if (grid[rr][cc] != t) return false;
        }
        return true;
    }
}