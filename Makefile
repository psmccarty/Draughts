all: Main.class
	java Main

CheckersBoard.class: CheckersBoard.java Square.class
	javac CheckersBoard.java

Menu.class: CheckersBoard.class GameLogic.class GameRunner.class Menu.java
	javac Menu.java

Main.class: Main.java Menu.class
	javac Main.java

Square.class: Square.java
	javac Square.java

GameLogic.class: GameLogic.java CheckersBoard.class
	javac GameLogic.java

GameRunner.class: GameRunner.java GameLogic.class
	javac GameRunner.java

clean:
	del *~ *.class
