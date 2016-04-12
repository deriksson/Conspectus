/*
  A UDP client for sending queries to a Conspectus server.

  Usage:

  query <hostname> <port> <query>
*/

package main

import (
	"fmt"
	"net"
	"os"
)

const BUFLEN = 15000

func main() {
	if len(os.Args) != 4 {
		fmt.Fprintf(os.Stderr, "Usage: %s <host> <port> <search term>\n", os.Args[0])
		os.Exit(1)
	}

	service := os.Args[1] + ":" + os.Args[2]
	search := os.Args[3]

	udpAddr, err := net.ResolveUDPAddr("udp4", service)
	check("Unable to resolve address", err)

	conn, err := net.DialUDP("udp", nil, udpAddr)
	check("Unable to contact host", err)

	_, err = conn.Write([]byte(search))
	check("Unable to send query", err)

	var buf [BUFLEN]byte
	n, err := conn.Read(buf[0:])
	check("No response", err)

	if n > 0 {
		fmt.Println(string(buf[0:n]))
	}

	os.Exit(0)
}

func check(msg string, err error) {
	if err != nil {
		fmt.Fprintf(os.Stderr, "%s: %s.\n", msg, err.Error())
		os.Exit(1)
	}
}
