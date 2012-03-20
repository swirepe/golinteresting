package printer;


import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import board.Coordinate;
import board.GameOfLifeBoard;


public class EncodedPrinter implements Printer {
	
	private GameOfLifeBoard board;
	
	public EncodedPrinter(GameOfLifeBoard board) {
		this.board = board;
	}

	@Override
	public void print() {
		System.out.println(board.getHeight() + " " + board.getWidth());
		for (Coordinate c : board.getLiveCells()) {
			System.out.println(c.getX() + " " + c.getY());
		}
	}

	@Override
	public void print(String filename) {
		try {
			Writer out = new OutputStreamWriter(new FileOutputStream(filename));
			out.write(board.getHeight() + " " + board.getWidth() + "\n");
			for (Coordinate c : board.getLiveCells()) {
				out.write(c.getX() + " " + c.getY() + "\n");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
