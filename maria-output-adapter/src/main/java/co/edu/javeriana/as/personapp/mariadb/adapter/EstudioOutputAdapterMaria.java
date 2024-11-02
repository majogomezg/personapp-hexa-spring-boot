package co.edu.javeriana.as.personapp.mariadb.adapter;

import co.edu.javeriana.as.personapp.application.port.out.EstudioOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;
import co.edu.javeriana.as.personapp.mariadb.mapper.EstudiosMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.EstudioRepositoryMaria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Adapter
public class EstudioOutputAdapterMaria implements EstudioOutputPort {
    @Autowired
    private EstudioRepositoryMaria estudioRepositoryMaria;

    @Autowired
    private EstudiosMapperMaria estudiosMapperMaria;

    @Override
    public Study save(Study study) {
        EstudiosEntity estudios = estudioRepositoryMaria.save(estudiosMapperMaria.fromDomainToAdapter(study));
        if(estudioRepositoryMaria.findById(estudios.getEstudiosPK()).isEmpty()){
            return null;
        }
        estudios = estudioRepositoryMaria.findById(estudios.getEstudiosPK()).get();
        return estudiosMapperMaria.fromAdapterToDomain(estudios);
    }

    @Override
    public Boolean delete(Integer personId, Integer professionId) {
        EstudiosEntityPK id = new EstudiosEntityPK();
        id.setCcPer(personId);
        id.setIdProf(professionId);
        estudioRepositoryMaria.deleteById(id);
        return estudioRepositoryMaria.findById(id).isEmpty();
    }

    @Override
    public Study findById(Integer personId, Integer professionId) {
        EstudiosEntityPK id = new EstudiosEntityPK();
        id.setCcPer(personId);
        id.setIdProf(professionId);
        if (estudioRepositoryMaria.findById(id).isEmpty()) {
            return null;
        } else {
            return estudiosMapperMaria.fromAdapterToDomain(estudioRepositoryMaria.findById(id).get());
        }
    }

    @Override
    public List<Study> find() {
        return estudioRepositoryMaria.findAll().stream().map(estudiosMapperMaria::fromAdapterToDomain).collect(java.util.stream.Collectors.toList());
    }
}
