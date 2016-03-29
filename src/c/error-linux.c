#include <stdio.h>   /* fprintf, perror */
#include <stdlib.h>   /* exit            */

void error(const char *s) {
  perror(s);
  exit(1);
}
