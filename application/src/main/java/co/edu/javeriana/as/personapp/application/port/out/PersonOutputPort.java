package co.edu.javeriana.as.personapp.application.port.out;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.domain.Person;

import java.util.List;

@Port
public interface PersonOutputPort {
	Person save(Person person);
	Boolean delete(Integer identification);
	List<Person> find();
	Person findById(Integer identification);
}
