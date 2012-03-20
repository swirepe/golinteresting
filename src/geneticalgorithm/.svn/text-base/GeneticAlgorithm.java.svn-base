package geneticalgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import printer.SimpleAsciiPrinter;
import board.Board;
import board.Cell;
import board.Coordinate;
import board.InfiniteBoard;

public class GeneticAlgorithm {
	private int iteration = 0;
	private int keepBest = 5;
	
	private int numberOfRunsPerBoard = 1000;
	private int numBoards = 6;
	private int chooseBest = 3;
	
	private int height = 10;
	private int width = 10;
	
	private TreeSet<InfiniteBoard> bestBoards;
	
	private SortedSet<InfiniteBoard> boards = new TreeSet<InfiniteBoard>();
	
	public GeneticAlgorithm() {
		initializeBoards();
	}
	
	private void initializeBoards() {
		for (int i = 0; i < numBoards; i++) {
			InfiniteBoard board = new InfiniteBoard(height, width);
			//randomizeBoard(board);
			boards.add(board);
		}
	}
	
	public void runOnce() {
		// Run all of the boards, each on its own thread
		List<Thread> threads = new ArrayList<Thread>(this.boards.size());
		for (InfiniteBoard b : boards) {
			Thread t = new Thread(new BoardRunner(b, numberOfRunsPerBoard));
			t.run();
			threads.add(t);
		}
		// wait for all threads to complete
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		selectSplice();
		iteration++;
	}
	
	public void print() {
		for (InfiniteBoard b : boards) {
			SimpleAsciiPrinter p = new SimpleAsciiPrinter(b);
			p.printTimeslice(0);
			p.printHeuristic();
		}
	}
	
	public void print(String filename) {
		for (InfiniteBoard b : boards) {
			SimpleAsciiPrinter p = new SimpleAsciiPrinter(b);
			p.printTimeslice(0, filename);
			p.printHeuristic(filename);
		}
	}
	
	private void selectSplice() {
		//SELECT BEST HEURISTIC BOARDS

		//Trim off the worst boards.
		while (boards.size() > this.chooseBest) {
			boards.remove(boards.first());
		}
		
		// Add all boards to the "keep" list, then trim off the worst so we only
		// keep as many as the "keepBest" parameter.
		this.bestBoards.addAll(boards);
		while (bestBoards.size() > keepBest) {
			bestBoards.remove(bestBoards.first());
		}
		
		// REWIND BOARDS
		for (InfiniteBoard b : boards) {
			b.rewindBoard();
		}
		
		//SPLICE BOARDS
		spliceBoards(boards);
	}
	
	/*
	 * 
	 * Selected
	 * 
	 * 	A				A/B
	 * 	B		=>		A/C
	 * 	C				B/A
	 * 					B/C
	 * 					C/A
	 * 					C/B
	 * 
	 */
	public Collection<InfiniteBoard> spliceBoards(Collection<InfiniteBoard> selectedBoards) {
		Random rand = new Random();

		boards = new TreeSet<InfiniteBoard>();
		
		for (InfiniteBoard b1 : selectedBoards) {
			for (InfiniteBoard b2 : selectedBoards) {
				if (b1 != b2) {
					
					int direction = rand.nextInt(2);
					int boundary;
					if (direction == 0) { //Horizontal boundary
						boundary = rand.nextInt(height);
					} else { //Vertical boundary
						boundary = rand.nextInt(width);
					}
					
					
					InfiniteBoard newBoard = new InfiniteBoard(height, width);
					for (Cell cell : b1.getCellsMap().values()) {
						if (cell.isAlive()) {
							if (direction == 0) { //horizontal boundary
								if (cell.getCoord().getX() >= boundary) {
									newBoard.setAlive(cell.getCoord().getX(), cell.getCoord().getY(), true);
								}
							} else { //vertical boundary
								if (cell.getCoord().getY() >= boundary) {
									newBoard.setAlive(cell.getCoord().getX(), cell.getCoord().getY(), true);
								}
							}	
						}
					}
					
					for (Cell cell : b2.getCellsMap().values()) {
						if (cell.isAlive()) {
							if (direction == 0) { //horizontal boundary
								if (cell.getCoord().getX() < boundary) {
									newBoard.setAlive(cell.getCoord().getX(), cell.getCoord().getY(), true);
								}
							} else { //vertical boundary
								if (cell.getCoord().getY() < boundary) {
									newBoard.setAlive(cell.getCoord().getX(), cell.getCoord().getY(), true);
								}
							}	
						}
					}
					
					if (boards.size() < this.numBoards) {
						boards.add(newBoard);
					}
				}
			}
		}
		return boards;
		
	}
	

/*	public void randomizeBoard(Board board) {
		// TODO: Figure out a clever way to randomly initialize boards.
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
			   if(Math.random()>0.5)
			   {
				   board.setAlive(i, j, true);
			   }
			}		
		}
	}
*/
	public void randomizeBoard(Integer seed_num, Integer cell_num, Board board) {
		// TODO: Figure out a clever way to randomly initialize boards.
		if (seed_num > cell_num) {
			System.out.print("Seed# must be less than the Cell#!");
		} else {
			
			for (int i = 0; i < board.getHeight(); i++) {//Initialization the board
				for (int j = 0; j < board.getWidth(); j++) {
					board.setAlive(i, j, false);
				}				
			}
			
			ArrayList<Integer> rand_index = new ArrayList<Integer>();//List of Saving the 1 to Height*Width number which used to be chosed as random number
			ArrayList<Integer> seed_index = new ArrayList<Integer>();//List of Saving the Seed index
			for (int i = 0; i <= board.getHeight() * board.getWidth() - 1; i++) {//input the num from 1 to Height*Width
				rand_index.add(i);
			}
			int cur_num = 0;
			while (cur_num < seed_num) {//random choose #cur_num of seed_index
				int value = (int) (Math.random() * rand_index.size());
				seed_index.add(rand_index.get(value) + 1);
				++cur_num;
			}

			for (int i = 0; i < seed_index.size(); i++) {//transfer the seed_index to coordinate
				int temp_v = ((List<Integer>) seed_index).get(i);

				if (temp_v % board.getWidth() != 0) {
					int x_axis = (int) (temp_v / board.getWidth());
					int y_axis = temp_v % board.getWidth()-1;
					board.setAlive(x_axis, y_axis, true);
				} else {
					int x_axis = (int) (temp_v / board.getWidth()) - 1;
					int y_axis = board.getWidth() - 1;
					board.setAlive(x_axis, y_axis, true);
				}
			}
			
			int cur_cellnum=seed_num;
			
			while (cur_cellnum<cell_num) //
			{
				//System.out.println(cur_cellnum);
				ArrayList<Coordinate> cur_liveCollection=(ArrayList<Coordinate>) board.getLiveCells();
				int value = (int) (Math.random() * cur_liveCollection.size());
				Coordinate selected_cell=cur_liveCollection.get(value);
				//System.out.println("wow "+board.countLiveNeighbors(selected_cell.getX(), selected_cell.getY(), 1));
				if (board.countLiveNeighbors(selected_cell.getX(), selected_cell.getY(), 1)!=8) 
				{
					List<Coordinate> toCheck = new ArrayList<Coordinate>();
					List<Coordinate> deadneigbours = new ArrayList<Coordinate>();
					
					for (int i = (selected_cell.getX()-1); i <= (selected_cell.getX()+1); i++) 
					{
						for (int j = (selected_cell.getY()-1); j <= (selected_cell.getY()+1); j++) 
						{
							if (i<=board.getHeight() && i>=0 && j<=board.getWidth()&& j>=0) 
							{
					            toCheck.add(new Coordinate(i,j));	
							}
							else 
							{
								continue;
							}
							
						}
					}
					
					
					for (Coordinate c : toCheck) {
						if (board.getCellsMap().get(c) != null) 
						{
							if (board.getCellsMap().get(c).isAlive()==false) 
							{
								deadneigbours.add(c);								
							}
						}
					
					}
					
					if(deadneigbours.size()!=0)
					{
					int neigbour_index = (int) (Math.random() * deadneigbours.size());
					Coordinate selectedlive_cell=deadneigbours.get(neigbour_index);
					board.setAlive(selectedlive_cell.getX(), selectedlive_cell.getY(), true);
					
					++cur_cellnum;					
					}
				}
				
			}
			
		}
	}

	
}
