package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Study;

import java.util.List;

public interface EstudioInputPort {
    Study create(Study study);
    Study edit(Integer personaCC, Integer profesionId, Study study) throws NoExistException;
    Boolean drop(Integer personaCC, Integer profesionId) throws NoExistException;
    List<Study> findAll();
    Study findOne(Integer personaCC, Integer profesionId) throws NoExistException;
    Integer count();
}
