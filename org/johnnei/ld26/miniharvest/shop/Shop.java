package org.johnnei.ld26.miniharvest.shop;

import org.johnnei.ld26.engine.render.TextRender;
import org.johnnei.ld26.miniharvest.entity.Player;

public class Shop {
	
	private ShopItem shopStock;
	private int stockRechargeRate;
	private int lastRestockAge;
	
	public void setShop(ShopItem shopStock, int rechargeRate) {
		this.shopStock = shopStock;
		this.stockRechargeRate = rechargeRate;
	}
	
	public void buyItem(Player player) {
		if(shopStock.hasStock()) {
			shopStock.buy(player);
		}
	}
	
	public void onTick(int deltaMs) {
		lastRestockAge += deltaMs;
		
		//Check Restocking
		if(lastRestockAge >= stockRechargeRate || (!shopStock.hasStock() && lastRestockAge >= (stockRechargeRate / 2))) {
			lastRestockAge = 0;
			shopStock.addToStock(1);
		}
	}
	
	public int getStockCount() {
		return shopStock.getStockCount();
	}
	
	public void renderShopTileText(Player player) {
		TextRender.getInstance().drawCentered(400, 530, "We've got " + getStockCount() + " " + shopStock.getName() + " at the price of " + shopStock.getCost() + " Gold Coins each.", null);
		if(shopStock.hasStock()) {
			if(player.getGoldCount() < shopStock.getCost()) {
				TextRender.getInstance().drawCentered(400, 540, "You don't have enough Gold Coins to afford the " + shopStock.getName(), null);
			} else {
				TextRender.getInstance().drawCentered(400, 540, "Press Spacebar to buy 1 " + shopStock.getName() + " for " + shopStock.getCost() + " Gold Coins", null);
			}
		} else {
			TextRender.getInstance().drawCentered(400, 540, "We're currently out of stock. Restocking will happen in " + ((stockRechargeRate - lastRestockAge) / 1000) + " seconds", null);
		}
	}

}
