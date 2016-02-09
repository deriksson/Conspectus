package se.abc.conspectus.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public final class XMLParser implements DefinitionsParser {

	public XMLParser() {
	}

	@Override
	public Map<String, Set<String>> apply(final Path definitions)  {
		final XMLInputFactory factory = XMLInputFactory.newInstance();

		try (InputStream stream = new FileInputStream(definitions.toFile())) {
			final XMLStreamReader reader = factory.createXMLStreamReader(stream);
			final Map<String, Set<String>> map = new HashMap<>();

			String text = null;
			Set<String> synonyms = null;
			while (reader.hasNext()) {
				final int event = reader.next();

				switch (event) {
				case XMLStreamConstants.END_ELEMENT: {
					if ("w1".equals(reader.getLocalName())) {
						if (text == null)
							throw new RuntimeException("Invalid record: no definiendum.");

						synonyms = map.get(text);
						if (synonyms == null) {
							synonyms = new TreeSet<String>();
							map.put(text, synonyms);
						}
					}
					if ("w2".equals(reader.getLocalName())) {
						if (text == null)
							throw new RuntimeException("Invalid record: no definiens.");
						if (synonyms == null)
							throw new RuntimeException("Invalid record: definiens lacks definiendum.");

						synonyms.add(text);
					}
					break;
				}
				case XMLStreamConstants.CHARACTERS: {
					text = reader.getText().trim();
					break;
				}
				}
			}
			return map;
		} catch (XMLStreamException|IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
