package main.java;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neural {
	private List<List<Neuron>> network;
	private List<Neuron> inputs; // neuronios que tem apenas a saida
	
	// as entradas sao o numero de neuronios para cada camada
	// inputs eh um vetor com as entradas
	public Neural(int num_inputs, int num_hidden, int num_outputs) {
		this.network = new ArrayList<>();
		
		// camadas ocultas
		List<Neuron> layer = new ArrayList<>();
		for (int i =0; i<num_hidden; i++)
			layer.add(new Neuron(num_inputs, true));
		
		this.network.add(layer);
		
		// obs.: camada de saida tem um pedaco da camada oculta? 
		// obs.: nao seria melhor resetar a variavel layer
		layer = new ArrayList<>();
		// camada de saida
		System.out.println("Camada de saida, pesos iniciais");
		for (int i=0; i<num_outputs; i++) {
			layer.add(new Neuron(num_hidden, true));
			String p = "";
			for (Double peso:(new Neuron(num_hidden, true)).getWeights())
				p += " " + peso;
			System.out.println(p);
		}
		this.network.add(layer);
	}
	
	public void back_propagation(int epochs, Double learning_rate, Double[][] treino, Double[] targets) {
		double result_rate = 0;
		int iter = 1;
		// enquanto nao atingir uma taxa de acerto esperada ou nao atingir um numero de epocas maximo
		while(iter <= epochs) {
			System.out.println("\nEpoch: " + iter);
			
			// percorre a lista de exemplos de treino
			for (int e=0; e<treino.length; e++) {
				Double[] exemplo = treino[e];
				// reinicializa a camada de entrada da rede
				this.inputs = new ArrayList<>();
				for(int i=0; i<exemplo.length; i++) {
					Neuron entrada = new Neuron(exemplo[i]);
					this.inputs.add(entrada);
				}
				
				// percorre a rede atualizando a saida para a entrada atual
				forward_propagation();
				
				System.out.println("\n"+e);
				//Double erro_externo = 0;
				////////// atualizando (delta) erro na camada de saida //////////
				List<Neuron> output_layer = this.network.get(1);
				for (Neuron neuron:output_layer) {
					Double out = neuron.getOut();
					Double delta = out * (1.0-out)*(targets[e]-out);
					neuron.setDelta(delta);
					System.out.println("alvo: "+targets[e]);
					//System.out.println("erro: "+delta);
					System.out.println("erro: "+delta);
					System.out.println("saida: "+out);
					/////////// atualizando erro na camada interna ////////
					List<Neuron> hidden_layer = this.network.get(0);
					for (int h=0; h<hidden_layer.size(); h++) {
					//for (Neuron hidden:hidden_layer) {
						Neuron hidden = hidden_layer.get(h);
						Double out_h = hidden.getOut();
						//Double delta_h = out_h * (1.0-out_h)*(targets[j]-out);
						Double delta_h = out_h*(1.0-out_h)*neuron.getWeights().get(h) * delta;
						hidden.setDelta(hidden.getDelta() + delta_h);
					}
				}
				
				update(learning_rate);
				
			}
			iter += 1;
		}
		
		System.out.println("Fim");
	}
	
	/**
	 * metodo que atualiza todos os pesos da rede
	 * @param learning_rate
	 */
	private void update(Double learning_rate) {
		List<Neuron> input = this.inputs; // a saida que eh fornecida para a proxima camada

		// percorre as camadas oculta e de saida da rede
		//??????????????? OBS.: CONSERTAR UPDATE DA CAMADA INTERNA, ELA DEVE PEGAR A DA PRIMEIRA CAMADA ???????????
		for (int m=0; m<this.network.size(); m++) {
			List<Neuron> camada_atual = this.network.get(m); // camada atual
			
			if (m==1)
	        	input = this.network.get(m-1);
			//System.out.println(m);
			// percorre os neuronios da camada atual
			for (int i=0; i<camada_atual.size(); i++) {
				Neuron neuronio_atual = camada_atual.get(i); // neuronio a ter seu peso atualizado
				//System.out.println(i+": "+neuronio_atual);
				List<Double> pesos = new ArrayList();
				// percorre os neuronios da camada anterior, preferencialmente as saidas, ou analogamente os pesos atuais
				for (int j=0; j<neuronio_atual.getWeights().size()-1; j++) {
					Double peso_w = learning_rate * neuronio_atual.getDelta() * input.get(j).getOut();
					pesos.add(peso_w);
					//System.out.println(input.get(j).getOut());
				}
				pesos.add(learning_rate * neuronio_atual.getDelta()); // bias
				neuronio_atual.setWeights(pesos);
				//System.out.println(pesos.size());
			}
		}
	}
	
	// fase forward da rede neural, saida de cada neuronio atualizada
	private void forward_propagation() {
	    List<Neuron> input = this.inputs; // inicia com a camada de entrada
	    
		// percorre cada camada da rede
	    for (int l=0; l<this.network.size(); l++) {
	    	List<Neuron> layer = this.network.get(l);
	        
	        // se for a camada de saida, a entrada deve ser a camada escondida
	        if (l==1)
	        	input = this.network.get(l-1); 
	        
	        // percorre cada noh da camada
	        for (Neuron neuron : layer) {
	            Double activation = Neuron.activate(neuron, input);
	            //neuron.setOut(neuron.sigmoid(activation));
	            neuron.setOut(neuron.relu(activation));
	        }
	    }
	}
}
