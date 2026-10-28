package modelo;

import java.util.ArrayList;

// Movimientos del Alfil
public class Alfil extends Pieza {

    public Alfil(Color color) {
        super(color);
    }

    @Override
    public char getLetra() {
        return 'A';
    }

    @Override
    public String getNombre() {
        return "Alfil";
    }

    @Override
    public ArrayList<Posicion> movimientos(Tablero tablero, Posicion desde) {
        ArrayList<Posicion> lista = new ArrayList<>();
        recorrer(tablero, desde, -1, -1, lista); // arriba a la izquierda
        recorrer(tablero, desde, -1, 1, lista);  // arriba a la derecha
        recorrer(tablero, desde, 1, -1, lista);  // abajo a la izquierda
        recorrer(tablero, desde, 1, 1, lista);   // abajo a la derecha
        return lista;
    }
}
