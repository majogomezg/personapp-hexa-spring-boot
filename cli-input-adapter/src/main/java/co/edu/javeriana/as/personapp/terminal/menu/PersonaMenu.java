package co.edu.javeriana.as.personapp.terminal.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.PersonaInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonaMenu {

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

	public void iniciarMenu(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
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
						personaInputAdapterCli.setPersonOutputPortInjection("MARIA");
						menuOpciones(personaInputAdapterCli, keyboard);
						break;
					case PERSISTENCIA_MONGODB:
						personaInputAdapterCli.setPersonOutputPortInjection("MONGO");
						menuOpciones(personaInputAdapterCli, keyboard);
						break;
					default:
						log.warn("La opción elegida no es válida.");
				}
			} catch (InvalidOptionException e) {
				log.warn(e.getMessage());
			}
		} while (!isValid);
	}

	private void menuOpciones(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
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
						personaInputAdapterCli.historial();
						break;
					case OPCION_CREAR:
						crearPersona(personaInputAdapterCli, keyboard);
						break;
					case OPCION_BUSCAR:
						buscarPersona(personaInputAdapterCli, keyboard);
						break;
					case OPCION_ACTUALIZAR:
						actualizarPersona(personaInputAdapterCli, keyboard);
						break;
					case OPCION_ELIMINAR:
						eliminarPersona(personaInputAdapterCli, keyboard);
						break;
					case OPCION_CONTAR:
						personaInputAdapterCli.count();
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
		System.out.println(OPCION_VER_TODO + " para ver todas las personas");
		System.out.println(OPCION_CREAR + " para crear una persona");
		System.out.println(OPCION_BUSCAR + " para buscar una persona");
		System.out.println(OPCION_ACTUALIZAR + " para actualizar una persona");
		System.out.println(OPCION_ELIMINAR + " para eliminar una persona");
		System.out.println(OPCION_CONTAR + " para contar las personas en la bd");
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

	private void crearPersona(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
		keyboard.nextLine(); // Limpiar el buffer
		System.out.println("Ingrese la cedula de ciudadanía de la persona: ");
		int cc = keyboard.nextInt();
		keyboard.nextLine(); // Limpiar el buffer
		System.out.print("Ingrese el nombre: ");
		String nombre = keyboard.nextLine();
		System.out.print("Ingrese el apellido: ");
		String apellido = keyboard.nextLine();
		System.out.print("Ingrese el género (M/F): ");
		String genero = keyboard.next();
		System.out.print("Ingrese la edad: ");
		Integer edad = keyboard.nextInt();
		keyboard.nextLine();
		personaInputAdapterCli.create(cc, nombre, apellido, genero, edad);
		System.out.println("Persona creada con éxito.");
	}

	private void buscarPersona(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
		System.out.println("Ingrese la cedula de ciudadanía de la persona: ");
		int cc = keyboard.nextInt();
		personaInputAdapterCli.findOne(cc);
	}

	private void actualizarPersona(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
		keyboard.nextLine(); // Limpiar el buffer
		System.out.println("Ingrese la cedula de ciudadanía de la persona: ");
		int cc = keyboard.nextInt();
		keyboard.nextLine(); // Limpiar el buffer
		System.out.print("Ingrese el nombre: ");
		String nombre = keyboard.nextLine();
		System.out.print("Ingrese el apellido: ");
		String apellido = keyboard.nextLine();
		System.out.print("Ingrese el género (M/F): ");
		String genero = keyboard.next();
		System.out.print("Ingrese la edad: ");
		Integer edad = keyboard.nextInt();
		keyboard.nextLine();
		personaInputAdapterCli.edit(cc, nombre, apellido, genero, edad);
	}

	private void eliminarPersona(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
		keyboard.nextLine();
		System.out.println("Ingrese la cedula de ciudadanía de la persona: ");
		int cc = keyboard.nextInt();
		keyboard.nextLine();
		personaInputAdapterCli.drop(cc);
	}
}