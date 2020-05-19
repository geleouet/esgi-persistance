package serveurSamples;

import java.util.List;
import java.util.stream.Collectors;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.FieldMapper;

import io.javalin.Javalin;

public class ClientMarinsJDBI {

	public static class Marin {
		public String nom;
		public String prenom;
		public int ddnaissance;
		public int ddmort;

		@Override
		public String toString() {
			return nom + " " + prenom + " " + ddnaissance + " " + ddmort;
		}

	}

	public static void main(String[] args) {
		Javalin app = Javalin.create();
		
		app.get("/json", ctx -> {
			Jdbi jdbi = Jdbi.create("jdbc:h2:tcp://localhost:9123/~/marins");

			List<Marin> marins = jdbi.withHandle(handle -> 
				handle.registerRowMapper(FieldMapper.factory(Marin.class))
				.createQuery("SELECT * FROM marins ORDER BY DDNAISSANCE")
				.mapTo(Marin.class)
				.list());
			ctx.json(marins);

			System.out.println(marins);

		});
		app.get("/marins", ctx -> {
			Jdbi jdbi = Jdbi.create("jdbc:h2:tcp://localhost:9123/~/marins");
			
			List<String> marins = jdbi.withHandle(handle -> 
			handle.createQuery("SELECT prenom || ' ' || nom FROM marins ORDER BY DDNAISSANCE")
			.mapTo(String.class)
			.list());
			ctx.json(marins);
			
		});
		app.get("/marins2", ctx -> {
			Jdbi jdbi = Jdbi.create("jdbc:h2:tcp://localhost:9123/~/marins");
			
			List<Marin> marins = jdbi.withHandle(handle -> 
			handle.registerRowMapper(FieldMapper.factory(Marin.class))
			.createQuery("SELECT * FROM marins ORDER BY DDNAISSANCE")
			.mapTo(Marin.class)
			.list());
			ctx.json(marins.stream().map(m -> m.prenom + " " + m.nom).collect(Collectors.toList()));
			
		});
		app.start(6983);
	}
}
