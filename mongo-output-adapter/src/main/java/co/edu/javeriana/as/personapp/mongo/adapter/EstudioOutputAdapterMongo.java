package co.edu.javeriana.as.personapp.mongo.adapter;

import co.edu.javeriana.as.personapp.application.port.out.EstudioOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.mapper.EstudiosMapperMongo;
import co.edu.javeriana.as.personapp.mongo.repository.EstudioRepositoryMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Adapter
public class EstudioOutputAdapterMongo implements EstudioOutputPort {
    @Autowired
    private final EstudioRepositoryMongo estudioRepositoryMongo;

    @Autowired
    private EstudiosMapperMongo estudiosMapperMongo;

    public EstudioOutputAdapterMongo(EstudioRepositoryMongo estudioRepositoryMongo) {
        this.estudioRepositoryMongo = estudioRepositoryMongo;
    }


    @Override
    public Study save(Study study) { 
        log.debug("Into save on Adapter MongoDB");
        try {
            EstudiosDocument persistedEstudio = estudioRepositoryMongo.save(estudiosMapperMongo.fromDomainToAdapter(study));
            return estudiosMapperMongo.fromAdapterToDomain(persistedEstudio);
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            return study;
        }
    }

    @Override
    public Boolean delete(Integer personId, Integer professionId) {
        log.debug("Into delete on Adapter MongoDB");
        estudioRepositoryMongo.deleteById(personId + "-" + professionId);
        return estudioRepositoryMongo.findById(personId + "-" + professionId).isEmpty();
    }

    @Override
    public Study findById(Integer personId, Integer professionId) {
        log.debug("Into findById on Adapter MongoDB");
        if (estudioRepositoryMongo.findById(personId + "-" + professionId).isEmpty()) {
            return null;
        } else {
            return estudiosMapperMongo.fromAdapterToDomain(estudioRepositoryMongo.findById(personId + "-" + professionId).get());
        }
    }

    @Override
    public List<Study> find() {
        log.debug("Into find on Adapter MongoDB");
        return estudioRepositoryMongo.findAll().stream().map(estudiosMapperMongo::fromAdapterToDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
