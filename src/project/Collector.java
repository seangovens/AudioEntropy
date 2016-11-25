package project;

import java.io.ByteArrayOutputStream;

public class Collector {
	static final int NUM_SEC = 120;
	
	public static void main(String[] args) {
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		AudioCapture audio = new AudioCapture(in);
		
		audio.start();
		for (int i = 0; i < NUM_SEC; i++) {
			System.out.printf("\rCapturing audio: %d...", NUM_SEC - i);
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		audio.stopListening();
		
		System.out.printf("\rSize of output: %d\n", in.size());
		
		Measurement m = new Measurement(in);
		System.out.printf("Naive min entropy: %.6f\n", m.getNaiveMinEntropy());
	}
}