package fr.iut.nantes.tcpserver.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Properties;

public abstract class AbstractServer implements Runnable {
	
	// Atrributs
	protected Socket clientSocket;
	protected static int port;
	protected static int timeout;
	protected static int maxConnection;
	protected static int runningConnections = 0;
	protected static Properties prop;
	
	/**
	 * Constructeur du serveur
	 * @param clientSocket [Socket]
	 */
	public AbstractServer(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
		
	/**
	 * Méthode run du thread
	 */
	public void run() {
		DataOutputStream writeToClient = null;
		BufferedReader readerFromClient = null;
		boolean bool_tooManyConnection = false;

		try {
			writeToClient = new DataOutputStream(clientSocket.getOutputStream());

			// Vérification du nombre de connexions
			if (runningConnections < maxConnection) {
				writeToClient.writeBytes("Serveur : Bienvenue !");
				writeToClient.write(13);
				writeToClient.write(10);
				writeToClient.flush();

				runningConnections++;
			} else {
				writeToClient.writeBytes("Serveur : nombre de connexions maximum atteint");
				writeToClient.write(13);
				writeToClient.write(10);
				writeToClient.flush();

				clientSocket.close();
				bool_tooManyConnection = true;
			}

			readerFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			// Boucle de lecture des messages du client
			while (true) {
				String messageFromClient = readerFromClient.readLine();

				System.out.println(clientSocket + " à dit : " + messageFromClient);

				writeToClient.writeBytes("Echo du serveur : " + messageFromClient);
				writeToClient.write(13);
				writeToClient.write(10);
				writeToClient.flush();
			}
		} catch (SocketTimeoutException ste) {
			System.out.println("Client déconnecté - Timeout :\t" + clientSocket);

			try {
				writeToClient.writeBytes("exit");
				writeToClient.write(13);
				writeToClient.write(10);
				writeToClient.flush();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} catch (SocketException se) {
			System.out.println("Client déconnecté :\t\t" + clientSocket);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			try {
				clientSocket.close();
				
				if(!bool_tooManyConnection) {
					runningConnections--;
				}

				Thread.currentThread().interrupt();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public static void loadProperties() {
		// Chargement du fichier de propriétées
		prop = new Properties();
		
		try {
			prop.load(new FileInputStream("config.properties"));
		} catch (FileNotFoundException e) {
			System.err.println("Fichier de configuration introuvable (config.properties)");
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		// Chargement des propriétées
		port = Integer.parseInt(prop.getProperty("port"));
		timeout = Integer.parseInt(prop.getProperty("timeout"));
		maxConnection = Integer.parseInt(prop.getProperty("maxConnection"));
	}
	
	public static ServerSocket getServerSocket() {
		ServerSocket serverSocket = null;
		// Création du socket sur le port chargé depuis les propriétées;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException ioe) {
			System.err.println("Erreur : port incorrect ou déjà utilisé");
			System.exit(1);
		}
		
		return serverSocket;
	}
}
