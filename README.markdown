# Conspectus: A Fast Java UDP Server for Querying a Definitions Database

This project creates a simple Java UDP server, used to query a database of definitions. Each key (definiendum) in the database may correspond to one or several explanations (definientia), and each response from the server contains a lists of all definientia for the definiendum submitted in the query. This structure is typical of a thesaurus, and the server may for instance be used to look up synonyms. The database may also contain just one definiens per definiendum, in which case the functionality is more similar to that of a dictionary.

## Installation

You can run the conspectus server from a command line console or as a system service. To run it from a command line simply do the following:

1. Compile the source code:  
  `ant`

2. Check that the configuration files src/config/conspectus-sv.xml and sr/config/logging.properties point to sensible directories on your system. The properties files conspectus-??.xml contain paths to the data sources. There is more information about these below, under the heading “Data”.

3. Issue a command like the following (you might need to adjust the date value in the name of the jar file):  
  `java -Djava.util.logging.config.file=src/config/logging.properties -classpath dist/lib/conspectus-20160208.jar se.abc.conspectus.server.ServerApplication src/config/conspectus-sv.xml`

The following steps will install the server as a system service on a Debian-based Linux system:

1. Create a distribution archive:
   `ant archive`

2. Unzip the archive, that was created in the dist directory, into a suitable folder, e.g. “/opt/conspectus”.

3. Adjust the settings in the configuration files `conspectus-sv.xml` and `logging.properties`.

4. Copy the script `conspectus` to `/etc/init.d/` and adjust the values of the variables at the top of the script.

5. Install the service with the command `sudo update-rc.d conspectus defaults`.

6. The server will now start automatically when the system is booted. To start and stop the service manually, issue the following commands:
   `sudo /etc/init.d/conspectus start`  
   `sudo /etc/init.d/conspectus stop`

The distribution also contains a simple UDP client written in ANSI C (C99). It is faster than the Java client that is also provided. If you wish to use it, you may install it with the following commands on a Linux system. The executable binary will reside in the directory `/usr/local/bin/`.

1. `cd src/c`  

2. `make`  

3. `sudo make install`  

3. `make distclean`  

## Usage

There are several possible ways of sending queries to the Conspectus server:

1. Using the Java client provided with the distribution:  
   `java -classpath build se.abc.conspectus.client.ClientApplication <host> <port number> <search term>`  
   E.g.
   `java -classpath build se.abc.conspectus.client.ClientApplication localhost 5006 good`

2. Using the Netcat application:  
   `echo -n "good" | nc -q 1 -u localhost 5005 ; echo`

3. The fastest method is to use the C client that comes with the distribution:  
   `query <hostname> <port> <query>`

## Data

When the server starts up the definitions are read from an input stream. Currently two data formats are supported, but you can easily use some other format by adding your own parser. The parser class is loaded at runtime based on a setting in the properties XML file. The out of the box parsers are `se.abc.conspectus.parser.CSVParser` and `se.abc.conspectus.parser.CSVParser`.

Usage:

java -classpath build se.abc.thesaurus.Export <xml file> 

Example:

java -classpath build se.abc.conspectus.client.ExportApplication data/mobythes.txt se.abc.conspectus.parser.CSVParser > ~/ergon/doc/thesaurus/english-synonymns.txt
