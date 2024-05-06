@echo off
rem 
cd src

rem 
javac WordLadderGame.java

rem 
if %errorlevel% equ 0 (
    rem 
    java WordLadderGame
)