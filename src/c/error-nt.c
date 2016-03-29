#include <stdio.h>      /* printf */
#include <winsock2.h>
#include <windef.h>

void error(const char *s) {
  printf("%s Error code: %d.\n", s, WSAGetLastError());
  exit(EXIT_FAILURE);
}
