package co.edu.javeriana.as.personapp.mariadb.adapter;

import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.mariadb.mapper.ProfesionMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.ProfesionRepositoryMaria;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Adapter
public class ProfesionOutputAdapterMaria implements ProfesionOutputPort {
    @Autowired
    private ProfesionRepositoryMaria profesionRepositoryMaria;

    @Autowired
    ProfesionMapperMaria profesionMapperMaria;

    @Override
    public Profession save(Profession profession) {
        return profesionMapperMaria.fromAdapterToDomain(profesionRepositoryMaria.save(profesionMapperMaria.fromDomainToAdapter(profession)));
    }

    @Override
    public Boolean delete(Integer identification) {
        profesionRepositoryMaria.deleteById(identification);
        return profesionRepositoryMaria.findById(identification).isEmpty();
    }

    @Override
    public Profession findById(Integer identification) {
        if (profesionRepositoryMaria.findById(identification).isEmpty()) {
            return null;
        } else {
            return profesionMapperMaria.fromAdapterToDomain(profesionRepositoryMaria.findById(identification).get());
        }
    }

    @Override
    public List<Profession> find() {
        return profesionRepositoryMaria.findAll().stream().map(profesionMapperMaria::fromAdapterToDomain).collect(java.util.stream.Collectors.toList());
    }
}
