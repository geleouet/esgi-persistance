package serveurSamples.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COURSE")
public class CourseDTO {
	@Id
	public Integer id;
	public String nom;
	public String lieu;
	
	public CourseDTO() {
	}

	public CourseDTO(Integer id, String nom, String lieu) {
		super();
		this.id = id;
		this.nom = nom;
		this.lieu = lieu;
	}

	@Override
	public String toString() {
		return id +" " + nom +" " +lieu;
	}
}


