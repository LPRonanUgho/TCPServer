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
 * Client class
 */
public class LaunchClient {

	private static Socket serverSocket = null;
	private static String messageFromUser = null;
	private static BufferedReader readerFromServer = null;
	private static DataOutputStream writeToServer = null;
	private static BufferedReader readerFromUser = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws Exception {
		
		Properties prop = new Properties();
		// load a properties file
		prop.load(new FileInputStream("config.properties"));
		
		try {
			serverSocket = new Socket(InetAddress.getLocalHost(), Integer.parseInt(prop.getProperty("port")));

			readerFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			
			String alertFromServer = readerFromServer.readLine();
			System.out.println(alertFromServer);
			
			if("Server say : too many connection".equals(alertFromServer)) {
				System.exit(0);
			}
			
			writeToServer = new DataOutputStream(serverSocket.getOutputStream());
			
			System.out.println("Enter exit or quit to exit.");
			
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
			System.out.println("Unknow host");
			System.exit(0);
		} catch (IOException ioe) {
			System.err.println("Couldn't connect to server");
			System.exit(0);
		}
	}
}
