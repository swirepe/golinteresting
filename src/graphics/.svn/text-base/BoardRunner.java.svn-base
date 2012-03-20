package graphics;

import board.GameOfLifeBoard;

public class BoardRunner {
	
	public static void runConsole(GameOfLifeBoard board, int iterations)  {
		final int DELAY_MS = 100;
		for (int i = 0; i < iterations; i++) {
			board.getPrinter().print();
			board.runOnce();
			try {
				Thread.sleep(DELAY_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void runFile(GameOfLifeBoard board, int iterations, String filename)  {
		for (int i = 0; i < iterations; i++) {
			board.runOnce();
		}
		board.getPrinter().print(filename);
		
	}

}
