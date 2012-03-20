package rule;

import board.Board;
import board.Coordinate;
import board.InfiniteBoard;
import board.StateChange;

public class SeedsRule implements Rule {

	private Board board;
	
	public SeedsRule(Board board) {
		this.board = (InfiniteBoard)board;
	}

	@Override
	public StateChange nextState(int x, int y) {
		int neighborCount = board.countLiveNeighbors(x, y, 1);
		boolean currentState;
		if (board.getCells() != null) {
			currentState = board.getCells()[x][y].isAlive();
		} else {
			if (board.getCellsMap().get(new Coordinate(x,y)) == null) {
				currentState = false;
			} else {
				currentState = board.getCellsMap().get(new Coordinate(x,y)).isAlive();
			}
			
		}
		
		if (currentState == false && neighborCount == 2) {
			return StateChange.SPAWN;
		} else {
			return StateChange.DIE;
		}
	}
	
}
