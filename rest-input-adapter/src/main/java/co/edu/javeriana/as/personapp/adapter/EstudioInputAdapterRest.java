package co.edu.javeriana.as.personapp.adapter;

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
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mapper.EstudioMapperRest;
import co.edu.javeriana.as.personapp.model.request.EstudioRequest;
import co.edu.javeriana.as.personapp.model.response.EstudioResponse;
import co.edu.javeriana.as.personapp.model.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Adapter
public class EstudioInputAdapterRest {

    @Autowired
    @Qualifier("estudioOutputAdapterMaria")
    private EstudioOutputPort estudioInputPortMaria;

    @Autowired
    @Qualifier("estudioOutputAdapterMongo")
    private EstudioOutputPort estudioInputPortMongo;

    @Autowired
    @Qualifier("personOutputAdapterMaria")
    private PersonOutputPort personOutputPortMaria;

    @Autowired
    @Qualifier("personOutputAdapterMongo")
    private PersonOutputPort personOutputPortMongo;

    @Autowired
    @Qualifier("profesionOutputAdapterMaria")
    private ProfesionOutputPort professionOutputPortMaria;

    @Autowired
    @Qualifier("profesionOutputAdapterMongo")
    private ProfesionOutputPort professionOutputPortMongo;

    @Autowired
    private EstudioMapperRest estudioMapperRest;

    @Autowired
    private EstudioInputPort estudioInputPort;

    @Autowired
    private PersonInputPort personInputPort;

    @Autowired
    private ProfesionInputPort professionInputPort;

    private String setEstudioInputPort(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            estudioInputPort = new EstudioUseCase(estudioInputPortMaria);
            professionInputPort = new ProfesionUseCase(professionOutputPortMaria);
            personInputPort = new PersonUseCase(personOutputPortMaria);
            return DatabaseOption.MARIA.toString();
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            estudioInputPort = new EstudioUseCase(estudioInputPortMongo);
            professionInputPort = new ProfesionUseCase(professionOutputPortMongo);
            personInputPort = new PersonUseCase(personOutputPortMongo);
            return DatabaseOption.MONGO.toString();
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public List<EstudioResponse> historial(String database) {
        log.info("Into historial StudyEntity in Input Adapter");
        try {
            setEstudioInputPort(database);
            return estudioInputPort.findAll().stream()
                    .map(study -> estudioMapperRest.fromDomainToAdapterRest(study, database))
                    .collect(Collectors.toList());
        } catch (InvalidOptionException e) {
            log.warn(e.getMessage());
            return new ArrayList<>();
        }
    }

    public ResponseEntity<?> crearEstudio(EstudioRequest request, String database) {
        try {
            if (request.getPersonId() == null || request.getProfessionId() == null ||
                    request.getGraduationDate() == null || request.getUniversityName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(HttpStatus.BAD_REQUEST.toString(),
                                "Study data is incomplete or null", LocalDateTime.now()));
            }
            setEstudioInputPort(database);

            Person person = personInputPort.findOne(Integer.parseInt(request.getPersonId()));
            if (person == null) {
                throw new NoExistException("The person with id " + request.getPersonId() + " does not exist in the database, cannot create study.");
            }

            Profession profession = professionInputPort.findOne(Integer.parseInt(request.getProfessionId()));
            if (profession == null) {
                throw new NoExistException("The profession with id " + request.getProfessionId() + " does not exist in the database, cannot create study.");
            }

            Study study = estudioMapperRest.fromAdapterToDomain(request, person, profession);
            estudioInputPort.create(study);

            EstudioResponse response = estudioMapperRest.fromDomainToAdapterRest(study, database);
            return ResponseEntity.ok(response);

        } catch (InvalidOptionException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(HttpStatus.BAD_REQUEST.toString(), "Invalid database option", LocalDateTime.now()));
        } catch (NoExistException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response(HttpStatus.NOT_FOUND.toString(), e.getMessage(), LocalDateTime.now()));
        } catch (DateTimeParseException e) {
            log.warn("Invalid date format: " + e.getParsedString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(HttpStatus.BAD_REQUEST.toString(), "Invalid date format", LocalDateTime.now()));
        } catch (Exception e) {
            log.error("Unexpected error while creating study: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal server error", LocalDateTime.now()));
        }
    }

    public ResponseEntity<?> obtenerEstudio(String database, int ccPerson, int idProf) throws NoExistException {
        try {
            setEstudioInputPort(database);
            Study study = estudioInputPort.findOne(ccPerson, idProf);
            EstudioResponse response = estudioMapperRest.fromDomainToAdapterRest(study, database);
            return ResponseEntity.ok(response);
        } catch (InvalidOptionException e) {
            log.warn(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> actualizarEstudio(String database, int ccPerson, int idProf, EstudioRequest request) throws NoExistException {
        try {
            setEstudioInputPort(database);

            Person person = personInputPort.findOne(Integer.valueOf(request.getPersonId()));
            Profession profession = professionInputPort.findOne(Integer.valueOf(request.getProfessionId()));

            if (person == null || profession == null) {
                throw new NoExistException("Person or Profession not found, cannot update study.");
            }

            Study study = estudioMapperRest.fromAdapterToDomain(request, person, profession);
            estudioInputPort.edit(ccPerson, idProf, study);

            EstudioResponse response = estudioMapperRest.fromDomainToAdapterRest(study, database);
            return ResponseEntity.ok(response);

        } catch (InvalidOptionException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal server error", LocalDateTime.now()));
        }
    }

    public ResponseEntity<?> eliminarEstudio(String database, int ccPerson, int idProf) throws NoExistException, InvalidOptionException {
        setEstudioInputPort(database);
        estudioInputPort.drop(ccPerson, idProf);
        return ResponseEntity.ok(new Response(HttpStatus.OK.toString(), "Study deleted successfully", LocalDateTime.now()));
    }

    public ResponseEntity<?> contarEstudios(String database) {
        try {
            setEstudioInputPort(database);
            return ResponseEntity.ok(estudioInputPort.count());
        } catch (InvalidOptionException e) {
            log.warn(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }
}
