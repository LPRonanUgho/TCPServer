package fr.iut.nantes.tcpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

class Server implements Runnable {

	private Socket clientSocket;
	private final static int timeOut = 5;

	public Server(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public static void main(String args[]) throws Exception {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(1234);
		} catch (IOException ioe) {
			System.out.println("Error finding port");
			System.exit(1);
		}

		System.out.println("Server listening...");

		try {
			while (true) {
				Socket socket = serverSocket.accept();
				socket.setSoTimeout(timeOut * 1000);

				if (socket != null) {
					System.out.println("Client connected at :\t\t" + socket);
				}

				Thread t = new Thread(new Server(socket));
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		DataOutputStream writeToClient = null;
		BufferedReader readerFromClient = null;
		
		try {
			try {
				System.out.println("Client run at :\t\t\t" + clientSocket);
				
				writeToClient = new DataOutputStream(clientSocket.getOutputStream());
				readerFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				while(true) {
					String messageFromClient = readerFromClient.readLine();
					
					System.out.println(clientSocket + " say : " + messageFromClient);

					writeToClient.writeBytes("Echo from server : " + messageFromClient);
					writeToClient.write(13);
					writeToClient.write(10);
					writeToClient.flush();
				}
			} finally {
				//clientSocket.close();
			}
		} catch(SocketTimeoutException ste) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
