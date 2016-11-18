package project;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class Measurement {
	int[] intData;
	
	public Measurement(ByteArrayOutputStream in) {
		byte[] data = in.toByteArray();
		intData = new int[data.length / 4];
		
		for (int i = 0; i < data.length; i+=4) {
			int ind = i / 4;
			intData[ind] = intData[ind] ^ data[i];
			intData[ind] = intData[ind] ^ (data[i+1] << 8);
			intData[ind] = intData[ind] ^ (data[i+2] << 16);
			intData[ind] = intData[ind] ^ (data[i+3] << 24);
		}
	}
	
	public double getNaiveMinEntropy() {
		double res = 0.0;
		int currMaxInt = 0; int currMaxFreq = 0;
		HashMap<Integer, Integer> freqs = new HashMap<>();
		
		for (int i = 0; i < intData.length; i++) {
			int currFreq = 0;
			if (!freqs.containsKey(intData[i]))
				currFreq = 1;
			else
				currFreq = freqs.get(intData[i]) + 1;
			
			if (currFreq > currMaxFreq) {
				currMaxFreq = currFreq;
				currMaxInt = intData[i];
			}
			
			freqs.put(intData[i], currFreq);
		}
		
		res = -Math.log10((double) currMaxFreq / intData.length) / Math.log10(2.0);
		System.out.printf("Max frequency symbol %d with frequency %d\n", currMaxInt, freqs.get(currMaxInt));
		
		return res;
	}
}
