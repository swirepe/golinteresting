package tests;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import printer.GraphicalPrinter;
import printer.SimpleAsciiPrinter;
import board.InfiniteBoard;
import boardgenerators.Box;
import boardgenerators.Glider;
import boardgenerators.GliderGun;

public class TrainingRuns extends TestCase {

	private InfiniteBoard board1;
	private int height = 20;
	private int width = 40;
	private int iterations = 1000;
	
	@Before
	public void setUp() {
		// All setup code.
		board1 = null;
	}
	
	/*
	 *     #
	 *       #
	 *   # # #
	 */
	@Test
	public void testGlider()  {
		board1 = new Glider().getBoard(height,width);
		board1.setPrinter(new SimpleAsciiPrinter(board1));
		runBoard(board1, iterations);
		System.out.println("Glider after " + iterations + " iterations: " + board1.getHeuristicValue());
	}
	
	/*
	 * Gosper's Glider Gun...
	 */
	@Test
	public void testGliderGun()  {
		board1 = new GliderGun().getBoard(height,width);
		runBoard(board1, iterations);
		System.out.println("GliderGun after " + iterations + " iterations: " + board1.getHeuristicValue());
	}
	
	/*
	 *  # #
	 *  # #
	 * 
	 */
	@Test
	public void testBox()  {
		board1 = new Box().getBoard(height,width);
		runBoard(board1, iterations);
		System.out.println("Box after " + iterations + " iterations: " + board1.getHeuristicValue());
	}

	/*
	 * 
	 *    # # # # #
	 *          # #
	 *          #
	 *          #
	 */
	@Test
	public void testDesign1()  {
		board1 = new InfiniteBoard(height, width);
		board1.setAlive(11, 11, true);
		board1.setAlive(11, 12, true);
		board1.setAlive(11, 13, true);
		board1.setAlive(11, 14, true);
		board1.setAlive(11, 15, true);
		board1.setAlive(12, 14, true);
		board1.setAlive(13, 14, true);
		board1.setAlive(14, 14, true);
		board1.setAlive(12, 15, true);
		runBoard(board1, iterations);
		System.out.println("Design1 after " + iterations + " iterations: " + board1.getHeuristicValue());
	}
	
	
	
	
	public void runBoard(InfiniteBoard board, int iterations)  {
		for (int i=0; i < iterations; i++) {
			board.runOnce();
			//if (i % 100 == 0)
				//displayBoard(board);
		}
	}
	public void displayBoard(InfiniteBoard board) throws InterruptedException {
		board.setPrinter(new GraphicalPrinter(board));
		board.getPrinter().print();
		Thread.sleep(500);
	}
}
