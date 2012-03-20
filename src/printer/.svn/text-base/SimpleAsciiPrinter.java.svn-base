package printer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import board.Board;
import board.Cell;
import board.Coordinate;
import board.InfiniteBoard;


public class SimpleAsciiPrinter implements Printer {
	
	InfiniteBoard board;
	
	public SimpleAsciiPrinter(Board board) {
		this.board = (InfiniteBoard)board;
	}
	
	public void print()  {
		try {
			Writer out = new OutputStreamWriter(System.out);
			print(out);
			// don't close the System.out stream!!!
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void print(String filename) {
		try {
			Writer out = new OutputStreamWriter(new FileOutputStream(filename, true));
			print(out);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void printTimeslice(int index) {
		try {
			Writer out = new OutputStreamWriter(System.out);
			printTimeslice(index, out);
			// don't close the System.out stream!!!
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void printTimeslice(int index, String filename) {
		try {
			Writer out = new OutputStreamWriter(new FileOutputStream(filename, true));
			printTimeslice(index, out);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void printHeuristic() {
		try{
			Writer out = new OutputStreamWriter(System.out);
			printHeuristic(out);
			// don't close the System.out stream!!!
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void printHeuristic(String filename) {
		try {
			Writer out = new OutputStreamWriter(new FileOutputStream(filename, true));
			printHeuristic(out);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void print(Writer out) throws IOException {
		printTimeslice(board.getIterations(), out);
	}
	
	private void printTimeslice(int index, Writer out) throws IOException {
		Map<Coordinate, Cell> slice;
		if (index == board.getIterations()) {
			slice = board.getCellsMap();
		} else {
			slice = board.getHistory().get(index);
		}
		
		for (int h = 0; h < board.getHeight(); h++) {
			for (int w = 0; w < board.getWidth(); w++) {
				Cell cell = slice.get(new Coordinate(h,w));
				if (cell != null && cell.isAlive()) {
					out.write("#");
				} else {
					out.write(".");
				}
				out.write(" ");
			}
			out.write("\n");
		}
		out.flush();
	}
	
	private void printHeuristic(Writer out) throws IOException {
		out.write("h = " + board.getHeuristicValue() + "\n");
		out.flush();
	}
}
