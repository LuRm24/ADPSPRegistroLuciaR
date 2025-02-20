package org.example.adpspregistroluciar.interfaces;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Scanner;

public class CriptoMenu {

    public static String algoritmo = "AES";

     static void criptoMenu() {

        Scanner sc = new Scanner(System.in);
        int opcion;
        do

        {
            System.out.println("--- MENÚ CRIPTOGRAFÍA ---");
            System.out.println("1. Crear clave simétrica");
            System.out.println("2. Cifrar mensaje con clave simétrica");
            System.out.println("3. Descifrar mensaje con clave simétrica");
            System.out.println("4. Verificar firma digital");
            System.out.println("5. Cifrar mensaje con clave asimétrica");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    crearClaveSimetrica();
                    break;
                case 2:
                    cifrarMensajeSimetrica();
                    break;
                case 3:
                    descifarMensaje();
                    break;
                case 4:
                    verificarFirma();
                    break;
                case 5:
                    cifrarMensajeAsimetrica();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while(opcion !=0);
    }

    public static void crearClaveSimetrica() {

        String filePath = "./Files/claveSimetrica.key";
        File file = new File(filePath);

        // Si el archivo existe, preguntar si quiere reemplazarlo
        if (file.exists()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("El archivo claveSimetrica.key ya existe. ¿Desea reemplazarlo? (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (!respuesta.equals("S")) {
                System.out.println("Se ha mantenido la clave existente.");
                return; // Salir del programa sin reemplazar la clave
            }
        }

        try (ObjectOutputStream oosKey = new ObjectOutputStream(new FileOutputStream(filePath))) {

            KeyGenerator keygen = KeyGenerator.getInstance(algoritmo);
            SecretKey key = keygen.generateKey();
            oosKey.write(key.getEncoded());
            System.out.println("OK: Clave AES generada y guardada en " + filePath);

        } catch (NoSuchAlgorithmException | FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //NECESITO LA CLAVE SIMETRICA Y EL MENSAJE
    public static void cifrarMensajeSimetrica() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Escribe el mensaje a cifrar:");
        String mensaje = sc.nextLine();

        File clavePublicaFile = new File("./Files/clavePublicaSete.key");
        if (!clavePublicaFile.exists()) {
            System.out.println("Clave pública no encontrada. Volviendo al menú principal...");
            criptoMenu();
            return;
        }

        try (ObjectOutputStream oosMensaje = new ObjectOutputStream(new FileOutputStream("./Files/mensajeCifrado.bin"));
             ObjectInputStream oisClaveSimetrica = new ObjectInputStream(new FileInputStream("./Files/claveSimetrica.key") {

             })) {

            SecretKey secretKey = (SecretKey) oisClaveSimetrica.readObject();
            //Cifro el mensaje
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cifrado = cipher.doFinal(mensaje.getBytes());

            // Guardar el mensaje cifrado en el archivo
            oosMensaje.write(cifrado);

            System.out.println("Mensaje cifrado y guardado en ./Files/mensajeCifrado.bin");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Necesitamos la clave simetrica y el mensaje
    public static void descifarMensaje() {

        String fileMensajeCifrado = "./Files/mensajeCifrado.bin";
        String fileClave = "./Files/claveSimetrica.key";

        File claveFile = new File(fileClave);
        File mensajeFile = new File(fileMensajeCifrado);

        if (!claveFile.exists() && !mensajeFile.exists()) {
            System.out.println("Error: No se encontraron los archivos clave y mensaje cifrado. Cifra un mensaje primero.");
            return;
        } else if (!claveFile.exists()) {
            System.out.println("Error: No se encontró el archivo de clave AES. Cifra un mensaje primero.");
            return;
        } else if (!mensajeFile.exists()) {
            System.out.println("Error: No se encontró el archivo cifrado. Cifra un mensaje primero.");
            return;
        }

        try (ObjectInputStream oisClave = new ObjectInputStream(new FileInputStream("./Files/claveSimetrica.key"));
             ObjectInputStream oisMensaje = new ObjectInputStream(new FileInputStream("./Files/mensajeCifrado.bin"))) {
            //leemos la clave

            //claveSimetrica.key contiene la clave secreta (SecretKey)
            SecretKey key = (SecretKey) oisClave.readObject();
            //Leer el mensaje cifrado
            byte[] mensajeCifrado = oisMensaje.readAllBytes();
            //Descifro el mensaje
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] Mensajedescifrado = cipher.doFinal(mensajeCifrado);
            //imprimo el mensaje
            System.out.println(new String(Mensajedescifrado));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verificarFirma() {

        //Tenemos que leer la firma y el documento
        Scanner sc = new Scanner(System.in);
        System.out.println("Dime el documento quieres comprobar:");


        String firmaPath = "./Files/firma.bin"; // Archivo de la firma
        String clavePublicaPath = "./Files/clavePublicaSete.key"; // Clave pública
        String documento = sc.nextLine();

        //Verificar si los archivos existen
        File doc = new File(documento);
        File firmaFile = new File(firmaPath);
        File claveFile = new File(clavePublicaPath);

        if (!doc.exists()) {
            System.out.println("Error: El archivo del documento no existe.");
            return;
        }
        if (!firmaFile.exists()) {
            System.out.println("Error: No se encontró la firma digital.");
            return;
        }
        if (!claveFile.exists()) {
            System.out.println("Error: No se encontró la clave pública.");
            return;
        }


        try (ObjectInputStream oisFirma = new ObjectInputStream(new FileInputStream("./Files/firma.bin"));
             ObjectInputStream oisClaveSete = new ObjectInputStream(new FileInputStream("./Files/clavePublicaSete.key"))) {
            //1. Leer la firma
            byte[] firma = (byte[]) oisFirma.readObject();

            //2. Leo la clave pública
            PublicKey publicKey = (PublicKey) oisClaveSete.readObject();

            //3 Leer el contenido del documento a verificar
            byte[] documentoBytes = Files.readAllBytes(Paths.get(documento));

            //4. Comprobar si ese mensaje corresponde a la firma
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(documentoBytes);

            if (signature.verify(firma)) {
                System.out.println("Sí está firmado");
            } else {
                System.out.println("No está firmado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        }
        public static void cifrarMensajeAsimetrica(){

            Scanner sc = new Scanner(System.in);
            System.out.print("Escribe el mensaje a cifrar:");
            String mensaje = sc.nextLine();

            File clavePublicaFile = new File("./Files/clavePublicaSete.key");
            if (!clavePublicaFile.exists()) {
                System.out.println("Clave pública no encontrada. Volviendo al menú principal...");
                // menuPrincipal();
                return;
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./Files/clavePublicaSete.key"));
                 ObjectOutputStream oosCifrado = new ObjectOutputStream(new FileOutputStream("./Files/LuciaR.bin"));) {

                PublicKey pk = (PublicKey)ois.readObject();
                Cipher cipher = Cipher.getInstance(pk.getAlgorithm());
                cipher.init(Cipher.ENCRYPT_MODE, pk);
                byte[] cifrado = cipher.doFinal(mensaje.getBytes());
                oosCifrado.writeObject(cifrado);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

