package fr.iut.nantes.tcpserver.server;

import java.net.ServerSocket;

/**
 * AbstractServer class
 * See EchoProtocol RFC https://tools.ietf.org/html/rfc862
 */
public abstract class AbstractServer {

	protected int port;
	protected int poolSize;
	protected int responseDelay;
	protected ServerSocket socket;


	/**
	 * AbstractServer constructor.
	 * Initialize the server (port, pool size and response delay).
	 */
	public AbstractServer() {
		this.port = 9900;
		this.poolSize = 10;
		this.responseDelay = 0;
	}

	/**
	 * Set pool size.
	 *
	 * This method is not specific to higher level server.
	 * With low level server the pool size corresponds to the number of threads.
	 *
	 * @param poolSize which represents the pool size or the number of threads
	 */
	public void setPoolSize(int poolSize){
		this.poolSize = poolSize;
	}

	/**
	 * Set server port.
	 *
	 * @param port which represents server port
	 */
	public void setPort(int port){
		this.port = port;
	}

	/**
	 * Set response delay.
	 *
	 * @param delay which represents the server response delay
	 */
	public void setResponseDelay(int delay){
		this.responseDelay = delay;
	}

	/**
	 * TODO : Adding abstract echo function
	 */

	/**
	 * Launch the server.
	 */
	public abstract void launch();

}
