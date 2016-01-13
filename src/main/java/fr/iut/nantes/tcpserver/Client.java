package fr.iut.nantes.tcpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client class
 */
public class Client {

	private static Socket serverSocket = null;
	private static String messageFromUser = null;
	private static BufferedReader readerFromServer = null;
	private static DataOutputStream writeToServer = null;
	private static BufferedReader readerFromUser = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) {
		try {
			serverSocket = new Socket(InetAddress.getLocalHost(), 1234);

			readerFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			writeToServer = new DataOutputStream(serverSocket.getOutputStream());
			
			System.out.println("Hello, enter exit or quit to exit.");
			
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
