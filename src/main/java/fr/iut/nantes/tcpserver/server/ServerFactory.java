package fr.iut.nantes.tcpserver.server;

public class ServerFactory {

	/**
	 * Get high level server instance.
	 *
	 * @return high level server
	 */
	public HighLevelServer getHighLevelServer(){
		return new HighLevelServer();
	}

	/**
	 * Get low level server instance.
	 *
	 * @return low level server
	 */
	public LowLevelServer getLowLevelServer(){
		return new LowLevelServer();
	}

	/**
	 * Get server based on configuration.
	 * The server level depends on configuration.
	 *
	 */
	public AbstractServer getServer(String configuration){
		// TODO
		return null;
	}
}
