package com.mixit.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.util.WaveRecorder;

public class RecordingHandler {
	File waveFile;
	WaveRecorder recorder;
	boolean recording = false;
	
	public RecordingHandler(Synthesizer synth, VariableRateDataReader audioPlayer) {
		waveFile = new File("temp_recording.wav");
		try {
			recorder = new WaveRecorder(synth, waveFile);
		} catch (FileNotFoundException e) {	e.printStackTrace();}
	
		audioPlayer.output.connect(0, recorder.getInput(), 0);
		audioPlayer.output.connect(1, recorder.getInput(), 1);
		recorder.start();
		recording = true;
	}
	
	public void terminate() {
		recorder.stop();
		try {
			recorder.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
