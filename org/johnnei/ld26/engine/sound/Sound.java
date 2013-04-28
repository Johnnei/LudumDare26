package org.johnnei.ld26.engine.sound;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSource3f;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcei;

import java.io.BufferedInputStream;

import org.lwjgl.util.WaveData;

public class Sound {
	
	private int buffer;
	private int source;
	
	public Sound(String filename) {
		WaveData data = WaveData.create(new BufferedInputStream(Sound.class.getResourceAsStream("/res/sound/" + filename + ".wav")));
		buffer = alGenBuffers();
		alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();
		
		//Prepare Source
		source = alGenSources();
		alSourcei(source, AL_BUFFER, buffer);
		alSource3f(source, AL_POSITION, 0f, 0f, 0f);
		alSource3f(source, AL_VELOCITY, 0f, 0f, 0f);
	}
	
	public void play() {
		alSourcePlay(source);
	}
	
	public void stop() {
		alSourceStop(source);
	}
	
	public void delete() {
		alDeleteSources(source);
		alDeleteBuffers(buffer);
	}

}
