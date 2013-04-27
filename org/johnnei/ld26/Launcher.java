package org.johnnei.ld26;

import org.johnnei.ld26.engine.Game;
import org.johnnei.ld26.miniharvest.MiniHarvest;

public class Launcher {
	
	public static void main(String[] args) {
		Game game = new Game(new MiniHarvest());
		game.run();
	}

}
