package org.johnnei.ld26.miniharvest;

import java.util.HashMap;
import java.util.Map.Entry;

import org.johnnei.ld26.engine.Game;

public class MapManager {
	
	private HashMap<String, Map> mapList;
	private String activeMap;
	
	public MapManager() {
		mapList = new HashMap<>();
		
		long startTime = Game.getCurrentMillis();
		load("farm");
		load("town");
		System.out.println("Load: " + (Game.getCurrentMillis() - startTime) + "ms");
	}
	
	private void load(String mapName) {
		Map map = new Map();
		map.load(mapName);
		mapList.put(mapName, map);
	}

	public Map getMap(String mapName) {
		activeMap = mapName;
		return mapList.get(mapName);
	}
	
	/**
	 * Updates all non-active map with a "quick" tick which only processes non-player actions
	 * @param deltaMs
	 */
	public void onQuickTick(int deltaMs) {
		for(Entry<String, Map> map : mapList.entrySet()) {
			if(!map.getKey().equals(activeMap)) {
				map.getValue().onQuickTick(deltaMs);
			}
		}
	}

	public void cleanup() {
		for(Entry<String, Map> map : mapList.entrySet()) {
			map.getValue().onDelete();
		}
	}

}
