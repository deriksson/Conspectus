package se.abc.conspectus.parser;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface DefinitionsParser extends Function<Path,Map<String, Set<String>>> {

	Map<String, Set<String>> apply(final Path definitions);
}
