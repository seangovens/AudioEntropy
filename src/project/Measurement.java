package project;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class Measurement {
	int[] intData;
	
	public Measurement(ByteArrayOutputStream in) {
		byte[] data = in.toByteArray();
		intData = new int[data.length / 32];
		
		for (int i = 0; i < data.length - (data.length % 32); i++) {
			intData[i / 32] = intData[i / 32] ^ ((data[i] & 1) << (i % 32));
		}
		
		/*for (int i = 0; i < data.length; i+=4) {
			int ind = i / 4;
			intData[ind] = intData[ind] ^ data[i];
			intData[ind] = intData[ind] ^ ((int) data[i+1] << 8);
			intData[ind] = intData[ind] ^ ((int) data[i+2] << 16);
			intData[ind] = intData[ind] ^ ((int) data[i+3] << 24);
		}*/
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
		System.out.printf("Max frequency symbol %s with frequency %d\n", Integer.toBinaryString(currMaxInt), freqs.get(currMaxInt));
		
		return res;
	}
}
