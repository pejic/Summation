#!/bin/sh

find src -iname \*java | xargs -n 1 ./prepend-licence-java.sh
