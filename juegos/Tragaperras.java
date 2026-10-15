package juegos;

import model.Jugador;
import model.Estadisticas;
import utils.InputHelper;
import java.util.Random;
import java.util.Scanner;

public class Tragaperras extends Juego {
    private String[] simbolos = {"7", "+", "*", "-", "ö", "@", "#"};
    private String nombreJ2;
    private double saldoJ2;
    private boolean j1Activo;
    private boolean j2Activo;
    private boolean turnoJ1;

    public Tragaperras(Jugador jugador) {
        super(jugador);
        this.saldoJ2 = 100.0; // Saldo inicial para el Jugador 2
        this.j1Activo = true;
        this.j2Activo = true;
        this.turnoJ1 = true;
    }

    @Override
    public String getNombreJuego() { 
        return "Tragaperras VS (2 Jugadores)"; 
    }

    @Override
    public void start(Scanner scanner) {
        Random random = new Random();
        System.out.println("\n[ SLOT MACHINE VS ] Jugador 1: " + jugador.getNombre());
        this.nombreJ2 = InputHelper.leerTexto(scanner, "Ingrese el nombre del Jugador 2: ");

        System.out.println("\n¡Bienvenidos al casino, " + jugador.getNombre() + " y " + nombreJ2 + "!");
        System.out.println("Cada uno empieza con $100. El turno se alterna después de cada giro.");

        while (j1Activo || j2Activo) {
            // Si le toca a un jugador que ya se retiró, saltar su turno
            if (turnoJ1 && !j1Activo) { 
                turnoJ1 = false; 
                continue; 
            }
            if (!turnoJ1 && !j2Activo) { 
                turnoJ1 = true; 
                continue; 
            }

            String nombreActual = turnoJ1 ? jugador.getNombre() : nombreJ2;
            double saldoActual = turnoJ1 ? jugador.getSaldo() : saldoJ2;

            System.out.println("\n--- Turno de " + nombreActual + " ---");
            System.out.println("Saldo actual: $" + saldoActual);
            String opcion = InputHelper.leerTexto(scanner, "Presione [ENTER] para girar ($10) o escriba 'R' para retirarse: ");

            if (opcion.equalsIgnoreCase("R")) {
                System.out.println(nombreActual + " se ha retirado de la mesa con $" + saldoActual);
                if (turnoJ1) j1Activo = false;
                else j2Activo = false;
            } else {
                if (saldoActual < 10) {
                    System.out.println("[X] No tienes suficiente dinero para girar. Te retiras automáticamente.");
                    if (turnoJ1) j1Activo = false;
                    else j2Activo = false;
                } else {
                    // Descontar saldo
                    if (turnoJ1) jugador.descontarSaldo(10);
                    else saldoJ2 -= 10;

                    System.out.println("\n--- Girando carretes ---");
                    for (int i = 0; i < 8; i++) {
                        String s1 = simbolos[random.nextInt(simbolos.length)];
                        String s2 = simbolos[random.nextInt(simbolos.length)];
                        String s3 = simbolos[random.nextInt(simbolos.length)];
                        System.out.print("\r   [ " + s1 + " | " + s2 + " | " + s3 + " ] ");
                        InputHelper.pausa(150);
                    }

                    String r1 = simbolos[random.nextInt(simbolos.length)];
                    String r2 = simbolos[random.nextInt(simbolos.length)];
                    String r3 = simbolos[random.nextInt(simbolos.length)];

                    System.out.println("\n\n+---------------+");
                    System.out.println("|  [ " + r1 + " | " + r2 + " | " + r3 + " ]  |");
                    System.out.println("+---------------+");

                    double premio = 0;
                    if (r1.equals(r2) && r2.equals(r3)) {
                        premio = r1.equals("7") ? 500 : 100;
                        System.out.println("[!] ¡TRES IGUALES! " + nombreActual + " gana $" + premio);
                    } else if (r1.equals(r2) || r2.equals(r3) || r1.equals(r3)) {
                        premio = 15;
                        System.out.println("[*] ¡PAR! " + nombreActual + " gana $15");
                    } else {
                        System.out.println("[-] Sin suerte esta vez.");
                    }

                    // Sumar premio si hubo ganancia
                    if (premio > 0) {
                        if (turnoJ1) jugador.sumarSaldo(premio);
                        else saldoJ2 += premio;
                    }
                }
            }
            
            // Cambiar turno al final
            turnoJ1 = !turnoJ1;
        }

        System.out.println("\nJuego terminado para ambos jugadores.");
        System.out.println("Saldo final de " + jugador.getNombre() + ": $" + jugador.getSaldo());
        System.out.println("Saldo final de " + nombreJ2 + ": $" + saldoJ2);
        guardarEstadisticas();
    }

    @Override
    public void guardarEstadisticas() {
        // Registra al Jugador 1
        Estadisticas.registrar(getNombreJuego(), jugador.getNombre(), jugador.getSaldo());
        // Registra al Jugador 2 (siempre y cuando se haya ingresado un nombre)
        if (nombreJ2 != null && !nombreJ2.trim().isEmpty()) {
            Estadisticas.registrar(getNombreJuego(), nombreJ2, saldoJ2);
        }
    }
}