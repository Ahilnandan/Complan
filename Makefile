#Easy to compile and run

CC = g++
JC = javac

JAVA_HOME := $(shell echo $$JAVA_HOME)

cleardb:
	cd java && rm user.txt
	cd java && rm WMSlots.txt
	cd java && rm Requests.txt
	cd java && rm VPBookings.txt

compilelinux: 
	$(JC) -cp . java/com/complan/basic_classes/*.java java/com/complan/function_handler/UserLogger.java java/com/complan/Main.java -h . java/com/complan/function_handler/UserHandler.java
	mv com_complan_function_handler_UserHandler.h Cpp/
	$(CC) -shared -fpic -o Cpp/libDB_Access.so Cpp/DB_Access.cpp -I $(JAVA_HOME)include -I $(JAVA_HOME)include/linux
	$(CC) -shared -fpic -o Cpp/libDisplay.so Cpp/Display.cpp -I $(JAVA_HOME)include -I $(JAVA_HOME)include/linux
	mv Cpp/libDB_Access.so java/
	mv Cpp/libDisplay.so java/
compilewin:
	$(JC) -cp . java/com/complan/basic_classes/*.java java/com/complan/function_handler/UserLogger.java java/com/complan/Main.java -h . java/com/complan/function_handler/UserHandler.java
	move com_complan_function_handler_UserHandler.h Cpp/
	$(CC) -shared -o Cpp/DB_Access.dll Cpp/DB_Access.cpp -I $(JAVA_HOME)include -I $(JAVA_HOME)include/win32
	$(CC) -shared -o Cpp/Display.dll Cpp/Display.cpp -I $(JAVA_HOME)include -I $(JAVA_HOME)include/win32
	move Cpp/libDB_Access.so java/
	move Cpp/libDisplay.so java/

run: 
	cd java && java -Djava.library.path= com/complan/Main
