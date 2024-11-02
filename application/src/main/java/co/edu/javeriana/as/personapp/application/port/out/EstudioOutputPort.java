package co.edu.javeriana.as.personapp.application.port.out;

import co.edu.javeriana.as.personapp.domain.Study;

import java.util.List;

public interface EstudioOutputPort {
    Study save(Study study);
    Boolean delete(Integer personId, Integer professionId);
    Study findById(Integer personId, Integer professionId);
    List<Study> find();
}
