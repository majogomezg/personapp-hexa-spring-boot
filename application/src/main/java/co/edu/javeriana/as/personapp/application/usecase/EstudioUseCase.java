package co.edu.javeriana.as.personapp.application.usecase;

import co.edu.javeriana.as.personapp.application.port.in.EstudioInputPort;
import co.edu.javeriana.as.personapp.application.port.out.EstudioOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Study;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Slf4j
@UseCase
public class EstudioUseCase implements EstudioInputPort {

    private final EstudioOutputPort estudioPersistence;

    public EstudioUseCase(@Qualifier("estudioOutputAdapterMaria") EstudioOutputPort persistence) {
        this.estudioPersistence = persistence;
    }

    @Override
    public Study create(Study study) {
        log.debug("Into create study on Application Domain");
        return estudioPersistence.save(study);
    }

    @Override
    public Study edit(Integer personaCC, Integer profesionId, Study study)  throws NoExistException {
        log.debug("Into edit study on Application Domain");
        Study oldStudy = estudioPersistence.findById(personaCC, profesionId);
        if (oldStudy != null)
            return estudioPersistence.save(study);
        throw new NoExistException(
                "The study with personId " + personaCC + " and professionId " + profesionId + " does not exist into db, cannot be edited"
        );
    }

    @Override
    public Boolean drop(Integer personaCC, Integer profesionId) throws NoExistException {
        log.debug("Into drop study on Application Domain");
        Study oldStudy = estudioPersistence.findById(personaCC, profesionId);
        if (oldStudy != null)
            return estudioPersistence.delete(personaCC, profesionId);
        throw new NoExistException(
                "The study with personId " + personaCC + " and professionId " + profesionId + " does not exist into db, cannot be dropped"
        );
    }

    @Override
    public List<Study> findAll() {
        log.debug("Into find all studies on Application Domain");
        System.out.println("Output: " + estudioPersistence.getClass());
        return estudioPersistence.find();
    }

    @Override
    public Study findOne(Integer personaCC, Integer profesionId) throws NoExistException {
        log.debug("Into find one study on Application Domain");
        Study study = estudioPersistence.findById(personaCC, profesionId);
        if(study != null)
            return study;
        throw new NoExistException(
                "The study with personId " + personaCC + " and professionId " + profesionId + " does not exist into db, cannot be found"
        );
    }

    @Override
    public Integer count() {
        return findAll().size();
    }
}
