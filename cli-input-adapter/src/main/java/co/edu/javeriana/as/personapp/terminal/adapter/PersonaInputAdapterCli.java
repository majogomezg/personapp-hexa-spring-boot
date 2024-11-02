package co.edu.javeriana.as.personapp.terminal.adapter;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@Adapter
public class PersonaInputAdapterCli {

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PersonaMapperCli personaMapperCli;

	PersonInputPort personInputPort;

	public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}
	public void historial() {
	    log.info("Into historial PersonaEntity in Input Adapter");
	    personInputPort.findAll().stream()
	        .map(personaMapperCli::fromDomainToAdapterCli)
	        .forEach(System.out::println);
	}


	public void create (int cc, String nombre, String apellido, String genero, Integer edad){
		PersonaModelCli persona = PersonaModelCli.builder()
				.cc(cc)
				.nombre(nombre)
				.apellido(apellido)
				.genero(genero)
				.edad(edad)
				.build();
		personInputPort.create(personaMapperCli.fromModelToDomain(persona));
	}

	public void edit (int cc, String nombre, String apellido, String genero, Integer edad){
		try{
			PersonaModelCli persona = PersonaModelCli.builder()
					.cc(cc)
					.nombre(nombre)
					.apellido(apellido)
					.genero(genero)
					.edad(edad)
					.build();
			persona = personaMapperCli.fromDomainToAdapterCli(personInputPort.edit(cc, personaMapperCli.fromModelToDomain(persona)));
			System.out.println("Persona actualizada");
			System.out.println(persona.toString());
		} catch (NoExistException e) {
			System.out.println(e.getMessage());
		}
	}

	public void drop (int cc){
		try{
			if (personInputPort.drop(cc))
				System.out.println("Persona eliminada");
			else
				System.out.println("No se pudo eliminar la persona");
		} catch (NoExistException e) {
			System.out.println(e.getMessage());
		}
	}

	public void count (){
		System.out.println("Cantidad de personas: " + personInputPort.count());
	}

	public void findOne (int cc){
		try{
			System.out.println(personaMapperCli.fromDomainToAdapterCli(personInputPort.findOne(cc)).toString());
		} catch (NoExistException e) {
			System.out.println(e.getMessage());
		}
	}

}
