package com.alexander.danliden.delend.utils;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Soundfile {

	private Clip sound;
	private FloatControl fc;

	public Soundfile(String file) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass()
					.getResource(file));
			sound = AudioSystem.getClip();
			sound.open(ais);
		} catch (LineUnavailableException | IOException
				| UnsupportedAudioFileException e) {
			System.out.println(file);
			e.printStackTrace();
		}
	}

	public void start() {
		if(!sound.isRunning()){
			sound.stop();
			sound.setFramePosition(0);
			sound.start();
		}
		
	}

	public void stop() {
		sound.stop();
	}

	public void setLoopable(boolean loopable) {
		sound.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public boolean isActive() {
		return sound.isActive();
	}

	public void setGain(double gain) {
		fc = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
		gain/=100.0;
		fc.setValue((float) (Math.log(gain) / Math.log(10.0) * 20.0));
	}

	public void reset() {
		sound.stop();
		sound.setMicrosecondPosition(0);
	}

	public void setVolume(double volume) {
		fc = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
		fc.setValue((float) (Math.log(volume) / Math.log(10.0) * 20.0));
	}
}
