package fr.iut.nantes.tcpserver;

import java.net.*;
import java.io.*;


public class Server extends Thread {
	private ServerSocket serverSocket;	
	
	/**
	 * 
	 * @param port
	 * @param timeOut
	 * @throws IOException 
	 */
	public Server(int port, int timeOut) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(timeOut);
	}

	public void run() {
		while(true) {
			try {
				System.out.println("SERVER : Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("SERVER : Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());
				System.out.println(in.readUTF());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("SERVER : Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("SERVER : Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}