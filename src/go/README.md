# Go UDP Client

This directory contains source code for a UDP client, which may be
used to send queries to the Conspectus server. The client code is
written in Go and may be used instead of the Java client for
lightening fast execution. The install option in the make file will
copy the binary to the `/usr/local/bin` directory.

## Installation

  `make`  
  `sudo make install`
  `make distclean`

## Usage
 
  `query <host name> <port> <query>`

## Windows version

The source code may also be compiled for the Windows NT operating
system. Use the following make command:

  `make nt`

The program may be tested on a Linux platform using Wine with a
command like the following:

  `wine query.exe localhost 5005 good`

The program outputs UTF 8. Issue the following command to use this
code page in an NT console window.

  `chcp 65001`
