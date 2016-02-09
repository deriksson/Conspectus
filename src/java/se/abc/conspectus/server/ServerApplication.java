package se.abc.conspectus.server;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import se.abc.conspectus.parser.DefinitionsParser;

/**
 * This is the main application entry point. The class should not be
 * instantiated.
 */
public final class ServerApplication {
	private static final Logger logger = Logger.getLogger(ServerApplication.class.getName());

	/* This class should never be instantiated. */
	private ServerApplication() {
	}

	public static void main(String[] arg) {
		if (arg.length != 1) {
			final String message = String.format("Usage: java %s <configuration file>",
					ServerApplication.class.getName());
			logger.log(Level.SEVERE, message);
			System.err.println(message);
			System.exit(1);
		}

		try {
			final InputStream in = new FileInputStream(arg[0]);
			final Properties properties = new Properties();
			properties.loadFromXML(in);
			in.close();

			final Function<Path, Map<String, Set<String>>> factory = (DefinitionsParser) Class
					.forName(properties.getProperty("data.parser")).newInstance();
			final Map<String, Set<String>> thesaurus = factory.apply(Paths.get(properties.getProperty("data.file")));

			final Server server = new Server(Integer.parseInt(properties.getProperty("server.port")), thesaurus);
			/*
			 * We create a thread that will close the server when the JVM
			 * terminates. This is useful if the server runs as a system
			 * service.
			 */
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					server.close();
				} catch (Exception e) {
					logger.log(Level.WARNING, e.getMessage());
					throw new RuntimeException(e.getMessage(), e);
				}
			}));
			server.run();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			System.err.println(e.getMessage());
			System.exit(2);
		}
		System.exit(0);
	}
}
