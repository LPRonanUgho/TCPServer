package fr.iut.nantes.tcpserver;

import fr.iut.nantes.tcpserver.client.Client;

public class LaunchClient {

	public static void main(String[] args) {
		try {
			Client.main(args);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
