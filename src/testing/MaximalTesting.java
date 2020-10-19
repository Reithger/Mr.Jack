package testing;

import java.io.*;

import controller.GameController;

public class MaximalTesting {

	private static final String MAP_1 = "assets/board/board1.txt";
	private static final String MAP_2 = "assets/board/board2.txt";
	private static final String FILE_PREFIX = "assets";
	
	public static void main(String[] args) throws Exception{
		GameController gam = null;
		if(new File(MAP_1).exists()) {
			gam = new GameController(new File(MAP_1));
		}
		else {
			gam = new GameController((MaximalTesting.class.getResourceAsStream(MAP_1.replace(FILE_PREFIX, ""))));
		}
	}
	
}