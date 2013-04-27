package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;

public class BlockFarmDirt extends Block {
	
	private final long DECAY_TIME = 60000;
	
	private int lifetime;
	
	public BlockFarmDirt(Map map, int x, int y) {
		super(map, x, y);
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		renderObject.setTexture("/res/world/farmdirt.png");
		renderObject.updateTexture();
	}
	

	@Override
	public void onTick(int deltaMs) {
		lifetime += deltaMs;
		if(lifetime >= DECAY_TIME) {
			map.setBlock(x, y, new BlockDirt(map, x, y));
			renderObject.delete();
		}
	}

}
