package co.edu.javeriana.as.personapp.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("profesion")
public class ProfesionDocument {
	@Id
	private Integer id;
	private String nom;
	private String des;
	@DocumentReference(lazy = true, lookup = "{ 'primaryProfesion' : ?#{#self._id} }")
	@ReadOnlyProperty
	private List<EstudiosDocument> estudios;
}
