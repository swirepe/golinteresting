package board;

import java.io.Serializable;
import java.util.Collection;

public class Coordinate implements Serializable {
	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object other) {
		Coordinate otherCoord;
		if (!(other instanceof Coordinate)) {
			return false;
		} else {
			otherCoord = (Coordinate)other;
		}
		return (otherCoord.x == this.x) && (otherCoord.y == this.y);
	}
	
	/**
	 * Given a list of coordinates, return true if the coordinate defined by X and Y
	 * is a coordinate in that list.
	 * @param x coordinate
	 * @param y coordinate
	 * @param coords
	 * @return True if (x,y) is included in the coords list.
	 */
	public static boolean coordContained(int x, int y, Collection<Coordinate> coords) {
		Coordinate coord = new Coordinate(x,y);
		for (Coordinate c : coords) {
			if (c.equals(coord)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return x*1000 + y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
