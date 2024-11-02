package co.edu.javeriana.as.personapp.terminal.adapter;

import co.edu.javeriana.as.personapp.application.port.in.EstudioInputPort;
import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.in.ProfesionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.EstudioOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.EstudioUseCase;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.application.usecase.ProfesionUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.mapper.EstudioMapperCli;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.mapper.ProfesionMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.EstudioModelCli;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;

@Slf4j
@Adapter
public class EstudioInputAdapterCli {

    @Autowired
    @Qualifier("estudioOutputAdapterMaria")
    private EstudioOutputPort estudioOutputPortMaria;

    @Autowired
    @Qualifier("estudioOutputAdapterMongo")
    private EstudioOutputPort estudioOutputPortMongo;

    @Autowired
    @Qualifier("personOutputAdapterMaria")
    private PersonOutputPort personaOutputPortMaria;

    @Autowired
    @Qualifier("personOutputAdapterMongo")
    private PersonOutputPort personaOutputPortMongo;

    @Autowired
    @Qualifier("profesionOutputAdapterMaria")
    private ProfesionOutputPort profesionOutputPortMaria;

    @Autowired
    @Qualifier("profesionOutputAdapterMongo")
    private ProfesionOutputPort profesionOutputPortMongo;

    @Autowired
    private EstudioMapperCli estudioMapperCli;
    @Autowired
    private PersonaMapperCli personaMapperCli;
    @Autowired
    private ProfesionMapperCli profesionMapperCli;

    EstudioInputPort estudioInputPort;
    PersonInputPort personaInputPort;
    ProfesionInputPort profesionInputPort;

    public void setEstudioOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase("MARIA")) {
            estudioInputPort = new EstudioUseCase(estudioOutputPortMaria);
            personaInputPort = new PersonUseCase(personaOutputPortMaria);
            profesionInputPort = new ProfesionUseCase(profesionOutputPortMaria);
        } else if (dbOption.equalsIgnoreCase("MONGO")) {
            estudioInputPort = new EstudioUseCase(estudioOutputPortMongo);
            personaInputPort = new PersonUseCase(personaOutputPortMongo);
            profesionInputPort = new ProfesionUseCase(profesionOutputPortMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial() {
        log.info("Into historial EstudioEntity in Input Adapter");
        estudioInputPort.findAll().stream()
                .map(estudioMapperCli::fromDomainToAdapterCli)
                .forEach(System.out::println);
    }

    public void create (Integer persona, Integer profesion, String fechaGraduacion, String nombreUniversidad){
        log.info("Into create EstudioEntity in Input Adapter");
        try {
            EstudioModelCli estudio = EstudioModelCli.builder()
                    .persona(personaMapperCli.fromDomainToAdapterCli(personaInputPort.findOne(persona)))
                    .profesion(profesionMapperCli.fromDomainToAdapterCli(profesionInputPort.findOne(profesion)))
                    .fechaGraduacion(LocalDate.parse(fechaGraduacion))
                    .nombreUniversidad(nombreUniversidad)
                    .build();
            estudioInputPort.create(estudioMapperCli.fromModelToDomain(estudio));
            System.out.println("Estudio creado.");
            System.out.println(estudio);
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update (Integer persona, Integer profesion, String fechaGraduacion, String nombreUniversidad){
        log.info("Into update EstudioEntity in Input Adapter");
        try {
            EstudioModelCli estudio = EstudioModelCli.builder()
                    .persona(personaMapperCli.fromDomainToAdapterCli(personaInputPort.findOne(persona)))
                    .profesion(profesionMapperCli.fromDomainToAdapterCli(profesionInputPort.findOne(profesion)))
                    .fechaGraduacion(LocalDate.parse(fechaGraduacion))
                    .nombreUniversidad(nombreUniversidad)
                    .build();
            estudioInputPort.edit(persona, profesion, estudioMapperCli.fromModelToDomain(estudio));
            System.out.println("Estudio actualizado correctamente.");
            System.out.println(estudio);
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete (Integer persona, Integer profesion){
        log.info("Into delete EstudioEntity in Input Adapter");
        try {
            estudioInputPort.drop(persona, profesion);
            System.out.println("Estudio eliminado.");
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void findOne (Integer persona, Integer profesion){
        log.info("Into findOne EstudioEntity in Input Adapter");
        try {
            System.out.println(estudioMapperCli.fromDomainToAdapterCli(estudioInputPort.findOne(persona, profesion)));
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void count() {
        log.info("Into count EstudioEntity in Input Adapter");
        System.out.println("Cantidad de estudios: " + estudioInputPort.count());
    }
}
