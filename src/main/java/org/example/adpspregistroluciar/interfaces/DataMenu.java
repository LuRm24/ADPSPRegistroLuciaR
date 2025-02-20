package org.example.adpspregistroluciar.interfaces;

import java.util.Scanner;

public class DataMenu {

    static void dataMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("--- MENÚ BASE DE DATOS ---");
            System.out.println("1. Mostrar todas las personas");
            System.out.println("2. Mostrar personas entre edades");
            System.out.println("3. Mostrar personas con email");
            System.out.println("4. Mostrar personas por código postal");
            System.out.println("5. Eliminar persona");
            System.out.println("6. Actualizar contraseña");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    mostrarPersonas();
                    break;
                case 2:
                    mostarPersonasEntreEdades();
                    break;
                case 3:
                    mostrarPersonasConEmail();
                    break;
                case 4:
                    mostrarPersonasPorCodigoPostal();
                    break;
                case 5:
                    eliminarPersona();
                    break;
                case 6:
                    actualizarPassword();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 0);

    }
    private static void mostrarPersonas() {}
    private static void mostarPersonasEntreEdades() {}
    private static void mostrarPersonasConEmail() {}
    private static void mostrarPersonasPorCodigoPostal() {}
    private static void eliminarPersona() {}
    private static void actualizarPassword() {}

}
