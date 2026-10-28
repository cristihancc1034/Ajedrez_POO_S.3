@echo off
REM Compila y ejecuta el juego en consola (necesita el JDK instalado)
if not exist out mkdir out
dir /s /b src\*.java > fuentes.txt
javac -encoding UTF-8 -d out @fuentes.txt
if errorlevel 1 (
  echo Error al compilar
  pause
  exit /b 1
)
java -cp out Main
pause
