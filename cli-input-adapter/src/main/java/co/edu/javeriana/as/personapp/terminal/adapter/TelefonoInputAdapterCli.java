package co.edu.javeriana.as.personapp.terminal.adapter;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.in.TelefonoInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.TelefonoOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.application.usecase.TelefonoUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.mapper.TelefonoMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@Adapter
public class TelefonoInputAdapterCli {

    @Autowired
    @Qualifier("telefonoOutputAdapterMaria")
    private TelefonoOutputPort telefonoOutputPortMaria;

    @Autowired
    @Qualifier("telefonoOutputAdapterMongo")
    private TelefonoOutputPort telefonoOutputPortMongo;

    @Autowired
    @Qualifier("personOutputAdapterMaria")
    private PersonOutputPort personaOutputPortMaria;

    @Autowired
    @Qualifier("personOutputAdapterMongo")
    private PersonOutputPort personaOutputPortMongo;

    @Autowired
    private TelefonoMapperCli telefonoMapperCli;
    @Autowired
    private PersonaMapperCli personaMapperCli;

    TelefonoInputPort telefonoInputPort;
    PersonInputPort personaInputPort;

    public void setTelefonoOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase("MARIA")) {
            telefonoInputPort = new TelefonoUseCase(telefonoOutputPortMaria);
            personaInputPort = new PersonUseCase(personaOutputPortMaria);
        } else if (dbOption.equalsIgnoreCase("MONGO")) {
            telefonoInputPort = new TelefonoUseCase(telefonoOutputPortMongo);
            personaInputPort = new PersonUseCase(personaOutputPortMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial() {
        log.info("Into historial TelefonoEntity in Input Adapter");
        telefonoInputPort.findAll().stream()
            .map(telefonoMapperCli::fromDomainToAdapterCli)
            .forEach(System.out::println);
    }

    public void create (String numero, String empresa, Integer propietario){
        try {
            PersonaModelCli persona = personaMapperCli.fromDomainToAdapterCli(personaInputPort.findOne(propietario));
            TelefonoModelCli telefono = TelefonoModelCli.builder()
                    .numero(numero)
                    .empresa(empresa)
                    .propietario(persona)
                    .build();
            telefonoInputPort.create(telefonoMapperCli.fromModelToDomain(telefono));
        } catch (NoExistException e) {
            log.warn(e.getMessage());
        }
    }

    public void edit (String numero, String empresa, Integer propietario){
        try{
            PersonaModelCli persona = personaMapperCli.fromDomainToAdapterCli(personaInputPort.findOne(propietario));
            TelefonoModelCli telefono = TelefonoModelCli.builder()
                    .numero(numero)
                    .empresa(empresa)
                    .propietario(persona)
                    .build();
            telefono = telefonoMapperCli.fromDomainToAdapterCli(telefonoInputPort.edit(numero, telefonoMapperCli.fromModelToDomain(telefono)));
            System.out.println("Telefono actualizado");
            System.out.println(telefono);
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void drop (String numero){
        try{
            if(telefonoInputPort.drop(numero))
                System.out.println("Telefono eliminado");
            else
                System.out.println("No se pudo eliminar el telefono");
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void count() {
        System.out.println("Cantidad de telefonos: " + telefonoInputPort.count());
    }

    public void findOne(String numero) {
        try {
            System.out.println(telefonoMapperCli.fromDomainToAdapterCli(telefonoInputPort.findOne(numero)));
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }
}
