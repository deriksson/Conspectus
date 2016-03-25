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

/**
   This parser handles text files containing records like the
   following. The records are separated by a line feed character.

   <key>:<element1>,<element 2>,<element n>

   A sample record: 

   zone:area,bailiwick,belt
 */
public final class ThesaurusParser implements DefinitionsParser {

	public ThesaurusParser() {
	}

	@Override
	public Map<String, Set<String>> apply(Path definitions) {
		try (Stream<String> stream = Files.lines(definitions)) {
			final Map<String, Set<String>> map = new HashMap<>();

			stream.forEach(s -> {
				final String[] values = s.split(":");
				if (values.length > 1)
					map.put(values[0], new TreeSet<String>(Arrays.asList(values[1].split(","))));
			});

			return map;
		} catch (IOException e) {
			throw new RuntimeException("Unable to parse file: " + e.getMessage(), e);
		}
	}
}
