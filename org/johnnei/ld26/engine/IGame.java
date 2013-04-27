package org.johnnei.ld26.engine;

public interface IGame {
	
	public void init();
	
	public void onTick(int deltaMs);
	
	public void render();

}
