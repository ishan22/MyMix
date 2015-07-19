package com.mixit.main;

import java.io.IOException;

import com.mixit.converters.Converter;
import com.mixit.handlers.GestureHandler;
import com.mixit.handlers.SoundHandler;
import com.mixit.handlers.VolumeHandler;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.LockingPolicy;
import com.thalmic.myo.enums.StreamEmgType;


public class Coordinator {
	
	public static void main(String[] args) {
		Hub hub = new Hub("com.mixit.main.Coordinator");
		hub.setLockingPolicy(LockingPolicy.LOCKING_POLICY_STANDARD);

		Myo myo = hub.waitForMyo(10000);
		myo.setStreamEmg(StreamEmgType.STREAM_EMG_ENABLED);
		
		try {
			Converter.convert("C:/Users/Ishan/Downloads/Drake - Trophies (Full Song).mp3", 
					"C:/Users/Ishan/AngelHacks/drake.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}

		SoundHandler sound = new SoundHandler("C:/Users/Ishan/AngelHacks/drake.wav");
		VolumeHandler volume = new VolumeHandler();
		
		hub.addListener(new GestureHandler(sound, volume));
		
		
		while(true){
			hub.run(500);
		}
	}

}
