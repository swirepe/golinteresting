package rule;

import board.Board;
import board.InfiniteBoard;
import board.StateChange;

public class TwoAwayRule implements Rule {
	
	InfiniteBoard board;
	
	public TwoAwayRule(Board board) {
		this.board = (InfiniteBoard)board;
	}
	
	@Override
	public StateChange nextState(int x, int y) {
		//countCells will be a number in the interval [0,24]
		int countCells = board.countLiveNeighbors(x, y, 2);
		
		if (countCells < 4) {
			return StateChange.DIE;
		} else if (countCells > 12) {
			return StateChange.DIE;
		} else if (countCells > 5 && countCells < 10) {
			return StateChange.SPAWN;
		} else {
			return StateChange.STAY;
		}
	}

}
