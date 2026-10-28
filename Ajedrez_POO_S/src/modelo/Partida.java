package modelo;

import java.util.ArrayList;

// Una partida: tiene el tablero, los jugadores, el turno y el historial de jugadas.
public class Partida {

    private Jugador blancas;
    private Jugador negras;
    private Tablero tablero;
    private Color turno;
    private boolean terminada;
    private Jugador ganador;
    private ArrayList<String> historial;

    public Partida(Jugador blancas, Jugador negras) {
        this.blancas = blancas;
        this.negras = negras;
        this.tablero = Tablero.inicial();
        this.turno = Color.BLANCO;
        this.terminada = false;
        this.ganador = null;
        this.historial = new ArrayList<>();
    }

    public Jugador getBlancas() {
        return blancas;
    }

    public Jugador getNegras() {
        return negras;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Color getTurno() {
        return turno;
    }

    public boolean isTerminada() {
        return terminada;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public ArrayList<String> getHistorial() {
        return historial;
    }

    public Jugador getJugadorEnTurno() {
        if (turno == Color.BLANCO) {
            return blancas;
        }
        return negras;
    }

    // jugadas posibles de la pieza seleccionada

    public ArrayList<Posicion> jugadasDe(Posicion desde) {
        Pieza pieza = tablero.get(desde);
        if (pieza == null || pieza.getColor() != turno) {
            return new ArrayList<>();
        }
        return pieza.movimientos(tablero, desde);
    }

    //  lanza una excepcion si la jugada es invalida
    public void mover(Posicion desde, Posicion hasta) throws MovimientoIlegalException {
        if (terminada) {
            throw new MovimientoIlegalException("La partida ya termino.");
        }
        Pieza pieza = tablero.get(desde);
        if (pieza == null) {
            throw new MovimientoIlegalException("No hay ninguna pieza en " + desde + ".");
        }
        if (pieza.getColor() != turno) {
            throw new MovimientoIlegalException("Esa pieza no es tuya, le toca a las " + turno.getNombre() + ".");
        }
        ArrayList<Posicion> posibles = pieza.movimientos(tablero, desde);
        if (!posibles.contains(hasta)) {
            throw new MovimientoIlegalException(pieza + " no puede ir de " + desde + " a " + hasta + ".");
        }

        Pieza capturada = tablero.get(hasta);
        tablero.mover(desde, hasta);

        if (capturada == null) {
            historial.add(desde + "-" + hasta);
        } else {
            historial.add(desde + "x" + hasta);
        }

        // el   jugador que captura al rey  gana la partida
        if (capturada instanceof Rey) {
            terminada = true;
            ganador = getJugadorEnTurno();
        }
        turno = turno.contrario();
    }
}
