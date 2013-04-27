package org.johnnei.ld26.miniharvest.shop;

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

}
