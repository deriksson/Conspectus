This project creates a simple UDP server.

*Running the server*

Usage:

java se.abc.thesaurus.ThesaurusServer <port number> <xml file>

Example:

java -Djava.util.logging.config.file=src/configuration/logging.properties -classpath build se.abc.thesaurus.ThesaurusServer 5005 data/synpairs.xml

Once the server is running, you can query it like this uing Netcat:

echo -n "övning" | nc -q 1 -u localhost 5005 ; echo

Or like this with the supplied Java client:

java se.abc.thesaurus.ThesaurusClient localhost 5005 färdighet

*Exporting the database*

Usage:

java -classpath build se.abc.thesaurus.Export <xml file> 

Example:

java -classpath build se.abc.thesaurus.Export data/synpairs-2013.xml > ~/ergon/doc/thesaurus/sylex-2013-12-28.txt
