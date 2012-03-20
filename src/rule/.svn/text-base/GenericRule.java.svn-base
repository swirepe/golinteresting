package rule;

import java.util.List;

import board.Board;
import board.InfiniteBoard;
import board.StateChange;


public class GenericRule implements Rule{

	private InfiniteBoard board;
	private List<Integer> survives;
	private List<Integer> born;
	
	public GenericRule(List<Integer> survives, List<Integer> born, Board board) {
		this.board = (InfiniteBoard)board;
		this.survives = survives;
		this.born = born;
	}
	
	@Override
	public StateChange nextState(int x, int y) {
		int neighborCount = board.countLiveNeighbors(x, y, 1);
		if (born.contains(neighborCount)) {
			return StateChange.SPAWN;
		} else if (survives.contains(neighborCount)) {
			return StateChange.STAY;
		} else {
			return StateChange.DIE;
		}
	}

}
