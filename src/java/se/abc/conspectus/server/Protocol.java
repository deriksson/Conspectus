package se.abc.conspectus.server;

/**
 * This class defines the application level communication protocol of the
 * Conspectus server.
 */
public final class Protocol {

	public static final int MAX_QUERY_LENGTH = 256;
	public static final int MAX_RESPONSE_LENGTH = 1024;

	/* This class should never be instantiated. */
	private Protocol() {
	}
}
