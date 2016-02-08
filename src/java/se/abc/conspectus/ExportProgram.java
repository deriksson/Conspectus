package se.abc.thesaurus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

/**
 * This class implements a program entry point and is called as an application
 * from a command line console. The class should never be instantiated. It
 * prints a sorted textual representation of the dictionary.
 */
final class ExportProgram {

	/* This class should never be instantiated. */
	private ExportProgram() {
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.printf("Usage: java %s <file>\n", ExportProgram.class.getName());
			System.exit(1);
		}

		final Parser factory = new Parser();
		try {
			final Map<String, Set<String>> thesaurus = factory.collect(new FileInputStream(new File(args[0])));
			thesaurus.entrySet().stream()
					.map(entry -> String.format("%s: %s", entry.getKey(), String.join(", ", entry.getValue())))
					.forEachOrdered(System.out::println);
		} catch (FileNotFoundException e) {
			System.err.printf("File not found: \"%s\".\n", args[0], e.getMessage());
			System.exit(2);
		} catch (XMLStreamException e) {
			System.err.printf("Error parsing XML file: \"%s\". %s\n", args[0], e.getMessage());
			System.exit(3);
		}
	}
}
