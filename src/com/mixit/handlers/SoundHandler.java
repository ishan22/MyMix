package com.mixit.handlers;

import java.io.File;
import java.io.IOException;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.AudioSample;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.unitgen.WhiteNoise;
import com.jsyn.util.AudioSampleLoader;
import com.jsyn.util.SampleLoader;

public class SoundHandler{
	
	public static final int MAX_PITCH = 3000;
	public static final int MIN_PITCH = 5;

	public final double A = 0.25;
	public final double F = 15.0;
	public final double P = 10;

	Synthesizer synth;
	SineOscillator sineOut;
	VariableRateDataReader audioPlayer;
	FilterStateVariable filter;
	LineOut lineOut; //assists in loading the sample
	AudioSample audioData; //stores multi-channel audio data (32-bit floats)
	File audio; //for testing purposes, remove later
	LinearRamp lag;
	RecordingHandler recorder;

	public static double accelerometerData = 1.0; //has to be evaluated every half second

	public SoundHandler(String path) {
		synth = JSyn.createSynthesizer();
		sineOut = new SineOscillator();
		filter = new FilterStateVariable();
		load(path);
		synth.add(filter);
		synth.start();
		recorder = new RecordingHandler(synth, audioPlayer);
	}

	public void load(String path) {
		audio = new File(path);
		try {
			audioData = SampleLoader.loadFloatSample(audio);
		} catch (IOException e) {
			e.printStackTrace();
		}

		synth.add(lineOut = new LineOut());
		synth.add(audioPlayer = new VariableRateStereoReader());
		synth.add(lag = new LinearRamp());
		synth.add(sineOut);
		sineOut.output.connect(0, lineOut.input, 0);
		audioPlayer.output.connect(0, lineOut.input, 0);
		
		//sineOut.output.connect(1, lineOut.input, 1); //second channel
		//audioPlayer.output.connect(1, lineOut.input, 1); //second channel

		lag.output.connect(sineOut.amplitude);
		lag.input.setup(0.0, 0.5, 1.0);
		lag.time.set(0.2);

		SampleLoader.setJavaSoundPreferred(false);

		audioPlayer.rate.set(audioData.getFrameRate());
		lineOut.start();
		
		if (audioData.getSustainBegin() < 0) {
			audioPlayer.dataQueue.queue(audioData);
		} 
		else {
			audioPlayer.dataQueue.queueOn(audioData);
			do {
				try {
					synth.sleepFor(1.0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (audioPlayer.dataQueue.hasMore());
			
			try {
				synth.sleepFor(0.5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}

	} 

	public void changeSpeed(double accelerometerValue) {
		System.out.println("Sound Speed: " + accelerometerValue * -500000);
		audioPlayer.rate.setMaximum(15);
		audioPlayer.rate.setMinimum(.5);
		audioPlayer.rate.set(accelerometerValue * -500000);
	}
	
	public void stopChange(double lastSpeed, double lastPitch){
		changeSpeed(lastSpeed);
		changePitch(lastPitch);
	}

	public void changePitch(double accelerometerValue){
		double prevAmp = audioPlayer.amplitude.get();
		double val = prevAmp + (accelerometerValue * F);
		System.out.println("Sound Pitch: " + prevAmp + (accelerometerValue * F));
		if(val > MAX_PITCH){
			val = MAX_PITCH;
		}
		else if(val < MIN_PITCH){
			val = MIN_PITCH;
		}
		audioPlayer.amplitude.set(val);
	}
	
	public void stop(){
		synth.stop();
		recorder.terminate();
	}
	
}
