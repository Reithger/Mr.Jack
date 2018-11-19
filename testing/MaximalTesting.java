package testing;

import game.GameController;
import java.io.*;

public class MaximalTesting {

	private static final String MAP_1 = "assets\\board1.txt";
	private static final String MAP_2 = "assets\\board2.txt";
	
	public static void main(String[] args) {
		GameController gam = new GameController(new File(MAP_1));
	}
	
}
