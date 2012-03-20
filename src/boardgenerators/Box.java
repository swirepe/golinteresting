package boardgenerators;

import learning.LearningBoard;
import rule.Rule;
import rule.SeedsRule;
import board.InfiniteBoard;

public class Box {
	public InfiniteBoard getBoard(int x, int y) {
		InfiniteBoard board = new InfiniteBoard(x,y);
		
		board.setAlive(10,10,true);
		board.setAlive(10,11,true);
		board.setAlive(11,10,true);
		board.setAlive(11,11,true);
		
		return board;
	}
	
	public LearningBoard getLearningBoard(int x, int y) {
		LearningBoard board = new LearningBoard(x,y);
		Rule std = new SeedsRule(board);
		board.setRule(std);
		
		board.setAlive(10,10,true);
		board.setAlive(10,11,true);
		board.setAlive(11,10,true);
		board.setAlive(11,11,true);
		
		return board;
	}
}
