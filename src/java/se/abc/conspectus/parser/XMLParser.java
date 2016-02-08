package se.abc.thesaurus;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

final class Parser {

	Parser() {
	}

	Map<String, Set<String>> collect(final InputStream thesaurus)
			throws XMLStreamException {
		final XMLInputFactory factory = XMLInputFactory.newInstance();
		final XMLStreamReader reader = factory.createXMLStreamReader(thesaurus);

		final Map<String, Set<String>> map = new TreeMap<>();

		String text = null;
		Set<String> synonyms = null;
		while (reader.hasNext()) {
			final int event = reader.next();

			switch (event) {
			case XMLStreamConstants.END_ELEMENT: {
				if ("w1".equals(reader.getLocalName())) {
					if (text == null)
						throw new RuntimeException(
								"Invalid record: no definiendum.");

					synonyms = map.get(text);
					if (synonyms == null) {
						synonyms = new TreeSet<String>();
						map.put(text, synonyms);
					}
				}
				if ("w2".equals(reader.getLocalName())) {
					if (text == null)
						throw new RuntimeException(
								"Invalid record: no definiens.");
					if (synonyms == null)
						throw new RuntimeException(
								"Invalid record: definiens lacks definiendum.");

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
	}
}
