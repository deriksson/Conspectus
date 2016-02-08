package se.abc.conspectus.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public interface DefinitionsParser {

	Map<String, Set<String>> parse(final Path definitions) throws IOException;
}
