package juegos;

import model.Jugador;
import model.Estadisticas;
import utils.InputHelper;
import java.util.Random;
import java.util.Scanner;

public class Tragaperras extends Juego {
    private String[] simbolos = {"7", "+", "*", "-", "ö", "@", "#"};

    public Tragaperras(Jugador jugador) {
        super(jugador);
    }

    @Override
    public String getNombreJuego() { return "Tragaperras (Slot Machine)"; }

    @Override
    public void start(Scanner scanner) {
        Random random = new Random();
        System.out.println("\n[ SLOT MACHINE ] Bienvenido, " + jugador.getNombre() + "!");

        while (jugador.getSaldo() > 0) {
            System.out.println("\nSaldo actual: $" + jugador.getSaldo());
            String opcion = InputHelper.leerTexto(scanner, "Presione [ENTER] para girar ($10) o 'R' para retirarse: ");

            if (opcion.equalsIgnoreCase("R")) {
                System.out.println("Te has retirado con $" + jugador.getSaldo());
                break;
            }

            if (!jugador.descontarSaldo(10)) {
                System.out.println("[X] No tienes suficiente dinero para girar.");
                break;
            }

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

            if (r1.equals(r2) && r2.equals(r3)) {
                double premio = r1.equals("7") ? 500 : 100;
                System.out.println("[!] TRES IGUALES! Ganaste $" + premio);
                jugador.sumarSaldo(premio);
            } else if (r1.equals(r2) || r2.equals(r3) || r1.equals(r3)) {
                System.out.println("[*] PAR! Ganaste $15");
                jugador.sumarSaldo(15);
            } else {
                System.out.println("[-] Sin suerte esta vez.");
            }
        }
        guardarEstadisticas();
    }

    @Override
    public void guardarEstadisticas() {
        Estadisticas.registrar(getNombreJuego(), jugador.getNombre(), jugador.getSaldo());
    }
}