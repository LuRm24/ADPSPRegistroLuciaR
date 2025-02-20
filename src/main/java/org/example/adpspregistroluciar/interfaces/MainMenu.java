package org.example.adpspregistroluciar.interfaces;



import java.util.Scanner;

import static org.example.adpspregistroluciar.interfaces.DataMenu.dataMenu;

public class MainMenu {
    static Scanner sc = new Scanner(System.in);

    static public void mainMenu() {

        int opcion;

        do {
            System.out.println("--- MENÚ PRINCIPAL ---");
            System.out.println("1. Registrar a una persona");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Ver datos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarPersona();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    verDatos();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void registrarPersona() {

    }

    private static void iniciarSesion() {

    }

    private static void verDatos() {
        dataMenu();
    }



}

