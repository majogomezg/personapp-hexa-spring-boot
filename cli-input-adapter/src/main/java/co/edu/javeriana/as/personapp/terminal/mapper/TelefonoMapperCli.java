package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public class TelefonoMapperCli {
    @Autowired
    private PersonaMapperCli personaMapperCli;

    public TelefonoModelCli fromDomainToAdapterCli(Phone telefono) {
        return TelefonoModelCli.builder()
                .numero(telefono.getNumber())
                .empresa(telefono.getCompany())
                .propietario(personaMapperCli.fromDomainToAdapterCli(telefono.getOwner()))
                .build();
    }

    public Phone fromModelToDomain(TelefonoModelCli telefono) {
        return Phone.builder()
                .number(telefono.getNumero())
                .company(telefono.getEmpresa())
                .owner(personaMapperCli.fromModelToDomain(telefono.getPropietario()))
                .build();
    }
}
