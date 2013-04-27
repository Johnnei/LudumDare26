package org.johnnei.ld26.miniharvest.shop;

import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.ItemGoldCoin;
import org.johnnei.ld26.miniharvest.item.ItemStack;

public class ShopItem {
	
	private ItemStack stock;
	private int cost;
	
	public ShopItem(ItemStack stock, int cost) {
		this.stock = stock;
		this.cost = cost;
	}
	
	public void addToStock(int amount) {
		stock.addToAmount(amount);
	}
	
	public boolean hasStock() {
		return stock.getAmount() > 0;
	}
	
	public int getStockCount() {
		return stock.getAmount();
	}
	
	public void buy(Player p) {
		if(p.getGoldCount() >= cost) {
			p.addAmountToItem(ItemGoldCoin.ID, -cost);
			p.addItem(stock.getItem(), 1);
		}
	}
	

}
