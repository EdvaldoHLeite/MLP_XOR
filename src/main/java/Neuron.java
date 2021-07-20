package main.java;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {
	private List<Double> weights; // pesos, ultimo elemento eh o bias
	private Double out; // saida de ativacao
	private Double delta; // erro do neuronio
	
	public Neuron(Double out) {
		this.out = out;
	}
	
	/*
	 * Construtor que inicializa os pesos do neuronio
	 */
	public Neuron(int num_weights, boolean bias) {
		this.delta = 0.0;
		this.out = 0.0;
		this.weights = new ArrayList<>();
		
		Random r = new Random();
		for(int i=0; i< num_weights; i++)
			this.weights.add(Math.random());
		
		// Obs. pode ser -0.5 a 0.5
		if (bias)
			this.weights.add(1.0);
	}
	
	/*
	 * Calcula a ativacao de um neuronio
	 */
	public static Double activate(Neuron neuron, List<Neuron> inputs) {
		List<Double> weights = neuron.getWeights();
		
		Double activation = weights.get(weights.size()-1); //bias ja esta no somatorio
		for (int i = 0; i<weights.size()-1; i++) // bias nao ja esta somados
			activation += weights.get(i) * inputs.get(i).getOut();
		return activation;
	}
	
	/* 
	 * funcao de ativacao sigmoid
	 */
	public static Double sigmoid(Double activation) {
		return 1.0 / (1.0 + Math.exp(-1.0 * activation));
	}
	
	public static Double relu(Double activation) {
		if (activation > 0)
			return 1.0;
		return 0.0;
	}
	
	public Double getOut() {
		return this.out;
	}

	public List<Double> getWeights() {
		return this.weights;
	}

	public Double getDelta() {
		return delta;
	}

	public void setDelta(Double delta) {
		this.delta = delta;
	}

	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}

	public void setOut(Double out) {
		this.out = out;
	}
}
