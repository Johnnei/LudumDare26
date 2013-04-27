package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.item.Seed;

public class BlockFarmSeeded extends Block {
	
	private int lifetime;
	private Seed seed;
	
	public BlockFarmSeeded(Seed seed, Map map, int x, int y) {
		super(map, x, y);
		this.seed = seed;
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		renderObject.setTexture("/res/world/farmseeded.png");
		renderObject.updateTexture();
	}
	

	@Override
	public void onTick(int deltaMs) {
		lifetime += deltaMs;
		if(lifetime >= seed.getGrowDuration()) {
			map.setBlock(x, y, new BlockDirt(map, x, y));
			renderObject.delete();
		}
	}

}
