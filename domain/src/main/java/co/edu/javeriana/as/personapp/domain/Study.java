package co.edu.javeriana.as.personapp.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Study {
	@NonNull
	private Person person;
	@NonNull
	private Profession profession;
	private LocalDate graduationDate;
	private String universityName;
}
