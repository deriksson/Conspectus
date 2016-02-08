package se.abc.thesaurus;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This class is used to send queries to a UDP server from a command line.
 */
public class Client {

	/* This class should never be instantiated. */
	private Client() {
	}

	public static void main(final String[] args) throws IOException {
		if (args.length != 3) {
			System.err.printf("Usage: java %s <host> <port> <query>\n",
					Client.class.getName());
			System.exit(1);
		}

		final InetAddress host = InetAddress.getByName(args[0]);
		final int port = Integer.parseInt(args[1]);
		final String query = args[2];

		try (final DatagramSocket socket = new DatagramSocket()) {
			final byte[] queryData = query.getBytes();

			final DatagramPacket queryPacket = new DatagramPacket(queryData,
					queryData.length, host, port);
			socket.send(queryPacket);

			final byte[] responseBuffer = new byte[Protocol.MAX_RESPONSE_LENGTH];
			final DatagramPacket responsePacket = new DatagramPacket(
					responseBuffer, responseBuffer.length, host, port);
			socket.receive(responsePacket);
			final String response = new String(responsePacket.getData(), 0,
					responsePacket.getLength());
			System.out.println(response);
		}
	}

}
