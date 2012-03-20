package tests;

import geneticalgorithm.GeneticAlgorithm;

import java.util.Collection;
import java.util.TreeSet;

import org.junit.Test;

import printer.GraphicalPrinter;
import printer.SimpleAsciiPrinter;
import board.InfiniteBoard;

public class GeneticAlgorithmTest {
	@Test
	public void test() {
		InfiniteBoard board1 = new InfiniteBoard(10,10);
		board1.setAlive(5, 5, true);
		board1.setAlive(5, 6, true);
		board1.setAlive(5, 7, true);
		board1.setAlive(5, 8, true);
		board1.setAlive(4, 6, true);
		board1.setAlive(4, 5, true);
		board1.setAlive(3, 3, true);
		board1.setAlive(2, 1, true);
		board1.setAlive(1, 5, true);
		
		for (int i = 0; i < 1000; i++) {
			board1.runOnce();
		}

		new SimpleAsciiPrinter(board1).print();
		new SimpleAsciiPrinter(board1).printTimeslice(0);
		new SimpleAsciiPrinter(board1).printHeuristic();
		System.out.println(board1.getUID());
	}
	
	@Test
	public void spliceTest() {
		InfiniteBoard board1 = new InfiniteBoard(10,10);
		InfiniteBoard board2 = new InfiniteBoard(10,10);
		board1.setAlive(5, 5, true);
		board1.setAlive(5, 6, true);
		board1.setAlive(5, 7, true);
		board1.setAlive(5, 8, true);
		board1.setAlive(4, 6, true);
		board1.setAlive(4, 5, true);
		board1.setAlive(3, 3, true);
		board1.setAlive(2, 1, true);
		board1.setAlive(1, 5, true);
		
		board2.setAlive(6, 6, true);
		board2.setAlive(6, 7, true);
		board2.setAlive(5, 6, true);
		board2.setAlive(4, 6, true);
		board2.setAlive(3, 6, true);
		board2.setAlive(2, 1, true);
		board2.setAlive(3, 1, true);
		board2.setAlive(5, 1, true);
		
		board1.setPrinter(new GraphicalPrinter(board1));
		board2.setPrinter(new GraphicalPrinter(board2));
		
		board1.getPrinter().print();
		board2.getPrinter().print();
		TreeSet<InfiniteBoard> unspliced = new TreeSet<InfiniteBoard>();
		unspliced.add(board1);
		unspliced.add(board2);


		Collection<InfiniteBoard> spliced = new GeneticAlgorithm().spliceBoards(unspliced);
		for (InfiniteBoard board : spliced) {
			board.setPrinter(new GraphicalPrinter(board));
			board.getPrinter().print();
		}

		while (true);
	}
}
