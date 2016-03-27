/*
 * A UDP client for sending queries to a Conspectus server.
 *
 * Usage:
 * 
 * query <hostname> <port> <query>
 */

#include "query.h"

#include <stdio.h>   /* fprintf, perror */

#ifdef _WIN32
#include <winsock2.h>
#include <windef.h>
#pragma comment(lib, "ws2_32.lib") /* Winsock Library */
#else
#include <string.h>  /* memset          */
#include <stdlib.h>  /* exit            */
#include <unistd.h>  /* close           */
#include <netdb.h>   /* gethostbyname   */
#endif

#define BUFLEN 15000 
 
/* 
   Parameters:

   argv[1]: hostname
   arv[2]: port
   argv[3]: query
*/
int main(int argc, char **argv) {
  
  struct hostent *server;
  struct sockaddr_in serveraddr;  
  int portno, socketfd;
  
#ifdef _WIN32
  WSADATA wsa;
  int slen = sizeof(serveraddr);
#else
  socklen_t slen = sizeof(serveraddr);
#endif

  char buf[BUFLEN];
  
  /* Check command line arguments. */
  if (argc != 4) {
    fprintf(stderr, "Usage: %s <hostname> <port> <query>\n", argv[0]);
    exit(1);
  }

#ifdef _WIN32
  /* Initialise winsock. */
  if (WSAStartup(MAKEWORD(2,2),&wsa) != 0) {
    printf("Failed. Error Code : %d.\n", WSAGetLastError());
    exit(EXIT_FAILURE);
  }
#endif
 
  portno = atoi(argv[2]);

  if ((socketfd = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1) 
    error("Error opening socket.");
  
  /* Get the server's DNS entry. */
  server = gethostbyname(argv[1]);
  if (server == NULL) {
    fprintf(stderr, "No such host: %s\n", argv[1]);
    exit(1);
  }

  memset((char *) &serveraddr, 0, sizeof(serveraddr));
  serveraddr.sin_family = AF_INET;
  serveraddr.sin_port = htons(portno);
  memcpy((char *)server->h_addr_list[0], 
	(char *)&serveraddr.sin_addr.s_addr, server->h_length);

  /* Send the query. */
  if (sendto(socketfd, argv[3], strlen(argv[3]), 0, (struct sockaddr *) &serveraddr, slen) ==-1) 
      error("Error sending query.");

  /* Handle the reply. */
  memset(buf, '\0', BUFLEN);
  if (recvfrom(socketfd, buf, BUFLEN, 0, (struct sockaddr *) &serveraddr, &slen) == -1) 
    error("Error receiving reply.");

  if (strlen(buf) > 0)
    puts(buf);

#ifdef _WIN32
  closesocket(socketfd);
  WSACleanup();
#else
  close(socketfd);
#endif

  return 0;
}

void error(char *s) {
    perror(s);
    exit(1);
}
 
