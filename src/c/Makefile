CC=gcc
CFLAGS=-I. -std=c11 -pedantic
DEPS = query.h
INSTALL = /usr/bin/install -c

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

query: query.o
	$(CC) -o query query.o -I.

clean:
	rm -f *.o *~

distclean: clean
	rm -f query

install: query
	$(INSTALL) query /usr/local/bin/
