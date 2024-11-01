package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;

import java.util.List;

@Port
public interface ProfesionInputPort {
    void setPersistence(ProfesionOutputPort persistence);

    Profession create(Profession profession);

    Profession edit(Integer identification, Profession profession) throws NoExistException;

    Boolean drop(Integer identification) throws NoExistException;

    List<Profession> findAll();

    Profession findOne(Integer identification) throws NoExistException;

    Integer count();

    List<Study> getStudies(Integer identification) throws NoExistException;
}
