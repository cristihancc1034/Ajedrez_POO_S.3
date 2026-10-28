package vista;

import java.util.ArrayList;
import java.util.Scanner;

import modelo.Color;
import modelo.Jugador;
import modelo.MovimientoIlegalException;
import modelo.Partida;
import modelo.Pieza;
import modelo.Posicion;

// Menu y pantalla de juego en la consola
public class MenuConsola {

    private Scanner teclado = new Scanner(System.in);

    // muestra el menu principal hasta que el usuario elija salir
    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("==============================");
            System.out.println("           AJEDREZ");
            System.out.println("==============================");
            System.out.println("1. Nueva partida");
            System.out.println("2. Como se juega");
            System.out.println("3. Salir");
            System.out.print("Elige una opcion: ");
            String opcion = teclado.nextLine().trim();

            if (opcion.equals("1")) {
                nuevaPartida();
            } else if (opcion.equals("2")) {
                mostrarAyuda();
            } else if (opcion.equals("3")) {
                salir = true;
                System.out.println("Hasta luego!");
            } else {
                System.out.println("Opcion no valida, escribe 1, 2 o 3.");
            }
        }
    }

    private void mostrarAyuda() {
        System.out.println();
        System.out.println("--- Como se juega ---");
        System.out.println("Las blancas se dibujan con letras MAYUSCULAS (abajo) y mueven primero.");
        System.out.println("Las negras se dibujan con letras minusculas.");
        System.out.println("R = Rey, D = Dama, T = Torre, A = Alfil, C = Caballo, P = Peon");
        System.out.println("En esta version todavia no hay jaque: gana quien captura al rey rival.");
        System.out.println();
        System.out.println("Comandos durante la partida:");
        System.out.println("  e2 e4      mueve la pieza de e2 a e4");
        System.out.println("  ver e2     muestra a donde puede ir la pieza de e2");
        System.out.println("  historial  muestra las jugadas hechas");
        System.out.println("  ayuda      muestra estos comandos");
        System.out.println("  salir      vuelve al menu (se pierde la partida)");
    }

    // pide los nombres, crea la partida y empieza a jugar
    private void nuevaPartida() {
        System.out.println();
        System.out.println("--- Nueva partida ---");
        String nombreBlancas = pedirNombre("Jugador con las blancas: ", null);
        String nombreNegras = pedirNombre("Jugador con las negras: ", nombreBlancas);

        Jugador blancas = new Jugador(nombreBlancas, Color.BLANCO);
        Jugador negras = new Jugador(nombreNegras, Color.NEGRO);
        Partida partida = new Partida(blancas, negras);
        jugar(partida);
    }

    // pide un nombre hasta que sea valido (y distinto al del otro jugador)
    private String pedirNombre(String mensaje, String nombreOtro) {
        while (true) {
            System.out.print(mensaje);
            String nombre = teclado.nextLine().trim();
            String error = Jugador.validarNombre(nombre);
            if (error != null) {
                System.out.println(error);
            } else if (nombreOtro != null && nombre.equalsIgnoreCase(nombreOtro)) {
                System.out.println("Los dos jugadores no pueden tener el mismo nombre.");
            } else {
                return nombre;
            }
        }
    }

    // ciclo principal de la partida
    private void jugar(Partida partida) {
        mostrarAyuda();
        boolean continuar = true;

        while (continuar && !partida.isTerminada()) {
            dibujarTablero(partida);
            System.out.print("Turno de " + partida.getJugadorEnTurno() + " > ");
            String linea = teclado.nextLine().trim().toLowerCase();

            if (linea.equals("salir")) {
                continuar = false;
            } else if (linea.equals("ayuda")) {
                mostrarAyuda();
            } else if (linea.equals("historial")) {
                mostrarHistorial(partida);
            } else if (linea.startsWith("ver ")) {
                verJugadas(partida, linea.substring(4).trim());
            } else {
                hacerJugada(partida, linea);
            }
        }

        if (partida.isTerminada()) {
            dibujarTablero(partida);
            mostrarResultado(partida);
        }
    }

    // intenta hacer una jugada escrita como "e2 e4"
    private void hacerJugada(Partida partida, String linea) {
        String[] partes = linea.split("\\s+");
        if (partes.length != 2) {
            System.out.println("No entendi. Escribe por ejemplo: e2 e4  (o 'ayuda')");
            return;
        }
        Posicion desde = Posicion.desdeTexto(partes[0]);
        Posicion hasta = Posicion.desdeTexto(partes[1]);
        if (desde == null || hasta == null) {
            System.out.println("Casilla no valida. Usa letras de la a a la h y numeros del 1 al 8.");
            return;
        }
        try {
            partida.mover(desde, hasta);
        } catch (MovimientoIlegalException e) {
            System.out.println("Jugada no permitida: " + e.getMessage());
        }
    }

    // muestra las casillas a las que puede ir la pieza
    private void verJugadas(Partida partida, String texto) {
        Posicion casilla = Posicion.desdeTexto(texto);
        if (casilla == null) {
            System.out.println("Casilla no valida. Ejemplo: ver e2");
            return;
        }
        Pieza pieza = partida.getTablero().get(casilla);
        if (pieza == null) {
            System.out.println("No hay ninguna pieza en " + casilla + ".");
            return;
        }
        ArrayList<Posicion> jugadas = partida.jugadasDe(casilla);
        if (jugadas.isEmpty()) {
            System.out.println(pieza + " en " + casilla + " no tiene jugadas disponibles ahora.");
            return;
        }
        System.out.print(pieza + " en " + casilla + " puede ir a:");
        for (int i = 0; i < jugadas.size(); i++) {
            System.out.print(" " + jugadas.get(i));
        }
        System.out.println();
    }

    private void mostrarHistorial(Partida partida) {
        ArrayList<String> historial = partida.getHistorial();
        if (historial.isEmpty()) {
            System.out.println("Todavia no se ha jugado nada.");
            return;
        }
        for (int i = 0; i < historial.size(); i++) {
            if (i % 2 == 0) {
                System.out.print((i / 2 + 1) + ". " + historial.get(i));
            } else {
                System.out.println("   " + historial.get(i));
            }
        }
        if (historial.size() % 2 == 1) {
            System.out.println();
        }
    }

    // dibuja el tablero con letras: mayusculas = blancas, minusculas = negras
    private void dibujarTablero(Partida partida) {
        System.out.println();
        System.out.println("    a b c d e f g h");
        System.out.println("   -----------------");
        for (int fila = 0; fila < 8; fila++) {
            System.out.print((8 - fila) + " | ");
            for (int col = 0; col < 8; col++) {
                Pieza pieza = partida.getTablero().get(new Posicion(fila, col));
                if (pieza == null) {
                    System.out.print(". ");
                } else if (pieza.getColor() == Color.BLANCO) {
                    System.out.print(Character.toUpperCase(pieza.getLetra()) + " ");
                } else {
                    System.out.print(Character.toLowerCase(pieza.getLetra()) + " ");
                }
            }
            System.out.println("| " + (8 - fila));
        }
        System.out.println("   -----------------");
        System.out.println("    a b c d e f g h");
    }

    private void mostrarResultado(Partida partida) {
        System.out.println();
        System.out.println("FIN DE LA PARTIDA: se capturo al rey. Gana " + partida.getGanador());
        System.out.println("Total de jugadas: " + partida.getHistorial().size());
    }
}
