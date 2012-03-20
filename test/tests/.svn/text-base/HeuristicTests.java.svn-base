package tests;

import heuristic.HeuristicImpl;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import printer.GraphicalPrinter;
import rule.StandardRule;
import board.InfiniteBoard;


public class HeuristicTests extends TestCase {


	private InfiniteBoard board1;
	private InfiniteBoard board2;
	
	
	@Before
	public void setUp() {
		board1 = new InfiniteBoard(20,20);
		board1.setRule(new StandardRule(board1));
		board1.setPrinter(new GraphicalPrinter(board1));
		board1.setHeuristic(new HeuristicImpl(board1));
		board1.setKeepHistory(true);
		board1.setAlive(1, 1, true);
		board1.setAlive(1, 2, true);
		board1.setAlive(1, 3, true);

		//Thread.sleep(5000);
	}
	
	@Test
	public void testDistance() {
		double distance;
		distance = board1.getHeuristic().getDistance();
		assertEquals(0.0, distance);
		board1.runOnce();
		distance = board1.getHeuristic().getDistance();
		assertEquals(4.0, distance);
		board1.runOnce();
		distance = board1.getHeuristic().getDistance();
		assertEquals(4.0, distance);
		board1.runOnce();	
	}
	
	@Test
	public void testStatic() {
		int staticPts;
		staticPts = board1.getHeuristic().getStaticPoints();
		assertEquals(0, staticPts);
		board1.runOnce();
		staticPts = board1.getHeuristic().getStaticPoints();
		assertEquals(1, staticPts);
		board1.runOnce();
		staticPts = board1.getHeuristic().getStaticPoints();
		assertEquals(1, staticPts);

	}
}
