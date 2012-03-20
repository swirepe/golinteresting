package rule;

import board.Board;
import board.InfiniteBoard;
import board.StateChange;

public class StandardRule implements Rule {
	
	InfiniteBoard board;
	
	public StandardRule(Board board) {
		this.board = (InfiniteBoard)board;
	}
	
	
	@Override
	public StateChange nextState(int x, int y) {
		int neighborCount = board.countLiveNeighbors(x, y, 1);
		if (neighborCount >=4) {
			return StateChange.DIE;
		} else if (neighborCount <=  1) {
			return StateChange.DIE;
		} else if (neighborCount == 3) {
			return StateChange.SPAWN;
		} else if (neighborCount == 2) {
			return StateChange.STAY;
		}
		return StateChange.NONE;
	}

}
