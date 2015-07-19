package com.mixit.handlers;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

public class VolumeHandler{

	Mixer.Info mixerInfo;
	Mixer mixer;
	Line.Info[] lineinfos;
	Line.Info lineinfo;
	Line line;
	FloatControl control;

	public VolumeHandler() {
		loadMixerDetails();
	}
	
	public void loadMixerDetails(){
		javax.sound.sampled.Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		for(int i=0;i<mixers.length;i++){
			mixerInfo = mixers[i];
			mixer = AudioSystem.getMixer(mixerInfo);
			if(mixerInfo.getName().equals("Port Speakers (Realtek High Definiti")){				
				lineinfos = mixer.getTargetLineInfo();
				lineinfo = lineinfos[0];
				try{
					line = mixer.getLine(lineinfo);
					line.open();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void adjustVolume(float target){
		javax.sound.sampled.Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		for(int i=0;i<mixers.length;i++){
			Mixer.Info mixerInfo = mixers[i];
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			if(mixerInfo.getName().equals("Port Speakers (Realtek High Definiti")){				
				Line.Info[] lineinfos = mixer.getTargetLineInfo();
				Line.Info lineinfo = lineinfos[0];
				try {
					Line line = mixer.getLine(lineinfo);
					line.open();
					if(line.isControlSupported(FloatControl.Type.VOLUME)){
						FloatControl control = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
						System.out.println("Volume:"+control.getValue());   
						// if you want to set the value for the volume 0.5 will be 50%
						// 0.0 being 0%
						// 1.0 being 100%
						control.setValue(target);
					}
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
	}


}