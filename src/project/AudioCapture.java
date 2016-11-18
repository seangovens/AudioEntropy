package project;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.*;

public class AudioCapture extends Thread {
	final int SAMPLE_RATE = 44100;
	final int BITS_PER_SAMPLE = 16;
	final int CHANNELS = 2;
	final boolean SIGNED = true;
	final boolean BIG_ENDIAN = true;
	
	TargetDataLine mic;
	ByteArrayOutputStream data;
	boolean stopped = false;
	
	public AudioCapture(ByteArrayOutputStream d) {
		data = d;
		
		AudioFormat format = new AudioFormat(SAMPLE_RATE,
											BITS_PER_SAMPLE,
											CHANNELS,
											SIGNED,
											BIG_ENDIAN);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
		if (!AudioSystem.isLineSupported(info)) {
			System.err.println("ERROR: Specified data line not supported. Check your audio input device/settings.");
			System.exit(1);
		}
		
		try {
			mic = (TargetDataLine) AudioSystem.getLine(info);
			mic.open(format);
		}
		catch (LineUnavailableException e) {
			System.err.println("ERROR: Specified line unavailable.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void run() {
		super.run();
		
		int numRead = 0;
		byte[] d = new byte[mic.getBufferSize() / 5];
		
		mic.start();
		
		while (!stopped) {
			numRead = mic.read(d, 0, d.length);
			data.write(d, 0, numRead);
		}
		
		mic.stop();
	}
	
	public void stopListening() {
		stopped = true;
	}
}