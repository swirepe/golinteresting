package boardgenerators;

import board.Board;

public interface BoardFactory {
	public Board getBoard(int x, int y);
}
