package fr.iut.nantes.tcpserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.iut.nantes.tcpserver.client.Client;

public class HighLevelServer extends AbstractServer {

	/**
	 * Executor service.
	 *
	 * Usefull to have thread pool strategies.
	 */
	private ExecutorService executorService;

	/**
	 * High level server constructor which initializes the executor service.
	 *
	 * Executor service is initialized with fixed thread pool.
	 * We use newFixedThreadPool factory method. It's usefull to
	 * avoid overhead by specifying pool size.
	 */
	public HighLevelServer() {
		super();
		executorService = Executors.newFixedThreadPool(poolSize);
	}

	/**
	 * Launch high level server as a (echo) service
	 * TODO : improve this part by adding explicit echo service.
	 */
	public void launch() {
		try {
			socket = new ServerSocket(port);
			System.out.println("TCP server is running on " + this.port + "...");

			while (true) {
				Socket tcpClient = socket.accept();
				Client client = new Client(tcpClient, responseDelay);

				// Here we run the client
				executorService.submit(client);
				System.out.println("Server is listening new client...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
