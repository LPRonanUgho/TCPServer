package fr.iut.nantes.tcpserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import fr.iut.nantes.tcpserver.server.HighLevelServer;
import fr.iut.nantes.tcpserver.server.LowLevelServer;


/**
 * Launcher du server (Haut/Bas niveau) en fonction de la configuration
 * @author Ronan / Ugho
 */
public class LaunchServer {
	/**
	 * Méthode main du launcher
	 * @param args [String[]]
	 * @throws Exception
	 */
	public static void main(String args[]){
		// Chargement du fichier de propriétées
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream("config.properties"));
			
			if("low".equals(prop.getProperty("level"))) {
				LowLevelServer.main(args);
			} else if("high".equals(prop.getProperty("level"))) {
				HighLevelServer.main(args);
			} else {
				System.err.println("Propriété \"level\" manquante ou corrompue");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Fichier de configuration introuvable (config.properties)");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
