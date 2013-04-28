package org.johnnei.ld26.miniharvest.shop;

import org.johnnei.ld26.engine.render.TextRender;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.item.ItemGoldCoin;

public class SellShop {

	private int[] sellValues;
	
	public SellShop() {
		sellValues = new int[] {
				-1, //Gold Coin
				-1, //Hoe
				-1, //Wheat Seed
				75, //Wheat
				-1, //Potato Seed
				90 //Potato
		};
	}
	
	public void sellItem(Player player, Item item) {
		int value = sellValues[item.getId()];
		if(value > 0) {
			player.addAmountToItem(item.getId(), -1);
			player.addAmountToItem(ItemGoldCoin.ID, value);
		}
	}
	
	public void renderShopTileText(Item item) {
		if(sellValues[item.getId()] > 0) {
			TextRender.getInstance().drawCentered(400, 540, "Press Spacebar to sell 1 " + item.getName() + " for " + sellValues[item.getId()] + " Gold Coins", null);
		} else {
			TextRender.getInstance().drawCentered(400, 540, "You can't sell that to us", null);
		}
	}
}
