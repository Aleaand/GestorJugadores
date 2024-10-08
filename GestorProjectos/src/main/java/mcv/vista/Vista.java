/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mcv.vista;

import java.util.Scanner;
import mvc.modelo.Jugador;

/**
 *
 * @author Alejandra
 */
public class Vista {

    private Scanner scanner;

    public Vista() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        System.out.println("\nMenú Principal:");
        System.out.println("1. Alta de jugadores");
        System.out.println("2. Baja de jugadores");
        System.out.println("3. Modificación de jugadores");
        System.out.println("4. Listado por código (ID)");
        System.out.println("5. Listado general");
        System.out.println("6. Salir");
        System.out.print("Elija una opción: ");
    }

    public void mostrarSubmenuConfiguracion() {
        System.out.println("Configuración de almacenamiento:");
        System.out.println("1. Fichero secuencial de texto");
        System.out.println("2. Fichero secuencial binario");
        System.out.println("3. Fichero de objetos binario");
        System.out.println("4. Fichero de acceso aleatorio binario");
        System.out.println("5. Fichero de texto XML");
        System.out.print("Seleccione el tipo de almacenamiento: ");
    }

    public String leerCadena() {
        return scanner.next();
    }

    public Jugador leerDatosJugadorSinId() {
        System.out.print("Ingrese nick del jugador: ");
        String nick = scanner.next();
        System.out.print("Ingrese experiencia del jugador: ");
        int experiencia = scanner.nextInt();
        System.out.print("Ingrese nivel de vida del jugador: ");
        int lifeLevel = scanner.nextInt();
        System.out.print("Ingrese monedas del jugador: ");
        int monedas = scanner.nextInt();

        return new Jugador(0, nick, experiencia, lifeLevel, monedas); // El ID se asigna después
    }

    public int solicitarIdJugador() {
        System.out.print("Ingrese ID del jugador: ");
        return scanner.nextInt();
    }

    public boolean preguntarModificar(String atributo) {
        System.out.print("¿Desea modificar el " + atributo + "? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s");
    }

    public String leerNuevoNick() {
        System.out.print("Ingrese el nuevo nick: ");
        return scanner.nextLine().trim();
    }

    public int leerNuevaExperiencia() {
        System.out.print("Ingrese la nueva experiencia: ");
        return leerEntero();
    }

    public int leerNuevoNivelVida() {
        System.out.print("Ingrese el nuevo nivel de vida: ");
        return leerEntero();
    }

    public int leerNuevasMonedas() {
        System.out.print("Ingrese la nueva cantidad de monedas: ");
        return leerEntero();
    }

    public int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Por favor, ingrese un número válido: ");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarJugador(Jugador jugador) {
        System.out.println(jugador.toString());
    }
}
