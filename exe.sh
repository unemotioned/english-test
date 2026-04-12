#!/usr/bin/env bash

set -e          # exit on error
set -u          # exit on undefined variable
set -o pipefail # exit on pipe failure

OUT_DIR="out"
BIN_DIR="bin"
JAR_NAME="english-test.jar"
MAIN_CLASS="kr.or.iei.start.Start"

# clean build
[ -d "$OUT_DIR" ] && rm -rf $OUT_DIR/*
[ -f "$BIN_DIR/$JAR_NAME" ] && rm "$BIN_DIR/$JAR_NAME"

mkdir -p "$OUT_DIR" "$BIN_DIR"

# compile
javac -d "$OUT_DIR" "$(find src -name "*.java")"

# create jar file
jar --create \
    --file "$BIN_DIR/$JAR_NAME" \
    --main-class "$MAIN_CLASS" \
    -C "$OUT_DIR" .

# run jar
java -jar "$BIN_DIR/$JAR_NAME"
