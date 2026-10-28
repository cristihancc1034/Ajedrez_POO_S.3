import java.util.ArrayList;

import modelo.Color;
import modelo.Jugador;
import modelo.MovimientoIlegalException;
import modelo.Partida;
import modelo.Peon;
import modelo.Posicion;
import modelo.Rey;
import modelo.Tablero;
import modelo.Torre;

// Pruebas simples del nivel basico (se ejecuta con main, no usa JUnit)
public class PruebasBasicas {

    static int pruebas = 0;
    static int fallos = 0;

    static void comprobar(String nombre, boolean ok) {
        pruebas++;
        if (ok) {
            System.out.println("OK    " + nombre);
        } else {
            fallos++;
            System.out.println("FALLO " + nombre);
        }
    }

    // cuenta cuantas jugadas posibles hay hasta cierta profundidad
    static long contar(Tablero tablero, Color turno, int profundidad) {
        if (profundidad == 0) {
            return 1;
        }
        long total = 0;
        ArrayList<Posicion> piezas = new ArrayList<>();
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Posicion p = new Posicion(f, c);
                if (tablero.get(p) != null && tablero.get(p).getColor() == turno) {
                    piezas.add(p);
                }
            }
        }
        for (int i = 0; i < piezas.size(); i++) {
            ArrayList<Posicion> destinos = tablero.get(piezas.get(i)).movimientos(tablero, piezas.get(i));
            for (int j = 0; j < destinos.size(); j++) {
                total = total + contarDesde(tablero, piezas.get(i), destinos.get(j), turno, profundidad);
            }
        }
        return total;
    }

    // ayuda de contar: copia el tablero, hace la jugada en la copia y sigue contando
    static long contarDesde(Tablero original, Posicion desde, Posicion hasta, Color turno, int profundidad) {
        Tablero siguiente = new Tablero();
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Posicion p = new Posicion(f, c);
                siguiente.set(p, original.get(p));
            }
        }
        siguiente.mover(desde, hasta);
        return contar(siguiente, turno.contrario(), profundidad - 1);
    }

    static boolean falla(Partida p, String jugada) {
        String[] x = jugada.split(" ");
        try {
            p.mover(Posicion.desdeTexto(x[0]), Posicion.desdeTexto(x[1]));
            return false;
        } catch (MovimientoIlegalException e) {
            return true;
        }
    }

    static void jugar(Partida p, String... jugadas) throws MovimientoIlegalException {
        for (int i = 0; i < jugadas.length; i++) {
            String[] x = jugadas[i].split(" ");
            p.mover(Posicion.desdeTexto(x[0]), Posicion.desdeTexto(x[1]));
        }
    }

    public static void main(String[] args) throws Exception {
        // jugadas posibles desde la posicion inicial (hasta 3 jugadas todavia coinciden con el ajedrez real)
        Tablero inicial = Tablero.inicial();
        comprobar("20 jugadas en profundidad 1", contar(inicial, Color.BLANCO, 1) == 20);
        comprobar("400 jugadas en profundidad 2", contar(inicial, Color.BLANCO, 2) == 400);
        comprobar("8902 jugadas en profundidad 3", contar(inicial, Color.BLANCO, 3) == 8902);

        // movimientos de piezas sueltas
        Tablero t = new Tablero();
        t.set(Posicion.desdeTexto("d4"), new Torre(Color.BLANCO));
        comprobar("la torre en el centro tiene 14 movimientos", t.get(Posicion.desdeTexto("d4")).movimientos(t, Posicion.desdeTexto("d4")).size() == 14);
        t.set(Posicion.desdeTexto("g6"), new Rey(Color.BLANCO));
        comprobar("el rey en el centro tiene 8 movimientos", t.get(Posicion.desdeTexto("g6")).movimientos(t, Posicion.desdeTexto("g6")).size() == 8);
        t.set(Posicion.desdeTexto("b2"), new Peon(Color.BLANCO));
        comprobar("el peon blanco en su fila inicial puede avanzar 1 o 2", t.get(Posicion.desdeTexto("b2")).movimientos(t, Posicion.desdeTexto("b2")).size() == 2);

        // partida corta: captura y historial
        Partida p = new Partida(new Jugador("A", Color.BLANCO), new Jugador("B", Color.NEGRO));
        jugar(p, "e2 e4", "d7 d5", "e4 d5");
        comprobar("el historial anota el movimiento", p.getHistorial().get(0).equals("e2-e4"));
        comprobar("el historial anota la captura", p.getHistorial().get(2).equals("e4xd5"));
        comprobar("despues de 3 jugadas le toca a las negras", p.getTurno() == Color.NEGRO);

        // jugadas ilegales
        Partida q = new Partida(new Jugador("A", Color.BLANCO), new Jugador("B", Color.NEGRO));
        comprobar("no se puede mover pieza del rival", falla(q, "e7 e5"));
        comprobar("no se puede mover desde casilla vacia", falla(q, "e4 e5"));
        comprobar("el peon no puede avanzar 3", falla(q, "e2 e5"));
        comprobar("el caballo no puede ir a una casilla ocupada por su bando", falla(q, "g1 e2"));

        // el rey capturado termina la partida
        Partida r = new Partida(new Jugador("A", Color.BLANCO), new Jugador("B", Color.NEGRO));
        jugar(r, "e2 e4", "f7 f6", "d1 h5", "g7 g6", "h5 g6", "a7 a6", "g6 e8");
        comprobar("al capturar al rey la partida termina", r.isTerminada());
        comprobar("gana quien capturo al rey", r.getGanador().getNombre().equals("A"));
        comprobar("no se puede seguir jugando", falla(r, "a2 a3"));

        System.out.println();
        if (fallos == 0) {
            System.out.println("TODAS LAS PRUEBAS PASARON (" + pruebas + ")");
        } else {
            System.out.println("HUBO " + fallos + " FALLOS");
        }
    }
}
