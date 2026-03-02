package data;

import enums.Token;

public class Board {
	//dimensione della tabella di forza quattro 6x7
	public static final int ROWS=6;
	public static final int COLS=7;	
	private final Token[][] grid;
	
	public Board() {
		this.grid=new Token[ROWS][COLS];
		//all'inizio è tutto vuoto (null)
	}
	//se la colonna ha almeno una cella vuota libera e è valida restituisce true
	public boolean isColumnAvailable(int col) {
		if(!inBoundsCol(col)) return false;
		return grid[0][col]==null;
	}
	
	public boolean applyMove(Token token, int col) {
		if(token==null) return false;
		if(!inBoundsCol(col)) return false;
		for(int r=ROWS-1; r>=0; r--) {
			if(grid[r][col]==null) {
				grid[r][col]=token;
				return true;
			}
		}
		return false; //colonna piena
	}
	
	public boolean checkVictory(Token token) {
		if(token==null) return false;
		
		//orizzontali
		for(int r=0; r<ROWS; r++) {
			for(int c=0; c<=COLS-4; c++) {
				if(line4(token, r, c, 0, 1)) return true;
			}
		}
		
		//verticali
		for(int c=0; c<COLS; c++) {
			for(int r=0; r<=ROWS-4; r++) {
				if(line4(token, r, c, 1, 0)) return true;
			}
		}
		//diagonali (dr=+1, dc=+1)
		for(int r=0; r<=ROWS-4; r++) {
			for(int c=0; c<=COLS-4; c++) {
				if(line4(token, r, c, 1, 1)) return true;
			}
		}
		//diagonali (dr=+1, dc=-1)
		for(int r=0; r<=ROWS-4; r++) {
			for(int c=3; c<COLS; c++) {
				if(line4(token, r, c, 1, -1)) return true;
			}
		}
		return false;
	}
	// true se la tabella è piena e quindi nessuna cella è libera
	
	public boolean isFull() {
		// la tabella non è piena se nella prima riga c'è una cella libera
		for(int c=0; c<COLS; c++) {
			if(grid[0][c]==null) return false;
		}
		return true;
	}
	
	//cella vuota -> .
	//Token.X -> X
	//Token.O -> O
	public String toLinearState() {
		StringBuilder sb=new StringBuilder(ROWS*COLS);
		for(int r=0; r<ROWS; r++) {
			for(int c=0; c<COLS; c++) {
				Token t=grid[r][c];
				sb.append(t==null ? '.' : (t==Token.X ? 'X' : 'O'));
			}
		}
		return sb.toString();
	}
	

	private boolean inBoundsCol(int c) {
        return c >= 0 && c < COLS;
    }
    private boolean inBounds(int r, int c) {
        return r >= 0 && r < ROWS && c >= 0 && c < COLS;
    }

    // Controlla una sottolinea di 4 celle a partire da (r,c) con direzione (dr,dc).
    private boolean line4(Token t, int r, int c, int dr, int dc) {
        for (int k = 0; k < 4; k++) {
            int rr = r + dr * k;
            int cc = c + dc * k;
            if (!inBounds(rr, cc)) return false;
            if (grid[rr][cc] != t) return false;
        }
        return true;
    }
	
}
