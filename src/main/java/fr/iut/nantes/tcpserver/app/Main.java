package fr.iut.nantes.tcpserver.app;

import fr.iut.nantes.tcpserver.server.HighLevelServer;
import fr.iut.nantes.tcpserver.server.ServerFactory;

public class Main {
	public static void main(String[] args) {

		ServerFactory serverFactory = new ServerFactory();
		HighLevelServer s = serverFactory.getHighLevelServer();

		s.setPoolSize(2);
		s.setPort(9900);
		s.setResponseDelay(2000);
		s.launch();
	}
}
