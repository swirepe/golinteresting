package boardgenerators;

import rule.Rule;
import rule.StandardRule;
import board.InfiniteBoard;

public class Glider implements BoardFactory{
	
	public Glider() {}
	
	public InfiniteBoard getBoard(int x, int y) {
		InfiniteBoard board = new InfiniteBoard(x,y);
		Rule std = new StandardRule(board);
		board.setRule(std);
		
		board.setAlive(1,2,true);
		board.setAlive(2,3,true);
		board.setAlive(3,1,true);
		board.setAlive(3,2,true);
		board.setAlive(3,3,true);
		
		return board;
	}

}
