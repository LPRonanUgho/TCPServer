package fr.iut.nantes.tcpserver;

import fr.iut.nantes.tcpserver.client.Client;

public class LaunchClient {
	/**
	 * Méthode main du launcher
	 * @param args [String[]]
	 * @throws Exception
	 */
	public static void main(String[] args) {
		try {
			Client.main(args);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
