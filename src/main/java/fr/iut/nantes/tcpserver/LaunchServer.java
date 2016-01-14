package fr.iut.nantes.tcpserver;

import java.io.FileInputStream;
import java.util.Properties;

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
	public static void main(String args[]) throws Exception {
		// Chargement du fichier de propriétées
		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		
		if("low".equals(prop.getProperty("level"))) {
			LowLevelServer.main(args);
		} else if("high".equals(prop.getProperty("level"))) {
			HighLevelServer.main(args);
		} else {
			System.out.println("Erreur properties");
		}
	}
}
