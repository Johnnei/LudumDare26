package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.Texture;
import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.shop.Shop;

public class BlockShop extends Block {
	
	private Shop shop;
	
	public BlockShop(Map map, int x, int y, int textureIndex, Shop shop) {
		super(map, x, y);
		this.shop = shop;
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		Texture texture = new Texture("/res/world/shopBlock.png");
		texture.addSubTexture(textureIndex * 16, 0, 16, 16);
		renderObject.setTexture(texture);
		renderObject.updateTexture();
	}

	@Override
	public void onTick(int deltaMs) {
		shop.onTick(deltaMs);
	}
	
	@Override
	public void onPlayerInteraction(Player player, Item item) {
		shop.buyItem(player);
	}
	

}
