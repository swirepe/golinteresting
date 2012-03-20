package board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InfiniteBoardCounter implements Counter {

	private InfiniteBoard board;
	
	public InfiniteBoardCounter(InfiniteBoard board) {
		this.board = board;
	}
	
	@Override
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
			if (board.getCellsMap().get(c) != null && board.getCellsMap().get(c).isAlive()) {
				count++;
			}
		}
		
		return count;
	}

	public Collection<Coordinate> getNeighbors(int x, int y) {
		Collection<Coordinate> coords = new ArrayList<Coordinate>();
		for (int i = (x-1); i <= (x+1); i++) {
			for (int j = (y-1); j <= (y+1); j++) {
				if (!(x==i && y==j)) {
					coords.add(new Coordinate(i,j));
				}
			}
		}
		return coords;
	}
	
	@Override
	public Collection<Coordinate> getLiveCells() {
		Collection<Coordinate> coords = new ArrayList<Coordinate>();
		for (Cell c : board.getCellsMap().values()) {
			if (c != null && c.isAlive()) {
				coords.add(c.getCoord());
			}
		}
		return coords;
	}

	@Override
	public boolean validateCoord(Coordinate coord) {
		return true;
	}

}
