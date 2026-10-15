package juegos;

import model.Jugador;
import model.Estadisticas;
import utils.InputHelper;
import java.util.Scanner;

public class Galaga extends Juego {
    private final int FILAS = 6;
    private final int COLUMNAS = 7;
    private String[][] tablero;
    private int posicionJugador;

    public Galaga(Jugador jugador) {
        super(jugador);
        this.tablero = new String[FILAS][COLUMNAS];
        this.posicionJugador = 3;
    }

    @Override
    public String getNombreJuego() { return "Galaga Express"; }

    @Override
    public void start(Scanner scanner) {
        System.out.println("\n[ GALAGA ] Bienvenido a Galaga Express, " + jugador.getNombre() + "!");
        inicializarEnemigos();

        boolean jugando = true;
        while (jugando) {
            mostrarTablero();
            System.out.println("Puntos acumulados: " + jugador.getPuntos());
            System.out.println("Movimientos: [A] Izquierda | [D] Derecha | [S] Mantenerse | [R] Retirarse");
            String mov = InputHelper.leerTexto(scanner, "Elija su opción: ").toUpperCase();

            if (mov.equals("R")) {
                jugando = false;
                break;
            }

            if (mov.equals("A") && posicionJugador > 0) posicionJugador--;
            else if (mov.equals("D") && posicionJugador < COLUMNAS - 1) posicionJugador++;

            String disparo = InputHelper.leerTexto(scanner, "¿Desea disparar en este turno? (S/N): ").toUpperCase();
            if (disparo.equals("S")) {
                procesarDisparo();
            }

            if (!hayEnemigos()) {
                mostrarTablero();
                System.out.println("\n[!] ¡Sector limpio! Generando nueva oleada...");
                InputHelper.pausa(1000);
                inicializarEnemigos();
            }
        }

        System.out.println("\nJuego terminado. Puntuación final: " + jugador.getPuntos() + " pts");
        guardarEstadisticas();
    }

    private void inicializarEnemigos() {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                tablero[f][c] = "   "; 
            }
        }
        for (int c = 1; c < COLUMNAS - 1; c++) {
            tablero[0][c] = " H ";
            tablero[1][c] = " H ";
        }
    }

    private void mostrarTablero() {
        System.out.println("\n".repeat(15));
        System.out.println("===============================");
        for (int f = 0; f < FILAS - 1; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                System.out.print(tablero[f][c] + " ");
            }
            System.out.println();
        }
        for (int c = 0; c < COLUMNAS; c++) {
            if (c == posicionJugador) System.out.print(" A  ");
            else System.out.print("    ");
        }
        System.out.println("\n===============================");
    }

    private void procesarDisparo() {
        for (int f = FILAS - 2; f >= 0; f--) {
            if (!tablero[f][posicionJugador].trim().equals("")) {
                animarExplosion(f, posicionJugador);
                jugador.sumarPuntos(100);
                return;
            } else {
                tablero[f][posicionJugador] = " . ";
                mostrarTablero();
                InputHelper.pausa(100);
                tablero[f][posicionJugador] = "   ";
            }
        }
        mostrarTablero();
        System.out.println("[-] El disparo se perdió en el vacío.");
    }

    private void animarExplosion(int f, int c) {
        tablero[f][c] = " X ";
        mostrarTablero();
        InputHelper.pausa(250);
        tablero[f][c] = "...";
        mostrarTablero();
        InputHelper.pausa(250);
        tablero[f][c] = "   ";
        mostrarTablero();
    }

    private boolean hayEnemigos() {
        for (int f = 0; f < FILAS - 1; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (tablero[f][c].trim().equals("H")) return true;
            }
        }
        return false;
    }

    @Override
    public void guardarEstadisticas() {
        Estadisticas.registrar(getNombreJuego(), jugador.getNombre(), jugador.getPuntos());
    }
}