cd src
javac -d ..\bin *.java
xcopy words.txt ..\bin\ /Y
cd ..
cd bin
java WordLadderGUI