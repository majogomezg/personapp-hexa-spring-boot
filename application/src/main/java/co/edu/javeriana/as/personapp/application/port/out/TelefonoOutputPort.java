package co.edu.javeriana.as.personapp.application.port.out;

import co.edu.javeriana.as.personapp.domain.Phone;

import java.util.List;

public interface TelefonoOutputPort {
    Phone save(Phone phone);
    Boolean delete(String number);
    Phone findById(String number);
    List<Phone> find();
}
