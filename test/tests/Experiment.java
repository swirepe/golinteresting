package tests;

import geneticalgorithm.GeneticAlgorithm;

import org.junit.Test;

public class Experiment {
	
	private int iterations = 1000;

	@Test
	public void run() {
		GeneticAlgorithm ga = new GeneticAlgorithm();
		
		
		for (int i = 0; i < iterations; i++) {
			ga.print("iteration_0.txt");
			ga.runOnce();
			
			
			
		}
		
		
	}
	
}
