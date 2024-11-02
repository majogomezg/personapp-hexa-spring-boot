package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.domain.Study;

import java.util.List;

public interface EstudioInputPort {
    Study create(Study study);
    Study edit(Integer personaCC, Integer profesionId, Study study);
    Boolean drop(Integer personaCC, Integer profesionId);
    List<Study> findAll();
    Study findOne(Integer personaCC, Integer profesionId);
    Integer count();
}
