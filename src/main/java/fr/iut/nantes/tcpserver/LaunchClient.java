package fr.iut.nantes.tcpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Client
 * @author Ronan / Ugho
 */
public class LaunchClient {

	private static Socket serverSocket = null;
	private static int port;
	private static String serverAddress;
	private static String messageFromUser = null;
	private static BufferedReader readerFromServer = null;
	private static DataOutputStream writeToServer = null;
	private static BufferedReader readerFromUser = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws Exception {
		// Chargement du fichier de propriétées
		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		
		// Chargement des propriétées
		port = Integer.parseInt(prop.getProperty("port"));
		serverAddress = prop.getProperty("serverAddress");
		
		try {
			serverSocket = new Socket(InetAddress.getByName(serverAddress), port);

			readerFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			
			String alertFromServer = readerFromServer.readLine();
			System.out.println(alertFromServer);
			
			if("Serveur : nombre de connexions maximum atteint".equals(alertFromServer)) {
				System.exit(0);
			}
			
			writeToServer = new DataOutputStream(serverSocket.getOutputStream());
			
			System.out.println("Entrez quit ou exit pour sortir");
			
			// Boucle d'envoie des messages du client et d'affichage des réponses du serveur
			while (true) {
				messageFromUser = readerFromUser.readLine();
				
				writeToServer.writeBytes(messageFromUser);
				writeToServer.write(13);
				writeToServer.write(10);
				writeToServer.flush();
				
				String messageFromServer = readerFromServer.readLine();
				
				if("exit".equals(messageFromUser) || "quit".equals(messageFromUser) || "exit".equals(messageFromServer)) {
					break;
				}
				
				System.out.println(messageFromServer);
			}
			
			readerFromServer.close();
			writeToServer.close();
			serverSocket.close();
			System.exit(0);
		} catch (UnknownHostException uhe) {
			System.out.println("Hôte inconnu");
			System.exit(0);
		} catch (IOException ioe) {
			System.err.println("Connexion impossible");
			System.exit(0);
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}
}
