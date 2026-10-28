package modelo;

import java.util.ArrayList;

// Reglas del peon
public class Peon extends Pieza {

    public Peon(Color color) {
        super(color);
    }

    @Override
    public char getLetra() {
        return 'P';
    }

    @Override
    public String getNombre() {
        return "Peon";
    }

    @Override
    public ArrayList<Posicion> movimientos(Tablero tablero, Posicion desde) {
        ArrayList<Posicion> lista = new ArrayList<>();
        int dir = getColor().direccionPeon();

        // Avanzar una casilla ; Restringir si hay una pieza adelante
        Posicion uno = desde.sumar(dir, 0);
        if (uno.esValida() && tablero.get(uno) == null) {
            lista.add(uno);
            // Desde su fila inicial puede avanzar dos casillas
            Posicion dos = desde.sumar(2 * dir, 0);
            if (desde.getFila() == getColor().filaInicialPeones() && tablero.get(dos) == null) {
                lista.add(dos);
            }
        }

        // Captura en diagonal
        ArrayList<Posicion> diagonales = diagonales(desde);
        for (int i = 0; i < diagonales.size(); i++) {
            Pieza objetivo = tablero.get(diagonales.get(i));
            if (objetivo != null && objetivo.getColor() != getColor()) {
                lista.add(diagonales.get(i));
            }
        }
        return lista;
    }

    // Las dos casillas donde el peon puede capturar
    private ArrayList<Posicion> diagonales(Posicion desde) {
        ArrayList<Posicion> lista = new ArrayList<>();
        int dir = getColor().direccionPeon();
        Posicion izquierda = desde.sumar(dir, -1);
        Posicion derecha = desde.sumar(dir, 1);
        if (izquierda.esValida()) {
            lista.add(izquierda);
        }
        if (derecha.esValida()) {
            lista.add(derecha);
        }
        return lista;
    }
}
