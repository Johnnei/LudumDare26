package org.johnnei.ld26.miniharvest.world;

import java.util.Random;

import org.johnnei.ld26.engine.render.Texture;
import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.item.ItemHoe;

public class BlockDirt extends Block {
	
	public BlockDirt(Random random, Map map, int x, int y) {
		super(map, x, y);
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		Texture texture = new Texture("/res/world/dirt.png");
		texture.addSubTexture(random.nextInt(2) * 16, 0, 16, 16);
		renderObject.setTexture(texture);
		renderObject.updateTexture();
	}
	

	@Override
	public void onTick(int deltaMs) {
	}
	
	@Override
	public void onPlayerInteraction(Player player, Item item) {
		if(item.getId() == ItemHoe.ID) {
			map.setBlock(x, y, new BlockFarmDirt(map, x, y));
			renderObject.delete();
		}
	}

}
