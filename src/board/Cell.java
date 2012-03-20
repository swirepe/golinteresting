package board;

import java.io.Serializable;

/**
 * A cell has a coordinate, and an "alive" state of true or false.  It also needs a StateChange value,
 * which says how the state will change in the next time slice.
 * @author pkehrer
 *
 */
public class Cell implements Serializable {
	
	protected Coordinate coord;
	protected boolean alive;
	protected StateChange change;
	
	public Cell(int x, int y) {
		this.coord = new Coordinate(x,y);
		this.alive = false;
		this.change = StateChange.NONE;
	}
	
	public Cell clone() {
		Cell clone = new Cell(this.coord.getX(), this.coord.getY());
		clone.setAlive(this.alive);
		return clone;
	}

	public void finalize() {
		switch(change) {
		case DIE:
			this.alive = false;
			break;
		case SPAWN:
			this.alive = true;
			break;
		}
		this.change = StateChange.NONE;
	}
	
	public void flag(StateChange change) {
		this.change = change;
	}
	
	public Coordinate getCoord() {
		return this.coord;
	}
	
	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Cell)) {
			return false;
		} else {
			Cell otherCell = (Cell)other;
			return this.coord.getX() == otherCell.coord.getX()
				&& this.coord.getY() == otherCell.coord.getY()
				&& this.alive == otherCell.alive;
		}
	}
}
