package board;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import printer.EncodedPrinter;
import printer.Printer;
import printer.SimpleAsciiPrinter;
import rule.Rule;
import rule.StandardRule;

public class GameOfLifeBoard implements Board {
	private static final boolean DEBUG = false;
	
	protected boolean keepHistory = true;

	protected List<Boolean[][]> history;
	
	protected int width;
	protected int height;
	
	protected Cell[][] cells;
	protected Rule rule;
	protected Printer printer;
	protected Counter counter;
	protected int iterations;
	
	
	public GameOfLifeBoard() {
		// empty
	}
	
	public GameOfLifeBoard(int height, int width) {
		this.width = width;
		this.height = height;
		this.iterations = 0;
		this.cells = new Cell[height][width];
		this.history = new ArrayList<Boolean[][]>();
		this.printer = new EncodedPrinter(this);
		this.rule = new StandardRule(this);
		this.counter = new GameOfLifeCellCounter(this);
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				this.cells[h][w] = new Cell(h,w);
			}
		}
	}
	
	
	/**
	 * Delegate to the counter to find the count of live neighbors.
	 * @param x coordinate
	 * @param y coordinate
	 * @return number of alive neighbors
	 */
	public int countLiveNeighbors(int x, int y, int distance) {
		return this.counter.countLiveNeighbors(x, y, distance);
	}
	
	/**
	 * Delegate to the counter to find all of the alive cells in the board
	 * @return A list of coordinates of the live cells on the board.
	 */
	public Collection<Coordinate> getLiveCells() {
		return counter.getLiveCells();
	}
	
	/**
	 * Make the given cell alive..
	 * @param x coordinate
	 * @param y coordinate
	 * @param alive New state of the cell.
	 */
	public void setAlive(int x, int y, boolean alive) {
		cells[x][y].setAlive(alive);
	}
	
	/**
	 * Advance all of the cells in the board ONE time slice.
	 */
	public void runOnce() {
		if (DEBUG && iterations == 0) {
			SimpleAsciiPrinter p = new SimpleAsciiPrinter(this);
			p.print(this.getUID() + ".txt");
			p.printHeuristic(this.getUID() + ".txt");
		}
		
		if (keepHistory)
			history.add(getCopy());
		
		flagCells();
		finalizeCells();
		
		this.iterations++;
		
		if (DEBUG) {
			SimpleAsciiPrinter p = new SimpleAsciiPrinter(this);
			p.print(this.getUID() + ".txt");
			p.printHeuristic(this.getUID() + ".txt");
		}
	} // end of method runOnce
	
	
	protected void flagCells() {
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				cells[h][w].flag(rule.nextState(h, w));
			}
		}
	} // end of method flagCells
	
	
	protected void finalizeCells(){
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				cells[h][w].finalize();
			}
		}
	} // end of method finalizeCells
	
	/**
	 * Return a simple boolean matrix of the current state
	 * of the board.
	 * @return 2-dimensional array of booleans.
	 */
	public Boolean[][] getCopy() {
		Boolean[][] cellsCopy = new Boolean[height][width];
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				cellsCopy[h][w] = cells[h][w].isAlive();
			}
		}
		return cellsCopy;
	}
	
	public boolean isKeepingHistory() {
		return this.keepHistory;
	}
	public void setKeepHistory(boolean keepHistory) {
		this.keepHistory = keepHistory;
	}
	public int getHeight() {
		return this.height;
	}
	public int getWidth() {
		return this.width;
	}
	public Cell[][] getCells() {
		return this.cells;
	}
	public List<Boolean[][]> getHistory() {
		return this.history;
	}
	public int getIterations() {
		return this.iterations;
	}
	public Rule getRule() {
		return this.rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	public Printer getPrinter() {
		return this.printer;
	}
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}

	@Override
	public Map<Coordinate, Cell> getCellsMap() {
		return null;
	}

	@Override
	public int getHeuristicValue() {
		return 0;
	}
	
	@Override
	public String getUID() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			if (getHistory() == null || getHistory().size() == 0) {
				oos.writeObject(getCopy());
			} else {
				oos.writeObject(getHistory().get(0));
			}
			byte[] data = baos.toByteArray();
			MessageDigest dig = MessageDigest.getInstance("md5");
			byte[] out = dig.digest(data);
			
			return getHexString(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String getHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
}
