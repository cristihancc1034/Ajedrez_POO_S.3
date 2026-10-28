package modelo;

// Tablero de 8x8. Guarda que pieza  en cada casilla
public class Tablero {

    private Pieza[][] casillas = new Pieza[8][8];

    //  tablero con las piezas en su posicion inicial
    public static Tablero inicial() {
        Tablero t = new Tablero();
        Color[] colores = {Color.BLANCO, Color.NEGRO};
        for (int i = 0; i < colores.length; i++) {
            Color c = colores[i];
            int base = c.filaBase();
            t.casillas[base][0] = new Torre(c);
            t.casillas[base][1] = new Caballo(c);
            t.casillas[base][2] = new Alfil(c);
            t.casillas[base][3] = new Dama(c);
            t.casillas[base][4] = new Rey(c);
            t.casillas[base][5] = new Alfil(c);
            t.casillas[base][6] = new Caballo(c);
            t.casillas[base][7] = new Torre(c);
            for (int col = 0; col < 8; col++) {
                t.casillas[c.filaInicialPeones()][col] = new Peon(c);
            }
        }
        return t;
    }

    public Pieza get(Posicion p) {
        return casillas[p.getFila()][p.getColumna()];
    }

    public void set(Posicion p, Pieza pieza) {
        casillas[p.getFila()][p.getColumna()] = pieza;
    }

    // mueve la pieza. Si habia una pieza en el destino, se pierde (captura).
    public void mover(Posicion desde, Posicion hasta) {
        Pieza pieza = get(desde);
        set(hasta, pieza);
        set(desde, null);
    }
}
