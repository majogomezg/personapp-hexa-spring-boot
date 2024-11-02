package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.TelefonoInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class TelefonoMenu {
    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;

    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_TODO = 1;
    private static final int OPCION_CREAR = 2;
    private static final int OPCION_BUSCAR = 3;
    private static final int OPCION_ACTUALIZAR = 4;
    private static final int OPCION_ELIMINAR = 5;
    private static final int OPCION_CONTAR = 6;

    public void iniciarMenu(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuMotorPersistencia();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MODULOS:
                        isValid = true;
                        break;
                    case PERSISTENCIA_MARIADB:
                        telefonoInputAdapterCli.setTelefonoOutputPortInjection("MARIA");
                        menuOpciones(telefonoInputAdapterCli, keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        telefonoInputAdapterCli.setTelefonoOutputPortInjection("MONGO");
                        menuOpciones(telefonoInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuOpciones();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
                        isValid = true;
                        break;
                    case OPCION_VER_TODO:
                        telefonoInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR:
                        crearTelefono(telefonoInputAdapterCli, keyboard);
                        break;
                    case OPCION_BUSCAR:
                        buscarTelefono(telefonoInputAdapterCli, keyboard);
                        break;
                    case OPCION_ACTUALIZAR:
                        actualizarTelefono(telefonoInputAdapterCli, keyboard);
                        break;
                    case OPCION_ELIMINAR:
                        eliminarTelefono(telefonoInputAdapterCli, keyboard);
                        break;
                    case OPCION_CONTAR:
                        telefonoInputAdapterCli.count();
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InputMismatchException e) {
                log.warn("Solo se permiten números.");
            }
        } while (!isValid);
    }

    private void mostrarMenuOpciones() {
        System.out.println("----------------------");
        System.out.println(OPCION_VER_TODO + " para ver todos los telefonos");
        System.out.println(OPCION_CREAR + " para crear un telefono");
        System.out.println(OPCION_BUSCAR + " para buscar un telefono");
        System.out.println(OPCION_ACTUALIZAR + " para actualizar un telefono");
        System.out.println(OPCION_ELIMINAR + " para eliminar un telefono");
        System.out.println(OPCION_CONTAR + " para contar los telefonos en la bd");
        System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
    }

    private void mostrarMenuMotorPersistencia() {
        System.out.println("----------------------");
        System.out.println(PERSISTENCIA_MARIADB + " para MariaDB");
        System.out.println(PERSISTENCIA_MONGODB + " para MongoDB");
        System.out.println(OPCION_REGRESAR_MODULOS + " para regresar");
    }

    private int leerOpcion(Scanner keyboard) {
        try {
            System.out.print("Ingrese una opción: ");
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            log.warn("Solo se permiten números.");
            return leerOpcion(keyboard);
        }
    }

    public void crearTelefono(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el número de teléfono: ");
        String numero = keyboard.nextLine();
        System.out.print("Ingrese la empresa: ");
        String empresa = keyboard.nextLine();
        System.out.print("Ingrese el número de cédula del propietario: ");
        Integer propietario = keyboard.nextInt();
        keyboard.nextLine(); // Limpiar el buffer
        telefonoInputAdapterCli.create(numero, empresa, propietario);
    }

    public void buscarTelefono(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el número de teléfono: ");
        String numero = keyboard.nextLine();
        telefonoInputAdapterCli.findOne(numero);
    }

    public void actualizarTelefono(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el número de teléfono: ");
        String numero = keyboard.nextLine();
        System.out.print("Ingrese la empresa: ");
        String empresa = keyboard.nextLine();
        System.out.print("Ingrese el número de cédula del propietario: ");
        Integer propietario = keyboard.nextInt();
        keyboard.nextLine(); // Limpiar el buffer
        telefonoInputAdapterCli.edit(numero, empresa, propietario);
    }

    public void eliminarTelefono(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el número de teléfono: ");
        String numero = keyboard.nextLine();
        telefonoInputAdapterCli.drop(numero);
    }
}
