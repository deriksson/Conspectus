CC=gcc
CFLAGS=-I.
DEPS = query.h

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

query: query.o
	$(CC) -o query query.o -I.

clean:
	rm -f *.o *~

distclean: clean
	rm -f query
