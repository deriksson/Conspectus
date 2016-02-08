#!/bin/bash
# Looks up synonyms by querying a Conspectus UDP server.
#
# Usage:
# query.sh <search term>
#
SEARCH_TERM="$1"
#
query localhost 5005 "$SEARCH_TERM"
