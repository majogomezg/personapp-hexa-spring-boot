package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.terminal.model.EstudioModelCli;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public class EstudioMapperCli {
    @Autowired
    private PersonaMapperCli personaMapperCli;
    @Autowired
    private ProfesionMapperCli profesionMapperCli;

    public EstudioModelCli fromDomainToAdapterCli(Study estudio) {
        return EstudioModelCli.builder()
                .persona(personaMapperCli.fromDomainToAdapterCli(estudio.getPerson()))
                .profesion(profesionMapperCli.fromDomainToAdapterCli(estudio.getProfession()))
                .fechaGraduacion(estudio.getGraduationDate())
                .nombreUniversidad(estudio.getUniversityName())
                .build();
    }

    public Study fromModelToDomain(EstudioModelCli estudio) {
        return Study.builder()
                .person(personaMapperCli.fromModelToDomain(estudio.getPersona()))
                .profession(profesionMapperCli.fromModelToDomain(estudio.getProfesion()))
                .graduationDate(estudio.getFechaGraduacion())
                .universityName(estudio.getNombreUniversidad())
                .build();
    }
}
