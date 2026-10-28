package modelo;

import java.util.ArrayList;

// La torre se mueve en linea recta.
public class Torre extends Pieza {

    public Torre(Color color) {
        super(color);
    }

    @Override
    public char getLetra() {
        return 'T';
    }

    @Override
    public String getNombre() {
        return "Torre";
    }

    @Override
    public ArrayList<Posicion> movimientos(Tablero tablero, Posicion desde) {
        ArrayList<Posicion> lista = new ArrayList<>();
        recorrer(tablero, desde, -1, 0, lista); // arriba
        recorrer(tablero, desde, 1, 0, lista);  // abajo
        recorrer(tablero, desde, 0, -1, lista); // izquierda
        recorrer(tablero, desde, 0, 1, lista);  // derecha
        return lista;
    }
}
