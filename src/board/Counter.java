package board;

import java.util.Collection;

public interface Counter {
	public int countLiveNeighbors(int x, int y, int distance);
	public Collection<Coordinate> getLiveCells();
	public boolean validateCoord(Coordinate coord);
	public Collection<Coordinate> getNeighbors(int x, int y);
}
