package learning;

import java.util.ArrayList;

import printer.EncodedPrinter;
import rule.StandardRule;
import board.GameOfLifeBoard;
import board.GameOfLifeCellCounter;



public class LearningBoard extends GameOfLifeBoard {

	final int NUM_HIDDEN_NODES = 12;
	
	public LearningBoard(int height, int width) {
		super();
		this.width = width;
		this.height = height;
		this.iterations = 0;
		this.cells = new LearningCell[height][width];
		this.history = new ArrayList<Boolean[][]>();
		this.printer = new EncodedPrinter(this);
		this.rule = new StandardRule(this);
		this.counter = new GameOfLifeCellCounter(this);
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				this.cells[h][w] = new LearningCell(h,w, NUM_HIDDEN_NODES);
			}
		}
		
		setAllNeighbors();
		
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				((LearningCell)(this.cells[h][w])).initializeWeights();
			}
		}
	} // end of constructor
	
	
	/**
	 * Advance all of the cells in the board ONE time slice.
	 */
	@Override
	public void runOnce()  {
		if (keepHistory)
			history.add(getCopy());
		
		flagCells();
		
		predictAll();
		updateWeights();
		reportError();
		
		finalizeCells();
		
		this.iterations++;
	} // end of method runOnce


	protected void updateWeights() {
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				((LearningCell)cells[h][w]).updateWeights();
			}
		}

	}

	
	protected void predictAll() {
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				((LearningCell)cells[h][w]).predictState();
			}
		}

	}
	
	
	protected void setAllNeighbors(){
		ArrayList<LearningCell> neighbors;
		
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				neighbors = new ArrayList<LearningCell>();
				
				// we are try-catching because we don't want to walk off the end of the matrix
				try{ neighbors.add((LearningCell) cells[h-1][w]);      }catch(Exception muted){}
				try{ neighbors.add((LearningCell) cells[h-1][w-1]);   }catch(Exception muted){}
				try{ neighbors.add((LearningCell) cells[h-1][w+1]);   }catch(Exception muted){}
				
				try{ neighbors.add((LearningCell) cells[h][w-1]);     }catch(Exception muted){}
				try{ neighbors.add((LearningCell) cells[h][w+1]);     }catch(Exception muted){}
				
				try{ neighbors.add((LearningCell) cells[h+1][w]);     }catch(Exception muted){}
				try{ neighbors.add((LearningCell) cells[h+1][w-1]);   }catch(Exception muted){}
				try{ neighbors.add((LearningCell) cells[h+1][w+1]);   }catch(Exception muted){}
				
				((LearningCell)cells[h][w]).setNeighbors(neighbors);
			}
		}
	} // end of method setAllNeighbors
	
	
	protected void reportError() {
		double error = 0.0;
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				error += ((LearningCell)cells[h][w]).getError();
			}
		}

		System.out.println("Average per-cell error: " + (error / (this.height*this.width)));
		
	}
	
	
} // end of class LearningBoard
