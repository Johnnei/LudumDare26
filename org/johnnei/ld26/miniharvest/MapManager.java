package org.johnnei.ld26.miniharvest;

import java.util.HashMap;

public class MapManager {
	
	private HashMap<String, Map> mapList;
	
	public MapManager() {
		mapList = new HashMap<>();
		
		load("farm");
		load("town");
	}
	
	private void load(String mapName) {
		Map map = new Map();
		map.load(mapName);
		mapList.put(mapName, map);
	}

	public Map getMap(String mapName) {
		return mapList.get(mapName);
	}

}
