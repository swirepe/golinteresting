package board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameOfLifeCellCounter implements Counter {

	private GameOfLifeBoard board;
	
	public GameOfLifeCellCounter(GameOfLifeBoard board) {
		this.board = board;
	}
	
	/**
	 * Return a count of the live cells that are "distance" cells
	 * away from a coordinate x,y.
	 * @param x coordinate
	 * @param y coordinate
	 * @param distance Distance away from (x,y) to check
	 * @return count of live neighbors.
	 */
	public int countLiveNeighbors(int x, int y, int distance) {
		int count = 0;
		List<Coordinate> toCheck = new ArrayList<Coordinate>();
		for (int i = (x-distance); i <= (x+distance); i++) {
			for (int j = (y-distance); j <= (y+distance); j++) {
				if (!(x==i && y==j)) {
					toCheck.add(new Coordinate(i,j));
				}
			}
		}
		for (Coordinate c : toCheck) {
			if (validateCoord(c) && board.getCells()[c.getX()][c.getY()].isAlive()) {
				count++;
			}
		}
		return count;
	}
	
	public List<Coordinate> getLiveCells() {
		List<Coordinate> list = new ArrayList<Coordinate>();
		for (int h = 0; h < board.getHeight(); h++) {
			for (int w = 0; w < board.getWidth(); w++) {
				if (board.getCells()[h][w].isAlive()) {
					list.add(new Coordinate(h,w));
				}
			}
		}
		return list;
	}
	
	/**
	 * Determine if a coordinate is valid. i.e. Are the x and y values
	 * at least 0, and are they not larger than the dimensions of the board.
	 * @param coord
	 * @return is the coordinate valid.
	 */
	public boolean validateCoord(Coordinate coord) {
		return coord.getX() >=0
				&& coord.getX() < board.getHeight() 
				&& coord.getY() >= 0 
				&& coord.getY() < board.getWidth();
	}

	@Override
	public Collection<Coordinate> getNeighbors(int x, int y) {
		List<Coordinate> toCheck = new ArrayList<Coordinate>();
		for (int i = (x-1); i <= (x+1); i++) {
			for (int j = (y-1); j <= (y+1); j++) {
				if (!(x==i && y==j)) {
					toCheck.add(new Coordinate(i,j));
				}
			}
		}
	
		return toCheck;
	}
}
