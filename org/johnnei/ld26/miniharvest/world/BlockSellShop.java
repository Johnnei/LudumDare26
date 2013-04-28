package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.TextRender;
import org.johnnei.ld26.engine.render.Texture;
import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.shop.SellShop;

public class BlockSellShop extends Block {
	
	private SellShop shop;
	
	public BlockSellShop(Map map, int x, int y) {
		super(map, x, y);
		this.shop = new SellShop();
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		Texture texture = new Texture("/res/world/shopBlock.png");
		texture.addSubTexture(0, 0, 16, 16);
		renderObject.setTexture(texture);
		renderObject.updateTexture();
	}

	@Override
	public void onTick(int deltaMs) {
	}
	
	@Override
	public void onPlayerInteraction(Player player, Item item) {
		shop.sellItem(player, item);
	}
	
	@Override
	public void renderOnPlayerStandOn(Player player) {
		Item item = player.getSelectedItem();
		if(item != null) {
			shop.renderShopTileText(item);
		} else {
			TextRender.getInstance().drawCentered(400, 540, "Equip an item to sell", null);
		}
	}
	

}
