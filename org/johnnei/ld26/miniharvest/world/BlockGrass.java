package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.item.ItemPlow;

public class BlockGrass extends Block {
	
	public BlockGrass(Map map, int x, int y) {
		super(map, x, y);
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		renderObject.setTexture("/res/world/grass.png");
		renderObject.updateTexture();
	}
	

	@Override
	public void onTick(int deltaMs) {
	}
	
	@Override
	public void onPlayerInteraction(Player player, Item item) {
		if(map.canSpade() && item.getId() == ItemPlow.ID) {
			map.setBlock(x, y, new BlockDirt(map, x, y));
			onDelete();
		}
	}

}
