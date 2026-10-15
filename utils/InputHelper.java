package utils;

import java.util.Scanner;

public class InputHelper {

    public static int leerEntero(Scanner scanner, String mensaje) {
        int numero = -1;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                valido = true;
            } else {
                System.out.println("⚠️ Entrada inválida. Ingrese un número entero.");
            }
            scanner.nextLine();
        }
        return numero;
    }

    public static String leerTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public static void pausa(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}