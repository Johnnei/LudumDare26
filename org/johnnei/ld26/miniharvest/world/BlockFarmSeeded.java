package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.Texture;
import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.item.ItemPickupEntity;
import org.johnnei.ld26.miniharvest.item.Seed;

public class BlockFarmSeeded extends Block {
	
	private int lifetime;
	private int textureIndex;
	private Seed seed;
	
	public BlockFarmSeeded(Seed seed, Map map, int x, int y) {
		super(map, x, y);
		this.seed = seed;
		Texture texture = new Texture("/res/world/farmseeded.png");
		texture.addSubTexture(0, 0, 16, 16);
		texture.addSubTexture(16, 0, 16, 16);
		texture.addSubTexture(32, 0, 16, 16);
		renderObject.setTexture(texture);
		renderObject.resetBuffers();
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		renderObject.updateTexture();
	}
	

	@Override
	public void onTick(int deltaMs) {
		lifetime += deltaMs;
		
		float progress = ((float)lifetime / seed.getGrowDuration()) * 100F;
		if(progress > 66f) {
			textureIndex = 2;
		} else if(progress > 33f) {
			textureIndex = 1;
		} else {
			textureIndex = 0;
		}
		
		if(lifetime >= seed.getGrowDuration()) {
			//Destroy this block for use
			map.setBlock(x, y, new BlockDirt(map, x, y));
			renderObject.delete();
			//Drop items
			int dropCount = seed.getDropCount();
			for(int i = 0; i < dropCount; i++) {
				map.addPickupEntity(new ItemPickupEntity(seed.getGrowProduct(), map, x * 16, y * 16));
			}
		}
	}
	
	@Override
	public void render() {
		final int offset = (2 * 4 * 4) + (textureIndex * 2 * 4 * 4);
		renderObject.render(offset);
	}

}
