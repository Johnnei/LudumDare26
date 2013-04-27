package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.item.ItemHoe;

public class BlockDirt extends Block {
	
	public BlockDirt(Map map, int x, int y) {
		super(map, x, y);
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		renderObject.setTexture("/res/world/dirt.png");
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
