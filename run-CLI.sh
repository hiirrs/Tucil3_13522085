cd "$(dirname "$0")"
javac -d ../bin src/*.java
cp src/words.txt ../bin/
cd ../bin
java -cp . WordLadderGame