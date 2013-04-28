package org.johnnei.ld26.engine.sound;

import java.util.HashMap;
import java.util.Map.Entry;

public class SoundManager {
	
	private static SoundManager instance = new SoundManager();
	
	public static SoundManager getInstance() {
		return instance;
	}
	
	private HashMap<String, Sound> soundMap;
	
	private SoundManager() {
		soundMap = new HashMap<>();
	}
	
	public void loadSound(String filename) {
		Sound sound = new Sound(filename);
		soundMap.put(filename, sound);
	}
	
	public void playSound(String filename) {
		Sound sound = soundMap.get(filename);
		if(sound != null) {
			sound.play();
		}
	}
	
	public void cleanup() {
		for(Entry<String, Sound> entry : soundMap.entrySet()) {
			entry.getValue().delete();
		}
	}

}
