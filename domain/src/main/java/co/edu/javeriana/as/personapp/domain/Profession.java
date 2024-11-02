package co.edu.javeriana.as.personapp.domain;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Profession {
	@NonNull
	private Integer identification;
	@NonNull
	private String name;
	private String description;
	@ToString.Exclude
	private List<Study> studies;
}
