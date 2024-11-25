#Easy to compile and run

CC = g++
JC = javac

cleardb:
	cd java && rm user.txt
	cd java && rm WMSlots.txt
	cd java && rm Requests.txt
	cd java && rm VPBookings.txt

compile: 
	$(JC) -cp . java/com/complan/basic_classes/*.java java/com/complan/function_handler/UserLogger.java java/com/complan/Main.java -h . java/com/complan/function_handler/UserHandler.java
	mv com_complan_function_handler_UserHandler.h Cpp/
	$(CC) -shared -fpic -o Cpp/libDB_Access.so Cpp/DB_Access.cpp -I /usr/lib/jvm/java-17-openjdk-amd64/include -I /usr/lib/jvm/java-17-openjdk-amd64/include/linux
	$(CC) -shared -fpic -o Cpp/libDisplay.so Cpp/Display.cpp -I /usr/lib/jvm/java-17-openjdk-amd64/include -I /usr/lib/jvm/java-17-openjdk-amd64/include/linux
	mv Cpp/libDB_Access.so java/
	mv Cpp/libDisplay.so java/


run: 
	cd java && java -Djava.library.path= com/complan/Main
