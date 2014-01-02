#!/bin/sh

TMP=tempfile

if [ -z "`grep 'Copyright (C)' "$1"`" ]; then
	cp "$1" "$TMP"
	cat prepend-licence-java-preamble.txt "$TMP" > "$1"
	rm -rf "$TMP"
fi
