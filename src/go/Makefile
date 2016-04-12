INSTALL = /usr/bin/install -c
NAME = query
SRC = query.go

query: $(SRC)
	go build -o $(NAME)

clean:
	rm -f *~

distclean: clean
	rm -f $(NAME) $(NAME).exe

install: query
	$(INSTALL) $(NAME) /usr/local/bin/

format:
	go fmt

nt: $(SRC)
	GOOS=windows GOARCH=amd64 go build -o $(NAME).exe
