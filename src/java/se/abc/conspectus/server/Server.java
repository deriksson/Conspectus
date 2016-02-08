package se.abc.conspectus.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * If the server is used over Internet the following modifications are necessary. You should use DatagramSocket's 
 * bind method to bind it to your Internet interface, otherwise it only listens on 127.0.0.1 or localhost. Like this:
 *
 * DatagramSocket socket = new DatagramSocket(null);
 * socket.bind(new InetSocketAddress(InetAddress.getByName("your.ip.add.ress"), 5005);
 */
public final class Server implements AutoCloseable, Runnable {
	private static final Logger logger = Logger.getLogger(Server.class.getName());

	private boolean running = false;
	private DatagramSocket socket = null;
	private final int port;
	private final Map<String, Set<String>> definitions;

	public Server(final int port, final Map<String, Set<String>> definitions) {
		this.port = port;
		this.definitions = definitions;
	}

	@Override
	public void run() {

		try {
			socket = new DatagramSocket(port);
			running = true;
			logger.info("Server is up.");

			while (running) {
				final byte[] queryBuffer = new byte[Protocol.MAX_QUERY_LENGTH];
				final DatagramPacket queryPacket = new DatagramPacket(queryBuffer, queryBuffer.length);
				socket.receive(queryPacket);
				final String message = new String(queryPacket.getData(), 0, queryPacket.getLength());
				logger.info(String.format("Query: \"%s\".", message));
				final Set<String> response = definitions.get(message);
				/* TODO: Check length of response. Limit to predefined size. */
				final byte[] responseData = response == null ? "".getBytes() : String.join(", ", response).getBytes();
				final DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length,
						queryPacket.getAddress(), queryPacket.getPort());
				socket.send(responsePacket);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			if (socket != null && socket.isConnected())
				socket.close();
		}
	}

	@Override
	public synchronized void close() throws Exception {
		running = false;
		if (socket != null && socket.isConnected())
			socket.close();
		logger.info("Server is turned off.");
	}
}
