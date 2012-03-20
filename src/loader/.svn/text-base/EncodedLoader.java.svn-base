package loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import rule.StandardRule;
import board.Coordinate;
import board.GameOfLifeBoard;

/**
 * Loader for loading boards that were saved using the EncodedPrinter.print(filename) method.
 * @author pkehrer
 *
 */
public class EncodedLoader implements Loader {
	private int height;
	private int width;
	private List<Coordinate> liveCells;
	
	public EncodedLoader() {
		this.liveCells = new ArrayList<Coordinate>();
	}

	@Override
	public GameOfLifeBoard load(String filename) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = in.readLine();
			StringTokenizer tok = new StringTokenizer(line, " ");
			String token = tok.nextToken();
			check(token);
			this.height = Integer.parseInt(token);
			token = tok.nextToken();
			check(token);
			this.width = Integer.parseInt(token);
			
			int x,y;
			while ((line = in.readLine()) != null) {
				tok = new StringTokenizer(line, " ");
				token = tok.nextToken();
				check(token);
				x = Integer.parseInt(token);
				token = tok.nextToken();
				check(token);
				y = Integer.parseInt(token);
				this.liveCells.add(new Coordinate(x,y));
			}
			
			GameOfLifeBoard board = new GameOfLifeBoard(height, width);
			board.setRule(new StandardRule(board));
			for (Coordinate c : this.liveCells) {
				board.setAlive(c.getX(), c.getY(), true);
			}
			return board;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (BoardFormatException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	private void check(Object obj) throws BoardFormatException {
		if (obj == null) {
			throw new BoardFormatException();
		}
	}
	
	@SuppressWarnings("serial")
	public class BoardFormatException extends Exception {
		
	}

}
