#ANSI C UDP Client
This directory contains the source code for a UDP client for sending queries to the Conspectus server. 
The client code is written in ANSI C (C99) and might be used instead of the Java client for ligthening fast 
execution. The client program should typically be installed in the `/usr/local/bin` directory.

##Installation

  make
  sudo make install

##Usage
 
  query <hostname> <port> <query>

