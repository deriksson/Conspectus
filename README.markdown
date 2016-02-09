# Conspectus: A Simple UDP Server for Querying a Definitions Database

This project creates a simple Java UDP server, used to query a database of definitions. Each key (definiendum) in the database may correspond to one or several explanations (definientia), and each response from the server contains a lists of all definientia for the definiendum submitted in the query. This structure is typical of a thesaurus, and the server may for instance be used to look up synonyms. The database may also contain just one definiens per definiendum, in which case the functionality is more similar to that of a dictionary.

## Installation
1. Compile the source code and create a distribution archive:  
  `ant archive`
2.  update-rc.d myserver defaults
  
## Usage

Usage:

java se.abc.conspectus.server.ServerApplication <configuration file>

Example:

java -Djava.util.logging.config.file=src/config/logging.properties -classpath dist/lib/conspectus-20160208.jar se.abc.conspectus.server.ServerApplication src/config/conspectus-sv.xml

java -Djava.util.logging.config.file=src/config/logging.properties -classpath dist/lib/conspectus-20160208.jar se.abc.conspectus.server.ServerApplication src/config/conspectus-en.xml

Once the server is running, you can query it like this, using Netcat:

echo -n "övning" | nc -q 1 -u localhost 5005 ; echo

Or like this with the supplied Java client:

java se.abc.thesaurus.ThesaurusClient localhost 5005 färdighet

## Exporting the database

Usage:

java -classpath build se.abc.thesaurus.Export <xml file> 

Example:

java -classpath build se.abc.conspectus.client.ExportApplication data/mobythes.txt se.abc.conspectus.parser.CSVParser > ~/ergon/doc/thesaurus/english-synonymns.txt
