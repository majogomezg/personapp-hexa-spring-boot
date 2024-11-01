package co.edu.javeriana.as.personapp.application.usecase;

import co.edu.javeriana.as.personapp.application.port.in.TelefonoInputPort;
import co.edu.javeriana.as.personapp.application.port.out.TelefonoOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Slf4j
@UseCase
public class TelefonoUseCase implements TelefonoInputPort {

    private final TelefonoOutputPort telefonoPersistence;

    public TelefonoUseCase(@Qualifier("telefonoOutputAdapterMaria") TelefonoOutputPort persistence) {
        this.telefonoPersistence = persistence;
    }

    @Override
    public Phone create(Phone phone) {
        log.debug("Into create phone on Application Domain");
        return telefonoPersistence.save(phone);
    }

    @Override
    public Phone edit(String number, Phone phone) throws NoExistException{
        log.debug("Into edit phone on Application Domain");
        Phone oldPhone = telefonoPersistence.findById(number);
        if (oldPhone != null)
            return telefonoPersistence.save(phone);
        throw new NoExistException(
                "The phone with number " + number + " does not exist into db, cannot be edited");
    }

    @Override
    public Boolean drop(String number) throws NoExistException {
        log.debug("Into drop phone on Application Domain");
        Phone oldPhone = telefonoPersistence.findById(number);
        if (oldPhone != null)
            return telefonoPersistence.delete(number);
        throw new NoExistException(
                "The phone with number " + number + " does not exist into db, cannot be dropped");
    }

    @Override
    public List<Phone> findAll() {
        log.debug("Into find all phones on Application Domain");
        System.out.println("Output: " + telefonoPersistence.getClass());
        return telefonoPersistence.find();
    }

    @Override
    public Phone findOne(String number) throws NoExistException {
        log.debug("Into find one phone on Application Domain");
        Phone phone = telefonoPersistence.findById(number);
        if(phone != null)
            return phone;
        throw new NoExistException(
                "The phone with number " + number + " does not exist into db, cannot be found");
    }

    @Override
    public Integer count() {
        return findAll().size();
    }
}
