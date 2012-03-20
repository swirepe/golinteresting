package board;

import java.util.Collection;
import java.util.Map;

import printer.Printer;
import rule.Rule;

/**
 * General abstraction of a board.
 * @author pkehrer
 *
 */
public interface Board {
	/**
	 * The UID for this board.  Should be the same for all boards that start
	 * with the same starting configuration, but different from all other boards.
	 * Should also be a valid filename.
	 * 
	 * @return the UID for the board
	 */
	public String getUID();
	
	/**
	 * Every board needs to be able to run for one discrete time slice.
	 * @throws Exception 
	 */
	public void runOnce()  ;
	
	/**
	 * Every board needs to know which cells are currently alive.
	 * @return
	 */
	public Collection<Coordinate> getLiveCells();
	
	/**
	 * Define a rule for a board.
	 * @param rule
	 */
	public void setRule(Rule rule);
	
	/**
	 * Every board should have a notion of how many iterations have occured.
	 * @return
	 */
	public int getIterations();

	/**
	 * A board should have a printer associated with it.
	 * @return
	 */
	public Printer getPrinter();
	
	public void setPrinter(Printer printer);

	public int getHeight();

	public int getWidth();
	
	public Cell[][] getCells();
	
	public Map<Coordinate, Cell> getCellsMap();
	
	public void setAlive(int x, int y, boolean alive);
	
	public int countLiveNeighbors(int x, int y, int distance);
	
	public int getHeuristicValue();
}
