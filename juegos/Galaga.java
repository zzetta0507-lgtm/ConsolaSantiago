package juegos;

import model.Jugador;
import model.Estadisticas;
import utils.InputHelper;
import java.util.Scanner;

public class Galaga extends Juego {
    private final int FILAS = 7;
    private final int COLUMNAS = 7;
    private int posJ1; 
    private int posJ2; 
    private boolean turnoJ1;
    private String nombreJ2;
    private int puntosJ2;

    public Galaga(Jugador jugador) {
        super(jugador);
        this.posJ1 = 3; 
        this.posJ2 = 3; 
        this.turnoJ1 = true; 
        this.puntosJ2 = 0;
    }

    @Override
    public String getNombreJuego() { 
        return "Galaga VS (2 Jugadores)"; 
    }

    @Override
    public void start(Scanner scanner) {
        System.out.println("\n[ GALAGA VS ] Jugador 1 (Abajo): " + jugador.getNombre());
        this.nombreJ2 = InputHelper.leerTexto(scanner, "Ingrese el nombre del Jugador 2 (Arriba): ");

        System.out.println("\nControles " + jugador.getNombre() + ": [A] Izquierda | [D] Derecha | [S] Mantener");
        System.out.println("Controles " + nombreJ2 + ": [J] Izquierda | [L] Derecha | [K] Mantener");
        InputHelper.leerTexto(scanner, "\nPresione ENTER para empezar la batalla...");

        boolean jugando = true;
        
        while (jugando) {
            mostrarTablero();
            String jugadorActual = turnoJ1 ? jugador.getNombre() : nombreJ2;
            System.out.println("\n--- Turno de " + jugadorActual + " ---");
            
            String mov = InputHelper.leerTexto(scanner, "Elija movimiento o [R] para retirarse: ").toUpperCase();

            if (mov.equals("R")) {
                System.out.println(jugadorActual + " se ha retirado de la batalla.");
                break;
            }

            moverJugador(mov);

            String disparo = InputHelper.leerTexto(scanner, "¿Desea disparar? (S/N): ").toUpperCase();
            if (disparo.equals("S")) {
                if (procesarDisparo()) {
                    break;
                }
            }

            turnoJ1 = !turnoJ1;
        }

        System.out.println("\nBatalla terminada.");
        System.out.println("Puntos de " + jugador.getNombre() + ": " + jugador.getPuntos() + " pts");
        System.out.println("Puntos de " + nombreJ2 + ": " + puntosJ2 + " pts");
        guardarEstadisticas();
    }

    private void moverJugador(String mov) {
        if (turnoJ1) {
            if (mov.equals("A") && posJ1 > 0) posJ1--;
            else if (mov.equals("D") && posJ1 < COLUMNAS - 1) posJ1++;
        } else {
            if (mov.equals("J") && posJ2 > 0) posJ2--;
            else if (mov.equals("L") && posJ2 < COLUMNAS - 1) posJ2++;
        }
    }

    private void mostrarTablero() {
        System.out.println("\n".repeat(15));
        System.out.println("===============================");
        
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (f == 0 && c == posJ2) {
                    System.out.print(" V  "); 
                } else if (f == FILAS - 1 && c == posJ1) {
                    System.out.print(" A  "); 
                } else {
                    System.out.print("    "); 
                }
            }
            System.out.println();
        }
        System.out.println("===============================");
    }

    private void dibujarLáser(int filaActual, int columna, String simboloLaser) {
        System.out.println("\n".repeat(15));
        System.out.println("===============================");
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (f == 0 && c == posJ2) {
                    System.out.print(" V  ");
                } else if (f == FILAS - 1 && c == posJ1) {
                    System.out.print(" A  ");
                } else if (f == filaActual && c == columna) {
                    System.out.print(simboloLaser); 
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
        System.out.println("===============================");
        InputHelper.pausa(100); 
    }

    private boolean procesarDisparo() {
        if (turnoJ1) {
            for (int f = FILAS - 2; f >= 0; f--) {
                dibujarLáser(f, posJ1, " |  ");
                
                if (f == 0 && posJ1 == posJ2) {
                    animarExplosion(0, posJ2);
                    System.out.println("¡Impacto directo! ¡" + jugador.getNombre() + " GANA LA BATALLA! (+200 pts)");
                    jugador.sumarPuntos(200);
                    return true;
                }
            }
        } else {
            for (int f = 1; f < FILAS; f++) {
                dibujarLáser(f, posJ2, " |  ");
                
                if (f == FILAS - 1 && posJ2 == posJ1) {
                    animarExplosion(FILAS - 1, posJ1);
                    System.out.println("¡Impacto directo! ¡" + nombreJ2 + " GANA LA BATALLA! (+200 pts)");
                    puntosJ2 += 200;
                    return true;
                }
            }
        }
        
        System.out.println("[-] El disparo falló.");
        InputHelper.pausa(600);
        return false;
    }

    private void animarExplosion(int filaDestruida, int columna) {
        String[] estados = {" X  ", "... ", "    "};
        
        for (String estado : estados) {
            System.out.println("\n".repeat(15));
            System.out.println("===============================");
            for (int f = 0; f < FILAS; f++) {
                for (int c = 0; c < COLUMNAS; c++) {
                    if (f == filaDestruida && c == columna) {
                        System.out.print(estado);
                    } else if (f == 0 && c == posJ2 && filaDestruida != 0) {
                        System.out.print(" V  ");
                    } else if (f == FILAS - 1 && c == posJ1 && filaDestruida != FILAS - 1) {
                        System.out.print(" A  ");
                    } else {
                        System.out.print("    ");
                    }
                }
                System.out.println();
            }
            System.out.println("===============================");
            InputHelper.pausa(300);
        }
    }

    @Override
    public void guardarEstadisticas() {
        Estadisticas.registrar(getNombreJuego(), jugador.getNombre(), jugador.getPuntos());
        if (nombreJ2 != null && !nombreJ2.trim().isEmpty()) {
            Estadisticas.registrar(getNombreJuego(), nombreJ2, puntosJ2);
        }
    }
}