package co.edu.javeriana.as.personapp.terminal.adapter;

import co.edu.javeriana.as.personapp.application.port.in.ProfesionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfesionUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.mapper.ProfesionMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@Adapter
public class ProfesionInputAdapterCli {

    @Autowired
    @Qualifier("profesionOutputAdapterMaria")
    private ProfesionOutputPort profesionOutputPortMaria;

    @Autowired
    @Qualifier("profesionOutputAdapterMongo")
    private ProfesionOutputPort profesionOutputPortMongo;

    @Autowired
    private ProfesionMapperCli profesionMapperCli;

    ProfesionInputPort profesionInputPort;

    public void setProfesionOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase("MARIA")) {
            profesionInputPort = new ProfesionUseCase(profesionOutputPortMaria);
        } else if (dbOption.equalsIgnoreCase("MONGO")) {
            profesionInputPort = new ProfesionUseCase(profesionOutputPortMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial() {
        log.info("Into historial ProfesionEntity in Input Adapter");
        profesionInputPort.findAll().stream()
            .map(profesionMapperCli::fromDomainToAdapterCli)
            .forEach(System.out::println);
    }

    public void create (int id, String nombre, String descripcion){
        ProfesionModelCli profesion = ProfesionModelCli.builder()
            .id(id)
            .nombre(nombre)
            .descripcion(descripcion)
            .build();
        profesionInputPort.create(profesionMapperCli.fromModelToDomain(profesion));
    }

    public void edit (int id, String nombre, String descripcion){
        try{
            ProfesionModelCli profesion = ProfesionModelCli.builder()
                    .id(id)
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .build();
            profesion = profesionMapperCli.fromDomainToAdapterCli(profesionInputPort.edit(id, profesionMapperCli.fromModelToDomain(profesion)));
            System.out.println("Profesion actualizada");
            System.out.println(profesion);
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void drop (int id){
        try{
            if(profesionInputPort.drop(id))
                System.out.println("Profesion eliminada");
            else
                System.out.println("No se pudo eliminar la profesion");
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void count() {
        System.out.println("Cantidad de profesiones: " + profesionInputPort.count());
    }

    public void findOne(int id) {
        try {
            System.out.println(profesionMapperCli.fromDomainToAdapterCli(profesionInputPort.findOne(id)));
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }
}
