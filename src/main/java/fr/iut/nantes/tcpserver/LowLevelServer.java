package fr.iut.nantes.tcpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Properties;

class LowLevelServer implements Runnable {

	private Socket clientSocket;
	private static int maxConnection;
	private static int runningConnections = 0;

	public LowLevelServer(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public static void main(String args[]) throws Exception {
		Properties prop = new Properties();
		// load a properties file
		prop.load(new FileInputStream("config.properties"));

		maxConnection = Integer.parseInt(prop.getProperty("maxConnection"));

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(Integer.parseInt(prop
					.getProperty("port")));
		} catch (IOException ioe) {
			System.out.println("Error finding port");
			System.exit(1);
		}

		System.out.println("Server listening...");

		try {
			while (true) {
				Socket socket = serverSocket.accept();
				socket.setSoTimeout(Integer.parseInt(prop
						.getProperty("timeout")) * 1000);

				if (socket != null) {
					System.out.println("Client connected at :\t\t" + socket);
				}

				Thread t = new Thread(new LowLevelServer(socket));
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		DataOutputStream writeToClient = null;
		BufferedReader readerFromClient = null;
		boolean bool_tooManyConnection = false;

		try {
			System.out.println("Client run at :\t\t\t" + clientSocket);

			writeToClient = new DataOutputStream(clientSocket.getOutputStream());

			if (runningConnections < maxConnection) {
				writeToClient.writeBytes("Server say Hello !");
				writeToClient.write(13);
				writeToClient.write(10);
				writeToClient.flush();

				runningConnections++;

				System.out.println("Connected cients : " + runningConnections);
			} else {
				writeToClient.writeBytes("Server say : too many connection");
				writeToClient.write(13);
				writeToClient.write(10);
				writeToClient.flush();

				clientSocket.close();
				bool_tooManyConnection = true;
			}

			readerFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			while (true) {
				String messageFromClient = readerFromClient.readLine();

				System.out.println(clientSocket + " say : " + messageFromClient);

				writeToClient.writeBytes("Echo from server : " + messageFromClient);
				writeToClient.write(13);
				writeToClient.write(10);
				writeToClient.flush();
			}
		} catch (SocketTimeoutException ste) {
			System.out.println("Client disconnect by timeout :\t" + clientSocket);

			try {
				writeToClient.writeBytes("exit");
				writeToClient.write(13);
				writeToClient.write(10);
				writeToClient.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SocketException se) {
			System.out.println("Client disconnect :\t\t" + clientSocket);
		} catch (IOException ioe) {
			System.out.println(ioe);
		} finally {
			try {
				clientSocket.close();
				if(!bool_tooManyConnection) {
					runningConnections--;
				}

				System.out.println("Connected cients : " + runningConnections);

				Thread.currentThread().interrupt();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
