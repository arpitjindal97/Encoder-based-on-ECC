javac *.java
jar cvfm myjar.jar manifest.txt *.java *.class *.txt
del *.class
