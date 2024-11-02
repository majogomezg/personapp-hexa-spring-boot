package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.EstudioInputAdapterCli;
import co.edu.javeriana.as.personapp.terminal.adapter.TelefonoInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class EstudioMenu {
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

    public void iniciarMenu(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
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
                        estudioInputAdapterCli.setEstudioOutputPortInjection("MARIA");
                        menuOpciones(estudioInputAdapterCli, keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        estudioInputAdapterCli.setEstudioOutputPortInjection("MONGO");
                        menuOpciones(estudioInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
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
                        estudioInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR:
                        crearEstudio(estudioInputAdapterCli, keyboard);
                        break;
                    case OPCION_BUSCAR:
                        buscarEstudio(estudioInputAdapterCli, keyboard);
                        break;
                    case OPCION_ACTUALIZAR:
                        actualizarEstudio(estudioInputAdapterCli, keyboard);
                        break;
                    case OPCION_ELIMINAR:
                        eliminarEstudio(estudioInputAdapterCli, keyboard);
                        break;
                    case OPCION_CONTAR:
                        estudioInputAdapterCli.count();
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
        System.out.println(OPCION_VER_TODO + " para ver todos los estudios");
        System.out.println(OPCION_CREAR + " para crear un estudio");
        System.out.println(OPCION_BUSCAR + " para buscar un estudio");
        System.out.println(OPCION_ACTUALIZAR + " para actualizar un estudio");
        System.out.println(OPCION_ELIMINAR + " para eliminar un estudio");
        System.out.println(OPCION_CONTAR + " para contar los estudios en la bd");
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

    private void crearEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el id de la persona: ");
        Integer persona = keyboard.nextInt();
        System.out.print("Ingrese el id de la profesion: ");
        Integer profesion = keyboard.nextInt();
        System.out.print("Ingrese la fecha de graduacion (formato YYYY-MM-DD): ");
        String fechaGraduacion = keyboard.next();
        System.out.print("Ingrese el nombre de la universidad: ");
        keyboard.nextLine(); // Limpiar el buffer
        String nombreUniversidad = keyboard.nextLine();
        estudioInputAdapterCli.create(persona, profesion, fechaGraduacion, nombreUniversidad);
    }

    private void buscarEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
        System.out.print("Ingrese el id de la persona: ");
        Integer persona = keyboard.nextInt();
        System.out.print("Ingrese el id de la profesion: ");
        Integer profesion = keyboard.nextInt();
        estudioInputAdapterCli.findOne(persona, profesion);
    }

    private void actualizarEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el id de la persona: ");
        Integer persona = keyboard.nextInt();
        System.out.print("Ingrese el id de la profesion: ");
        Integer profesion = keyboard.nextInt();
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese la fecha de graduacion (formato YYYY-MM-DD): ");
        String fechaGraduacion = keyboard.next();
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el nombre de la universidad: ");
        String nombreUniversidad = keyboard.nextLine();
        estudioInputAdapterCli.update(persona, profesion, fechaGraduacion, nombreUniversidad);
    }

    private void eliminarEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el id de la persona: ");
        Integer persona = keyboard.nextInt();
        System.out.print("Ingrese el id de la profesion: ");
        Integer profesion = keyboard.nextInt();
        estudioInputAdapterCli.delete(persona, profesion);
    }
}
