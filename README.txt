This project creates a simple UDP server.

*Installation*

  ant
  update-rc.d myserver defaults
  
*Running the server*

Usage:

java se.abc.conspectus.server.ServerApplication <configuration file>

Example:

java -Djava.util.logging.config.file=src/config/logging.properties -classpath dist/lib/conspectus-20160208.jar se.abc.conspectus.server.ServerApplication src/config/conspectus-sv.xml

java -Djava.util.logging.config.file=src/config/logging.properties -classpath dist/lib/conspectus-20160208.jar se.abc.conspectus.server.ServerApplication src/config/conspectus-en.xml

Once the server is running, you can query it like this, uing Netcat:

echo -n "övning" | nc -q 1 -u localhost 5005 ; echo

Or like this with the supplied Java client:

java se.abc.thesaurus.ThesaurusClient localhost 5005 färdighet

*Exporting the database*

Usage:

java -classpath build se.abc.thesaurus.Export <xml file> 

Example:

java -classpath build se.abc.conspectus.client.ExportApplication data/mobythes.txt se.abc.conspectus.parser.CSVParser > ~/ergon/doc/thesaurus/english-synonymns.txt
