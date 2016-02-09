package se.abc.conspectus.client;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import se.abc.conspectus.parser.DefinitionsParser;

/**
 * This class constitutes a program entry point and is executed as an
 * application from a command line console. The class should never be
 * instantiated. It prints a sorted textual representation of a dictionary.
 */
public final class ExportApplication {

	/* This class should never be instantiated. */
	private ExportApplication() {
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.printf("Usage: java %s <file> <parser>\n", ExportApplication.class.getName());
			System.exit(1);
		}

		try {
			final DefinitionsParser factory = (DefinitionsParser) Class.forName(args[1]).newInstance();
			final Map<String, Set<String>> thesaurus = factory.apply(Paths.get(args[0]));
			thesaurus.entrySet().stream().sorted()
					.map(entry -> String.format("%s: %s", entry.getKey(), String.join(", ", entry.getValue())))
					.forEachOrdered(System.out::println);
		} catch (RuntimeException e) {
			System.err.printf("Unable to parse file: \"%s\".\n", args[0], e.getMessage());
			System.exit(2);
		}  catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.err.printf("No such parser: %s.\n", args[0], e.getMessage());
			System.exit(4);
		}
	}
}
