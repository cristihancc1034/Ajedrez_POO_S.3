package modelo;

import java.util.ArrayList;

// Movimientos de la Dama
public class Dama extends Pieza {

    public Dama(Color color) {
        super(color);
    }

    @Override
    public char getLetra() {
        return 'D';
    }

    @Override
    public String getNombre() {
        return "Dama";
    }

    @Override
    public ArrayList<Posicion> movimientos(Tablero tablero, Posicion desde) {
        ArrayList<Posicion> lista = new ArrayList<>();
        // como la torre
        recorrer(tablero, desde, -1, 0, lista);
        recorrer(tablero, desde, 1, 0, lista);
        recorrer(tablero, desde, 0, -1, lista);
        recorrer(tablero, desde, 0, 1, lista);
        // como el alfil
        recorrer(tablero, desde, -1, -1, lista);
        recorrer(tablero, desde, -1, 1, lista);
        recorrer(tablero, desde, 1, -1, lista);
        recorrer(tablero, desde, 1, 1, lista);
        return lista;
    }
}
