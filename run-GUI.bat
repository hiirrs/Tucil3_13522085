@echo off
rem 
cd src

rem 
javac WordLadderGUI.java

rem 
if %errorlevel% equ 0 (
    rem 
    java WordLadderGUI
)