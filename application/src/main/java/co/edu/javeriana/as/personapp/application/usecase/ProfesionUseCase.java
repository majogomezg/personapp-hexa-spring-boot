package co.edu.javeriana.as.personapp.application.usecase;

import co.edu.javeriana.as.personapp.application.port.in.ProfesionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Slf4j
@UseCase
public class ProfesionUseCase implements ProfesionInputPort {

    private ProfesionOutputPort profesionPersistence;

    public ProfesionUseCase(@Qualifier("profesionOutputAdapterMaria") ProfesionOutputPort persistence) {
        this.profesionPersistence = persistence;
    }

    @Override
    public void setPersistence(ProfesionOutputPort persistence) {
        this.profesionPersistence = persistence;
    }

    @Override
    public Profession create(Profession profession) {
        log.debug("Into create profession on Application Domain");
        return profesionPersistence.save(profession);
    }

    @Override
    public Profession edit(Integer identification, Profession profession) throws NoExistException {
        log.debug("Into edit profession on Application Domain");
        Profession oldProfession = profesionPersistence.findById(identification);
        if (oldProfession != null)
            return profesionPersistence.save(profession);
        throw new NoExistException(
                "The profession with id " + identification + " does not exist into db, cannot be edited");
    }

    @Override
    public Boolean drop(Integer identification) throws NoExistException {
        log.debug("Into drop profession on Application Domain");
        Profession oldProfession = profesionPersistence.findById(identification);
        if (oldProfession != null)
            return profesionPersistence.delete(identification);
        throw new NoExistException(
                "The profession with id " + identification + " does not exist into db, cannot be dropped");
    }

    @Override
    public List<Profession> findAll() {
        log.debug("Into find all professions on Application Domain");
        System.out.println("Output: " + profesionPersistence.getClass());
        return profesionPersistence.find();
    }

    @Override
    public Profession findOne(Integer identification) throws NoExistException {
        log.debug("Into find one profession on Application Domain");
        Profession oldProfession = profesionPersistence.findById(identification);
        if (oldProfession != null)
            return oldProfession;
        throw new NoExistException(
                "The profession with id " + identification + " does not exist into db, cannot be found");
    }

    @Override
    public Integer count() {
        return findAll().size();
    }

    @Override
    public List<Study> getStudies(Integer identification) throws NoExistException {
        return List.of();
    }
}
