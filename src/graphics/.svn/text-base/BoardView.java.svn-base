package graphics;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import board.Board;
import board.Coordinate;
import board.GameOfLifeBoard;


@SuppressWarnings("serial")
public class BoardView extends JPanel {

	int height;
	int width;
	Board board;
	JPanel[][] cells;
	int iterations;
	
	
	public BoardView(final Board genericBoard, boolean interactive) {
		this.iterations = 0;
		this.board = genericBoard;
		this.height = board.getHeight();
		this.width = board.getWidth();
		GridLayout layout = new GridLayout(height, width);
		this.setLayout(layout);
		this.cells = new JPanel[height][width];
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				final int I = i;
				final int J = j;
				cells[I][J] = new JPanel();
				cells[i][j].setBorder(new LineBorder(Color.GRAY));
				if (interactive) {
					cells[I][J].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent arg0) {
							if (cells[I][J].getBackground() != Color.BLUE) { 
								cells[I][J].setBackground(Color.BLUE);
								board.setAlive(I, J, true);
							} else {
								cells[I][J].setBackground(Color.WHITE);
								board.setAlive(I, J, false);
							}
						}
						
					});
				}
				this.add(cells[i][j]);
			}
		}
		initialize();
		setCells();
		this.setVisible(true);
	}
	
	public void initialize() {
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				cells[i][j].setBackground(Color.WHITE);
			}
		}
	}
	
	public void setCells() {
		Collection<Coordinate> coords = board.getLiveCells();
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				if (Coordinate.coordContained(i, j, coords)) {
					if (cells[i][j].getBackground() == Color.WHITE
							|| cells[i][j].getBackground() == Color.YELLOW) { 
						cells[i][j].setBackground(Color.BLUE);
					}
				} else {
					if (cells[i][j].getBackground() == Color.BLUE){ 
						cells[i][j].setBackground(Color.YELLOW);	
					}
				}
			}
		}
		this.iterations = board.getIterations();
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void runGraphical(GameOfLifeBoard board, int iterations) {
		try {
			JFrame frame = new JFrame();
			BoardView view = new BoardView(board, false);
			frame.add(view);
			frame.pack();
			frame.setVisible(true);
			view.setCells();
			Thread.sleep(2000);

			for (int i=0; i<iterations; i++) {
				board.runOnce();
				view.setCells();
				Thread.sleep(80);
			}
			view.setVisible(false);
		} catch (Exception e) {}
	}

}
