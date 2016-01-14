package fr.iut.nantes.tcpserver.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Serveur - Bas niveau
 * @author Ronan / Ugho
 */
public class LowLevelServer extends AbstractServer {
	
	/**
	 * Constructeur du serveur
	 * @param clientSocket [Socket]
	 */
	public LowLevelServer(Socket clientSocket) {
		super(clientSocket);
	}

	/**
	 * Méthode main du serveur
	 * @param args [String[]]
	 * @throws Exception
	 */
	public static void main(String args[]){
		
		loadProperties();
		
		ServerSocket serverSocket = getServerSocket();

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
}
