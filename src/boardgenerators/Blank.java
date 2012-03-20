package boardgenerators;

import rule.Rule;
import rule.SeedsRule;
import board.GameOfLifeBoard;

public class Blank {
	public GameOfLifeBoard getBoard(int x, int y) {
		GameOfLifeBoard board = new GameOfLifeBoard(x,y);
		Rule std = new SeedsRule(board);
		board.setRule(std);
		return board;
	}
}
