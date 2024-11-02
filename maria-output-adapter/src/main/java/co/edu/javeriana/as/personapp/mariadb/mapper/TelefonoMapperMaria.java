package co.edu.javeriana.as.personapp.mariadb.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.TelefonoEntity;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public class TelefonoMapperMaria {

	@Autowired
	private PersonaMapperMaria personaMapperMaria;

	public TelefonoEntity fromDomainToAdapter(Phone phone) {
		TelefonoEntity telefonoEntity = new TelefonoEntity();
		telefonoEntity.setNum(phone.getNumber());
		telefonoEntity.setOper(phone.getCompany());
		telefonoEntity.setDuenio(personaMapperMaria.fromDomainToAdapter(phone.getOwner()));
		return telefonoEntity;
	}

	public Phone fromAdapterToDomain(TelefonoEntity telefonoEntity) {
		Phone phone = new Phone();
		phone.setNumber(telefonoEntity.getNum());
		phone.setCompany(telefonoEntity.getOper());
		phone.setOwner(personaMapperMaria.fromAdapterToDomain(telefonoEntity.getDuenio()));
		return phone;
	}
}