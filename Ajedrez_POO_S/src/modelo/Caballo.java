package modelo;

import java.util.ArrayList;

// Movimientos del caballo
public class Caballo extends Pieza {

    public Caballo(Color color) {
        super(color);
    }

    @Override
    public char getLetra() {
        return 'C';
    }

    @Override
    public String getNombre() {
        return "Caballo";
    }

    @Override
    public ArrayList<Posicion> movimientos(Tablero tablero, Posicion desde) {
        ArrayList<Posicion> lista = new ArrayList<>();
        saltar(tablero, desde, -2, -1, lista);
        saltar(tablero, desde, -2, 1, lista);
        saltar(tablero, desde, -1, -2, lista);
        saltar(tablero, desde, -1, 2, lista);
        saltar(tablero, desde, 1, -2, lista);
        saltar(tablero, desde, 1, 2, lista);
        saltar(tablero, desde, 2, -1, lista);
        saltar(tablero, desde, 2, 1, lista);
        return lista;
    }
}
