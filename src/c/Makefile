CC=gcc
CFLAGS=-I. -std=c11 -pedantic
DEPS = query.h
INSTALL = /usr/bin/install -c
OBJ=query.o error-linux.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

query: $(OBJ)
	$(CC) -o query $(OBJ) -I.

clean:
	rm -f *.o *~

distclean: clean
	rm -f query

install: query
	$(INSTALL) query /usr/local/bin/
