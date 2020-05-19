package serveurSamples;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import serveurSamples.models.CourseDTO;

public class ClientCourse {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("me.egaetan.client.jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		CourseDTO course_2 = entityManager.find(CourseDTO.class, 2);
		
		var newCourse = new CourseDTO(9, "AmericaCup", "Caraibes");
		entityManager.getTransaction().begin();
		entityManager.persist(newCourse);
		entityManager.getTransaction().commit();
		
		System.out.println(course_2);

	}
}
