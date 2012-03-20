package printer;

import graphics.BoardView;

import javax.swing.JFrame;

import board.Board;


public class GraphicalPrinter implements Printer {

	private Board board;
	
	public GraphicalPrinter(Board board) {
		this.board = board;
	}
	
	@Override
	public void print() {
		JFrame frame = new JFrame("View Board");
		frame.add(new BoardView(board, false));
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void print(String filename) {
		System.out.println("Sorry, but you cannot print graphically to a file...");
	}

}
