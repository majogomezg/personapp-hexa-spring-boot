package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;

@Mapper
public class PersonaMapperCli {

	public PersonaModelCli fromDomainToAdapterCli(Person person) {
		PersonaModelCli personaModelCli = new PersonaModelCli();
		personaModelCli.setCc(person.getIdentification());
		personaModelCli.setNombre(person.getFirstName());
		personaModelCli.setApellido(person.getLastName());
		personaModelCli.setGenero(person.getGender().toString());
		personaModelCli.setEdad(person.getAge());
		return personaModelCli;
	}

	public Person fromModelToDomain(PersonaModelCli personaModelCli) {
		return personaModelCli != null ? Person.builder()
				.identification(personaModelCli.getCc())
				.firstName(personaModelCli.getNombre())
				.lastName(personaModelCli.getApellido())
				.gender(personaModelCli.getGenero() != null ? parseGender(personaModelCli.getGenero()) : Gender.OTHER)
				.age(personaModelCli.getEdad())
				.build() : null;
	}

	private Gender parseGender(String genero) {
		switch (genero.toUpperCase()) {
			case "M":
				return Gender.MALE;
			case "F":
				return Gender.FEMALE;
			default:
				return Gender.OTHER;
		}
	}
}
