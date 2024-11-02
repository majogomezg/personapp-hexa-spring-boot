package co.edu.javeriana.as.personapp.adapter;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.mapper.PersonaMapperRest;
import co.edu.javeriana.as.personapp.model.request.PersonaRequest;
import co.edu.javeriana.as.personapp.model.response.PersonaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Adapter
public class PersonaInputAdapterRest {

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PersonaMapperRest personaMapperRest;

	PersonInputPort personInputPort;

	private String setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMaria);
			return DatabaseOption.MARIA.toString();
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMongo);
			return  DatabaseOption.MONGO.toString();
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public ResponseEntity<List<PersonaResponse>> historial(String database) {
		log.info("Into historial PersonaEntity in Input Adapter");
		try {
			if(setPersonOutputPortInjection(database).equalsIgnoreCase(DatabaseOption.MARIA.toString())){
				return ResponseEntity.ok(personInputPort.findAll().stream().map(personaMapperRest::fromDomainToAdapterRestMaria)
						.collect(Collectors.toList()));
			}else {
				return ResponseEntity.ok(personInputPort.findAll().stream().map(personaMapperRest::fromDomainToAdapterRestMongo)
						.collect(Collectors.toList()));
			}

		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
		}
	}

	public ResponseEntity<PersonaResponse> crearPersona(PersonaRequest request) {
		log.info("Into crearPersona in Input Adapter");
		try {
			setPersonOutputPortInjection(request.getDatabase());
			Person person = personInputPort.create(personaMapperRest.fromAdapterToDomain(request));
			return ResponseEntity.ok(personaMapperRest.fromDomainToAdapterRestMaria(person));
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	public ResponseEntity<PersonaResponse> obtenerPersona(String database, int cc) {
		log.info("Into obtenerPersona in Input Adapter");
		try {
			setPersonOutputPortInjection(database);
			Person person = personInputPort.findOne(cc);
			return ResponseEntity.ok(personaMapperRest.fromDomainToAdapterRestMaria(person));
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (NoExistException e){
			log.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	public ResponseEntity<PersonaResponse> actualizarPersona(String database, int cc, PersonaRequest request) {
		log.info("Into actualizarPersona in Input Adapter");
		try {
			setPersonOutputPortInjection(database);
			Person person = personInputPort.edit(cc, personaMapperRest.fromAdapterToDomain(request));
			return ResponseEntity.ok(personaMapperRest.fromDomainToAdapterRestMaria(person));
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (NoExistException e){
			log.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	public ResponseEntity<PersonaResponse> eliminarPersona(String database, int cc) {
		log.info("Into eliminarPersona in Input Adapter");
		try {
			setPersonOutputPortInjection(database);
			personInputPort.drop(cc);
			return ResponseEntity.ok().build();
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (NoExistException e){
			log.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	public ResponseEntity<Integer> contarPersonas(String database) {
		log.info("Into contarPersonas in Input Adapter");
		try {
			setPersonOutputPortInjection(database);
			return ResponseEntity.ok(personInputPort.count());
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}