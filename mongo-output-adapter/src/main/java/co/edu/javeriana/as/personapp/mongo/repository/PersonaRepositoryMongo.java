package co.edu.javeriana.as.personapp.mongo.repository;

import co.edu.javeriana.as.personapp.mongo.document.PersonaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonaRepositoryMongo extends MongoRepository<PersonaDocument, Integer> {

}
