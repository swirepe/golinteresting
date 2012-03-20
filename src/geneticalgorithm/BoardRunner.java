package geneticalgorithm;

import board.InfiniteBoard;

class BoardRunner implements Runnable {
	private InfiniteBoard board;
	private int numRuns;
	
	BoardRunner(InfiniteBoard board, int numRuns) {
		this.board = board;
		this.numRuns = numRuns;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < this.numRuns; i++) {
			this.board.runOnce();
			if (this.board.isImmobile()) {
				break;
			}
		}
	}
}
