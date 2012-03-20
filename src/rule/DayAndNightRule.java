package rule;

import board.Board;
import board.InfiniteBoard;
import board.StateChange;

public class DayAndNightRule implements Rule {

	private InfiniteBoard board;
	
	public DayAndNightRule(Board board) {
		this.board = (InfiniteBoard)board;
	}

	@Override
	public StateChange nextState(int x, int y) {
		int neighborCount = board.countLiveNeighbors(x, y, 1);
		
		switch(neighborCount) {
		case 0:
			return StateChange.DIE;
		case 1:
			return StateChange.DIE;
		case 2:
			return StateChange.DIE;
		case 3:
			return StateChange.SPAWN;
		case 4:
			return StateChange.STAY;
		case 5:
			return StateChange.DIE;
		case 6:
			return StateChange.SPAWN;
		case 7:
			return StateChange.SPAWN;
		case 8:
			return StateChange.SPAWN;
		default:
			return StateChange.NONE;
		}
	}
	

}
