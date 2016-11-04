package project;

import java.io.ByteArrayOutputStream;

public class Collector {
	public static void main(String[] args) {
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		AudioCapture audio = new AudioCapture(in);
		
		audio.start();
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		audio.stopListening();
		
		System.out.printf("Size of output: %d\nFirst 20 chars: %s\n", in.size(), in.toString().substring(0, 19));
	}
}