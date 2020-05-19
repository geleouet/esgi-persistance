package serveurSamples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.javalin.Javalin;

public class ClientUsersPreparedStatement {
	
	public static String encodePassword(String login, String password) {
		String input = password + login.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if       (c >= 'a' && c <= 'm') c += 13;
			else if  (c >= 'A' && c <= 'M') c += 13;
			else if  (c >= 'n' && c <= 'z') c -= 13;
			else if  (c >= 'N' && c <= 'Z') c -= 13;
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static void main(String[] args)  {
		String databaseUrl = "jdbc:h2:tcp://localhost:9123/~/marins";

		Javalin app = Javalin.create();
		app.get("/login", ctx -> {
			ResultSet resultats = null;
			String user = ctx.queryParam("user");
			String pass = encodePassword(user, ctx.queryParam("pass"));
			String requete = "SELECT login FROM user WHERE login=? AND password=?";

			System.err.println(requete);
			try {
				Connection conn = DriverManager.getConnection(databaseUrl, "", "");
				PreparedStatement prep = conn.prepareStatement(requete);
				prep.setString(1, user);
				prep.setString(2, pass);
				resultats = prep.executeQuery();
				if (resultats.next()) {
					String res = resultats.getString("login");
					if ("admin" .equals(res)) {
						ctx.result("Welcome Master");
					}
					else {
						ctx.result("hello " + res);
					}
				}
				else {
					ctx.status(403);
				}
			} catch (SQLException e) {
				// traitement de l'exception
				e.printStackTrace();
			}

		});
		app.start(6982);
	}

}
