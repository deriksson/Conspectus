/*
 * A UDP client for sending queries to a Conspectus server.
 *
 * Usage:
 * 
 * query <hostname> <port> <query>
 */
#include "query.h"
#include <stdio.h> 
#include <string.h> 
#include <stdlib.h> 
#include <unistd.h>
#include <netdb.h> /* gethostbyname */
#include <arpa/inet.h>
#include <sys/socket.h>

#define BUFLEN 1024 
 
int main(int argc, char **argv) {
  char *query, *hostname;
  struct hostent *server;
  struct sockaddr_in serveraddr;  
  int portno, socketfd, slen=sizeof(serveraddr);
  char buf[BUFLEN];
  
  /* Check command line arguments. */
  if (argc != 4) {
    fprintf(stderr, "Usage: %s <hostname> <port> <query>\n", argv[0]);
    exit(1);
  }
     
  hostname = argv[1];
  portno = atoi(argv[2]);
  query = argv[3];
     
  if ((socketfd = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1) 
    error("Error opening socket.");
  
  /* Get the server's DNS entry */
  server = gethostbyname(hostname);
  if (server == NULL) {
    fprintf(stderr,"No such host as %s\n", hostname);
    exit(1);
  }

  memset((char *) &serveraddr, 0, sizeof(serveraddr));
  serveraddr.sin_family = AF_INET;
  serveraddr.sin_port = htons(portno);
  bcopy((char *)server->h_addr, 
	(char *)&serveraddr.sin_addr.s_addr, server->h_length);

  /* Send the query. */
  if (sendto(socketfd, query, strlen(query) , 0 , (struct sockaddr *) &serveraddr, slen)==-1) 
      error("Error sending query.");

  /* Handle the reply. */
  memset(buf,'\0', BUFLEN);
  if (recvfrom(socketfd, buf, BUFLEN, 0, (struct sockaddr *) &serveraddr, &slen) == -1) 
    error("Error receiving reply.");
         
  puts(buf);

  close(socketfd);
  return 0;
}

void error(char *s) {
    perror(s);
    exit(1);
}
 