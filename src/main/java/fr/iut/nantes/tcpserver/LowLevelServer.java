package fr.iut.nantes.tcpserver;

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

/**
 * Serveur - Bas niveau
 * @author Ronan / Ugho
 */
class LowLevelServer implements Runnable {

	// Atrributs
	private Socket clientSocket;
	private static int port;
	private static int timeout;
	private static int maxConnection;
	private static int runningConnections = 0;
	
	/**
	 * Constructeur du serveur
	 * @param clientSocket [Socket]
	 */
	public LowLevelServer(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	/**
	 * Méthode main du serveur
	 * @param args [String[]]
	 * @throws Exception
	 */
	public static void main(String args[]){
		// Chargement du fichier de propriétées
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream("config.properties"));
		} catch (FileNotFoundException e) {
			System.err.println("Fichier de configuration introuvable (config.properties)");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		// Chargement des propriétées
		port = Integer.parseInt(prop.getProperty("port"));
		timeout = Integer.parseInt(prop.getProperty("timeout"));
		maxConnection = Integer.parseInt(prop.getProperty("maxConnection"));
		
		ServerSocket serverSocket = null;

		// Création du socket sur le port chargé depuis les propriétées;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException ioe) {
			System.err.println("Erreur : port incorrect ou déjà utilisé");
			System.exit(1);
		}

		System.out.println("Serveur en attente de clients...");

		try {
			while (true) {
				Socket socket = serverSocket.accept();

				if (socket != null) {
					socket.setSoTimeout(timeout * 1000);
					
					System.out.println("Client connecté :\t\t" + socket);

					Thread t = new Thread(new LowLevelServer(socket));
					t.start();
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
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
}
