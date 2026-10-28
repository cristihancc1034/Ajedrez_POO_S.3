#!/bin/sh
# Compila y ejecuta el juego en consola (Linux / Mac)
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Main
