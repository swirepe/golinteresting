package test;

import printer.GraphicalPrinter;
import rule.StandardRule;
import board.Board;
import board.InfiniteBoard;
import geneticalgorithm.GeneticAlgorithm;



public class Main {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {

		int height = 30;
		int width = 40;
		Board board = new InfiniteBoard(height, width);
		board.setRule(new StandardRule(board));
		board.setPrinter(new GraphicalPrinter(board));
		GeneticAlgorithm A=new GeneticAlgorithm();
		A.randomizeBoard(200,700,board);
		//board.setAlive(3, 4, true);
		//board.setAlive(3, 5, true);
		//board.setAlive(3, 6, true);
		//board.setAlive(2, 6, true);
		//board.setAlive(1, 5, true);
		
		board.getPrinter().print();
		
		for (int i = 0; i < 2; i++) {
			board.runOnce();

			board.getPrinter().print();
		}




		
	   
	}

}
