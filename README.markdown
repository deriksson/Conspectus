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

The distribution also contains a simple UDP client written in ANSI C (C11). It is faster than the Java client that is also provided. If you wish to use it, you may install it with the following commands on a Linux system. The executable binary will reside in the directory `/usr/local/bin/`.

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
   `query <host name> <port> <query>`

## Data

When the server starts up, the definitions are read from an input stream. Currently two data formats are supported, but you can easily use some other format by adding your own parser. The parser class is loaded at run time based on settings in the properties XML file. The out of the box parsers are `se.abc.conspectus.parser.CSVParser` and `se.abc.conspectus.parser.XMLParser`.

### XML Parser
The built in XML parser accepts any valid XML that contains sibling elements with the names “w1” and “w2”. The contents of the w1 element should contain the definiendum and that of the w2 element the definiens. If there are several w1 elements with the same contents, the definientia are merged into a list that is mapped to the same definiendum. The sibling elements might look like this: `<w1>abonnera</w1><w2>boka</w2>`.

An example of this structure is Professor Viggo Kann's Synlex (cf. <http://folkets-lexikon.csc.kth.se/synlex.html>), which is freely available here: <http://folkets-lexikon.csc.kth.se/lexikon/synpairs.xml>. Synlex is a Swedish language thesaurus. There is a copy in the data directory: `thesaurus-sv.xml`.

### Text File Parser
The text file parser reads a file consisting of one record per line. Each record is a list of entries separated by commas, but the number of entries per record may differ. The first entry of each record is the key value (definiendum).

### Export
There is a data export command line program, that writes the database to a text file. The output text file has the following structure:  
>  `<definiendum>: <definiens 1>, <definiens 2>, ... <definiens n>`  

An example from a Swedish thesaurus:  
>  `tung: krävande, mödosam, svår, svårmodig`

Text files of this structure may be searched using the shell scripts `ensyn` and `svsyn` in the src/sh directory.


The following are the steps to run the export program:

1. Compile the source code:
>	`ant compile`

2. Run the Java application. Usage:  
>   `java se.abc.conspectus.client.ExportApplication <file> <parser>`  

An example:
>   `java -classpath build se.abc.conspectus.client.ExportApplication data/thesaurus-sv.xml se.abc.conspectus.parser.XMLParser`



