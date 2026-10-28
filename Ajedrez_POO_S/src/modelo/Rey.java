package modelo;

import java.util.ArrayList;

// El rey se mueve una casilla en cualquier direccion.
public class Rey extends Pieza {

    public Rey(Color color) {
        super(color);
    }

    @Override
    public char getLetra() {
        return 'R';
    }

    @Override
    public String getNombre() {
        return "Rey";
    }

    @Override
    public ArrayList<Posicion> movimientos(Tablero tablero, Posicion desde) {
        ArrayList<Posicion> lista = new ArrayList<>();
        saltar(tablero, desde, -1, -1, lista);
        saltar(tablero, desde, -1, 0, lista);
        saltar(tablero, desde, -1, 1, lista);
        saltar(tablero, desde, 0, -1, lista);
        saltar(tablero, desde, 0, 1, lista);
        saltar(tablero, desde, 1, -1, lista);
        saltar(tablero, desde, 1, 0, lista);
        saltar(tablero, desde, 1, 1, lista);
        return lista;
    }
}
