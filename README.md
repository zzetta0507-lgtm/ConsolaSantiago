# Consola Dinámica de Juegos en Java

Una plataforma de juegos por consola desarrollada en Java que utiliza carga dinámica de clases (Reflection) para integrar nuevos juegos automáticamente sin modificar el código base. El sistema gestiona billeteras virtuales, puntajes y un registro persistente en memoria del Top 3 de mejores jugadores para cada juego.

## 🚀 Cómo Ejecutar el Proyecto

⚠️ IMPORTANTE: Se recomienda NO usar comandos en la terminal para compilar o ejecutar el proyecto, ya que esto puede generar interferencias o errores relacionados con las rutas de los archivos. 

Es mejor y se recomienda usar el botón de "Run" (Ejecutar) o ejecutar directamente la clase `Main` desde tu editor de código (como VS Code, Eclipse o IntelliJ) una vez que tengas la estructura del proyecto completamente hecha.

## 🏗️ Arquitectura y Documentación de Interfaces

El proyecto está diseñado bajo los pilares de la Programación Orientada a Objetos (POO), haciendo un uso intensivo de interfaces, clases abstractas y polimorfismo para garantizar escalabilidad.

### 1. Interfaz `Jugable` (`juegos/Jugable.java`)
Define el contrato principal que cualquier componente ejecutable debe cumplir para ser considerado un juego dentro de la consola.
* Método: `void start(Scanner scanner)`
* Propósito: Obliga a las clases que la implementan a tener un punto de entrada estandarizado. El menú principal invoca este método de forma polimórfica sin necesitar conocer los detalles internos de cada juego.

### 2. Clase Abstracta `Juego` (`juegos/Juego.java`)
Actúa como plantilla base para todos los juegos del sistema. Implementa la interfaz `Jugable`.
* Atributos: Mantiene una referencia `protected` al objeto `Jugador` actual.
* Métodos Abstractos: 
  * `String getNombreJuego()`: Utilizado por el motor de Reflection para pintar dinámicamente el nombre del juego en el menú principal.
  * `void guardarEstadisticas()`: Obliga a cada juego a definir cómo envía sus resultados (puntos o dinero) al gestor global de estadísticas.

### 3. Interfaz `Comparable<T>` (`model/RegistroPuntaje.java`)
Implementación de la interfaz nativa de Java para dotar a los registros de puntuación de un orden natural.
* Método: `int compareTo(RegistroPuntaje otro)`
* Propósito: Permite utilizar los operadores lógicos (`<`, `>`, `==`) de forma interna en `Collections.sort()` para ordenar automáticamente los puntajes de mayor a menor y mantener estrictamente el Top 3.

## 📂 Estructura del Proyecto

```text
Consola/
├── bin/                        # Archivos compilados (.class)
├── src/                        # (Opcional) Código fuente si se usa estructura de paquetes completa
├── juegos/                     # Módulo de juegos (Plug & Play)
│   ├── Jugable.java            # Interfaz base
│   ├── Juego.java              # Clase abstracta principal
│   ├── Tragaperras.java        # Juego 1: Máquina tragamonedas
│   ├── Galaga.java             # Juego 2: Shooter espacial en matriz
│   └── Pong.java               # Juego 3: Ping-pong interactivo
├── model/                      # Módulo de datos y lógica de negocio
│   ├── Jugador.java            # Entidad de usuario (Nombre, saldo, puntos)
│   ├── Estadisticas.java       # Gestor del ranking global (Top 3)
│   └── RegistroPuntaje.java    # Modelo ordenable (Implementa Comparable)
├── utils/                      # Módulo de herramientas
│   └── InputHelper.java        # Validaciones de Scanner y manejo de Threads
└── Main.java                   # Punto de entrada y motor de carga dinámica

```
🔌 Cómo añadir un juego nuevo (Plug & Play)
Gracias a la implementación de Java Reflection en la clase Main, agregar un nuevo juego no requiere tocar el código del menú.

Crea un nuevo archivo .java dentro de la carpeta juegos/.

Asegúrate de que la clase herede de Juego: public class MiNuevoJuego extends Juego.

Implementa el constructor que reciba al Jugador llamando a super(jugador);.

Sobrescribe los métodos start(Scanner sc), getNombreJuego() y guardarEstadisticas().

Ejecuta el proyecto. El menú detectará automáticamente el archivo .class (o .java compilado) y lo agregará como una nueva opción ejecutable.

Autor:

Santiago Alonzo Vargas Céspedes
