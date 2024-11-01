package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.ProfesionInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class ProfesionMenu {
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

    public void iniciarMenu(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
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
                        profesionInputAdapterCli.setProfesionOutputPortInjection("MARIA");
                        menuOpciones(profesionInputAdapterCli, keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        profesionInputAdapterCli.setProfesionOutputPortInjection("MONGO");
                        menuOpciones(profesionInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
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
                        profesionInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR:
                        crearProfesion(profesionInputAdapterCli, keyboard);
                        break;
                    case OPCION_BUSCAR:
                        buscarProfesion(profesionInputAdapterCli, keyboard);
                        break;
                    case OPCION_ACTUALIZAR:
                        actualizarProfesion(profesionInputAdapterCli, keyboard);
                        break;
                    case OPCION_ELIMINAR:
                        eliminarProfesion(profesionInputAdapterCli, keyboard);
                        break;
                    case OPCION_CONTAR:
                        profesionInputAdapterCli.count();
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
        System.out.println(OPCION_VER_TODO + " para ver todas las profesions");
        System.out.println(OPCION_CREAR + " para crear una profesion");
        System.out.println(OPCION_BUSCAR + " para buscar una profesion");
        System.out.println(OPCION_ACTUALIZAR + " para actualizar una profesion");
        System.out.println(OPCION_ELIMINAR + " para eliminar una profesion");
        System.out.println(OPCION_CONTAR + " para contar las profesions en la bd");
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

    private void crearProfesion(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el id de la profesion: ");
        int id = keyboard.nextInt();
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el nombre de la profesion: ");
        String nombre = keyboard.nextLine();
        System.out.print("Ingrese la descripcion de la profesion: ");
        String descripcion = keyboard.nextLine();
        profesionInputAdapterCli.create(id, nombre, descripcion);
    }

    private void buscarProfesion(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
        System.out.print("Ingrese el id de la profesion: ");
        int id = keyboard.nextInt();
        profesionInputAdapterCli.findOne(id);
    }

    private void actualizarProfesion(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el id de la profesion: ");
        int id = keyboard.nextInt();
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el nombre de la profesion: ");
        String nombre = keyboard.nextLine();
        System.out.print("Ingrese la descripcion de la profesion: ");
        String descripcion = keyboard.nextLine();
        profesionInputAdapterCli.edit(id, nombre, descripcion);
    }

    private void eliminarProfesion(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el id de la profesion: ");
        int id = keyboard.nextInt();
        keyboard.nextLine(); // Limpiar el buffer
        profesionInputAdapterCli.drop(id);
    }
}
