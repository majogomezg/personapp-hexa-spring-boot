package co.edu.javeriana.as.personapp.mariadb.adapter;

import co.edu.javeriana.as.personapp.application.port.out.TelefonoOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.mapper.TelefonoMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.TelefonoRepositoryMaria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Adapter
public class TelefonoOutputAdapterMaria implements TelefonoOutputPort {
    @Autowired
    private TelefonoRepositoryMaria telefonoRepositoryMaria;

    @Autowired
    private TelefonoMapperMaria telefonoMapperMaria;

    @Override
    public Phone save(Phone phone) {
        return telefonoMapperMaria.fromAdapterToDomain(telefonoRepositoryMaria.save(telefonoMapperMaria.fromDomainToAdapter(phone)));
    }

    @Override
    public Boolean delete(String number) {
        telefonoRepositoryMaria.deleteById(number);
        return telefonoRepositoryMaria.findById(number).isEmpty();
    }

    @Override
    public Phone findById(String number) {
        if (telefonoRepositoryMaria.findById(number).isEmpty()) {
            return null;
        } else {
            return telefonoMapperMaria.fromAdapterToDomain(telefonoRepositoryMaria.findById(number).get());
        }
    }

    @Override
    public List<Phone> find() {
        return telefonoRepositoryMaria.findAll().stream().map(telefonoMapperMaria::fromAdapterToDomain).collect(java.util.stream.Collectors.toList());
    }
}
