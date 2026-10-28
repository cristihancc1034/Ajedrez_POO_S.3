package modelo;

// Una casilla del tablero de ajedrez, columna 0 = columna "a"
public class Posicion {

    private int fila;
    private int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean esValida() {
        return fila >= 0 && fila < 8 && columna >= 0 && columna < 8;
    }

    // devuelve las  casilla de dc filas y dc columnas
    public Posicion sumar(int df, int dc) {
        return new Posicion(fila + df, columna + dc);
    }

    // convierte un texto en una Posicion
    public static Posicion desdeTexto(String texto) {
        if (texto == null || texto.length() != 2) {
            return null;
        }
        int columna = texto.charAt(0) - 'a';
        int fila = 8 - (texto.charAt(1) - '0');
        Posicion p = new Posicion(fila, columna);
        if (!p.esValida()) {
            return null;
        }
        return p;
    }

    @Override
    public boolean equals(Object otro) {
        if (!(otro instanceof Posicion)) {
            return false;
        }
        Posicion p = (Posicion) otro;
        return fila == p.fila && columna == p.columna;
    }

    @Override
    public int hashCode() {
        return fila * 8 + columna;
    }

    // devuelve el texto como "e2"
    @Override
    public String toString() {
        char letra = (char) ('a' + columna);
        return "" + letra + (8 - fila);
    }
}
