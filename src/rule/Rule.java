package rule;

import board.StateChange;

public interface Rule {
	
	public abstract StateChange nextState(int x, int y);
	
}
