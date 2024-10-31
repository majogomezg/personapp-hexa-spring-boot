package co.edu.javeriana.as.personapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.as.personapp.adapter.PersonaInputAdapterRest;
import co.edu.javeriana.as.personapp.model.request.PersonaRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/persona")
public class PersonaControllerV1 {

	@Autowired
	private PersonaInputAdapterRest personaInputAdapterRest;

	@ResponseBody
	@GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> personas(@PathVariable String database) {
		log.info("Into personas REST API");
		return personaInputAdapterRest.historial(database.toUpperCase());
	}

	@ResponseBody
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> crearPersona(@RequestBody PersonaRequest request) {
		log.info("esta en el metodo crearTarea en el controller del api");
		return personaInputAdapterRest.crearPersona(request);
	}

	@ResponseBody
	@GetMapping(path = "/{database}/{cc}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> persona(@PathVariable String database, @PathVariable int cc) {
		log.info("Into persona REST API");
		return personaInputAdapterRest.obtenerPersona(database.toUpperCase(), cc);
	}

	@ResponseBody
	@PutMapping(path = "/{database}/{cc}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarPersona(@PathVariable String database, @PathVariable int cc, @RequestBody PersonaRequest request) {
		log.info("Into actualizarPersona REST API");
		return personaInputAdapterRest.actualizarPersona(database.toUpperCase(), cc, request);
	}

	@ResponseBody
	@DeleteMapping(path = "/{database}/{cc}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> eliminarPersona(@PathVariable String database, @PathVariable int cc) {
		log.info("Into eliminarPersona REST API");
		return personaInputAdapterRest.eliminarPersona(database.toUpperCase(), cc);
	}

	@ResponseBody
	@GetMapping(path = "/{database}/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> contarPersonas(@PathVariable String database) {
		log.info("Into contarPersonas REST API");
		return personaInputAdapterRest.contarPersonas(database.toUpperCase());
	}

}
