**JAVA COMPILE INSTRUCTION**
javac steviecompiler/*.java steviecompiler/node/*.java steviecompiler/node/expression/*.java steviecompiler/error/*.java -d out

**JAVA RUN INSTRUCTION**
java -cp out steviecompiler/Main <files to compile>

**Do Both**
javac steviecompiler/*.java steviecompiler/node/*.java steviecompiler/node/expression/*.java steviecompiler/error/*.java -d out &&
java -cp out steviecompiler/Main <files to compile>