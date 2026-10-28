package modelo;

import java.util.ArrayList;

// Clase padre de todas las piezas. Cada pieza hija dice como se mueve.
public abstract class Pieza {

    private Color color;

    public Pieza(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    // letra con la que se dibuja la pieza en la consola (R, D, T, A, C, P)
    public abstract char getLetra();

    public abstract String getNombre();

    // casillas a las que la pieza se puede mover 
    public abstract ArrayList<Posicion> movimientos(Tablero tablero, Posicion desde);

    // camina en una direccion (df filas y dc columnas por paso) hasta chocar con algo.
    // La usan la torre, el alfil y la dama.
    protected void recorrer(Tablero tablero, Posicion desde, int df, int dc, ArrayList<Posicion> lista) {
        Posicion p = desde.sumar(df, dc);
        while (p.esValida()) {
            Pieza otra = tablero.get(p);
            if (otra == null) {
                lista.add(p);
            } else {
                if (otra.getColor() != color) {
                    lista.add(p); // se puede capturar
                }
                return; // choco con una pieza, ya no sigue
            }
            p = p.sumar(df, dc);
        }
    }

    // da un solo salto de df filas y dc columnas. La usan el rey y el caballo.
    protected void saltar(Tablero tablero, Posicion desde, int df, int dc, ArrayList<Posicion> lista) {
        Posicion p = desde.sumar(df, dc);
        if (p.esValida()) {
            Pieza otra = tablero.get(p);
            if (otra == null || otra.getColor() != color) {
                lista.add(p);
            }
        }
    }

    @Override
    public String toString() {
        return getNombre() + " " + color.getNombre().toLowerCase();
    }
}
