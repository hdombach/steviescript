**JAVA COMPILE INSTRUCTION**
javac steviecompiler/*.java steviecompiler/node/*.java steviecompiler/node/expression/*.java steviecompiler/error/*.java steviecompiler/symbol/*.java steviecompiler/commands/*.java -d out

**JAVA RUN INSTRUCTION**
java -cp out steviecompiler/Main <files to compile>

**Do Both**
javac steviecompiler/*.java steviecompiler/node/*.java steviecompiler/node/expression/*.java steviecompiler/error/*.java steviecompiler/symbol/*.java -d out steviecompiler/commands/*.java &&
java -cp out steviecompiler/Main <files to compile>


**Create JAR file**
jar cvf steviecompiler.jar out/steviecompiler

**Run JAR file**
java -jar steviecompiler.jar

**Create EXE file**
jpackage -i . -n steviecompiler --main-jar  steviecompiler.jar --main-class steviecompiler/Main --win-console