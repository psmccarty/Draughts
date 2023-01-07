all: Main.class
	java Main

CheckersBoard.class: CheckersBoard.java Square.class
	javac CheckersBoard.java

Menu.class: CheckersBoard.class Menu.java
	javac Menu.java

Main.class: Main.java Menu.class
	javac Main.java

Square.class: Square.java
	javac Square.java

clean:
	del *~ *.class
