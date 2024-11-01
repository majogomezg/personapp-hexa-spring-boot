package co.edu.javeriana.as.personapp.application.port.out;

import co.edu.javeriana.as.personapp.domain.Profession;

import java.util.List;

public interface ProfesionOutputPort {
    Profession save(Profession profession);
    Boolean delete(Integer identification);
    List<Profession> find();
    Profession findById(Integer identification);
}
