package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.domain.Study;

import java.util.List;

@Port
public interface PersonInputPort {
	
	void setPersintence(PersonOutputPort personPersintence);
	
	Person create(Person person);

	Person edit(Integer identification, Person person) throws NoExistException;

	Boolean drop(Integer identification) throws NoExistException;

	List<Person> findAll();

	Person findOne(Integer identification) throws NoExistException;

	Integer count();

	List<Phone> getPhones(Integer identification) throws NoExistException;

	List<Study> getStudies(Integer identification) throws NoExistException;
}
