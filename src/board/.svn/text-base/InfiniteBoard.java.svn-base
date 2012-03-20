package board;

import heuristic.HeuristicImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import printer.Printer;
import printer.SimpleAsciiPrinter;
import rule.Rule;
import rule.StandardRule;

public class InfiniteBoard implements Board, Comparable<InfiniteBoard> {
	private boolean debug = false;
	
	private int width;
	private int height;
	
	private Map<Coordinate, Cell> cellMap;
	private List<Map<Coordinate, Cell>> listHistory;
	
	private HeuristicImpl heuristic;
	private Rule rule;
	private Printer printer;
	private Counter counter;
	
	private boolean keepHistory = true;
	private boolean immobile;
	private int iterations;
	
	public InfiniteBoard(int height, int width) {
		this.width = width;
		this.height = height;
		this.iterations = 0;
		this.immobile = false;
		
		this.listHistory = new ArrayList<Map<Coordinate, Cell>>();
		this.rule = new StandardRule(this);
		this.counter = new InfiniteBoardCounter(this);
		this.heuristic = new HeuristicImpl(this);
		
		this.cellMap = Collections.synchronizedMap(new HashMap<Coordinate, Cell>());
	}
	
	/**
	 * Set the cell at coordinate (x,y) to alive if 
	 * parameter "alive" is true, or dead if it is false.
	 */
	@Override
	public void setAlive(int x, int y, boolean alive) {
		Cell cell = new Cell(x,y);
		cell.setAlive(alive);
		this.cellMap.put(new Coordinate(x,y), cell);
	}

	/**
	 * Run the board for one timeslice.
	 * Do this by first recording the current configuration
	 * to the history.
	 * 
	 * Then check all cells that need to change and flag them.
	 * 
	 * Then "finalize cells," actually changing their values.
	 * 
	 * Then calculate the heuristic value of the board.
	 */
	@Override
	public void runOnce() {
		if (debug && iterations == 0) {
			SimpleAsciiPrinter p = new SimpleAsciiPrinter(this);
			p.print(this.getUID() + ".txt");
			p.printHeuristic(this.getUID() + ".txt");
		}
		
		if (!immobile) {
		
			if (keepHistory)
				listHistory.add(getListCopy());
			
			flagCells();
			finalizeCells();
			
			heuristic.run();
			this.iterations++;
			if (keepHistory)
				checkImmobile();
			
			if (debug) {
				SimpleAsciiPrinter p = new SimpleAsciiPrinter(this);
				p.print(this.getUID() + ".txt");
				p.printHeuristic(this.getUID() + ".txt");
			}
		}
	} // end of method runOnce
	
	/**
	 * Check to see if the board didn't change since the last timeslice.
	 */
	private void checkImmobile() {
		Map<Coordinate, Cell> lastIteration = listHistory.get(listHistory.size()-1);
		if (lastIteration.size() == cellMap.values().size()
				&& lastIteration.values().containsAll(cellMap.values())) {
				this.immobile = true;
		}
	}
	
	
	private void flagCells() {
		addNeighborCells();
		for (Cell cell : this.cellMap.values()) {
			cell.flag(rule.nextState(cell.getCoord().getX(), cell.getCoord().getY()));
		}
	} // end of method flagCells
	
	
	/**
	 * Because we need to check all the neighbors of the live cells,
	 * this method just adds dead cell entries to the neighbors of all
	 * the live cells...
	 */
	private void addNeighborCells() {
		Collection<Coordinate> coords = new ArrayList<Coordinate>();
		for (Cell cell : this.cellMap.values()) {
			coords.addAll(this.counter.getNeighbors(cell.getCoord().getX(), cell.getCoord().getY()));
		}
		for (Coordinate coord : coords) {
			if (this.cellMap.get(coord) == null) {
				this.setAlive(coord.getX(), coord.getY(), false);
			}
		}
	}
	
	private void finalizeCells(){
		Collection<Coordinate> toRemove = new ArrayList<Coordinate>();
		for (Cell cell : this.cellMap.values()) {
			cell.finalize();
			if (!cell.isAlive()) {
				toRemove.add(cell.getCoord());
			}
		}
		for (Coordinate c : toRemove) {
			this.cellMap.remove(c);
		}
	}
	
	/**
	 * Return a list of all the live cells on the board.
	 * @return
	 */
	private Map<Coordinate, Cell> getListCopy() {
		Map<Coordinate, Cell> cells = new HashMap<Coordinate, Cell>();
		for (Cell cell : cellMap.values()) {
			if (cell.isAlive()) {
				Cell newCell = new Cell(cell.getCoord().getX(), cell.getCoord().getY());
				Coordinate coord = new Coordinate(cell.getCoord().getX(), cell.getCoord().getY());
				newCell.setAlive(true);
				cells.put(coord, newCell);
			}
		}
		return cells;
	}
	

	
	public void rewindBoard() {
		if (this.iterations > 0) {
			this.cellMap = new HashMap<Coordinate, Cell>();
			for (Cell c : this.listHistory.get(0).values()) {
				if (c.isAlive()) {
					setAlive(c.getCoord().getX(), c.getCoord().getY(), true);
				}
			}
			this.iterations = 0;
			this.immobile = false;
			this.listHistory = new ArrayList<Map<Coordinate, Cell>>();
			this.heuristic = new HeuristicImpl(this);
		}
		
	}
	
	public InfiniteBoard cloneNewBoard() {
		InfiniteBoard board = new InfiniteBoard(this.height, this.width);
		for (Cell c : this.cellMap.values()) {
			if (c.isAlive()) {
				board.setAlive(c.getCoord().getX(), c.getCoord().getY(), true);
			}
		}
		return board;
	}
	
	@Override
	public Map<Coordinate, Cell> getCellsMap() {
		return this.cellMap;
	}
	public List<Map<Coordinate, Cell>> getHistory() {
		return this.listHistory;
	}
	public void setHeuristic(HeuristicImpl heuristic) {
		this.heuristic = heuristic;
	}
	public HeuristicImpl getHeuristic() {
		return this.heuristic;
	}
	@Override
	public int getHeuristicValue() {
		return (int)heuristic.getValue();
	}
	@Override
	public Collection<Coordinate> getLiveCells() {
		return counter.getLiveCells();
	}
	@Override
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	@Override
	public int getIterations() {
		return iterations;
	}
	@Override
	public Printer getPrinter() {
		return printer;
	}
	@Override
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
	@Override
	public int getHeight() {
		return height;
	}
	@Override
	public int getWidth() {
		return width;
	}
	@Override
	public int countLiveNeighbors(int x, int y, int distance) {
		return counter.countLiveNeighbors(x, y, distance);
	}

	@Override
	public Cell[][] getCells() {
		throw new UnsupportedOperationException();
	}

	public void setKeepHistory(boolean b) {
		this.keepHistory = b;
	}

	@Override
	public int compareTo(InfiniteBoard other) {
		InfiniteBoard otherBoard = (InfiniteBoard) other;
		if (this.getHeuristicValue() < otherBoard.getHeuristicValue()) {
			return -1;
		} else {
			return 1;
		}
	}
	
	@Override
	public String getUID() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			if (getHistory() == null || getHistory().size() == 0) {
				oos.writeObject(getListCopy());
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
	
	public boolean isImmobile() {
		return this.immobile;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
