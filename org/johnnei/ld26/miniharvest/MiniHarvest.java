package org.johnnei.ld26.miniharvest;

import org.johnnei.ld26.engine.GameKeyboard;
import org.johnnei.ld26.engine.IGame;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.lwjgl.input.Keyboard;

public class MiniHarvest implements IGame {
	
	private MapManager mapManager;
	private Map currentMap;
	private Player player;

	@Override
	public void init() {
		mapManager = new MapManager();
		currentMap = mapManager.getMap("farm");
		player = new Player(currentMap, 5, 5);
		GameKeyboard.getInstance().trackKey(Keyboard.KEY_SPACE);
		GameKeyboard.getInstance().trackKey(Keyboard.KEY_LEFT);
		GameKeyboard.getInstance().trackKey(Keyboard.KEY_RIGHT);
	}

	@Override
	public void onTick(int deltaMs) {
		player.onTick(deltaMs);
		currentMap.onTick(deltaMs, player);
		currentMap.checkForExit(this, player);
	}

	@Override
	public void render() {
		currentMap.render();
		player.render();
	}
	
	public void switchMap(String mapName) {
		currentMap = mapManager.getMap(mapName);
		player.setMap(currentMap);
	}

}
