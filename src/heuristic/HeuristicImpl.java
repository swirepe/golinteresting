package heuristic;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import board.Cell;
import board.Coordinate;
import board.InfiniteBoard;



public class HeuristicImpl implements Heuristic {

	private InfiniteBoard board;
	
	private int generations;
	
	private double absGrowth;
	private double totalAbsGrowth;
	
	private double pctAlive;
	private double totalPctAlive;
	
	private double distance;
	private double totalDistance;
	
	private int staticPoints;
	private int totalStaticPoints;
	
	public HeuristicImpl(InfiniteBoard board) {
		this.board = board;
	}
	
	public void run() {
		getGrowth();
		getPctAlive();
		getStaticPoints();
		getDistance();
		
		update();
	}
	
	public double getGrowth() {
		List<Map<Coordinate, Cell>> history = board.getHistory();
		Map<Coordinate, Cell> lastIteration = history.get(history.size()-1);
		int lastIterationSize = 0;
		for (Cell cell : lastIteration.values()) {
			if (cell.isAlive()) {
				lastIterationSize++;
			}
		}
		int currentIterationSize = 0;
		for (Cell cell : board.getCellsMap().values()) {
			if (cell.isAlive()) {
				currentIterationSize++;
			}
		}
		this.absGrowth = (double) currentIterationSize - lastIterationSize;
		return this.absGrowth;
	}
	
	
	public double getPctAlive() {
		int totalCells = board.getHeight() * board.getWidth();
		int aliveCells = 0;
		for (Cell cell : board.getCellsMap().values()) {
			if (cell.isAlive()
					&& cell.getCoord().getX() < board.getHeight()
					&& cell.getCoord().getY() < board.getWidth()) {
				aliveCells++;
			}
		}
		this.pctAlive = (double)aliveCells / totalCells;
		return this.pctAlive;
	}
	
	
	public int getStaticPoints() {
		this.staticPoints = 0;
		if (board.getHistory().size() == 0) {
			return 0;
		}
		for (Cell cell : board.getCellsMap().values()) {
			if (cell.isAlive()) {
				for (Cell lastCell : board.getHistory().get(board.getHistory().size()-1).values()) {
					if (cell.equals(lastCell))
						staticPoints++;
					
				}
			}
		}
		return staticPoints;
	}
	
	public double getDistance() {
		int distance = 0;
		List<Map<Coordinate, Cell>> history = board.getHistory();
		Collection<Cell> lastIteration = history.get(history.size()-1).values();

		for (Cell cell : lastIteration) {
			if (!board.getCellsMap().values().contains(cell))
				distance++;
		}
		for (Cell cell : board.getCellsMap().values()) {
			if (!lastIteration.contains(cell))
				distance++;
		}

		this.distance = distance;
		return distance;
	}
	
	public void update() {
		totalAbsGrowth += absGrowth;
		totalPctAlive += pctAlive;
		totalDistance += distance;
		totalStaticPoints += staticPoints;
		generations++;
	}
	
	public double getValue() {
		System.out.println("Growth:\t" + absGrowth + "\n%Alive:\t" + pctAlive + "\nAvg Distance:\t" + distance + "\nStatic Points:\t" + staticPoints + "\n");
		return totalAbsGrowth
			+ totalPctAlive * 1000
			+ totalDistance * -.25
			+ totalStaticPoints;
	}
}
