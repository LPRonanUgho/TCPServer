package fr.iut.nantes.tcpserver;

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String serverName = "localhost";

		// TODO : Lire le fichier de configuration pour charger les variables

		// Port de connexion
		final int port = 6066;
		// Délai de déconnexion (1 minute)
		final int timeOut = 60000;

		try {
			// Instanciation du server
			Thread server = new Server(port, timeOut);
			// Démarrage du server
			server.start();

			// Instanciation des clients
			Client c1 = new Client(serverName, port);
			Client c2 = new Client(serverName, port);
			Client c3 = new Client(serverName, port);
			Client c4 = new Client(serverName, port);
			Client c5 = new Client(serverName, port);

			// Connexion des clients au server
			c1.connect();
			c2.connect();
			c3.connect();
			c4.connect();
			c5.connect();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
