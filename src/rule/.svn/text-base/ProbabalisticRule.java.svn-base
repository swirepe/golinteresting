package rule;

import java.util.Random;

import board.Board;
import board.InfiniteBoard;
import board.StateChange;


public class ProbabalisticRule implements Rule {

	private InfiniteBoard board;
	
	public ProbabalisticRule(Board board) {
		this.board = (InfiniteBoard)board;
	}

	@Override
	public StateChange nextState(int x, int y) {
		int neighborCount = board.countLiveNeighbors(x, y, 1);
		Random rand = new Random();
		
		boolean somethingDifferent = (rand.nextInt(10) == 1);
		
		if (neighborCount >=4) {
			if (somethingDifferent)
				return StateChange.SPAWN;
			else
				return StateChange.DIE;
		} else if (neighborCount <= 1) {
			return StateChange.DIE;
		} else if (neighborCount == 3) {
			return StateChange.SPAWN;
		} else if (neighborCount == 2) {
			return StateChange.STAY;
		}
		return StateChange.NONE;
	}
	
}
