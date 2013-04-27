package org.johnnei.ld26.engine;

import org.lwjgl.input.Keyboard;


public class KeyboardKey {
	
	private int keyId;
	private boolean keyPressState;
	private boolean keyState;
	
	public KeyboardKey(int keyId) {
		this.keyId = keyId;
		keyPressState = false;
		keyState = Keyboard.isKeyDown(keyId);
	}
	
	/**
	 * Updates this key
	 */
	public void poll() {
		boolean newState = Keyboard.isKeyDown(keyId); 
		if(keyState) {
			if(!newState) {
				keyPressState = true;
			}
		}
		keyState = newState;
	}
	
	public boolean isPressed(boolean consumePress) {
		if(keyPressState) {
			if(consumePress) {
				keyPressState = false;
			}
			return true;
		} else
			return false;
	}
	
	public int getId() {
		return keyId;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof KeyboardKey) {
			KeyboardKey key = (KeyboardKey)o;
			return (key.keyId == keyId);
		} else 
			return false;
	}

}
