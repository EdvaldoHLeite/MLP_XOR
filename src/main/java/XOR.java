package main.java;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class XOR {
	public List<List<Double>> input;
	public List<Double> targets;
	
	public static void main(String[] args) {
		// neuronios da rede 3-3-3
		XOR xor = new XOR();		
		Double [][] data = {{0., 0.},
						{0., 1.},
						{1., 0.},
						{1., 1.}};
		Double[] target = {0.1, 0.9, 0.9, 0.1};
		
		Neural rede = new Neural(2, 2, 1);
		rede.back_propagation(10, 0.01, data, target);
	}

}
