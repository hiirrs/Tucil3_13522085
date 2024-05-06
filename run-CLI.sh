cd src

javac -d ..\bin WordLadderGame.java

if [ $? -eq 0 ]; then
    java WordLadderGame
fi
