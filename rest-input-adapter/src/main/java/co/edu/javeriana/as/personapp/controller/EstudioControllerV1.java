package co.edu.javeriana.as.personapp.controller;

import co.edu.javeriana.as.personapp.adapter.EstudioInputAdapterRest;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.model.request.EstudioRequest;
import co.edu.javeriana.as.personapp.model.response.EstudioResponse;
import co.edu.javeriana.as.personapp.model.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/study")
public class EstudioControllerV1 {

    @Autowired
    private EstudioInputAdapterRest estudioInputAdapterRest;

    @ResponseBody
    @GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EstudioResponse> obtenerEstudios(@PathVariable String database) {
        log.info("Into obtenerEstudios REST API");
        return estudioInputAdapterRest.historial(database.toUpperCase());
    }

    @ResponseBody
    @PostMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearEstudio(@RequestBody EstudioRequest request, @PathVariable String database) {
        log.info("Into crearEstudio REST API");
        return estudioInputAdapterRest.crearEstudio(request, database.toUpperCase());
    }

    @ResponseBody
    @GetMapping(path = "/{database}/{ccPerson}/{idProf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerEstudio(@PathVariable String database, @PathVariable int ccPerson, @PathVariable int idProf) {
        log.info("Into obtenerEstudio REST API");
        try {
            return estudioInputAdapterRest.obtenerEstudio(database.toUpperCase(), ccPerson, idProf);
        } catch (NoExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response(HttpStatus.NOT_FOUND.toString(),
                            "Study with person ID " + ccPerson + " and profession ID " + idProf + " does not exist in the database", LocalDateTime.now()));
        }
    }

    @ResponseBody
    @PutMapping(path = "/{database}/{ccPerson}/{idProf}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarEstudio(@PathVariable String database, @PathVariable int ccPerson, @PathVariable int idProf, @RequestBody EstudioRequest request) {
        log.info("Into actualizarEstudio REST API");
        try {
            return estudioInputAdapterRest.actualizarEstudio(database.toUpperCase(), ccPerson, idProf, request);
        } catch (NoExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response(HttpStatus.NOT_FOUND.toString(),
                            "Study with person ID " + ccPerson + " and profession ID " + idProf + " does not exist in the database", LocalDateTime.now()));
        }
    }

    @ResponseBody
    @DeleteMapping(path = "/{database}/{ccPerson}/{idProf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminarEstudio(@PathVariable String database, @PathVariable int ccPerson, @PathVariable int idProf) {
        log.info("Into eliminarEstudio REST API");
        try {
            return estudioInputAdapterRest.eliminarEstudio(database.toUpperCase(), ccPerson, idProf);
        } catch (NoExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response(HttpStatus.NOT_FOUND.toString(),
                            "Study with person ID " + ccPerson + " and profession ID " + idProf + " does not exist in the database", LocalDateTime.now()));
        } catch (InvalidOptionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(HttpStatus.BAD_REQUEST.toString(),
                            "Invalid database option", LocalDateTime.now()));
        }
    }

    @ResponseBody
    @GetMapping(path = "/{database}/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> contarEstudios(@PathVariable String database) {
        log.info("Into contarEstudios REST API");
        return estudioInputAdapterRest.contarEstudios(database.toUpperCase());
    }
}
