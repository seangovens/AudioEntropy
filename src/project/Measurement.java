package project;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Measurement {
	int[] intData;
	int numRuns = 0, num0 = 0, num1 = 0;
	HashMap<Integer, Integer> freqs = new HashMap<>();
	
	public Measurement(ByteArrayOutputStream in) {
		byte[] data = in.toByteArray();
		intData = new int[data.length / 32];
		
		byte lastSym = 0;
		for (int i = 0; i < data.length - (data.length % 32); i++) {
			byte theBit = (byte) (data[i] & 1);
			intData[i / 32] = intData[i / 32] ^ (theBit << (i % 32));
			if (i > 0 && lastSym != theBit) numRuns++;
			lastSym = theBit;
			if (theBit == 1) num1++;
			else num0++;
		}
		
		calcFreqs(freqs);
		
		/*for (int i = 0; i < data.length; i+=4) {
			int ind = i / 4;
			intData[ind] = intData[ind] ^ data[i];
			intData[ind] = intData[ind] ^ ((int) data[i+1] << 8);
			intData[ind] = intData[ind] ^ ((int) data[i+2] << 16);
			intData[ind] = intData[ind] ^ ((int) data[i+3] << 24);
		}*/
	}
	
	int currMaxFreq;
	
	public void calcFreqs(HashMap<Integer, Integer> freqs) {
		int currMaxInt = 0; currMaxFreq = 0;
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
	}
	
	public double getNaiveMinEntropy() {
		double res = 0.0;
		res = -Math.log10((double) currMaxFreq / intData.length) / Math.log10(2.0);
		//System.out.printf("Max frequency symbol %s with frequency %d\n", Integer.toBinaryString(currMaxInt), freqs.get(currMaxInt));
		
		return res;
	}
	
	public double getShannonEntropy() {
		double theShannon = 0.0;
		for (Map.Entry<Integer, Integer> e : freqs.entrySet()) {
			double prob = (double) e.getValue() / intData.length;
			theShannon += -prob * (Math.log10(prob) / Math.log10(2.0));
		}
		
		return theShannon;
	}
	
	public double getRunsData() {
		double eRuns = ((2.0 * num0 * num1) / ((double) num0 + num1)) + 1.0;
		double var = (2.0 * num0 * num1 * ((2.0 * num0 * num1) - num0 - num1));
		var /= (double) Math.pow(num1 + num0, 2) * (num0 + num1 - 1.0);
		double sDev = Math.sqrt(var);
		
		return Math.abs((numRuns - eRuns) / sDev); 
	}
	
	public double getFreqData() {
		double zeroPer = (double) num0 / (num0 + num1);
		double onePer = (double) num1 / (num0 + num1);
		
		return Math.abs(zeroPer - onePer);
	}
}
