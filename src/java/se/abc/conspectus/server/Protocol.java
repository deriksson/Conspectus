package se.abc.thesaurus;

/**
 * This class defines the communication protocol of the Thesaurus server.
 */
final class Protocol {

	public static final int MAX_QUERY_LENGTH = 256;
	public static final int MAX_RESPONSE_LENGTH = 1024;

	/* This class should never be instantiated. */
	private Protocol() {
	}
}
