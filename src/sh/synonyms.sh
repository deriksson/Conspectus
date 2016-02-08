#!/bin/bash
# Looks up synonyms in local text file.
#
# Usage:
# synonyms.sh <file name> <search term> 
#
# Examples:
# synonyms.sh swedish-synonyms.txt liten
# synonyms.sh english-synonyms.txt whole
#
HERE="$(dirname "${BASH_SOURCE[0]}")"
FILE=$HERE/../../data/"$1"
SEARCH_TERM="$2"
#
grep "^"$SEARCH_TERM": " $FILE | sed "s/^[a-zA-Z ]*: //g"
