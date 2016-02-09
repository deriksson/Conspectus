package se.abc.conspectus.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public final class CSVParser implements DefinitionsParser {

	public CSVParser() {
	}

	/**
	 * @throws RuntimeException If the file can not be read.
	 */
	@Override
	public Map<String, Set<String>> apply(final Path definitions) {
		try (Stream<String> stream = Files.lines(definitions)) {
			/*
			 * HashMap gives slightly faster lookups than TreeMap in this
			 * application. Approximately 5 milliseconds versus 8 milliseconds
			 * on my computer using a dictionary containing 30000 records.
			 */
			final Map<String, Set<String>> map = new HashMap<>();

			stream.forEach(s -> {
				final String[] values = s.split(",");
				if (values.length > 1)
					map.put(values[0],
							new TreeSet<String>(
									/*
									 * Using Arrays.copyOfRange(values, 1,
									 * values.length) is slightly faster, but
									 * perhaps less obvious than subList.
									 */
									Arrays.<String> asList(values).subList(1, values.length)));
			});

			return map;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
}
