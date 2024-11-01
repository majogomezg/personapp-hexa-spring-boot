package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Phone;

import java.util.List;

public interface TelefonoInputPort {
    Phone create(Phone phone);
    Phone edit(String number, Phone phone) throws NoExistException;
    Boolean drop(String number) throws NoExistException;
    List<Phone> findAll();
    Phone findOne(String number) throws NoExistException;
    Integer count();
}
