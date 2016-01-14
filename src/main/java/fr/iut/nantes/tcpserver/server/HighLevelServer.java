package fr.iut.nantes.tcpserver.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Serveur - Haut niveau
 * @author Ronan / Ugho
 */
public class HighLevelServer extends AbstractServer {
	
	// Atrributs
	private static ExecutorService es;

	/**
	 * Constructeur du serveur
	 * @param clientSocket [Socket]
	 */
	public HighLevelServer(Socket clientSocket) {
		super(clientSocket);
	}

	/**
	 * Méthode main du serveur
	 * @param args [String[]]
	 * @throws Exception
	 */
	public static void main(String args[]) {
		loadProperties();
		
		es = Executors.newFixedThreadPool(maxConnection+1);
		
		ServerSocket serverSocket = getServerSocket();

		System.out.println("Serveur en attente de clients...");

		try {
			while (true) {
				Socket socket = serverSocket.accept();

				if (socket != null) {
					socket.setSoTimeout(timeout * 1000);
					
					System.out.println("Client connecté :\t\t" + socket);

					es.submit(new HighLevelServer(socket));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
