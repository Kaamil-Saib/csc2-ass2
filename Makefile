compile:
	javac -d bin src/*.java

clean:
	rm ./bin/*.class

run: compile	
	java -cp ./bin GenericsKbAVLApp
