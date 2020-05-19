package serveurSamples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.javalin.Javalin;

public class ClientMarinsJDBC {

	public static void main(String[] args) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:9123/~/marins", "", "");

		displayColumnNames(conn);
		
		Javalin app = Javalin.create();
		app.get("/marins", ctx -> {
			ResultSet resultats = null;
			String requete = "SELECT prenom, nom FROM marins ORDER BY DDNAISSANCE";

			try {
				Statement stmt = conn.createStatement();
				resultats = stmt.executeQuery(requete);
				List<String> res = new ArrayList<>();
				while (resultats.next()) {
					res.add(resultats.getString(1) + " " + resultats.getString(2));
				}
				ctx.json(res);
			} catch (SQLException e) {
				// traitement de l'exception
				e.printStackTrace();
			}

		});
		app.start(6980);
	}

	private static void displayColumnNames(Connection conn) {
		try {
			String requete = "SELECT * FROM marins ORDER BY DDNAISSANCE";
			Statement stmt = conn.createStatement();
			ResultSet resultats = stmt.executeQuery(requete);
			ResultSetMetaData rsmd = resultats.getMetaData();
			int nbCols = rsmd.getColumnCount();
			System.out.println("Colonnes de la table marins");
			for (int i = 0; i < nbCols; i++) {
				System.out.print(rsmd.getColumnName(i+1) +" ");
			}
			System.out.println();
			
		} catch (SQLException e) {
			// traitement de l'exception
			e.printStackTrace();
		}
	}
}
