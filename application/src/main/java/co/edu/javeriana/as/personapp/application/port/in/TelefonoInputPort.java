package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.domain.Phone;

import java.util.List;

public interface TelefonoInputPort {
    Phone create(Phone phone);
    Phone edit(String number, Phone phone);
    Boolean drop(String number);
    List<Phone> findAll();
    Phone findOne(String number);
    Integer count();
}
