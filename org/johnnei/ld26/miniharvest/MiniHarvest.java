package org.johnnei.ld26.miniharvest;

import org.johnnei.ld26.engine.GameKeyboard;
import org.johnnei.ld26.engine.IGame;
import org.johnnei.ld26.engine.sound.SoundManager;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.lwjgl.input.Keyboard;

public class MiniHarvest implements IGame {
	
	public static final String SOUND_PLANT_SEED = "plant_seed";
	public static final String SOUND_ENTITY_PICKUP = "entity_pickup";
	public static final String SOUND_SHOP_SELL = "shop_sell";
	public static final String SOUND_SHOP_BUY = "shop_buy";
	public static final String SOUND_ITEM_HOE = "use_hoe";
	public static final String SOUND_ITEM_PLOW = "use_plow";
	
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
		//Load Sound
		SoundManager.getInstance().loadSound(SOUND_PLANT_SEED);
		SoundManager.getInstance().loadSound(SOUND_ENTITY_PICKUP);
		SoundManager.getInstance().loadSound(SOUND_SHOP_SELL);
		SoundManager.getInstance().loadSound(SOUND_SHOP_BUY);
		SoundManager.getInstance().loadSound(SOUND_ITEM_HOE);
		SoundManager.getInstance().loadSound(SOUND_ITEM_PLOW);
	}

	@Override
	public void onTick(int deltaMs) {
		player.onTick(deltaMs);
		currentMap.onTick(deltaMs, player);
		currentMap.checkForExit(this, player);
		mapManager.onQuickTick(deltaMs);
	}

	@Override
	public void render() {
		currentMap.render(player);
		player.render();
	}
	
	public void switchMap(String mapName) {
		currentMap = mapManager.getMap(mapName);
		player.setMap(currentMap);
	}
	
	public void cleanup() {
		player.onDelete();
		mapManager.cleanup();
	}

}
