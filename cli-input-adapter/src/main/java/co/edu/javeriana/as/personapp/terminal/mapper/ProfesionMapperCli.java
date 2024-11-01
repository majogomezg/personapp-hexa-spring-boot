package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;

@Mapper
public class ProfesionMapperCli {
    public ProfesionModelCli fromDomainToAdapterCli(Profession profession) {
        return ProfesionModelCli.builder()
                .id(profession.getIdentification())
                .nombre(profession.getName())
                .descripcion(profession.getDescription())
                .build();
    }

    public Profession fromModelToDomain(ProfesionModelCli profesion) {
        return Profession.builder()
                .identification(profesion.getId())
                .name(profesion.getNombre())
                .description(profesion.getDescripcion())
                .build();
    }
}
