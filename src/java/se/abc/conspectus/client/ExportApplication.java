package se.abc.conspectus.client;

import java.io.FileNotFoundException;
import java.io.IOException;
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
			final Map<String, Set<String>> thesaurus = factory.parse(Paths.get(args[0]));
			thesaurus.entrySet().stream().sorted()
					.map(entry -> String.format("%s: %s", entry.getKey(), String.join(", ", entry.getValue())))
					.forEachOrdered(System.out::println);
		} catch (FileNotFoundException e) {
			System.err.printf("File not found: \"%s\".\n", args[0], e.getMessage());
			System.exit(2);
		} catch (IOException e) {
			System.err.printf("Error parsing file: \"%s\". %s\n", args[0], e.getMessage());
			System.exit(3);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.err.printf("No such parser: %s.\n", args[0], e.getMessage());
			System.exit(4);
		}
	}
}
