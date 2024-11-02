package co.edu.javeriana.as.personapp.mongo.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.document.PersonaDocument;
import co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;

@Mapper
public class EstudiosMapperMongo {

	@Autowired
	@Lazy
	private PersonaMapperMongo personaMapperMongo;

	@Autowired
	@Lazy
	private ProfesionMapperMongo profesionMapperMongo;

	public EstudiosDocument fromDomainToAdapter(Study study) {
		EstudiosDocument estudio = new EstudiosDocument();
		PersonaDocument personaDocument = personaMapperMongo.fromDomainToAdapter(study.getPerson());
		ProfesionDocument profesionDocument = profesionMapperMongo.fromDomainToAdapter(study.getProfession());

		estudio.setId(validateId(study.getPerson().getIdentification(), study.getProfession().getIdentification()));
		estudio.setPrimaryPersona(personaDocument);
		estudio.setPrimaryProfesion(profesionDocument);
		estudio.setFecha(study.getGraduationDate());
		estudio.setUniver(validateUniver(study.getUniversityName()));
		return estudio;
	}

	public Study fromAdapterToDomain(EstudiosDocument estudiosDocument) {
		Study study = new Study();
		Person person = personaMapperMongo.fromAdapterToDomain(estudiosDocument.getPrimaryPersona());
		Profession profession = profesionMapperMongo.fromAdapterToDomain(estudiosDocument.getPrimaryProfesion());

		study.setPerson(person);
		study.setProfession(profession);
		study.setGraduationDate(estudiosDocument.getFecha());
		study.setUniversityName(validateUniver(estudiosDocument.getUniver()));
		return study;
	}

	private String validateId(@NonNull Integer identificationPerson, @NonNull Integer identificationProfession) {
		return identificationPerson + "-" + identificationProfession;
	}

	private String validateUniver(String universityName) {
		return universityName != null ? universityName : "";
	}
}