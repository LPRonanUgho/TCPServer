package fr.iut.nantes.tcpserver.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client class
 */
public class Client implements Runnable {

	private int delay;
	Socket socket;

	/**
	 * Client constructor.
	 * Initialize client with a socket and default delay.
	 *
	 * @param socket which represents the client socket
	 */
	public Client(Socket socket) {
		this.socket = socket;
		this.delay = 0;
	}

	/**
	 * Client constructor.
	 * Initialize client with a socket and a specified sleep time.
	 *
	 * @param socket which represents the open tcp connection with a client.
	 * @param delay which represents client's sleep time.
	 **/
	public Client(Socket socket, int delay) {
		this(socket);
		this.delay = delay;
	}

	/**
	 * Client is runnable.
	 * When client is running we process to Read/Write (input/output standard).
	 * TODO : move responsability to the server which should offer echo service.
	 * The client should only write on standard input.
	 */
	public void run() {
		try {
			OutputStream output = socket.getOutputStream();
			byte[] line = new Scanner(socket.getInputStream()).nextLine().getBytes();

			Thread.sleep(delay);

			output.write(line);
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
