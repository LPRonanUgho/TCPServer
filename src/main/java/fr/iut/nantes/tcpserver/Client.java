package fr.iut.nantes.tcpserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	
	private String serverName;
	private int port;

	/**
	 * 
	 * @param serverName
	 */
	public Client(String serverName, int port) {
		this.serverName = serverName;
		this.port = port;
	}
	
	public void connect() {
		try {
			System.out.println("CLIENT : Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);
			System.out.println("CLIENT : Just connected to " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF("CLIENT : Hello from " + client.getLocalSocketAddress());
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			System.out.println("CLIENT : Server says " + in.readUTF());
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
}