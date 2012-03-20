package learning;

import java.util.ArrayList;
import java.util.Random;

import board.Cell;


public class LearningCell extends Cell {

	final double LEARNING_RATE = 0.05;
	
	protected ArrayList<LearningCell> neighbors;
	protected int numHiddenNodes;
	protected boolean[] hiddenStates;
	protected double[] neighborHiddenWeights;
	protected double[] hiddenOutputWeights;
	protected boolean predictedState;
	private double[] hiddenDeltas; 
	
	
	public LearningCell(int x, int y, int numHiddenNodes) {
		super(x, y);
		this.numHiddenNodes = numHiddenNodes;
		this.hiddenStates = new boolean[numHiddenNodes];
		this.hiddenDeltas = new double[numHiddenNodes];

	} // end of constructor

	
	public void initializeWeights() {
		Random r = new Random();
		
		this.neighborHiddenWeights = new double[this.neighbors.size() * this.numHiddenNodes];
		this.hiddenOutputWeights = new double[this.numHiddenNodes];
			

		for(int h = 0; h < this.numHiddenNodes; h++ ){
			for(int n = 0; n < neighbors.size(); n++){
				this.neighborHiddenWeights[ (h*neighbors.size()) + n ] = r.nextGaussian();
			}
		}
		
		for (int i=0; i<this.hiddenOutputWeights.length; i++) {
			this.hiddenOutputWeights[i] = r.nextGaussian();
		}

	} // end of method initialize weights

	
	public void predictState(){
		// activate the hidden nodes from the neighbors
		for(int h=0; h< this.numHiddenNodes; h++){
			double total = 0.0;
			for(int n = 0; n < this.neighbors.size(); n++){
				if(this.neighbors.get(n).alive){
					total += this.neighborHiddenWeights[(h*this.neighbors.size()) + n];
				}
			}
			total = logsig(total);
			this.hiddenStates[h] = (total > 0.5);
		}
		
		// activate the output node from the hidden nodes
		double total = 0.0;
		for(int i = 0; i < this.hiddenOutputWeights.length; i++){
			// there is only one output node, so the weight array is the same length as the node array 
			if(this.hiddenStates[i]){
				total += this.hiddenOutputWeights[i];
			}
		}
		
		total = logsig(total);
		this.predictedState = (total) > 0.5;

	} // end of method predictState
	
	
	
	protected boolean getNextActualState(){
		switch(this.change){
			case SPAWN:
				return true;
			case DIE:
				return false;
			case STAY:
				return this.alive;
		}
		return false;
	}
	

	public void updateWeights(){
		// if we are right, there is nothing to do
		boolean nextActual = getNextActualState();
		if(this.predictedState == nextActual){
			return;
		} else{
			// Backpropagation:
			// delta refers to the change in weights.
			// It is the error times the derivative of it's current state
			double outputDelta = ((nextActual? 1 : 0) - (predictedState ? 1.0 : 0.0)) * logsigDeriv(predictedState);
			
			// update the weights between the visible node and the hidden layer
			for(int h = 0; h < this.numHiddenNodes; h++){
				if(this.hiddenStates[h]){
					this.hiddenDeltas[h] = outputDelta * this.hiddenOutputWeights[h] * logsigDeriv(this.hiddenStates[h]);
					this.hiddenOutputWeights[h] += outputDelta * LEARNING_RATE;
				}
			}
			
			// update the weights between the hidden layer and the neighbor nodes
			int arraypos;
			for(int h = 0; h < this.numHiddenNodes; h++){
				for(int n = 0; n < this.neighbors.size(); n++){
					if(this.neighbors.get(n).alive){
						arraypos = (this.neighbors.size() * h) + n;
						this.neighborHiddenWeights[arraypos] += hiddenDeltas[h] * LEARNING_RATE;
					}
				}
			}
			
		}
	} // end of method updateWeights
	
	
	
	protected double logsig(double x){
		if(x > 50){
			return 0.99999;  // effectively 1
		}else if(x < -50){
			return 0.00001;  // effectively zero
		}
		
		return 1.0 / (1 + Math.pow(2.718281828, -1*x));
	} 
	
	
	public double getError(){
		if (this.predictedState == getNextActualState()) {
			return 0.0;
		} else {
			return 1.0;
		}
	}
	
	
	protected double logsigDeriv(double x){
		double v = logsig(x);
		return v*(1-v);
	}
	
	protected double logsigDeriv(boolean v){
		double x = v? 1.0: 0.0;
		return logsigDeriv(x);
		
	}
	
	
	
	
	public ArrayList<LearningCell> getNeighbors() {
		return neighbors;
	}

	@SuppressWarnings("unchecked")
	public void setNeighbors(ArrayList<LearningCell> neighbors) {
		this.neighbors = (ArrayList<LearningCell>)neighbors.clone();
	}


	
	public String toString() {
		return "Coordinate: ("+this.coord.getX()+","+coord.getY()+") Alive: "+alive;
	}
	
} // end of class LearningCell
