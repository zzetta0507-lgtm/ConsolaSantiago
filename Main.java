import model.Jugador;
import model.Estadisticas;
import utils.InputHelper;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("\n========= MENÚ PRINCIPAL =========");
            System.out.println("1. Lanzar Juego");
            System.out.println("2. Ver Estadísticas");
            System.out.println("3. Salir");

            opcion = InputHelper.leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    lanzarMenuDinamico(scanner);
                    break;
                case 2:
                    Estadisticas.mostrar();
                    break;
                case 3:
                    System.out.println("¡Gracias por jugar!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void lanzarMenuDinamico(Scanner scanner) {
        List<Class<?>> juegosInstalados = buscarJuegosEnCarpeta();

        if (juegosInstalados.isEmpty()) {
            System.out.println("\n[!] No se encontró ningún juego instalado en la carpeta 'juegos'.");
            return;
        }

        System.out.println("\n--- SELECCIONAR JUEGO ---");
        for (int i = 0; i < juegosInstalados.size(); i++) {
            try {
                Constructor<?> constructor = juegosInstalados.get(i).getConstructor(Jugador.class);
                juegos.Juego juegoTemp = (juegos.Juego) constructor.newInstance(new Jugador("Temp"));
                System.out.println((i + 1) + ". " + juegoTemp.getNombreJuego());
            } catch (Exception e) {
                System.out.println((i + 1) + ". " + juegosInstalados.get(i).getSimpleName());
            }
        }

        int seleccion = InputHelper.leerEntero(scanner, "\nElija un juego: ");

        if (seleccion < 1 || seleccion > juegosInstalados.size()) {
            System.out.println("Opción de juego inválida.");
            return;
        }

        String nombre = InputHelper.leerTexto(scanner, "Ingrese el nombre del jugador: ");
        Jugador jugadorNuevo = new Jugador(nombre);

        try {
            Constructor<?> constructorFinal = juegosInstalados.get(seleccion - 1).getConstructor(Jugador.class);
            juegos.Juego juegoSeleccionado = (juegos.Juego) constructorFinal.newInstance(jugadorNuevo);
            juegoSeleccionado.start(scanner);
        } catch (Exception e) {
            System.out.println("Hubo un error al intentar iniciar este juego.");
        }
    }

    private static List<Class<?>> buscarJuegosEnCarpeta() {
        List<Class<?>> lista = new ArrayList<>();
        
        // Busca dinámicamente la ruta donde Java compiló las clases, sin importar la terminal
        String rutaBase = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        // Limpiamos la ruta para evitar problemas con espacios en Windows (%20)
        rutaBase = rutaBase.replace("%20", " ");
        
        File carpetaJuegos = new File(rutaBase + "juegos");

        // Fallback por si acaso sigue sin encontrarla
        if (!carpetaJuegos.exists() || !carpetaJuegos.isDirectory()) {
            carpetaJuegos = new File("bin/juegos");
        }
        if (!carpetaJuegos.exists() || !carpetaJuegos.isDirectory()) {
            carpetaJuegos = new File("juegos");
        }

        if (carpetaJuegos.exists() && carpetaJuegos.isDirectory()) {
            for (File archivo : carpetaJuegos.listFiles()) {
                String nombreArchivo = archivo.getName();
                
                if (nombreArchivo.endsWith(".class") && !nombreArchivo.equals("Juego.class") && !nombreArchivo.equals("Jugable.class") && !nombreArchivo.contains("$")) {
                    try {
                        String nombreClase = "juegos." + nombreArchivo.replace(".class", "");
                        Class<?> clase = Class.forName(nombreClase);
                        
                        if (juegos.Juego.class.isAssignableFrom(clase)) {
                            lista.add(clase);
                        }
                    } catch (Exception e) {
                        // Ignorar clases inválidas
                    }
                }
            }
        }
        return lista;
    }
}